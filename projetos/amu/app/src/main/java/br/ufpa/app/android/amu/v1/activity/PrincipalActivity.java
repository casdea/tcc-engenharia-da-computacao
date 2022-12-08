package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.adapter.MedicamentoAdapter;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.helper.RecyclerItemClickListener;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoUsuario;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class PrincipalActivity extends AppCompatActivity implements GerenteServicosListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
    private TextView txvListaVazia;
    private TextToSpeech textoLido;

    private ActivityResultLauncher<Intent> detalheMedicamentoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        prepararLista();
                    }
                }
            });

    private ActivityResultLauncher<Intent> consultarAvisaActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        prepararLista();
                    }
                }
            });

    private ActivityResultLauncher<Intent> reconheceVozActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if (text == null || text.size() == 0) {
                            //txvStatusComando.setText("Texto de Voz inválido");
                            return;
                        }

                        if (App.comandoAtualVoz.equals(TipoFuncao.CHAMADA_TELA)) {
                            int idView = App.integracaoUsuario.findComando(text.get(0));

                            if (idView == -1) {
                                //txvStatusComando.setText("Comando não foi reconhecido");
                                return;
                            }

                            if (idView == R.id.btnConsultaMedicamento) {
                                Intent intent = new Intent();
                                intent.setClass(App.context, ConsultaAnvisaActivity.class);
                                App.context.startActivity(intent);
                            }
                        } else if (App.comandoAtualVoz.equals(TipoFuncao.CADASTRO_MEDICAMENTOS)) {
                           incluirMedicamento();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
        App.integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico(App.fontesConsulta);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(this);

        txvListaVazia = findViewById(R.id.txvListaVazia);
        txvListaVazia.setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButton).setOnClickListener(this);

        App.integracaoUsuario.bemVindoFuncao(TipoFuncao.PESQUISA_MEDICAMENTOS);

        prepararLista();
    }

    @Override
    public void onClick(View v) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            App.integracaoUsuario.instrucaoParaUsuario(R.raw.falecomando);
            ThreadUtil.esperar(ThreadUtil.QUATRO_SEGUNDOS);
            chamarItenteReconechimentoVoz();

        } else {
            if (v.getId() == R.id.floatingActionButton) {
                incluirMedicamento();
            }
        }
    }

    private void incluirMedicamento() {
        Intent intent = new Intent(PrincipalActivity.this, ConsultaAnvisaActivity.class);
        consultarAvisaActivityResultLauncher.launch(intent);
    }

    private void chamarItenteReconechimentoVoz() {
        App.integracaoUsuario.pararMensagem();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        try {
            reconheceVozActivityResultLauncher.launch(intent);
            //tvText.setText("");
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

            //Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_LONG).show();
            //e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPerfil:
                startActivity(new Intent(this, UsuarioActivity.class));
                break;
        }
        switch (item.getItemId()) {
            case R.id.menuSair:
                autenticacao.signOut();
                startActivity(new Intent(this, BemVindoActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepararLista() {
        GerenteServicos gerenteServicos = new GerenteServicos(PrincipalActivity.this);
        gerenteServicos.obterListaMedicamentosByUsuario(App.usuario.getIdUsuario());
    }


    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {
        if (numeroAcao == Constantes.ACAO_OBTER_LISTA_MEDICAMENTO_POR_USUARIO) {
            this.listaMedicamentos = (List<MedicamentoDTO>) lista;

            txvListaVazia.setVisibility(View.INVISIBLE);

            if (this.listaMedicamentos.size()==0) {

                if (App.integracaoUsuario.lerTexto())
                    App.integracaoUsuario.avisarListaVazia();
                else
                    txvListaVazia.setVisibility(View.VISIBLE);
            }

            MedicamentoAdapter medicamentoAdapter = new MedicamentoAdapter((List<MedicamentoDTO>) lista);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(medicamentoAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                    getApplicationContext(),
                    recyclerView,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            App.medicamentoDTO = (MedicamentoDTO) listaMedicamentos.get(position);
                            Intent intent = new Intent(PrincipalActivity.this, DetalheMedicamentoActivity.class);
                            detalheMedicamentoActivityResultLauncher.launch(intent);

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    }));
        }
    }

    @Override
    public void executarAcao(int numeroAcao, String[] parametros) {

    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {

    }
}
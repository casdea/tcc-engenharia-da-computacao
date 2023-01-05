package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

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

public class PrincipalActivity extends AppCompatActivity implements GerenteServicosListener, View.OnClickListener, View.OnTouchListener {

    RecyclerView recyclerView;
    final FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
    TextView txvListaVazia;
    TextView txvQtdeCadastrados;
    TextView txvQtdeNaoAdministrado;
    RecursoVozLifeCyCleObserver mRecursoVozObserver;

    final Handler timerHandler = new Handler();
    final Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            Log.i("Alarme de Medicamentos", "Verificando Alarme");

            verificarAlarme();

            timerHandler.postDelayed(this, 60000);
        }
    };

    final ActivityResultLauncher<Intent> detalheMedicamentoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        prepararLista();
                    }
                }
            });

    final ActivityResultLauncher<Intent> consultarAvisaActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        prepararLista();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        App.context = this;

        App.escutandoComando = false;

        App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
        App.integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico(App.fontesConsulta);
        App.listaAlarmes = new ArrayList<>();

        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
            getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(this);
        //recyclerView.setOnTouchListener(this);

        mRecursoVozObserver = new RecursoVozLifeCyCleObserver(getActivityResultRegistry(), PrincipalActivity.this);
        getLifecycle().addObserver(mRecursoVozObserver);

        txvListaVazia = findViewById(R.id.txvListaVazia);
        txvListaVazia.setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButton).setOnClickListener(this);
        findViewById(R.id.fundo).setOnClickListener(this);
        findViewById(R.id.cabecalho).setOnClickListener(this);

        txvQtdeCadastrados = findViewById(R.id.txvQtdeCadastrados);
        txvQtdeNaoAdministrado = findViewById(R.id.txvQtdeNaoAdministrado);

        App.integracaoUsuario.bemVindoFuncao(TipoFuncao.PESQUISA_MEDICAMENTOS);

        prepararLista();

        if (timerHandler != null && timerRunnable != null)
            timerHandler.postDelayed(timerRunnable, 0);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timerHandler != null && timerRunnable != null)
            timerHandler.removeCallbacks(timerRunnable);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.restaurarPerfil) {
            App.tipoPerfil = TipoPerfil.valueOf(App.usuario.getTipoPerfil());
            App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
            App.integracaoUsuario.avisoSaidaPerfilAdmin();

            if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                getSupportActionBar().hide();

        }
        if (item.getItemId() == R.id.menuPerfil)
            startActivity(new Intent(this, UsuarioActivity.class));
        else if (item.getItemId() == R.id.menuSair) {
            autenticacao.signOut();
            startActivity(new Intent(this, BemVindoActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void prepararLista() {
        GerenteServicos gerenteServicos = new GerenteServicos(PrincipalActivity.this);
        gerenteServicos.obterListaMedicamentosByUsuario(App.usuario.getIdUsuario());
    }

    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {
        if (numeroAcao == Constantes.ACAO_OBTER_LISTA_MEDICAMENTO_POR_USUARIO) {
            this.listaMedicamentos = (List<MedicamentoDTO>) lista;

            txvListaVazia.setVisibility(View.INVISIBLE);

            if (this.listaMedicamentos.size() == 0) {

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
                            if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
                                mRecursoVozObserver.chamarItenteReconechimentoVoz();
                            } else {
                                App.medicamentoDTO = (MedicamentoDTO) listaMedicamentos.get(position);
                                Intent intent = new Intent(PrincipalActivity.this, DetalheMedicamentoActivity.class);
                                detalheMedicamentoActivityResultLauncher.launch(intent);
                            }
                        }

                        @Override
                        public void onLongItemClick(int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    }));
        }
    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {
        if (numeroAcao == Constantes.ACAO_VOZ_LISTA_MEDICAMENTOS) {
            App.integracaoUsuario.listarMedicamentos(listaMedicamentos);
        } else if (numeroAcao == Constantes.ACAO_VOZ_DESCREVA_MEDICAMENTO) {
            App.medicamentoDTO = App.integracaoUsuario.descrerverMedicamento(listaMedicamentos, (String) parametro);
            ThreadUtil.esperar(ThreadUtil.QUATRO_SEGUNDOS);

            if (App.medicamentoDTO != null) {
                Intent intent = new Intent(PrincipalActivity.this, DetalheMedicamentoActivity.class);
                detalheMedicamentoActivityResultLauncher.launch(intent);
            }
        } else if (numeroAcao == Constantes.ACAO_VOZ_ALTERNAR_PERFIL) {
            App.integracaoUsuario.avisoEntradaPerfilAdmin();
            App.tipoPerfil = TipoPerfil.COMUM;
            App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);

            getSupportActionBar().show();
        } else if (numeroAcao == Constantes.ACAO_VOZ_FECHAR_APP) {
            finish();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
                    mRecursoVozObserver.chamarItenteReconechimentoVoz();
                }
                return true;
            case MotionEvent.ACTION_UP:
                view.performClick();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            AlertDialog alert_back = new AlertDialog.Builder(this).create();
            alert_back.setMessage("Deseja sair?");

            alert_back.setButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert_back.setButton2("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alert_back.show();
        }

    }

    public void verificarAlarme() {
        GerenteServicos gerenteServicos = new GerenteServicos(PrincipalActivity.this);
        gerenteServicos.verificarAlarme(this.listaMedicamentos, this.txvQtdeCadastrados, this.txvQtdeNaoAdministrado);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerHandler != null && timerRunnable != null)
            timerHandler.postDelayed(timerRunnable, 0);


    }
}
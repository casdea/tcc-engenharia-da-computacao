package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.dto.ConsultarMedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class ConsultaAnvisaActivity extends AppCompatActivity implements View.OnClickListener, GerenteServicosListener {

    MedicamentoRetDTO medicamentoRetDTO;
    TextToSpeech textoLido;
    RecursoVozLifeCyCleObserver mRecursoVozObserver;

    private final ActivityResultLauncher<Intent> detalheMedicamentoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                    }
                }
            });

    private final ActivityResultLauncher<Intent> consultarMedicamentoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        setResult(Activity.RESULT_OK, null);
                        finish();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_anvisa);

        Button btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(this);

        mRecursoVozObserver = new RecursoVozLifeCyCleObserver(getActivityResultRegistry(), ConsultaAnvisaActivity.this);
        getLifecycle().addObserver(mRecursoVozObserver);

        App.integracaoUsuario.bemVindoFuncao(TipoFuncao.CONSULTA_MEDICAMENTOS);

        textoLido = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textoLido.setLanguage(Locale.getDefault());
                }
            }
        });

        textoLido.setSpeechRate(0.75f);
    }

    @Override
    public void onClick(View v) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        } else {
            if (v.getId() == R.id.btnPesquisar) {
                EditText edtNomeComercial = (EditText) findViewById(R.id.edtNomeComercial);

                montarLista(edtNomeComercial.getText().toString());
            }
        }
    }

    public void montarLista(String argumento) {
        TextView txvStatusConsulta = (TextView) findViewById(R.id.txvStatusConsulta);
        txvStatusConsulta.setText("");

        ConsultarMedicamentoRetDTO consultarMedicamentoRetDTO = App.integracaoBularioEletronico.consultarDadosMedicamentos(this, argumento);

        if (!consultarMedicamentoRetDTO.isOperacaoExecutada()) {
            textoLido.speak("Consulta falhou", TextToSpeech.QUEUE_FLUSH, null);
            txvStatusConsulta.setText(consultarMedicamentoRetDTO.getMensagemExecucao());
        } else {
            App.integracaoUsuario.exibirMedicamentosEncontrados(textoLido, consultarMedicamentoRetDTO.getMedicamentos(), argumento);
        }

        ListView lvMedicamentos = (ListView) findViewById(R.id.lvMedicamentos);

        ConsultaMedicamentosAdapter adapter = new ConsultaMedicamentosAdapter(ConsultaAnvisaActivity.this, (ArrayList) consultarMedicamentoRetDTO.getMedicamentos());
        lvMedicamentos.setAdapter(adapter);
        lvMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicamentoRetDTO = consultarMedicamentoRetDTO.getMedicamentos().get(position);

                GerenteServicos gerenteServicos = new GerenteServicos(ConsultaAnvisaActivity.this);
                gerenteServicos.obterMedicamentoByUsuarioIdProduto(App.usuario.getIdUsuario(), medicamentoRetDTO.getIdProduto());
            }
        });
    }

    public class ConsultaMedicamentosAdapter extends ArrayAdapter<MedicamentoRetDTO> {
        public ConsultaMedicamentosAdapter(Context context, ArrayList<MedicamentoRetDTO> lista) {
            super(context, 0, lista);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final MedicamentoRetDTO medicamentosRetDTO = getItem(position);
            final ViewHolder holder;

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_medicamento, parent, false);

                holder = new ViewHolder();
                convertView.setTag(holder);

                holder.txvNomeComercial = (TextView) convertView.findViewById(R.id.txvNomeComercial);
                holder.txvNomeLaboratorio = (TextView) convertView.findViewById(R.id.txvNomeLaboratorio);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.txvNomeComercial.setText(medicamentosRetDTO.getNomeComercial());
            holder.txvNomeLaboratorio.setText(medicamentosRetDTO.getNomeLaboratorio());

            return convertView;
        }

        class ViewHolder {
            TextView txvNomeComercial;
            TextView txvNomeLaboratorio;
            int ref;
        }

    }

    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {
        if (numeroAcao == Constantes.ACAO_OBTER_MEDICAMENTO_POR_USUARIO_PRODUTO) {
            if (lista.size() == 0) {
                GerenteServicos gerenteServicos = new GerenteServicos(ConsultaAnvisaActivity.this);
                gerenteServicos.obterTextoBula(medicamentoRetDTO);
            } else {
                App.medicamentoDTO = (MedicamentoDTO) lista.get(0);
                Intent intent = new Intent(ConsultaAnvisaActivity.this, DetalheMedicamentoActivity.class);
                detalheMedicamentoActivityResultLauncher.launch(intent);
            }
        }
    }

    @Override
    public void executarAcao(int numeroAcao,  Object parametro) {
        if (numeroAcao == Constantes.ACAO_RECEBER_TEXTO_BULA) {
            Intent intent = new Intent(ConsultaAnvisaActivity.this, DetalheAnvisaActivity.class);
            consultarMedicamentoActivityResultLauncher.launch(intent);
        } else if (numeroAcao == Constantes.ACAO_FECHAR_TELA) {
            setResult(Activity.RESULT_OK, null);
            finish();
        }
        else if (numeroAcao == Constantes.ACAO_VOZ_MONTAR_LISTA_ANVISA) {
            montarLista((String) parametro);
        }
    }
}
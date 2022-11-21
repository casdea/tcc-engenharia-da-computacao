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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.dto.ConsultarMedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class ConsultaMedicamentoActivity extends AppCompatActivity implements View.OnClickListener {

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
                                intent.setClass(App.context, ConsultaMedicamentoActivity.class);
                                App.context.startActivity(intent);
                            }
                        } else if (App.comandoAtualVoz.equals(TipoFuncao.PESQUISA_MEDICAMENTOS)) {
                            montarLista(text.get(0));
                        }
                    }
                }
            });

    private TextToSpeech textoLido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_medicamento);

        Button btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(this);

        App.integracaoUsuario.bemVindoFuncao(TipoFuncao.CONSULTA_MEDICAMENTOS);

        textoLido = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i !=  TextToSpeech.ERROR) {
                    textoLido.setLanguage(Locale.getDefault());
                }
            }
        });

        textoLido.setSpeechRate(0.75f);
    }

    @Override
    public void onClick(View v) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            App.comandoAtualVoz = TipoFuncao.PESQUISA_MEDICAMENTOS;
            App.integracaoUsuario.instrucaoParaUsuario(R.raw.falenomecomercialmedicamento);
            ThreadUtil.esperar(ThreadUtil.QUATRO_SEGUNDOS);
            chamarItenteReconechimentoVoz();

        } else {
            if (v.getId() == R.id.btnPesquisar) {
                EditText edtNomeComercial = (EditText) findViewById(R.id.edtNomeComercial);

                montarLista(edtNomeComercial.getText().toString());
            }
        }
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

    public void montarLista(String argumento) {
        TextView txvStatusConsulta = (TextView) findViewById(R.id.txvStatusConsulta);
        txvStatusConsulta.setText("");

        ConsultarMedicamentoRetDTO consultarMedicamentoRetDTO = App.integracaoBularioEletronico.consultarDadosMedicamentos(this, argumento);

        if (consultarMedicamentoRetDTO.isOperacaoExecutada() == false) {
            textoLido.speak("Consulta falhou", TextToSpeech.QUEUE_FLUSH, null);
            txvStatusConsulta.setText(consultarMedicamentoRetDTO.getMensagemExecucao());
        }
        else {
            App.integracaoUsuario.exibirMedicamentosEncontrados(textoLido,consultarMedicamentoRetDTO.getMedicamentos(), argumento);
        }

        ListView lvMedicamentos = (ListView) findViewById(R.id.lvMedicamentos);

        ConsultaMedicamentosAdapter adapter = new ConsultaMedicamentosAdapter(ConsultaMedicamentoActivity.this, (ArrayList) consultarMedicamentoRetDTO.getMedicamentos());
        lvMedicamentos.setAdapter(adapter);
        lvMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicamentoRetDTO medicamentoRetDTO = consultarMedicamentoRetDTO.getMedicamentos().get(position);

                String bula = App.integracaoBularioEletronico.obterTextoBula(medicamentoRetDTO);

                Intent intent = new Intent();
                intent.putExtra("texto", bula);
                intent.setClass(ConsultaMedicamentoActivity.this, DetalheMedicamentoActivity.class);
                startActivity(intent);
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

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
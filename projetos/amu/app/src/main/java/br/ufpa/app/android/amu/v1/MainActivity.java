
package br.ufpa.app.android.amu.v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufpa.app.android.amu.v1.integracao.classes.FontesConsulta;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoUsuario;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.util.App;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSpeak;
    protected static final int RESULT_SPEECH = 1;
    private TextView txvStatusComando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.context = this;
        App.tipoPerfil = TipoPerfil.PCD_VISAO_REDUZIDA;
        App.fontesConsulta = FontesConsulta.ANVISA;

        App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
        App.integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico(App.fontesConsulta);

        Button btnCadastroMedicamento = (Button) findViewById(R.id.btnCadastroMedicamento);
        btnCadastroMedicamento.setOnClickListener(this);

        Button btnConsultaMedicamento = (Button) findViewById(R.id.btnConsultaMedicamento);
        btnConsultaMedicamento.setOnClickListener(this);

        txvStatusComando = (TextView) findViewById(R.id.txvStatusComando);

        btnSpeak = findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.integracaoUsuario.pararMensagem();

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    //tvText.setText("");
                } catch (ActivityNotFoundException e) {
                    String appPackageName = "com.google.android.googlequicksearchbox";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }

                    Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        App.integracaoUsuario.bemVindo();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCadastroMedicamento) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MedicamentoActivity.class);
            startActivity(intent);
        }
        else
        if (v.getId() == R.id.btnConsultaMedicamento) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ConsultaMedicamentoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (text == null || text.size()==0) {
                        txvStatusComando.setText("Texto de Voz inválido");
                        return;
                    }

                    int idView = App.integracaoUsuario.findComando(text.get(0));

                    if (idView == -1) {
                        txvStatusComando.setText("Comando não foi reconhecido");
                        return;
                    }

                    View view = findViewById(idView);
                    onClick(view);

                }
                break;
        }
    }

}
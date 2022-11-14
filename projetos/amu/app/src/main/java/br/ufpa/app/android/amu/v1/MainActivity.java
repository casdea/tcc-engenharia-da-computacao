
package br.ufpa.app.android.amu.v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.util.App;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSpeak;
    protected static final int RESULT_SPEECH = 1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.tipoPerfil = TipoPerfil.PCD_VISAO_REDUZIDA;

        Button btnCadastroMedicamento = (Button) findViewById(R.id.btnCadastroMedicamento);
        btnCadastroMedicamento.setOnClickListener(this);

        Button btnConsultaMedicamento = (Button) findViewById(R.id.btnConsultaMedicamento);
        btnConsultaMedicamento.setOnClickListener(this);

        btnSpeak = findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    //tvText.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.bemvindo);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.opcaomenuprincipal);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you

            }
        });
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
                    if (text.get(0).toUpperCase().contains("2") || text.get(0).toUpperCase().contains("DOIS") || text.get(0).toUpperCase().contains("CONSULTA DE MEDICAMENTO")) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, ConsultaMedicamentoActivity.class);
                        startActivity(intent);
                        //tvText.setText(text.get(0));
                    }
                }
                break;
        }
    }

}
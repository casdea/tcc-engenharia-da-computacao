package br.ufpa.app.android.amu.v1.integracao.api.usuario.pcd.visual;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;
import br.ufpa.app.android.amu.v1.util.App;

public class IntegracaoUsuarioVisaoReduzida implements IntegracaoUsuario {

    private MediaPlayer mediaPlayer;
    protected static final int RESULT_SPEECH = 1;

    private String[] NUMERO_UM = {
            "1",
            "HUM",
            "UM",
            "CADASTRO DE MEDICAMENTO"
    };

    private String[] NUMERO_DOIS = {
            "2",
            "DOIS",
            "CONSULTA DE MEDICAMENTO"
    };

    private String[] NUMERO_TRES = {
            "3",
            "TRES",
            "TRÊS",
            "HORÁRIO DE MEDICAMENTO",
            "HORÁRIOS DE MEDICAMENTO",
            "HORARIO DE MEDICAMENTO",
            "HORARIOS DE MEDICAMENTO"
    };

    private String[] NUMERO_QUATRO = {
            "4",
            "QUATRO",
            "USO DE MEDICAMENTO",
            "UZO DE MEDICAMENTO"
    };

    private String[] NUMERO_CINCO = {
            "5",
            "CINCO",
            "ESTOQUE DE MEDICAMENTO"
    };

    private String[] NUMERO_SEIS = {
            "6",
            "SEIS",
            "CEIS",
            "PERFIL DE USUARIO",
            "PERFIL DE USUÁRIO"
    };

    private boolean findTexto(String[] palavras, String palavra) {
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].equals(palavra.toUpperCase()) || palavra.toUpperCase().contains(palavras[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void bemVindo() {
        mediaPlayer = MediaPlayer.create(App.context, R.raw.bemvindo);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer = MediaPlayer.create(App.context, R.raw.opcaomenuprincipal);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you

            }
        });
    }

    @Override
    public int findComando(String texto) {
        if (findTexto(NUMERO_UM, texto)) {
            return R.id.btnCadastroMedicamento;
        } else if (findTexto(NUMERO_DOIS, texto)) {
            return R.id.btnConsultaMedicamento;
        } else if (findTexto(NUMERO_TRES, texto)) {
            return R.id.btnHorarioMedicamento;
        } else if (findTexto(NUMERO_QUATRO, texto)) {
            return R.id.btnUsoMedicamento;
        } else if (findTexto(NUMERO_CINCO, texto)) {
            return R.id.btnEstoqueMedicamento;
        } else if (findTexto(NUMERO_SEIS, texto)) {
            return R.id.btnPerfilUsuario;
        }

        return -1;
    }

    public void pararMensagem() {
        if (mediaPlayer == null) return;

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void bemVindoFuncao(TipoFuncao tipoFuncao) {
        int idBemVindo = 0;

        switch (tipoFuncao) {
            case CADASTRO_MEDICAMENTOS:
                idBemVindo = R.raw.cadastromedicamento;
                break;
            case CONSULTA_MEDICAMENTOS:
                idBemVindo = R.raw.consultamedicamento;
                break;
            case HORARIO_MEDICAMENTOS:
                idBemVindo = R.raw.horariomedicamento;
                break;
            case USO_MEDICAMENTOS:
                idBemVindo = R.raw.usomedicamento;
                break;
            case ESTOQUE_MEDICAMENTOS:
                idBemVindo = R.raw.estoquemedicamento;
                break;
            case PERFIL_USUARIO:
                idBemVindo = R.raw.perfilusuario;
                break;
            default:
                throw new IllegalStateException("Valor não esperado: " + tipoFuncao);
        }

        mediaPlayer = MediaPlayer.create(App.context, idBemVindo);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer = MediaPlayer.create(App.context, R.raw.opcaomenuprincipal);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you

            }
        });
    }

/*    public View.OnClickListener criarRecursoVoz() {
        App.integracaoUsuario.pararMensagem();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        try {
            App.context.startActivityForResult(intent, RESULT_SPEECH);
            //tvText.setText("");
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                App.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                App.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

            Toast.makeText(App.context.getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
*/
}

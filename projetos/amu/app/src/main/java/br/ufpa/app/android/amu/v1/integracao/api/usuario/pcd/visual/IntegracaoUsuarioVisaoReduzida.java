package br.ufpa.app.android.amu.v1.integracao.api.usuario.pcd.visual;

import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;
import br.ufpa.app.android.amu.v1.util.App;

public class IntegracaoUsuarioVisaoReduzida implements IntegracaoUsuario {

    private MediaPlayer mediaPlayer;
    private Comando comando;

    public IntegracaoUsuarioVisaoReduzida() {
    }

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
        int idBemVindoFuncao = 0;

        switch (tipoFuncao) {
            case CADASTRO_MEDICAMENTOS:
                idBemVindoFuncao = R.raw.cadastromedicamento;
                break;
            case CONSULTA_MEDICAMENTOS:
                idBemVindoFuncao = R.raw.telaconsultamedicamentoeinstrucao;
                break;
            case HORARIO_MEDICAMENTOS:
                idBemVindoFuncao = R.raw.horariomedicamento;
                break;
            case USO_MEDICAMENTOS:
                idBemVindoFuncao = R.raw.usomedicamento;
                break;
            case ESTOQUE_MEDICAMENTOS:
                idBemVindoFuncao = R.raw.estoquemedicamento;
                break;
            case PERFIL_USUARIO:
                idBemVindoFuncao = R.raw.perfilusuario;
                break;
            default:
                throw new IllegalStateException("Valor não esperado: " + tipoFuncao);
        }

        mediaPlayer = MediaPlayer.create(App.context, idBemVindoFuncao);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // mediaPlayer = MediaPlayer.create(App.context, R.raw.opcaomenuprincipal);
                // mediaPlayer.start(); // no need to call prepare(); create() does that for you

            }
        });
    }

    @Override
    public void capturarComandoIniciado() {
        mediaPlayer = MediaPlayer.create(App.context, R.raw.falecomando);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    @Override
    public void capturarComandoEncerrado() {
        mediaPlayer = MediaPlayer.create(App.context, R.raw.capturaencerrada);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    @Override
    public void instrucaoParaUsuario(int idSom) {
        mediaPlayer = MediaPlayer.create(App.context, idSom);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    @Override
    public void exibirMedicamentosEncontrados(TextToSpeech textoLido, List<MedicamentoRetDTO> medicamentos, String argumento) {
        if (medicamentos.size() == 0) {
            textoLido.speak("Nenhum medicamento encontrado com o nome " + argumento, TextToSpeech.QUEUE_ADD, null);
            return;
        }

        textoLido.speak("Foram encontrados " +medicamentos.size()+" medicamentos com o argumento informado "+argumento, TextToSpeech.QUEUE_ADD, null);

        int item = 1;

        for (MedicamentoRetDTO medicamentoRetDTO : medicamentos)
        {
            textoLido.speak("Item " +item+" Nome: "+medicamentoRetDTO.getNomeComercial()+" Laboratorio: "+medicamentoRetDTO.getNomeLaboratorio(), TextToSpeech.QUEUE_ADD, null);
            item++;
        }

    }

}

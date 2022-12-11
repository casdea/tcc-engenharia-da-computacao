package br.ufpa.app.android.amu.v1.integracao.api.usuario.pcd.visual;

import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import java.util.List;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.ComandosVoz;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.StringUtil;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class IntegracaoUsuarioVisaoReduzida implements IntegracaoUsuario {

    private MediaPlayer mediaPlayer;
    private TextToSpeech textoLido;

    public IntegracaoUsuarioVisaoReduzida() {
        textoLido = new TextToSpeech(App.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textoLido.setLanguage(Locale.getDefault());
                }
            }
        });

        textoLido.setSpeechRate(0.75f);
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
            "HORARIO DE MEDICAMENTO",
            "HORARIOS DE MEDICAMENTO"
    };

    private String[] NUMERO_QUATRO = {
            "4",
            "QUATRO",
            "USO DE MEDICAMENTO"
    };

    private String[] NUMERO_CINCO = {
            "5",
            "CINCO",
            "ESTOQUE DE MEDICAMENTO"
    };

    private String[] NUMERO_SEIS = {
            "6",
            "SEIS",
            "PERFIL DE USUARIO"
    };

    private String[] COMANDO_VOZ_LISTA_MEDICAMENTO = {
            "LISTA DE MEDICAMENTO",
            "LISTAGEM DE MEDICAMENTO",
            "QUAIS MEDICAMENTOS TENHO",
            "LISTE MINHA FARMARCIA",
            "MINHA FARMACIA",
            "O QUE TENHO NA MINHA FARMACIA",
            "O QUE TENHO NA FARMACIA"
    };

    private static String[] COMANDO_VOZ_DESCREVE_MEDICAMENTO = {
            "DESCRICAO DO MEDICAMENTO",
            "DESCRICAO DO ITEM",
            "DESCREVA O MEDICAMENTO",
            "DESCREVA O ITEM",
            "EXPLIQUE O MEDICAMENTO",
            "EXPLIQUE O ITEM",
            "DETALHE O MEDICAMENTO",
            "DETALHE O ITEM",
            "QUERO SABER TUDO SOBRE O MEDICAMENTO",
            "QUERO SABER TUDO SOBRE O ITEM",
            "QUERO SABER DETALHES DO MEDICAMENTO",
            "QUERO SABER DETALHES DO ITEM"
    };

    private static boolean findTexto(String[] palavras, String palavra) {
        for (int i = 0; i < palavras.length; i++) {
            String palavraSemAcento = StringUtil.removerAcentos(palavra);

            if (palavras[i].equals(palavraSemAcento.toUpperCase()) || palavraSemAcento.toUpperCase().contains(palavras[i])) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String texto = "DESCREVA O ITEM 1";

        if (findTexto(COMANDO_VOZ_DESCREVE_MEDICAMENTO, texto)) {
            System.out.println("achou");
        }
        else {
            System.out.println("nao tem correspondencia");
        }

        ;

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
        } else if (findTexto(COMANDO_VOZ_LISTA_MEDICAMENTO, texto)) {
            return ComandosVoz.LISTA_MEDICAMENTOS;
        } else if (findTexto(COMANDO_VOZ_DESCREVE_MEDICAMENTO, texto)) {
            return ComandosVoz.DESCREVA_MEDICAMENTO;
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
            case PESQUISA_MEDICAMENTOS:
                idBemVindoFuncao = R.raw.pesquisamedicamentosfalecomando;
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

        textoLido.speak("Foram encontrados " + medicamentos.size() + " medicamentos com o argumento informado " + argumento, TextToSpeech.QUEUE_ADD, null);

        int item = 1;

        for (MedicamentoRetDTO medicamentoRetDTO : medicamentos) {
            textoLido.speak("Item " + item + " " + medicamentoRetDTO.getNomeComercial(), TextToSpeech.QUEUE_ADD, null);
            item++;
            ThreadUtil.esperar(ThreadUtil.HUM_SEGUNDO);
        }

    }

    private void falar(String texto) {
        textoLido = new TextToSpeech(App.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textoLido.setLanguage(Locale.getDefault());
                }
                if (i == TextToSpeech.SUCCESS) {
                    textoLido.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        textoLido.setSpeechRate(0.75f);
    }

    @Override
    public void avisarListaVazia() {
        falar("A lista de medicamentos está vazia. Aproveite para começar a cadastrar. Toque na tela e fale o comando incluir medicamento.");
    }

    @Override
    public boolean lerTexto() {
        return true;
    }

    @Override
    public void comandoNaoReconhecido(String comandoInformado) {
        falar("Comando. "+comandoInformado+". não reconhecido. Toque na tela e fale o comando novamente.");
    }

    @Override
    public void listarMedicamentos(List<MedicamentoDTO> medicamentos) {
        textoLido.speak("Há " + medicamentos.size() + " medicamentos cadastrados", TextToSpeech.QUEUE_ADD, null);

        int item = 1;

        for (MedicamentoDTO medicamentoDTO : medicamentos) {
            textoLido.speak("Item . " + item + ". " + medicamentoDTO.getNomeFantasia(), TextToSpeech.QUEUE_ADD, null);
            item++;
            ThreadUtil.esperar(ThreadUtil.HUM_SEGUNDO);
        }
    }

}

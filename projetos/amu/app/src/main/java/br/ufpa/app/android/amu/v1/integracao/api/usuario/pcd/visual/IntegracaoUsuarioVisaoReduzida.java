package br.ufpa.app.android.amu.v1.integracao.api.usuario.pcd.visual;

import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
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

    private String[] COMANDO_VOZ_LISTA_MEDICAMENTO = {
            "LISTA DE MEDICAMENTO",
            "LISTAGEM DE MEDICAMENTO",
            "QUAIS MEDICAMENTOS TENHO",
            "LISTE MINHA FARMARCIA",
            "MINHA FARMACIA",
            "O QUE TENHO NA MINHA FARMACIA",
            "O QUE TENHO NA FARMACIA"
    };

    private String[] COMANDO_VOZ_DESCREVE_MEDICAMENTO = {
            "DESCRICAO DO MEDICAMENTO",
            "DESCRICAO DO ITEM",
            "DESCREVA O MEDICAMENTO",
            "DESCREVA O ITEM",
            "EXPLIQUE O MEDICAMENTO",
            "EXPLIQUE O ITEM",
            "DETALHE O MEDICAMENTO",
            "DETALHE MEDICAMENTO",
            "DETALHE O ITEM",
            "QUERO SABER TUDO SOBRE O MEDICAMENTO",
            "QUERO SABER TUDO SOBRE O ITEM",
            "QUERO SABER DETALHES DO MEDICAMENTO",
            "QUERO SABER DETALHES DO ITEM"
    };

    private String[] COMANDO_VOZ_HORARIO_MEDICAMENTO = {
            "HORARIO DO MEDICAMENTO",
            "HORARIO DO ITEM",
            "QUERO SABER HORARIO DO MEDICAMENTO",
            "QUERO SABER HORARIO DO ITEM",
            "DEVO TOMAR QUE HORAS O MEDICAMENTO",
            "DEVO TOMAR QUE HORAS O REMEDIO",
            "DEVO TOMAR QUE HORAS O ITEM",
    };

    private String[] NUMEROS_EM_VOZ = {
            "UM",
            "DOIS",
            "TRES",
            "QUATRO",
            "CINCO",
            "SEIS",
            "SETE",
            "OITO",
            "NOVE",
            "DEZ",
            "ONZE",
            "DOZE",
            "TREZE",
            "QUARTOZE",
            "QUINZE",
            "DEZESSEIS",
            "DEZESSETE",
            "DEZOITO",
            "DEZENOVE",
            "VINTE"
    };

    private boolean findTexto(String[] palavras, String palavra) {
        String palavraSemAcento = StringUtil.removerAcentos(palavra);

        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].equals(palavraSemAcento.toUpperCase()) || palavraSemAcento.toUpperCase().contains(palavras[i])) {
                return true;
            }
        }

        return false;
    }

    private String findCorrespondencia(String[] palavras, String palavra) {
        String palavraSemAcento = StringUtil.removerAcentos(palavra);

        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].equals(palavraSemAcento.toUpperCase()) || palavraSemAcento.toUpperCase().contains(palavras[i])) {
                return palavras[i];
            }
        }

        return "";
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
        if (findTexto(COMANDO_VOZ_LISTA_MEDICAMENTO, texto)) {
            return ComandosVoz.LISTA_MEDICAMENTOS;
        } else if (findTexto(COMANDO_VOZ_DESCREVE_MEDICAMENTO, texto)) {
            return ComandosVoz.DESCREVA_MEDICAMENTO;
        } else if (findTexto(COMANDO_VOZ_HORARIO_MEDICAMENTO, texto)) {
            return ComandosVoz.DESCREVA_HORARIO;
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
            case DETALHES_MEDICAMENTO:
                idBemVindoFuncao = R.raw.detalhesmedicamentofalecomando;
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
        Log.i("Comando nao reconhecido", comandoInformado);
        falar("Comando. " + comandoInformado + ". não reconhecido. Toque na tela e fale o comando novamente.");
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

    @Override
    public MedicamentoDTO descrerverMedicamento(List<MedicamentoDTO> medicamentos, String s) {
        s = s.toUpperCase();

        String textoCorrespondente = findCorrespondencia(COMANDO_VOZ_DESCREVE_MEDICAMENTO, s);

        MedicamentoDTO medicamentoDTO = findMedicamentoByVoz(medicamentos, s, textoCorrespondente);

        if (medicamentoDTO == null) return null;

        String texto = "Item . " + medicamentoDTO.getNomeFantasia() + " . " +
                "Quantidade da Embalagem . " + medicamentoDTO.getQtdeEmbalagem();
        //"Composição . " + medicamentoDTO.getComposicao()+" . "+
        //"Principio Ativo . " + medicamentoDTO.getPrincipioAtivo();

        textoLido.speak(texto, TextToSpeech.QUEUE_ADD, null);

        return medicamentoDTO;
    }

    @Override
    public void descrerverHorario(List<MedicamentoDTO> medicamentos, List<HorarioDTO> horarios, String s) {

        if (horarios == null || horarios.size() == 0) {
            textoLido.speak(" Item selecionado " + s + " não tem horário cadastrado. Toque na tela e fale o comando novamente.", TextToSpeech.QUEUE_ADD, null);
            return;
        }

        s = s.toUpperCase();

        String textoCorrespondente = findCorrespondencia(COMANDO_VOZ_HORARIO_MEDICAMENTO, s);

        MedicamentoDTO medicamentoDTO = findMedicamentoByVoz(medicamentos, s, textoCorrespondente);

        if (medicamentoDTO == null) return;

        HorarioDTO horarioDTO = horarios.get(horarios.size() - 1);

        String texto = "O ultimo horário cadastradado para o ." + medicamentoDTO.getNomeFantasia() + " . " +
                "Data Inicial de Administracao " + horarioDTO.getDataInicial() + " . " +
                "Horario Inicial " + horarioDTO.getHorarioInicial() + " . " +
                "Intervalo entre as doses " + horarioDTO.getIntervalo() + " . " +
                "Numero de Doses " + horarioDTO.getNrDoses() + " . " +
                "Quantidade de Doses " + horarioDTO.getQtdePorDose();

        textoLido.speak(texto, TextToSpeech.QUEUE_ADD, null);
    }

    private MedicamentoDTO findMedicamentoByVoz(List<MedicamentoDTO> medicamentos, String s, String textoCorrespondente) {
        if (textoCorrespondente.equals("")) {
            textoLido.speak(" Item selecionado " + s + " não existe na lista. Toque na tela e fale o comando novamente.", TextToSpeech.QUEUE_ADD, null);
            return null;
        }

        String restoTexto = StringUtil.removerAcentos(s.replace(textoCorrespondente, "").trim());

        int item = 0;

        for (MedicamentoDTO medicamentoDTO : medicamentos) {
            String numeroTexto = item <= NUMEROS_EM_VOZ.length ? NUMEROS_EM_VOZ[item] : "";
            String nomeFantasia = medicamentoDTO.getNomeFantasiaSemAssento().trim().toUpperCase();

            if (nomeFantasia.equals(restoTexto) || numeroTexto.equals(restoTexto)) {
                return medicamentoDTO;
            }
            item++;
        }

        textoLido.speak(" Item selecionado " + restoTexto + " não existe na lista. Toque na tela e fale o comando novamente.", TextToSpeech.QUEUE_ADD, null);

        return null;
    }

}

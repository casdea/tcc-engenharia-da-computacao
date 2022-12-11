package br.ufpa.app.android.amu.v1.integracao.interfaces;

import android.speech.tts.TextToSpeech;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;

public interface IntegracaoUsuario {
    public void bemVindo();
    public int findComando(String texto);
    public void pararMensagem();
    public void bemVindoFuncao(TipoFuncao tipoFuncao);
    public void capturarComandoIniciado();
    public void capturarComandoEncerrado();

    void instrucaoParaUsuario(int idSom);

    void exibirMedicamentosEncontrados(TextToSpeech textoLido, List<MedicamentoRetDTO> medicamentos, String argumento);

    void avisarListaVazia();

    boolean lerTexto();

    void comandoNaoReconhecido(String comandoInformado);

    void listarMedicamentos(List<MedicamentoDTO> medicamentos);
}

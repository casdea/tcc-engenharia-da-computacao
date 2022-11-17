package br.ufpa.app.android.amu.v1.integracao.api.usuario.comum;

import android.speech.tts.TextToSpeech;

import java.util.List;

import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;

public class IntegracaoUsuarioComum implements IntegracaoUsuario {
    @Override
    public void bemVindo() {

    }

    @Override
    public int findComando(String texto) {
        return 0;
    }

    @Override
    public void pararMensagem() {

    }

    @Override
    public void bemVindoFuncao(TipoFuncao tipoFuncao) {

    }

    @Override
    public void capturarComandoIniciado() {

    }

    @Override
    public void capturarComandoEncerrado() {

    }

    @Override
    public void instrucaoParaUsuario(int idSom) {

    }

    @Override
    public void exibirMedicamentosEncontrados(TextToSpeech textoLido, List<MedicamentoRetDTO> medicamentos, String argumento) {

    }
}

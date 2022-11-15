package br.ufpa.app.android.amu.v1.integracao.interfaces;

import android.content.Context;

import java.util.List;

import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;

public interface IntegracaoUsuario {
    public void bemVindo();
    public int findComando(String texto);
    public void pararMensagem();
    public void bemVindoFuncao(TipoFuncao tipoFuncao);
}

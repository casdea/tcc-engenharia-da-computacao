package br.ufpa.app.android.amu.v1.integracao.factory;

import br.ufpa.app.android.amu.v1.integracao.api.usuario.comum.IntegracaoUsuarioComum;
import br.ufpa.app.android.amu.v1.integracao.api.usuario.pcd.visual.IntegracaoUsuarioVisaoReduzida;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;

public class FactoryIntegracaoUsuario {
    public IntegracaoUsuario createIntegracaoUsuario(TipoPerfil tipoPerfil) {
        if (tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
            return new IntegracaoUsuarioVisaoReduzida();
        else
            return new IntegracaoUsuarioComum();
    }
}

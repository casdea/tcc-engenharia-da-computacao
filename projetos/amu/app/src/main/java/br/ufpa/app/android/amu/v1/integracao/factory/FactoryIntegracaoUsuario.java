package br.ufpa.app.android.amu.v1.integracao.factory;

import br.ufpa.app.android.amu.v1.integracao.api.usuario.comum.IntegracaoUsuarioComum;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;

public class FactoryIntegracaoUsuario {
    public IntegracaoUsuario createIntegracaoUsuario(String perfil) {
        if (perfil.equals("COMUN"))
            return new IntegracaoUsuarioComum();
        else
            return null;
    }
}

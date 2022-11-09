package br.ufpa.app.android.amu.v1.integracao.factory;

import br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa.IntegracaoBularioEletronicoAnvisa;
import br.ufpa.app.android.amu.v1.integracao.api.usuario.comum.IntegracaoUsuarioComum;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;

public class FactoryIntegracaoBularioEletronico {
    public IntegracaoBularioEletronico createIntegracaoBularioEletronico(String fonte) {
        if (fonte.equals("ANVISA"))
            return new IntegracaoBularioEletronicoAnvisa();
        else
            return null;
    }
}

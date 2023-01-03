package br.ufpa.app.android.amu.v1.integracao.factory;

import br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa.IntegracaoBularioEletronicoAnvisa;
import br.ufpa.app.android.amu.v1.integracao.classes.FontesConsulta;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;

public class FactoryIntegracaoBularioEletronico {
    public IntegracaoBularioEletronico createIntegracaoBularioEletronico(FontesConsulta fontesConsulta) {
        if (fontesConsulta.equals(FontesConsulta.ANVISA))
            return new IntegracaoBularioEletronicoAnvisa();
        else
            return null;
    }
}

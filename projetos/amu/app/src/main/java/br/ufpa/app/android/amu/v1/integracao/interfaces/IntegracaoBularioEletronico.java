package br.ufpa.app.android.amu.v1.integracao.interfaces;

import android.content.Context;

import java.util.List;

import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;

public interface IntegracaoBularioEletronico {
    public List<MedicamentoRetDTO> consultarDadosMedicamentos(Context context, String nomeComercial);
}

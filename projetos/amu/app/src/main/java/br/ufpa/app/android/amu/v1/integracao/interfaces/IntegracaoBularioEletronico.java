package br.ufpa.app.android.amu.v1.integracao.interfaces;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import br.ufpa.app.android.amu.v1.integracao.dto.ConsultarMedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;

public interface IntegracaoBularioEletronico {
    ConsultarMedicamentoRetDTO consultarDadosMedicamentos(Context context, String nomeComercial);

    void obterTextoBula(AppCompatActivity atividade, MedicamentoRetDTO medicamentoRetDTO);
}

package br.ufpa.app.android.amu.v1.interfaces;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public interface GerenteServicosListener {
    void carregarLista(int numeroAcao, List<MedicamentoDTO> lista);
    void executarAcao(int numeroAcao, Object parametro);
}

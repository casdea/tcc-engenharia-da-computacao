package br.ufpa.app.android.amu.v1.interfaces;

import java.util.List;

public interface GerenteServicosListener {
    void carregarLista(List<?> lista);
    void executarAcao(int numeroAcao, String[] parametros);
}

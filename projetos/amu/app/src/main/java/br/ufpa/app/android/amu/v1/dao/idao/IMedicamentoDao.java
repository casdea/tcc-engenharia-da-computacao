package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;

public interface IMedicamentoDao extends IDao<Medicamento>
{
    void findAllByUsuario(String idUsuario);
}

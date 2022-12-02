package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;

public interface IEstoqueDao extends IDao<Estoque>
{
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento);
}

package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Comando;

public interface IComandoDao extends IDao<Comando>
{
    public void findAllByUsuario(String idUsuario);
}

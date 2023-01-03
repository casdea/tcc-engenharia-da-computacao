package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Alarme;

public interface IAlarmeDao extends IDao<Alarme>
{
    public void findAllByUsuario(String idUsuario);
}

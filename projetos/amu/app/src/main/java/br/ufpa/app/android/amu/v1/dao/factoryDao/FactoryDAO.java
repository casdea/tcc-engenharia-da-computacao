package br.ufpa.app.android.amu.v1.dao.factoryDao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.impl.UsuarioDao;

public class FactoryDAO {

    private DatabaseReference em;

    public FactoryDAO(DatabaseReference em)
    {
        this.em = em;
    }

    private IUsuarioDao usuarioDao;

    public IUsuarioDao getUsuarioDao()
    {
        if (usuarioDao == null)
        {
            usuarioDao = new UsuarioDao(em);
        }
        return usuarioDao;
    }

}

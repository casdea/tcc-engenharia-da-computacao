package br.ufpa.app.android.amu.v1.dao.impl;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;

public class UsuarioDao extends AbstractEntityDao<Usuario> implements IUsuarioDao {

    public UsuarioDao(DatabaseReference em) {
        super(em);
    }

    @Override
    public Usuario create(Usuario usuario) {
        DatabaseReference usuariosRef = em.child(usuario.getNomeTabela());
        usuariosRef.child(String.valueOf(usuario.getIdUsuario())).setValue(usuario);
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        DatabaseReference usuariosRef = em.child(usuario.getNomeTabela());
        usuariosRef.child(String.valueOf(usuario.getIdUsuario())).setValue(usuario);

        return usuario;
    }
}

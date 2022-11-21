package br.ufpa.app.android.amu.v1.dao.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;

public class UsuarioDao extends AbstractEntityDao<Usuario> implements IUsuarioDao {

    private ValueEventListener valueEventListenerUsuario;

    public UsuarioDao(DatabaseReference em) {
        super(em);
    }

    public Class<Usuario> getClassImplement() {
        return Usuario.class;
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

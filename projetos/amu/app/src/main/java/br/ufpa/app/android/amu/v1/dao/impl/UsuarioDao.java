package br.ufpa.app.android.amu.v1.dao.impl;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;

public class UsuarioDao extends AbstractEntityDao<Usuario> implements IUsuarioDao {

    private GerenteServicosListener gerenteServicosListener;
    private AppCompatActivity atividade;

    public UsuarioDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;

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

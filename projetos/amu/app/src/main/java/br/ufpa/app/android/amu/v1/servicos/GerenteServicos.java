package br.ufpa.app.android.amu.v1.servicos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufpa.app.android.amu.v1.dao.factoryDao.FactoryDAO;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;

public class GerenteServicos {

    private DatabaseReference em = FirebaseDatabase.getInstance().getReference();

    public void incluirUsuario(UsuarioDTO usuarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em);
        Usuario usuario = new Usuario(usuarioDTO);
        factoryDAO.getUsuarioDao().create(usuario);
    }

    public void alterarUsuario(UsuarioDTO usuarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em);
        Usuario usuario = new Usuario(usuarioDTO);
        factoryDAO.getUsuarioDao().update(usuario);
    }

}

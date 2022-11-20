package br.ufpa.app.android.amu.v1.dao.impl;

import com.google.firebase.database.DatabaseReference;

import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;

public class UsuarioDao extends AbstractEntityDao<Usuario> implements IUsuarioDao
{

	public UsuarioDao(DatabaseReference em)
	{
		super(em);
	}

	public Class<Usuario> getClassImplement()
	{
		return Usuario.class;
	}

	@Override
	public Usuario create(Usuario usuario)
	{
		DatabaseReference usuarios = em.child(usuario.getNomeTabela());
		usuarios.child(String.valueOf(usuario.getId())).setValue(usuario);

		return usuario;
	}

	@Override
	public Usuario update(Usuario usuario)
	{
		DatabaseReference usuarios = em.child(usuario.getNomeTabela());
		usuarios.child(String.valueOf(usuario.getId())).setValue(usuario);

		return usuario;
	}
}

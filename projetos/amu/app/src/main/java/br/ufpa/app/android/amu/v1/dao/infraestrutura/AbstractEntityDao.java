package br.ufpa.app.android.amu.v1.dao.infraestrutura;

import com.google.firebase.database.DatabaseReference;

public abstract class AbstractEntityDao<T> implements IDao<T>, IEntityDao
{
	protected Class<?> implClass;

	protected DatabaseReference em;

	public String getIdField()
	{
		return "id";
	}

	public AbstractEntityDao()
	{
	}

	public AbstractEntityDao(DatabaseReference em)
	{
		this.em = em;
	}

	public T create(T objeto)
	{
		return objeto;
	}

	public T update(T objeto)
	{
		return objeto;
	}


}
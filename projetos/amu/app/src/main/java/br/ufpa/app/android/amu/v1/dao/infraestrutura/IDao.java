package br.ufpa.app.android.amu.v1.dao.infraestrutura;

public interface IDao<T>
{
	T create(T objeto);

	T update(T objeto);
}
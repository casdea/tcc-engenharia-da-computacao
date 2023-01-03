package br.ufpa.app.android.amu.v1.dao.infraestrutura;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.ufpa.app.android.amu.v1.dao.exception.InfraStructureException;

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

	public Long gerarId(T objeto)
	{
		return null;
	}
	
	public Long gerarIdAsInteger(T objeto)
	{
		return null;
	}

	public T create(T objeto)
	{
		return objeto;
	}

	public T update(T objeto)
	{
		return objeto;
	}

	public void delete(T objeto)
	{
	}

	public void refresh(T objeto)
	{

	}

	@SuppressWarnings("unchecked")
	public T findById(long id)
	{
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public T findValidaById(long id) throws InfraStructureException
	{
		Object T = null;
		return (T) T;
	}

	@SuppressWarnings("unchecked")
	public T findValidaByIdMensagem(long id, String mensagem) throws InfraStructureException
	{
		Object T = null;
		

		return (T) T;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	private String getEsquemaTabelaAnotado(Class classe)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	private String getNomeTabelaAnotado(Class classe)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	private String getNomeColunaIdAnotado(Class classe)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	public T findByDesc(String field, String desc)
	{
		return null;
	}
}
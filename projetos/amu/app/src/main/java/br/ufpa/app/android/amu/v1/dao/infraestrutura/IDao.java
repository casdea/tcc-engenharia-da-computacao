package br.ufpa.app.android.amu.v1.dao.infraestrutura;

import java.util.List;

import br.ufpa.app.android.amu.v1.dao.exception.InfraStructureException;

public interface IDao<T>
{
	Long gerarId(T objeto);
	
	public Long gerarIdAsInteger(T objeto);

	T create(T objeto);

	T update(T objeto);

	void delete(T objeto);

	void refresh(T objeto);

	T findById(long id);
	
	T findValidaById(long id)  throws InfraStructureException;
	
	T findValidaByIdMensagem(long id, String mensagem) throws InfraStructureException;
	
	T findByDesc(String field, String desc);

	List<T> findAll();
}
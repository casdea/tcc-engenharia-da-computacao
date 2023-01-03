package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.VariacoesComando;

public interface IVariacoesComandoDao extends IDao<VariacoesComando>
{
    public void findAllByUsuarioIdComando(String idUsuario, String idComando);
}

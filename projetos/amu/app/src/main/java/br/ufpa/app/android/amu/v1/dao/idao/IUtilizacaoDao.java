package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Utilizacao;

public interface IUtilizacaoDao extends IDao<Utilizacao>
{
    void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento);
    void findAllByUsuario(String idUsuario);
}

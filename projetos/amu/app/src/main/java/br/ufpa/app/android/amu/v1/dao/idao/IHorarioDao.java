package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;

public interface IHorarioDao extends IDao<Horario>
{
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento);
}

package br.ufpa.app.android.amu.v1.dao.idao;

import java.util.List;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public interface IMedicamentoDao extends IDao<Medicamento>
{
    List<MedicamentoDTO> findAllByUsuario(String idUsuario);
}

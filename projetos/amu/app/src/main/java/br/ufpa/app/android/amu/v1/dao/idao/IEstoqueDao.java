package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;

public interface IEstoqueDao extends IDao<Estoque>
{
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento);
    public void atualizarSaldoEstoque(String idUsuario, String idMedicamento, EstoqueDTO movtoEstoqueDTO);
    public void sinalizarDoseRealizada(String idUsuario, String idMedicamento, EstoqueDTO movtoEstoqueDTO);
}

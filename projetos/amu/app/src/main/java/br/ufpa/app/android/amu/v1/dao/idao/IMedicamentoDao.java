package br.ufpa.app.android.amu.v1.dao.idao;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.IDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;

public interface IMedicamentoDao extends IDao<Medicamento>
{
    public Medicamento create(Medicamento medicamento, Horario horario);
    void findAllByUsuario(String idUsuario);
    void findByUsuarioIdProduto(String idUsuario, String idProduto);
}

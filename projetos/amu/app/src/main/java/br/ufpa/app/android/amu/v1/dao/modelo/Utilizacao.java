package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;

public class Utilizacao  extends AbstractEntity  {
    private String idUtilizacao;
    private String idMedicamento;
    private String idUsuario;
    private String dataHora;

    public Utilizacao(UtilizacaoDTO utilizacaoDTO) {
        nomeTabela = "utilizacoes";
        this.idUtilizacao = utilizacaoDTO.getIdUtilizacao();
        this.idMedicamento = utilizacaoDTO.getIdMedicamento();
        this.idUsuario = utilizacaoDTO.getIdUsuario();
        this.dataHora = utilizacaoDTO.getDataHora();
    }

    public String getIdUtilizacao() {
        return idUtilizacao;
    }

    public void setIdUtilizacao(String idUtilizacao) {
        this.idUtilizacao = idUtilizacao;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getDataHora() {
        return dataHora;
    }
}

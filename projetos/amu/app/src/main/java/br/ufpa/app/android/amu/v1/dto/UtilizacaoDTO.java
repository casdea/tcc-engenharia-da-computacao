package br.ufpa.app.android.amu.v1.dto;

import java.util.Date;

public class UtilizacaoDTO {
    private String idUtilizacao;
    private String idMedicamento;
    private String idUsuario;
    private String dataHora;
    private Date dataUtilizacao;

    public String getIdUtilizacao() {
        return idUtilizacao;
    }

    public void setIdUtilizacao(String idUtilizacao) {
        this.idUtilizacao = idUtilizacao;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Date getDataUtilizacao() {
        return dataUtilizacao;
    }

    public void setDataUtilizacao(Date dataUtilizacao) {
        this.dataUtilizacao = dataUtilizacao;
    }
}

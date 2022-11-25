package br.ufpa.app.android.amu.v1.dao.modelo;

import java.util.Date;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;

public class Utilizacao  extends AbstractEntity  {
    private String idUtilizacao;
    private String idMedicamento;
    private String idUsuario;
    private Date dataHora;

    public Utilizacao() {
    }

    public Utilizacao(String idUtilizacao, String idMedicamento, String idUsuario, Date dataHora) {
        this.idUtilizacao = idUtilizacao;
        this.idMedicamento = idMedicamento;
        this.idUsuario = idUsuario;
        this.dataHora = dataHora;
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

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}

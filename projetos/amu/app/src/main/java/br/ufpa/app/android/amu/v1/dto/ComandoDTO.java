package br.ufpa.app.android.amu.v1.dto;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;

public class ComandoDTO {
    private String idComando;
    private String idUsuario;
    private String nrComando;
    private String nomeComando;

    public String getIdComando() {
        return idComando;
    }

    public void setIdComando(String idComando) {
        this.idComando = idComando;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNrComando() {
        return nrComando;
    }

    public void setNrComando(String nrComando) {
        this.nrComando = nrComando;
    }

    public String getNomeComando() {
        return nomeComando;
    }

    public void setNomeComando(String nomeComando) {
        this.nomeComando = nomeComando;
    }
}

package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public class Comando extends AbstractEntity {
    private String idComando;
    private String idUsuario;
    private String nrComando;
    private String nomeComando;

    public Comando() {
        nomeTabela = "comandos";
    }

    public Comando(String idComando, String idUsuario, String nrComando, String nomeComando) {
        nomeTabela = "comandos";
        this.idComando = idComando;
        this.idUsuario = idUsuario;
        this.nrComando = nrComando;
        this.nomeComando = nomeComando;
    }

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

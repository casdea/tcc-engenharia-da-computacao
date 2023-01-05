package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.AlarmeDTO;

public class Alarme extends AbstractEntity {

    private String idAlarme;
    private String idMedicamento;
    private String idUsuario;
    private String dataHora;
    private int tipoAlarme;
    private String titulo;
    private String descricao;

    public Alarme() {
        this.nomeTabela = "alarmes";
    }

    public Alarme(AlarmeDTO alarmeDTO) {
        this.nomeTabela = "alarmes";
        this.idAlarme = alarmeDTO.getIdAlarme();
        this.idMedicamento = alarmeDTO.getIdMedicamento();
        this.idUsuario = alarmeDTO.getIdUsuario();
        this.dataHora = alarmeDTO.getDataHora();
        this.tipoAlarme = alarmeDTO.getTipoAlarme();
        this.titulo = alarmeDTO.getTitulo();
        this.descricao = alarmeDTO.getDescricao();
    }

    public String getIdAlarme() {
        return idAlarme;
    }

    public void setIdAlarme(String idAlarme) {
        this.idAlarme = idAlarme;
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

    public int getTipoAlarme() {
        return tipoAlarme;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

}


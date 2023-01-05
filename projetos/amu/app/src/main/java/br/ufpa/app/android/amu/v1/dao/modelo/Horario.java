package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;

public class Horario extends AbstractEntity {
    private String idHorario;
    private String idMedicamento;
    private String idUsuario;
    private int qtdePorDose;
    private String dataInicial;
    private String horarioInicial;
    private String intervalo;
    private int nrDoses;
    private String ativo;

    public Horario() {
        this.nomeTabela = "horarios";
    }

    public Horario(HorarioDTO horarioDTO) {
        this.nomeTabela = "horarios";
        this.idHorario = horarioDTO.getIdHorario();
        this.idMedicamento = horarioDTO.getIdMedicamento();
        this.idUsuario = horarioDTO.getIdUsuario();
        this.dataInicial = horarioDTO.getDataInicial();
        this.horarioInicial = horarioDTO.getHorarioInicial();
        this.intervalo = horarioDTO.getIntervalo();
        this.nrDoses = horarioDTO.getNrDoses();
        this.qtdePorDose = horarioDTO.getQtdePorDose();
        this.ativo = horarioDTO.getAtivo();
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
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

    public int getQtdePorDose() {
        return qtdePorDose;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public int getNrDoses() {
        return nrDoses;
    }

    public String getAtivo() {
        return ativo;
    }
}

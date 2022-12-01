package br.ufpa.app.android.amu.v1.dto;

public class HorarioDTO {
    private String idHorario;
    private String idMedicamento;
    private String idUsuario;
    private int qtdePorDose;
    private String dataInicial;
    private String horarioInicial;
    private String intervalo;
    private int nrDoses;
    private String ativo;

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

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getQtdePorDose() {
        return qtdePorDose;
    }

    public void setQtdePorDose(int qtdePorDose) {
        this.qtdePorDose = qtdePorDose;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public int getNrDoses() {
        return nrDoses;
    }

    public void setNrDoses(int nrDoses) {
        this.nrDoses = nrDoses;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}

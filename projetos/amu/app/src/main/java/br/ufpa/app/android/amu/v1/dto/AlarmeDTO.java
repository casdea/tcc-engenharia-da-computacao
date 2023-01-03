package br.ufpa.app.android.amu.v1.dto;

import br.ufpa.app.android.amu.v1.util.DataUtil;

public class AlarmeDTO {
    public static final int TIPO_ALARME_PROXIMA_DOSE = 1;

    public static final int TIPO_ALARME_HORA_DOSE = 2;

    public static final int TIPO_ALARME_DOSE_ATRASADA = 3;

    public static final int TIPO_ALARME_DOSE_EXCEDIDA = 4;

    private String idAlarme;
    private String idMedicamento;
    private String idUsuario;
    private String dataHora;
    private int tipoAlarme;
    private String titulo;
    private String descricao;

    public AlarmeDTO() {
    }

    public AlarmeDTO(String idAlarme, MedicamentoDTO medicamentoDTO, int tipoAlarme, int minutos) {
        this.idAlarme = idAlarme;
        this.idMedicamento = medicamentoDTO.getIdMedicamento();
        this.idUsuario = medicamentoDTO.getIdUsuario();
        this.dataHora = DataUtil.convertDateTimeToString(DataUtil.getDataAtual());
        this.tipoAlarme = tipoAlarme;

        switch (tipoAlarme) {
            case AlarmeDTO.TIPO_ALARME_PROXIMA_DOSE: {
                this.titulo = "Proxima dose de " + medicamentoDTO.getNomeFantasia();
                this.descricao = "Faltam " + minutos + " minutos para tomar o remédio " + medicamentoDTO.getNomeFantasia();
                break;
            }

            case AlarmeDTO.TIPO_ALARME_HORA_DOSE: {
                this.titulo = "Hora de Tomar " + medicamentoDTO.getNomeFantasia();
                this.descricao = "Não esqueça de tomar o remédio " + medicamentoDTO.getNomeFantasia();
                break;
            }

            case AlarmeDTO.TIPO_ALARME_DOSE_ATRASADA: {
                this.titulo = "Dose atrasada de " + medicamentoDTO.getNomeFantasia();
                this.descricao = "Passou " + (minutos) * -1 + " minutos de tomar o remédio " + medicamentoDTO.getNomeFantasia();
            }
        }
    }

    public AlarmeDTO(String idAlarme, MedicamentoDTO medicamentoDTO, int tipoAlarme, int nrDoses, int nrUtilizacoes) {
        this.idAlarme = idAlarme;
        this.idMedicamento = medicamentoDTO.getIdMedicamento();
        this.idUsuario = medicamentoDTO.getIdUsuario();
        this.dataHora = DataUtil.convertDateTimeToString(DataUtil.getDataAtual());
        this.tipoAlarme = tipoAlarme;

        if (tipoAlarme == AlarmeDTO.TIPO_ALARME_DOSE_EXCEDIDA) {
            this.titulo = "Doses Excedidas do Remédio " + medicamentoDTO.getNomeFantasia();
            this.descricao = "Estava prescrita " + nrDoses + " doses para o remédio " + medicamentoDTO.getNomeFantasia() + ", porem foram administradas " + nrUtilizacoes;
        }

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

    public int getTipoAlarme() {
        return tipoAlarme;
    }

    public void setTipoAlarme(int tipoAlarme) {
        this.tipoAlarme = tipoAlarme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

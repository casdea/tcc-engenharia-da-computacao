package br.ufpa.app.android.amu.v1.dto;

public class AlarmeDTO
{
	public static final int TIPO_ALARME_PROXIMA_DOSE = 1;

	public static final int TIPO_ALARME_DOSE_ATRASADA = 2;

	public static final int TIPO_ALARME_DOSE_EXCEDIDA = 3;

	private String idAlarme;
	private String idMedicamento;
	private String idUsuario;
	private String dataHora;
	private int tipoAlarme;
	private String titulo;
	private String descricao;

	public AlarmeDTO() {
	}

	public AlarmeDTO(String idAlarme, String idMedicamento, String idUsuario, String dataHora, int tipoAlarme, String titulo, String descricao) {
		this.idAlarme = idAlarme;
		this.idMedicamento = idMedicamento;
		this.idUsuario = idUsuario;
		this.dataHora = dataHora;
		this.tipoAlarme = tipoAlarme;
		this.titulo = titulo;
		this.descricao = descricao;
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

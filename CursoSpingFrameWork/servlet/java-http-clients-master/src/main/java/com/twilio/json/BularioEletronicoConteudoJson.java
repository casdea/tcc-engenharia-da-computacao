package com.twilio.json;

public class BularioEletronicoConteudoJson {

	private int idProduto;
	private String numeroRegistro;
	private String nomeProduto;
	private String expediente;
	private String razaoSocial;
	private String cnpj;
	private String numeroTransacao;
	private String data;
	private String numProcesso;
	private String idBulaPacienteProtegido;
	private String idBulaProfissionalProtegido;

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNumeroTransacao() {
		return numeroTransacao;
	}

	public void setNumeroTransacao(String numeroTransacao) {
		this.numeroTransacao = numeroTransacao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNumProcesso() {
		return numProcesso;
	}

	public void setNumProcesso(String numProcesso) {
		this.numProcesso = numProcesso;
	}

	public String getIdBulaPacienteProtegido() {
		return idBulaPacienteProtegido;
	}

	public void setIdBulaPacienteProtegido(String idBulaPacienteProtegido) {
		this.idBulaPacienteProtegido = idBulaPacienteProtegido;
	}

	public String getIdBulaProfissionalProtegido() {
		return idBulaProfissionalProtegido;
	}

	public void setIdBulaProfissionalProtegido(String idBulaProfissionalProtegido) {
		this.idBulaProfissionalProtegido = idBulaProfissionalProtegido;
	}

	@Override
	public String toString() {
		return "BularioEletronicoConteudoJson [idProduto=" + idProduto + ", numeroRegistro=" + numeroRegistro
				+ ", nomeProduto=" + nomeProduto + ", expediente=" + expediente + ", razaoSocial=" + razaoSocial
				+ ", cnpj=" + cnpj + ", numeroTransacao=" + numeroTransacao + ", data=" + data + ", numProcesso="
				+ numProcesso + ", idBulaPacienteProtegido=" + idBulaPacienteProtegido
				+ ", idBulaProfissionalProtegido=" + idBulaProfissionalProtegido + "]";
	}

	
	
}

package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import androidx.annotation.NonNull;

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
	private String dataAtualizacao;

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getIdBulaPacienteProtegido() {
		return idBulaPacienteProtegido;
	}

	public void setIdBulaPacienteProtegido(String idBulaPacienteProtegido) {
		this.idBulaPacienteProtegido = idBulaPacienteProtegido;
	}

	@NonNull
	@Override
	public String toString() {
		return "BularioEletronicoConteudoJson{" +
				"idProduto=" + idProduto +
				", numeroRegistro='" + numeroRegistro + '\'' +
				", nomeProduto='" + nomeProduto + '\'' +
				", expediente='" + expediente + '\'' +
				", razaoSocial='" + razaoSocial + '\'' +
				", cnpj='" + cnpj + '\'' +
				", numeroTransacao='" + numeroTransacao + '\'' +
				", data='" + data + '\'' +
				", numProcesso='" + numProcesso + '\'' +
				", idBulaPacienteProtegido='" + idBulaPacienteProtegido + '\'' +
				", idBulaProfissionalProtegido='" + idBulaProfissionalProtegido + '\'' +
				", dataAtualizacao='" + dataAtualizacao + '\'' +
				'}';
	}
}

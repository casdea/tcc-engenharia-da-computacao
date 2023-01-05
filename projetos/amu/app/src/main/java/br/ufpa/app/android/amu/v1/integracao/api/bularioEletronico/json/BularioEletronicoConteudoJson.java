package br.ufpa.app.android.amu.v1.integracao.api.bularioEletronico.json;

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

	@Override
	public String toString() {
		return "BularioEletronicoConteudoJson [idProduto=" + idProduto + ", numeroRegistro=" + numeroRegistro
				+ ", nomeProduto=" + nomeProduto + ", expediente=" + expediente + ", razaoSocial=" + razaoSocial
				+ ", cnpj=" + cnpj + ", numeroTransacao=" + numeroTransacao + ", data=" + data + ", numProcesso="
				+ numProcesso + ", idBulaPacienteProtegido=" + idBulaPacienteProtegido
				+ ", idBulaProfissionalProtegido=" + idBulaProfissionalProtegido + "]";
	}

	
	
}

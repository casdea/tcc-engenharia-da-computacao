package br.ufpa.app.android.amu.v1.integracao.dto;

public class MedicamentoRetDTO {

    private final String nomeComercial;
    private final String nomeLaboratorio;
    private final String nomeArquivoBulaPaciente;
    private final String idProduto;
    private final String dataProduto;

    public String getNomeArquivoPdf() {

        return this.idProduto + "_" + this.dataProduto + ".pdf";
    }

    public String getNomeArquivoTxt() {

        return this.idProduto + "_" + this.dataProduto + ".txt";
    }

    public MedicamentoRetDTO(String nomeComercial, String nomeLaboratorio, String nomeArquivoBulaPaciente, String idPoduto, String dataProduto) {
        this.nomeComercial = nomeComercial;
        this.nomeLaboratorio = nomeLaboratorio;
        this.nomeArquivoBulaPaciente = nomeArquivoBulaPaciente;
        this.idProduto = idPoduto;
        this.dataProduto = dataProduto;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public String getNomeLaboratorio() {
        return nomeLaboratorio;
    }

    public String getNomeArquivoBulaPaciente() {
        return nomeArquivoBulaPaciente;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getDataProduto() {
        return dataProduto;
    }

}

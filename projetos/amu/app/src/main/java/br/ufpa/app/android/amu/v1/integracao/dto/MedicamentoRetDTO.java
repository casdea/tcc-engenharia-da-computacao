package br.ufpa.app.android.amu.v1.integracao.dto;

public class MedicamentoRetDTO {

    private String nomeComercial;
    private String nomeLaboratorio;
    private String nomeArquivoBulaPaciente;
    private String idProduto;
    private String dataProduto;

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

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getNomeLaboratorio() {
        return nomeLaboratorio;
    }

    public void setNomeLaboratorio(String nomeLaboratorio) {
        this.nomeLaboratorio = nomeLaboratorio;
    }

    public String getNomeArquivoBulaPaciente() {
        return nomeArquivoBulaPaciente;
    }

    public void setNomeArquivoBulaPaciente(String nomeArquivoBulaPaciente) {
        this.nomeArquivoBulaPaciente = nomeArquivoBulaPaciente;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getDataProduto() {
        return dataProduto;
    }

    public void setDataProduto(String dataProduto) {
        this.dataProduto = dataProduto;
    }
}

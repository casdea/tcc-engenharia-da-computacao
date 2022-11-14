package br.ufpa.app.android.amu.v1.integracao.dto;

public class MedicamentoRetDTO {

    private String nomeComercial;
    private String nomeLaboratorio;
    private String nomeArquivoBulaPaciente;
    private String nomeArquivoPdf;
    private String nomeArquivoTxt;

    public MedicamentoRetDTO(String nomeComercial, String nomeLaboratorio, String nomeArquivoBulaPaciente, String nomeArquivoPdf, String nomeArquivoTxt) {
        this.nomeComercial = nomeComercial;
        this.nomeLaboratorio = nomeLaboratorio;
        this.nomeArquivoBulaPaciente = nomeArquivoBulaPaciente;
        this.nomeArquivoPdf = nomeArquivoPdf;
        this.nomeArquivoTxt = nomeArquivoTxt;
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

    public String getNomeArquivoPdf() {
        return nomeArquivoPdf;
    }

    public void setNomeArquivoPdf(String nomeArquivoPdf) {
        this.nomeArquivoPdf = nomeArquivoPdf;
    }

    public String getNomeArquivoTxt() {
        return nomeArquivoTxt;
    }

    public void setNomeArquivoTxt(String nomeArquivoTxt) {
        this.nomeArquivoTxt = nomeArquivoTxt;
    }
}

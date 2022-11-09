package br.ufpa.app.android.amu.v1.integracao.dto;

public class MedicamentoRetDTO {

    private String nomeComercial;
    private String nomeLaboratorio;

    public MedicamentoRetDTO(String nomeComercial, String nomeLaboratorio) {
        this.nomeComercial = nomeComercial;
        this.nomeLaboratorio = nomeLaboratorio;
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
}

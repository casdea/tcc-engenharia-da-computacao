package br.ufpa.app.android.amu.v1.dto;

public class EstoqueDTO {
    private String idEstoque;
    private String idMedicamento;
    private String idUsuario;
    private String dataHora;
    private int entrada;
    private int saida;
    private int saldo;

    public String getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(String idEstoque) {
        this.idEstoque = idEstoque;
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

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public int getSaida() {
        return saida;
    }

    public void setSaida(int saida) {
        this.saida = saida;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}


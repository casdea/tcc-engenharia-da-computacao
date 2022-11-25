package br.ufpa.app.android.amu.v1.dao.modelo;

import java.util.Date;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;

public class Estoque  extends AbstractEntity {
    private String idEstoque;
    private String idMedicamento;
    private String idUsuario;
    private Date data;
    private double entrada;
    private double saida;
    private double saldo;

    public Estoque() {
    }

    public Estoque(String idEstoque, String idMedicamento, String idUsuario, Date data, double entrada, double saida, double saldo) {
        this.idEstoque = idEstoque;
        this.idMedicamento = idMedicamento;
        this.idUsuario = idUsuario;
        this.data = data;
        this.entrada = entrada;
        this.saida = saida;
        this.saldo = saldo;
    }

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}


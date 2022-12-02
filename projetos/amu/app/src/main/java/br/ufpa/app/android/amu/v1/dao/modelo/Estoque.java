package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;

public class Estoque  extends AbstractEntity {
    private String idEstoque;
    private String idMedicamento;
    private String idUsuario;
    private String data;
    private int entrada;
    private int saida;
    private int saldo;

    public Estoque() {
        nomeTabela = "estoques";
    }

    public Estoque(EstoqueDTO estoqueDTO) {
        nomeTabela = "estoques";
        this.idEstoque = estoqueDTO.getIdEstoque();
        this.idMedicamento = estoqueDTO.getIdMedicamento();
        this.idUsuario = estoqueDTO.getIdUsuario();
        this.data = estoqueDTO.getData();
        this.entrada = estoqueDTO.getEntrada();
        this.saida = estoqueDTO.getSaida();
        this.saldo = estoqueDTO.getSaldo();
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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


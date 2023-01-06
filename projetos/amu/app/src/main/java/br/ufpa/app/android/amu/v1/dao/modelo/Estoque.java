package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;

public class Estoque  extends AbstractEntity {
    private String idEstoque;
    private String idMedicamento;
    private String idUsuario;
    private String dataHora;
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
        this.dataHora = estoqueDTO.getDataHora();
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

    public String getDataHora() {
        return dataHora;
    }

    public int getEntrada() {
        return entrada;
    }

    public int getSaida() {
        return saida;
    }

    public int getSaldo() {
        return saldo;
    }

}


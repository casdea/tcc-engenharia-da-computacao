package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public class Medicamento extends AbstractEntity {
    private String idMedicamento;
    private String nomeComercial;
    private String nomeFantasia;
    private String cor;
    private String principioAtivo;
    private String fabricante;
    private String formaApresentacao;
    private String composicao;
    private String viaAdministracao;
    private String publicoAlvo;
    private String textoParaQueIndicado;
    private String textoComoFunciona;
    private String textoComoUsar;
    private String textoSeEsquecerQueFazer;
    private String idProdutoAnvisa;
    private String dataProdutoAnvisa;
    private String idUsuario;
    private int qtdeEmbalagem;

    public Medicamento() {
        this.nomeTabela = "medicamentos";
    }

    public Medicamento(MedicamentoDTO medicamentoDTO) {
        this.nomeTabela = "medicamentos";
        
        this.idMedicamento = medicamentoDTO.getIdMedicamento();
        this.nomeComercial = medicamentoDTO.getNomeComercial();
        this.nomeFantasia = medicamentoDTO.getNomeFantasia();
        this.cor = medicamentoDTO.getCor();
        this.principioAtivo = medicamentoDTO.getPrincipioAtivo();
        this.fabricante = medicamentoDTO.getFabricante();
        this.formaApresentacao = medicamentoDTO.getFormaApresentacao();
        this.composicao = medicamentoDTO.getComposicao();
        this.viaAdministracao = medicamentoDTO.getViaAdministracao();
        this.publicoAlvo = medicamentoDTO.getPublicoAlvo();
        this.textoParaQueIndicado = medicamentoDTO.getTextoParaQueIndicado();
        this.textoComoFunciona = medicamentoDTO.getTextoComoFunciona();
        this.textoComoUsar = medicamentoDTO.getTextoComoUsar();
        this.textoSeEsquecerQueFazer = medicamentoDTO.getTextoSeEsquecerQueFazer();
        this.idProdutoAnvisa = medicamentoDTO.getIdProdutoAnvisa();
        this.dataProdutoAnvisa = medicamentoDTO.getDataProdutoAnvisa();
        this.idUsuario = medicamentoDTO.getIdUsuario();
        this.qtdeEmbalagem = medicamentoDTO.getQtdeEmbalagem();
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCor() {
        return cor;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getFormaApresentacao() {
        return formaApresentacao;
    }

    public String getComposicao() {
        return composicao;
    }

    public String getViaAdministracao() {
        return viaAdministracao;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public String getTextoParaQueIndicado() {
        return textoParaQueIndicado;
    }

    public String getTextoComoFunciona() {
        return textoComoFunciona;
    }

    public String getTextoComoUsar() {
        return textoComoUsar;
    }

    public String getTextoSeEsquecerQueFazer() {
        return textoSeEsquecerQueFazer;
    }

    public String getIdProdutoAnvisa() {
        return idProdutoAnvisa;
    }

    public String getDataProdutoAnvisa() {
        return dataProdutoAnvisa;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public int getQtdeEmbalagem() {
        return qtdeEmbalagem;
    }
}

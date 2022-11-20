package br.ufpa.app.android.amu.v1.dao.modelo;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public class Medicamento extends AbstractEntity {
    private String idMedicamento;
    private String nomeComercial;
    private String cor;
    private String principioAtivo;
    private String fabricante;
    private String formato;
    private String composicao;
    private String formaUso;
    private String indicacaoIdade;
    private String textoParaQueIndicado;
    private String textoComoFunciona;
    private String textoComoUsar;
    private String textoSeEsquecerQueFazer;
    private String idProdutoAnvisa;
    private String dataProdutoAnvisa;
    private String idUsuario;

    public Medicamento() {
    }

    public Medicamento(MedicamentoDTO medicamentoDTO) {
        this.idMedicamento = medicamentoDTO.getIdMedicamento();
        this.nomeComercial = nomeComercial;
        this.cor = cor;
        this.principioAtivo = principioAtivo;
        this.fabricante = fabricante;
        this.formato = formato;
        this.composicao = composicao;
        this.formaUso = formaUso;
        this.indicacaoIdade = indicacaoIdade;
        this.textoParaQueIndicado = textoParaQueIndicado;
        this.textoComoFunciona = textoComoFunciona;
        this.textoComoUsar = textoComoUsar;
        this.textoSeEsquecerQueFazer = textoSeEsquecerQueFazer;
        this.idProdutoAnvisa = idProdutoAnvisa;
        this.dataProdutoAnvisa = dataProdutoAnvisa;
        this.idUsuario = idUsuario;
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

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getFormaUso() {
        return formaUso;
    }

    public void setFormaUso(String formaUso) {
        this.formaUso = formaUso;
    }

    public String getIndicacaoIdade() {
        return indicacaoIdade;
    }

    public void setIndicacaoIdade(String indicacaoIdade) {
        this.indicacaoIdade = indicacaoIdade;
    }

    public String getTextoParaQueIndicado() {
        return textoParaQueIndicado;
    }

    public void setTextoParaQueIndicado(String textoParaQueIndicado) {
        this.textoParaQueIndicado = textoParaQueIndicado;
    }

    public String getTextoComoFunciona() {
        return textoComoFunciona;
    }

    public void setTextoComoFunciona(String textoComoFunciona) {
        this.textoComoFunciona = textoComoFunciona;
    }

    public String getTextoComoUsar() {
        return textoComoUsar;
    }

    public void setTextoComoUsar(String textoComoUsar) {
        this.textoComoUsar = textoComoUsar;
    }

    public String getTextoSeEsquecerQueFazer() {
        return textoSeEsquecerQueFazer;
    }

    public void setTextoSeEsquecerQueFazer(String textoSeEsquecerQueFazer) {
        this.textoSeEsquecerQueFazer = textoSeEsquecerQueFazer;
    }

    public String getIdProdutoAnvisa() {
        return idProdutoAnvisa;
    }

    public void setIdProdutoAnvisa(String idProdutoAnvisa) {
        this.idProdutoAnvisa = idProdutoAnvisa;
    }

    public String getDataProdutoAnvisa() {
        return dataProdutoAnvisa;
    }

    public void setDataProdutoAnvisa(String dataProdutoAnvisa) {
        this.dataProdutoAnvisa = dataProdutoAnvisa;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}

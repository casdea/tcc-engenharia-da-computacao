package br.ufpa.app.android.amu.v1.dao.modelo;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public class Medicamento extends AbstractEntity {
    //automatico
    //nao aparece
    private String idMedicamento;
    //edittext
    //DORFLEX
    private String nomeComercial;
    private String nomeFantasia;
    //selecao de cores
    //Cores Basicas VERMELHO AZUL AMARELO ROSEO
    private String cor;
    //edit text
    //dipirona monoidratada + citrato de orfenadrina + cafeína anidra
    private String principioAtivo;
    //edit text
    //Sanofi Medley Farmacêutica Ltda
    //Laboratorios fernao dias
    private String fabricante;
    //dose dos principios ativos
    //Comprimido 300mg + 35mg + 50mg
    //caixa de texto
    //Solucao de 350ml
    //Frasco de 30ml
    //Creme de contato
    private String formaApresentacao;

    //caixa de texto
    //Cada comprimido contém 300 mg de dipirona monoidratada, 35 mg de citrato de orfenadrina (equivalente a 20,4                                                                                             mg de orfenadrina base) e 50 mg de cafeína anidra.
    //Excipientes: amido de milho, amidoglicolato de sódio, talco e estearato de magnésio.
    private String composicao;
    //oral injetavel topico
    private String viaAdministracao;
    //
    private String publicoAlvo;
    //
    //DORFLEX é indicado no alívio da dor associada a contraturas musculares, incluindo dor de cabeça tensiona
    private String textoParaQueIndicado;
    //DORFLEX possui ação analgésica e relaxante muscular. O início da ação ocorre a partir de 30 minutos
    private String textoComoFunciona;
    //Você deve tomar os comprimidos com líquido (aproximadamente ½ a 1 copo), por via oral.
    //Posologia: 1 a 2 comprimidos, 3 a 4 vezes ao dia. Não ultrapassar estes limites.
    //Não há estudos dos efeitos de DORFLEX administrado por vias não recomendadas. Portanto, por segurança e para
    //eficácia deste medicamento, a administração deve ser somente pela via oral.
    //Siga corretamente o modo de usar. Em caso de dúvidas sobre este medicamento, procure orientação do
    //farmacêutico. Não desaparecendo os sintomas, procure orien tação de seu médico ou cirurgião-dentista.
    //Este medicamento não deve ser partido, aberto ou mastigado
    private String textoComoUsar;
    //Baseando-se nos sintomas, reintroduzir a medicação respeitando sempre os horários e intervalos recomendados.
    //Nunca devem ser administradas duas doses ao mesmo tempo.
    private String textoSeEsquecerQueFazer;
    //nao aparece
    private String idProdutoAnvisa;
    //nao aparece
    private String dataProdutoAnvisa;
    //nao aparece
    private String idUsuario;

    private int qtdeEmbalagem;

    private List<Horario> horarios;

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
        this.horarios = new ArrayList<>();
        for (HorarioDTO horarioDTO : medicamentoDTO.getHorarios())
        {
            this.horarios.add(new Horario(horarioDTO));
        }
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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

    public String getFormaApresentacao() {
        return formaApresentacao;
    }

    public void setFormaApresentacao(String formaApresentacao) {
        this.formaApresentacao = formaApresentacao;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getViaAdministracao() {
        return viaAdministracao;
    }

    public void setViaAdministracao(String viaAdministracao) {
        this.viaAdministracao = viaAdministracao;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
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

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public int getQtdeEmbalagem() {
        return qtdeEmbalagem;
    }

    public void setQtdeEmbalagem(int qtdeEmbalagem) {
        this.qtdeEmbalagem = qtdeEmbalagem;
    }
}
package br.ufpa.app.android.amu.v1.integracao.dto;

import java.util.ArrayList;
import java.util.List;

public class ConsultarMedicamentoRetDTO {

    private boolean operacaoExecutada;
    private String mensagemExecucao;
    private List<MedicamentoRetDTO> medicamentos;

    public ConsultarMedicamentoRetDTO(boolean operacaoExecutada, String mensagemExecucao) {
        this.operacaoExecutada = operacaoExecutada;
        this.mensagemExecucao = mensagemExecucao;
        this.medicamentos = new ArrayList<>();
    }

    public boolean isOperacaoExecutada() {
        return operacaoExecutada;
    }

    public void setOperacaoExecutada(boolean operacaoExecutada) {
        this.operacaoExecutada = operacaoExecutada;
    }

    public String getMensagemExecucao() {
        return mensagemExecucao;
    }

    public void setMensagemExecucao(String mensagemExecucao) {
        this.mensagemExecucao = mensagemExecucao;
    }

    public List<MedicamentoRetDTO> getMedicamentos() {
        return medicamentos;
    }

}

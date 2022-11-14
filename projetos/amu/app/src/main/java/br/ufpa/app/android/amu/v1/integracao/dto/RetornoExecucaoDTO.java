package br.ufpa.app.android.amu.v1.integracao.dto;

/**
 * Created by Carlos on 12/11/2022.
 */
public class RetornoExecucaoDTO {

    private boolean operacaoExecutada;

    private String mensagemExecucao;

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
}

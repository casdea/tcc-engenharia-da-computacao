package br.ufpa.app.android.amu.v1.dto;

public class VariacoesComandoDTO {
    private String idVariacaoComando;
    private String idComando;
    private String idUsuario;
    private String texto;

    public String getIdVariacaoComando() {
        return idVariacaoComando;
    }

    public void setIdVariacaoComando(String idVariacaoComando) {
        this.idVariacaoComando = idVariacaoComando;
    }

    public String getIdComando() {
        return idComando;
    }

    public void setIdComando(String idComando) {
        this.idComando = idComando;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}

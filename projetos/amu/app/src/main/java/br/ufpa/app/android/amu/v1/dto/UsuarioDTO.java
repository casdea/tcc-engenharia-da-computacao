package br.ufpa.app.android.amu.v1.dto;

public class UsuarioDTO  {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String tipoPerfil;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String idUsuario, String nome, String email, String senha, String tipoPerfil) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoPerfil = tipoPerfil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }
}

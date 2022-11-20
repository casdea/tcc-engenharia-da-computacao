package br.ufpa.app.android.amu.v1.dto;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;

public class UsuarioDTO extends AbstractEntity {

    private long id;
    private String nome;
    private String email;
    private String senha;
    private String tipoPerfil;

    public UsuarioDTO() {
    }

    public UsuarioDTO(long id, String nome, String email, String senha, String tipoPerfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoPerfil = tipoPerfil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

package br.ufpa.app.android.amu.v1.dao.modelo;

import com.google.firebase.database.Exclude;

import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntity;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;

public class Usuario extends AbstractEntity {

    private String idUsuario;
    private String nome;
    private String email;
    private String tipoPerfil;

    public Usuario() {
        nomeTabela = "usuarios";
    }

    public Usuario(UsuarioDTO usuarioDTO) {
        nomeTabela = "usuarios";
        this.idUsuario = usuarioDTO.getIdUsuario();
        this.nome = usuarioDTO.getNome();
        this.email = usuarioDTO.getEmail();
        this.tipoPerfil = usuarioDTO.getTipoPerfil();
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }
}

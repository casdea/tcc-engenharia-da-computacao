package br.ufpa.app.android.amu.v1.dao.infraestrutura;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
    protected String nomeTabela;

    @Exclude
    public String getNomeTabela() {
        return nomeTabela;
    }
}


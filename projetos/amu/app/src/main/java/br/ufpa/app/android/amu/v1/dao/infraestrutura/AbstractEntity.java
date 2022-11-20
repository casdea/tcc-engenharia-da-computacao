package br.ufpa.app.android.amu.v1.dao.infraestrutura;

import java.io.Serializable;

import br.ufpa.app.android.amu.v1.dao.exception.InfraStructureException;

public abstract class AbstractEntity implements Serializable {
    protected String nomeTabela;

    public String getNomeTabela() {
        return nomeTabela;
    }
}


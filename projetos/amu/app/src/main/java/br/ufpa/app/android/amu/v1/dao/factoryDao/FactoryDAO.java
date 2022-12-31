package br.ufpa.app.android.amu.v1.dao.factoryDao;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.dao.idao.IAlarmeDao;
import br.ufpa.app.android.amu.v1.dao.idao.IEstoqueDao;
import br.ufpa.app.android.amu.v1.dao.idao.IHorarioDao;
import br.ufpa.app.android.amu.v1.dao.idao.IMedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.idao.IUtilizacaoDao;
import br.ufpa.app.android.amu.v1.dao.impl.AlarmeDao;
import br.ufpa.app.android.amu.v1.dao.impl.EstoqueDao;
import br.ufpa.app.android.amu.v1.dao.impl.HorarioDao;
import br.ufpa.app.android.amu.v1.dao.impl.MedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.impl.UsuarioDao;
import br.ufpa.app.android.amu.v1.dao.impl.UtilizacaoDao;

public class FactoryDAO {

    private DatabaseReference em;
    private AppCompatActivity atividade;
    private Callable proximoComando;

    public FactoryDAO(DatabaseReference em, AppCompatActivity atividade)
    {
        this.em = em;
        this.atividade = atividade;
    }

    public FactoryDAO(DatabaseReference em, AppCompatActivity atividade, Callable proximoComando)
    {
        this.em = em;
        this.atividade = atividade;
        this.proximoComando = proximoComando;
    }

    private IUsuarioDao usuarioDao;

    public IUsuarioDao getUsuarioDao()
    {
        if (usuarioDao == null)
        {
            usuarioDao = new UsuarioDao(em, atividade);
        }
        return usuarioDao;
    }

    private IMedicamentoDao medicamentoDao;

    public IMedicamentoDao getMedicamentosDao()
    {
        if (medicamentoDao == null)
        {
            medicamentoDao = new MedicamentoDao(em, atividade);
        }
        return medicamentoDao;
    }

    private IHorarioDao horarioDao;

    public IHorarioDao getHorarioDao()
    {
        if (horarioDao == null)
        {
            horarioDao = new HorarioDao(em, atividade, proximoComando);
        }
        return horarioDao;
    }


    private IUtilizacaoDao utilizacaoDao;

    public IUtilizacaoDao getUtilizacaoDao()
    {
        if (utilizacaoDao == null)
        {
            utilizacaoDao = new UtilizacaoDao(em, atividade, proximoComando);
        }
        return utilizacaoDao;
    }

    private IEstoqueDao estoqueDao;

    public IEstoqueDao getEstoqueDao()
    {
        if (estoqueDao == null)
        {
            estoqueDao = new EstoqueDao(em, atividade, proximoComando);
        }
        return estoqueDao;
    }

    private IAlarmeDao alarmeDao;

    public IAlarmeDao getAlarmeDao()
    {
        if (alarmeDao == null)
        {
            alarmeDao = new AlarmeDao(em, atividade, proximoComando);
        }
        return alarmeDao;
    }

}

package br.ufpa.app.android.amu.v1.dao.factoryDao;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import br.ufpa.app.android.amu.v1.dao.idao.IHorarioDao;
import br.ufpa.app.android.amu.v1.dao.idao.IMedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.idao.IUsuarioDao;
import br.ufpa.app.android.amu.v1.dao.impl.HorarioDao;
import br.ufpa.app.android.amu.v1.dao.impl.MedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.impl.UsuarioDao;

public class FactoryDAO {

    private DatabaseReference em;
    private AppCompatActivity atividade;

    public FactoryDAO(DatabaseReference em, AppCompatActivity atividade)
    {
        this.em = em;
        this.atividade = atividade;
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
            horarioDao = new HorarioDao(em, atividade);
        }
        return horarioDao;
    }

}

package br.ufpa.app.android.amu.v1.classes;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.factoryDao.FactoryDAO;
import br.ufpa.app.android.amu.v1.util.App;

public class ObterListaHorariosTask implements Callable<Integer> {

    private final DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();
    private final AppCompatActivity atividade;
    private final Callable proximoComando;

    public ObterListaHorariosTask(AppCompatActivity atividade, Callable proximoComando) {
        this.atividade = atividade;
        this.proximoComando = proximoComando;
    }

    @Override
    public Integer call() {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade, proximoComando);
        factoryDAO.getHorarioDao().findAllByUsuario(App.usuario.getIdUsuario());
        return 0;
    }
}


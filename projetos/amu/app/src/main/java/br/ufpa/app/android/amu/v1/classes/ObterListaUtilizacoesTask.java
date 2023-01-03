package br.ufpa.app.android.amu.v1.classes;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.factoryDao.FactoryDAO;
import br.ufpa.app.android.amu.v1.util.App;

public class ObterListaUtilizacoesTask implements Callable<Integer> {

    private DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();
    private AppCompatActivity atividade;
    private Callable proximoComando;

    public ObterListaUtilizacoesTask(AppCompatActivity atividade, Callable proximoComando) {
        this.atividade = atividade;
        this.proximoComando = proximoComando;
    }

    @Override
    public Integer call() {
        FactoryDAO factoryDAO = new FactoryDAO(em, atividade, proximoComando);
        factoryDAO.getUtilizacaoDao().findAllByUsuario(App.usuario.getIdUsuario());
        return 0;
    }
}
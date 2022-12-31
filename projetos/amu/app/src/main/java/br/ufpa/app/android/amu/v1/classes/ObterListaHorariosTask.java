package br.ufpa.app.android.amu.v1.classes;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;

public class ObterListaHorariosTask implements Callable<Integer> {

    private AppCompatActivity atividade;
    private Callable proximoComando;

    public ObterListaHorariosTask(AppCompatActivity atividade, Callable proximoComando) {
        this.atividade = atividade;
        this.proximoComando = proximoComando;
    }

    @Override
    public Integer call() {
        GerenteServicos gerenteServicos = new GerenteServicos(atividade, proximoComando);
        gerenteServicos.obterListaHorariosByUsuario(App.usuario.getIdUsuario());
        return 0;
    }
}


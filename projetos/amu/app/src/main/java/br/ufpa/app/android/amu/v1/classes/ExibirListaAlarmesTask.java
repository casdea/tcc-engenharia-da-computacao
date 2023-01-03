package br.ufpa.app.android.amu.v1.classes;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.util.App;

public class ExibirListaAlarmesTask implements Callable<Integer> {

    private AppCompatActivity atividade;
    private List<MedicamentoDTO> medicamentos;
    private TextView txvCadastrados;
    private TextView txvNaoAdministrado;

    public ExibirListaAlarmesTask(AppCompatActivity atividade, List<MedicamentoDTO> medicamentos, TextView txvCadastrados, TextView txvNaoAdministrado) {
        this.atividade = atividade;
        this.medicamentos = medicamentos;
        this.txvCadastrados = txvCadastrados;
        this.txvNaoAdministrado = txvNaoAdministrado;
    }

    @Override
    public Integer call() throws InterruptedException {
        GerenteAlarme gerenteAlarme = new GerenteAlarme(atividade, medicamentos, App.listaUtilizacoes, App.listaAlarmes);
        gerenteAlarme.verificar(txvCadastrados, txvNaoAdministrado);
        return 0;
    }
}
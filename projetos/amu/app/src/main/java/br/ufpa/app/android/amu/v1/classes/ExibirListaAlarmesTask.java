package br.ufpa.app.android.amu.v1.classes;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.util.App;

public class ExibirListaAlarmesTask implements Callable<Integer> {

    private final AppCompatActivity atividade;
    private final List<MedicamentoDTO> medicamentos;
    private final TextView txvQtdeCadastrados;
    private final TextView txvQtdeNaoAdministrado;

    public ExibirListaAlarmesTask(AppCompatActivity atividade, List<MedicamentoDTO> medicamentos, TextView txvQtdeCadastrados, TextView txvQtdeNaoAdministrado) {
        this.atividade = atividade;
        this.medicamentos = medicamentos;
        this.txvQtdeCadastrados = txvQtdeCadastrados;
        this.txvQtdeNaoAdministrado = txvQtdeNaoAdministrado;
    }

    @Override
    public Integer call() throws InterruptedException {
        GerenteAlarme gerenteAlarme = new GerenteAlarme(atividade, medicamentos, App.listaUtilizacoes, App.listaAlarmes);
        gerenteAlarme.verificar(txvQtdeCadastrados, txvQtdeNaoAdministrado);
        return 0;
    }
}
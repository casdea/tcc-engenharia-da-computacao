package br.ufpa.app.android.amu.v1.classes;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.interfaces.Transacao;

public class TransacaoVerificarAlarme implements Transacao {

    private final AppCompatActivity atividade;
    private final List<MedicamentoDTO> medicamentos;
    private final TextView txvQtdeCadastrados;
    private final TextView txvQtdeNaoAdministrado;

    public TransacaoVerificarAlarme(AppCompatActivity atividade, List<MedicamentoDTO> medicamentos, TextView txvQtdeCadastrados, TextView txvQtdeNaoAdministrado) {
        this.atividade = atividade;
        this.medicamentos = medicamentos;
        this.txvQtdeCadastrados = txvQtdeCadastrados;
        this.txvQtdeNaoAdministrado = txvQtdeNaoAdministrado;
    }

    @Override
    public void executar() throws InterruptedException {
        new ObterListaHorariosTask(
                atividade,
                  new ObterListaUtilizacoesTask(atividade,
                     new ObterListaAlarmesTask(atividade,
                        new ExibirListaAlarmesTask(atividade,
                                  medicamentos, txvQtdeCadastrados, txvQtdeNaoAdministrado)))).call();
        System.out.println("Tarefa completa!");
    }
}

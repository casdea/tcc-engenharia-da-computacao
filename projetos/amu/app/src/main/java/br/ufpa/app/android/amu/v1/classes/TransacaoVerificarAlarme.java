package br.ufpa.app.android.amu.v1.classes;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.interfaces.Transacao;

public class TransacaoVerificarAlarme implements Transacao {

    private AppCompatActivity atividade;
    private List<MedicamentoDTO> medicamentos;
    private TextView txvCadastrados;
    private TextView txvNaoAdministrado;

    public TransacaoVerificarAlarme(AppCompatActivity atividade, List<MedicamentoDTO> medicamentos, TextView txvCadastrados, TextView txvNaoAdministrado) {
        this.atividade = atividade;
        this.medicamentos = medicamentos;
        this.txvCadastrados = txvCadastrados;
        this.txvNaoAdministrado = txvNaoAdministrado;
    }

    @Override
    public void executar() throws InterruptedException {
        new ObterListaHorariosTask(
                atividade,
                  new ObterListaUtilizacoesTask(atividade,
                     new ObterListaAlarmesTask(atividade,
                        new ExibirListaAlarmesTask(atividade,
                                  medicamentos, txvCadastrados, txvNaoAdministrado)))).call();
        System.out.println("Tarefa completa!");
    }
}

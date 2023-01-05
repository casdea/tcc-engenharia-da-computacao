package br.ufpa.app.android.amu.v1.integracao.api.usuario.comum;

import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;
import br.ufpa.app.android.amu.v1.util.App;

public class IntegracaoUsuarioComum implements IntegracaoUsuario {
    @Override
    public int findComando(String texto) {
        return 0;
    }

    @Override
    public void bemVindoFuncao(TipoFuncao tipoFuncao) {

    }

    @Override
    public void capturarComandoIniciado() {

    }

    @Override
    public void capturarComandoEncerrado() {

    }

    @Override
    public void exibirMedicamentosEncontrados(TextToSpeech textoLido, List<MedicamentoRetDTO> medicamentos, String argumento) {

    }

    @Override
    public void avisarListaVazia() {

    }

    @Override
    public boolean lerTexto() {
        return false;
    }

    @Override
    public void comandoNaoReconhecido(String comandoInformado) {

    }

    @Override
    public void listarMedicamentos(List<MedicamentoDTO> medicamentos) {

    }

    @Override
    public MedicamentoDTO descrerverMedicamento(List<MedicamentoDTO> medicamentos, String s) {
        return null;

    }

    @Override
    public void descrerverHorario(MedicamentoDTO medicamentoDTO, List<HorarioDTO> horarios) {

    }

    @Override
    public void tenteNovamenteComandoVoz() {

    }

    @Override
    public void falar(String texto) {

    }

    @Override
    public boolean validarUtilizacaoMedicamento(List<HorarioDTO> horarios) {
        if (horarios == null || horarios.size() <= 0) {
            Toast.makeText(App.context,
                    "Cadastre um horário para o medicamento e o ative !",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (!horarios.get(horarios.size() - 1).getAtivo().equals("SIM")) {
            Toast.makeText(App.context,
                    "O ultimo horário deve está ativo !",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void saidaNegadaSemSaldo() {
        Toast.makeText(App.context,
                "Saldo negativo saida negada !",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void informarErro(String parametro) {
        Toast.makeText(App.context,parametro, Toast.LENGTH_LONG).show();

    }

    @Override
    public void utilizacaoRemedioConcluida(MedicamentoDTO medicamentoDTO) {
        Toast.makeText(App.context,"Utilização de Remédio "+medicamentoDTO.getNomeFantasia()+" registrada com sucesso !", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean validarEntradaEstoque(String qtde) {
        if (qtde.isEmpty()) {
            Toast.makeText(App.context,
                    "Preencha a quantidade de compra do medicamento !",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (qtde.equals("0")) {
            Toast.makeText(App.context,
                    "Preencha a quantidade de compra do medicamento !",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean validarSaidaEstoque(String qtde) {
        if (qtde.isEmpty()) {
            Toast.makeText(App.context,
                    "Preencha a quantidade de saída do medicamento !",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (qtde.equals("0")) {
            Toast.makeText(App.context,
                    "Preencha a quantidade de saída do medicamento !",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    @Override
    public int obterQtde(String s, int acao) {
        return 0;
    }

    @Override
    public void informarSaldoEstoque() {

    }

    @Override
    public void avisarSaldoAtualizadoComSucesso(int novoSaldo) {
        Toast.makeText(App.context,
                "Saldo atualizado com sucesso. Estoque atual: " + novoSaldo,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void avisoEntradaPerfilAdmin() {

    }

    @Override
    public void avisoSaidaPerfilAdmin() {

    }

    @Override
    public void avisarSaidaApp() {

    }

    @Override
    public void dispararAlarme(String descricao) {

    }
}

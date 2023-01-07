package br.ufpa.app.android.amu.v1.integracao.interfaces;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;

public interface IntegracaoUsuario {
    int findComando(String texto);
    void bemVindoFuncao(TipoFuncao tipoFuncao);
    void capturarComandoIniciado();
    void capturarComandoEncerrado();

    void exibirMedicamentosEncontrados(List<MedicamentoRetDTO> medicamentos, String argumento);

    void avisarListaVazia();

    boolean lerTexto();

    void comandoNaoReconhecido(String comandoInformado);

    void listarMedicamentos(List<MedicamentoDTO> medicamentos);

    MedicamentoDTO descrerverMedicamento(List<MedicamentoDTO> medicamentos, String s);

    void descrerverHorario(MedicamentoDTO medicamentoDTO, List<HorarioDTO> horarios);

    void tenteNovamenteComandoVoz();

    void falar(String texto);

    boolean validarUtilizacaoMedicamento(List<HorarioDTO> horarios);

    void saidaNegadaSemSaldo();

    void informarErro(String parametro);

    void utilizacaoRemedioConcluida(MedicamentoDTO medicamentoDTO);

    boolean validarEntradaEstoque(String qtde);

    boolean validarSaidaEstoque(String qtde);

    int obterQtde(String s, int acao);

    void informarSaldoEstoque();

    void avisarSaldoAtualizadoComSucesso(int novoSaldo);

    void avisoEntradaPerfilAdmin();

    void avisoSaidaPerfilAdmin();

    void avisarSaidaApp();

    void dispararAlarme(String descricao);
}

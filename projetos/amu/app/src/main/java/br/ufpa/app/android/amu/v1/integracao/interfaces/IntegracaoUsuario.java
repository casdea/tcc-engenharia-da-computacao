package br.ufpa.app.android.amu.v1.integracao.interfaces;

import android.speech.tts.TextToSpeech;

import java.util.List;

import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;

public interface IntegracaoUsuario {
    public void bemVindo();
    public int findComando(String texto);
    public void pararMensagem();
    public void bemVindoFuncao(TipoFuncao tipoFuncao);
    public void capturarComandoIniciado();
    public void capturarComandoEncerrado();

    void instrucaoParaUsuario(int idSom);

    void exibirMedicamentosEncontrados(TextToSpeech textoLido, List<MedicamentoRetDTO> medicamentos, String argumento);

    void avisarListaVazia();

    boolean lerTexto();

    void comandoNaoReconhecido(String comandoInformado);

    void listarMedicamentos(List<MedicamentoDTO> medicamentos);

    MedicamentoDTO descrerverMedicamento(List<MedicamentoDTO> medicamentos, String s);

    public void descrerverHorario(MedicamentoDTO medicamentoDTO, List<HorarioDTO> horarios);

    void tenteNovamenteComandoVoz();

    public void falar(String texto);

    public MedicamentoDTO findMedicamentoByAcaoVoz(List<MedicamentoDTO> medicamentos, String s, int acao);

    boolean validarUtilizacaoMedicamento(List<HorarioDTO> horarios);

    void saidaNegadaSemSaldo();

    void informarErro(String parametro);

    void utilizacaoRemedioConcluida(MedicamentoDTO medicamentoDTO);

    boolean validarEntradaEstoque(String qtde);

    boolean validarSaidaEstoque(String qtde);

    int obterQtde(String s, int acao);

    public void informarSaldoEstoque();

    void avisarSaldoAtualizadoComSucesso(int novoSaldo);

    void avisoEntradaPerfilAdmin();

    void avisoSaidaPerfilAdmin();

    void avisarSaidaApp();

    void dispararAlarme(String descricao);
}

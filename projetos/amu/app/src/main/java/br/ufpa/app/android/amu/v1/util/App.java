package br.ufpa.app.android.amu.v1.util;

import android.app.Application;
import android.content.Context;
import android.view.View;

import java.util.List;

import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.dto.ComandoDTO;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.dto.VariacoesComandoDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.FontesConsulta;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;

/**
 * Created by joao on 12/11/2022.
 */
public class App extends Application {

    public static Context context;
    public static String mensagemExecucao;
    public static TipoPerfil tipoPerfil;
    public static FontesConsulta fontesConsulta;
    public static IntegracaoUsuario integracaoUsuario;
    public static IntegracaoBularioEletronico integracaoBularioEletronico;
    public static TipoFuncao comandoAtualVoz;
    public static Usuario usuario;
    public static MedicamentoDTO medicamentoDTO;
    public static HorarioDTO horarioDTO;
    public static UtilizacaoDTO utilizacaoDTO;
    public static List<HorarioDTO> listaHorarios;
    public static List<UtilizacaoDTO> listaUtilizacoes;
    public static EstoqueDTO estoqueDTO;
    public static List<EstoqueDTO> listaEstoques;
    public static ComandoDTO comandoDTO;
    public static VariacoesComandoDTO variacoesComandoDTO;
    public static List<ComandoDTO> listaComandos;
    public static List<VariacoesComandoDTO> listaVariacoesComandos;
}
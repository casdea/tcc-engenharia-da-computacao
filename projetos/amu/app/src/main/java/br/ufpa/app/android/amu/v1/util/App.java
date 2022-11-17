package br.ufpa.app.android.amu.v1.util;

import android.app.Application;
import android.content.Context;

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
}
package br.ufpa.app.android.amu.v1.util;

import android.app.Application;
import android.content.Context;

import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;

/**
 * Created by joao on 12/11/2022.
 */
public class App extends Application {

    public static Context context;
    public static String mensagemExecucao;
    public static TipoPerfil tipoPerfil;
}
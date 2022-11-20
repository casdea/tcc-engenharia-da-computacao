package br.ufpa.app.android.amu.v1.dao.config;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by jamiltondamasceno
 */

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){

        if( autenticacao == null ){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;

    }

}

package br.ufpa.app.android.amu.v1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.integracao.classes.FontesConsulta;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;

public class BemVindoActivity extends IntroActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.context = this;
        App.fontesConsulta = FontesConsulta.ANVISA;

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        //findViewById(R.id.btnCadastrar).setOnClickListener(this);
        //findViewById(R.id.txvEntrar).setOnClickListener(this);

        addSlide( new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .build());
    }


    @Override
    protected void onStart() {
        super.onStart();
        GerenteServicos gerenteServicos = new GerenteServicos();
        gerenteServicos.verificarUsuarioLogado(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txvEntrar)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
        else if (view.getId() == R.id.btnCadastrar)
        {
            App.usuario = null;
            startActivity(new Intent(this, UsuarioActivity.class));
        }
    }
}
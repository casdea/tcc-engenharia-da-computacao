package br.ufpa.app.android.amu.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.ufpa.app.android.amu.v1.integracao.api.bularioEletronico.BularioEletronicoClientRetrofit;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracao;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoUsuario;

public class ConsultaMedicamentoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_medicamento);

        Button btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(this);

        //IntegracaoUsuario integracaoUsuario = new FactoryIntegracao().createIntegracaoUsuario("COMUM");
        //integracaoUsuario.consultarDadosMedicamentos();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPesquisar) {
            BularioEletronicoClientRetrofit bularioEletronicoClientRetrofit = new BularioEletronicoClientRetrofit();
            bularioEletronicoClientRetrofit.obter();

        }
    }
}
package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.util.App;

public class DetalheAnvisaActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> cadastrarMedicamentoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        setResult(Activity.RESULT_OK, null);
                        finish();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_anvisa);

        //apontar referencias
        TextView txvNomeComercial = findViewById(R.id.txvNomeComercial);
        TextView txvNomeFabricante = findViewById(R.id.txvNomeFabricante);
        TextView txvPrincipioAtivo = findViewById(R.id.txvPrincipioAtivo);
        TextView txvFormaApresentacao = findViewById(R.id.txvFormaApresentacao);
        TextView txvViaAdministracao = findViewById(R.id.txvViaAdministracao);
        TextView txvPublicoAlvo = findViewById(R.id.txvPublicoAlvo);
        TextView txvTextoComposicao = findViewById(R.id.txvTextoComposicao);
        TextView txvTextoParaQue = findViewById(R.id.txvTextoParaQue);
        TextView txvTextoComoFunciona = findViewById(R.id.txvTextoComoFunciona);
        TextView txvTextoComoDevoUsar = findViewById(R.id.txvTextoComoDevoUsar);
        TextView txvTextoQuandoEsquecer = findViewById(R.id.txvTextoQuandoEsquecer);
        txvNomeFabricante = findViewById(R.id.txvNomeFabricante);
        //preencher dados
        txvNomeComercial.setText(App.medicamentoDTO.getNomeComercial());
        txvNomeFabricante.setText(App.medicamentoDTO.getFabricante());
        txvPrincipioAtivo.setText(App.medicamentoDTO.getPrincipioAtivo());
        txvFormaApresentacao.setText(App.medicamentoDTO.getFormaApresentacao());
        txvViaAdministracao.setText(App.medicamentoDTO.getViaAdministracao());
        txvPublicoAlvo.setText(App.medicamentoDTO.getPublicoAlvo());
        txvTextoComposicao.setText(App.medicamentoDTO.getComposicao());
        txvTextoParaQue.setText(App.medicamentoDTO.getTextoParaQueIndicado());
        txvTextoComoFunciona.setText(App.medicamentoDTO.getTextoComoFunciona());
        txvTextoComoDevoUsar.setText(App.medicamentoDTO.getTextoComoUsar());
        txvTextoQuandoEsquecer.setText(App.medicamentoDTO.getTextoSeEsquecerQueFazer());

        findViewById(R.id.btnNovaConsulta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btnCapturarDados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(DetalheAnvisaActivity.this, MedicamentoActivity.class);
                cadastrarMedicamentoActivityResultLauncher.launch(intent);

            }
        });


    }
}
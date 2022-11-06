
package br.ufpa.app.android.amu.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCadastroMedicamento = (Button) findViewById(R.id.btnCadastroMedicamento);
        btnCadastroMedicamento.setOnClickListener(this);

        Button btnConsultaMedicamento = (Button) findViewById(R.id.btnConsultaMedicamento);
        btnConsultaMedicamento.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCadastroMedicamento) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MedicamentoActivity.class);
            startActivity(intent);
        }
        else
        if (v.getId() == R.id.btnConsultaMedicamento) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ConsultaMedicamentoActivity.class);
            startActivity(intent);
        }
    }
}
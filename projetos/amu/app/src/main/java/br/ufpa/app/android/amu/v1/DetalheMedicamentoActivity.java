package br.ufpa.app.android.amu.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class DetalheMedicamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_medicamento);

        EditText edtTextoBula = (EditText) findViewById(R.id.edtTextoBula);
        String texto = getIntent().getStringExtra("texto");
        edtTextoBula.setText(texto);

    }
}
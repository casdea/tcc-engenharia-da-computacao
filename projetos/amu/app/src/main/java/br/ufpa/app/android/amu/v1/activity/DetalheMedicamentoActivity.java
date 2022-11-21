package br.ufpa.app.android.amu.v1.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.ufpa.app.android.amu.v1.R;

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
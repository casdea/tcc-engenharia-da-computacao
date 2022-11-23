package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import br.ufpa.app.android.amu.v1.R;

public class MedicamentoActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> selecionarCorActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        String cor = data.getStringExtra("cor");
                        //findViewById(R.id.editTextTextPersonName3).setBackgroundDrawable(new ColorDrawable(Color.parseColor(cor)));
                        findViewById(R.id.txvCorSelecionada).setBackground(new ColorDrawable(Color.parseColor(cor)));
                        //use setBackground(android.graphics.drawable.Drawable)
                    }
                }
            });

   /* private String[] horarios = new String[]{
            "6 horas",    "8 horas", "12 horas", "24 horas",
            "1 hora",    "2 horas",  "3 horas",  "4 horas",  "5 horas",
            "6 horas",   "7 horas",  "8 horas",  "9 horas", "10 horas",
            "11 horas", "12 horas", "13 horas", "14 horas", "15 horas",
            "16 horas", "18 horas", "19 horas", "20 horas", "21 horas",
            "22 horas", "23 horas", "24 horas"};
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        findViewById(R.id.txvCorSelecionada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentoActivity.this, PaletaCoresActivity.class);
                selecionarCorActivityResultLauncher.launch(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.horarios, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spIntervalos = findViewById(R.id.spIntervalos);
        spIntervalos.setAdapter(adapter);

        spIntervalos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}


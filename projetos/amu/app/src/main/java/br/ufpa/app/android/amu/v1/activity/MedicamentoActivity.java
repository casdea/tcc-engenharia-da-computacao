package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.fragments.DatePickerFragment;
import br.ufpa.app.android.amu.v1.fragments.TimePickerFragment;
import br.ufpa.app.android.amu.v1.helper.PaletaCoresActivity;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.interfaces.PickDateListener;
import br.ufpa.app.android.amu.v1.interfaces.PickTimeListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.DataUtil;
import br.ufpa.app.android.amu.v1.util.StringUtil;

public class MedicamentoActivity extends AppCompatActivity implements PickDateListener, PickTimeListener, GerenteServicosListener {

    private String cor;

    private ActivityResultLauncher<Intent> selecionarCorActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        cor = data.getStringExtra("cor");
                        //findViewById(R.id.editTextTextPersonName3).setBackgroundDrawable(new ColorDrawable(Color.parseColor(cor)));
                        findViewById(R.id.txvCorSelecionada).setBackground(new ColorDrawable(Color.parseColor(cor)));
                        //use setBackground(android.graphics.drawable.Drawable)
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        TextView txvNomeComercial = findViewById(R.id.txvNomeComercial);
        TextView txvNomeFabricante = findViewById(R.id.txvNomeFabricante);
        TextView txvViaAdministracao = findViewById(R.id.txvViaAdministracao);

        txvNomeComercial.setText(App.medicamentoDTO.getNomeComercial());
        txvNomeFabricante.setText(App.medicamentoDTO.getFabricante());
        txvViaAdministracao.setText(App.medicamentoDTO.getViaAdministracao());

        this.cor = null;

        findViewById(R.id.txvCorSelecionada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentoActivity.this, PaletaCoresActivity.class);
                selecionarCorActivityResultLauncher.launch(intent);
            }
        });

        findViewById(R.id.textInpTextInicioAdministracao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        findViewById(R.id.textInpTextHorarioPrimeiraDose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        //textInpTextPrimeiraDose

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

        findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText textInpTextApelido = findViewById(R.id.textInpTextApelido);
                TextInputEditText textInpTextQtdeEmbalagem = findViewById(R.id.textInpTextQtdeEmbalagem);
                TextInputEditText textInpTextInicioAdministracao = findViewById(R.id.textInpTextInicioAdministracao);
                TextInputEditText textInpTextHorarioPrimeiraDose =  findViewById(R.id.textInpTextHorarioPrimeiraDose);
                Spinner spIntervalos = findViewById(R.id.spIntervalos);
                TextInputEditText textInpTextDosesDia = findViewById(R.id.textInpTextDosesDia);
                TextInputEditText textInpTextQtdeDose = findViewById(R.id.textInpTextQtdeDose);
                Switch swAtivo = findViewById(R.id.swAtivo);

                if (textInpTextApelido.getText().toString().isEmpty()) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Preencha o apelido do medicamento !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (textInpTextQtdeEmbalagem.getText().toString().isEmpty()) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Preencha Quantidade por Embalagem !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (textInpTextInicioAdministracao.getText().toString().isEmpty()) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Escolha uma data de Inicio de Administração do Medicamento !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (!DataUtil.isDataValida(textInpTextInicioAdministracao.getText().toString())) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Data de Inicio de Administração do Medicamento inválida !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (textInpTextHorarioPrimeiraDose.getText().toString().isEmpty()) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Escolha uma Hora Inicio da Primeira Dose !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (spIntervalos.getSelectedItem()==null) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Escolha um intervalo entre as doses !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (textInpTextDosesDia.getText().toString().isEmpty()) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Preencha o numero de doses diárias !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (textInpTextQtdeDose.getText().toString().isEmpty()) {
                    Toast.makeText(MedicamentoActivity.this,
                            "Preencha Quantidade por dose !",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Log.i("Dados Cadastrados ","Medicamentos");
                Log.i("Apelido do Medicamento ",textInpTextApelido.getText().toString());
                Log.i("Qtde Embalagem ",textInpTextQtdeEmbalagem.getText().toString());
                Log.i("Data Inicio ",textInpTextInicioAdministracao.getText().toString());
                Log.i("Hora Inicio ",textInpTextHorarioPrimeiraDose.getText().toString());
                Log.i("Intervalo ",spIntervalos.getSelectedItem().toString());
                Log.i("Dose/Dia ",textInpTextDosesDia.getText().toString());
                Log.i("Qtde Dose ",textInpTextQtdeDose.getText().toString());
                Log.i("Ativo ",swAtivo.isChecked() ? "SIM" : "NAO");

                App.medicamentoDTO.setNomeFantasia(textInpTextApelido.getText().toString());
                App.medicamentoDTO.setCor(cor);
                App.medicamentoDTO.setIdUsuario(App.usuario.getIdUsuario());
                App.medicamentoDTO.setQtdeEmbalagem(Integer.parseInt(textInpTextQtdeEmbalagem.getText().toString()));
                App.medicamentoDTO.setEstoques(new ArrayList<>());
                App.medicamentoDTO.setUtilizacoes(new ArrayList<>());

                App.horarioDTO = new HorarioDTO();
                App.horarioDTO.setIdHorario(StringUtil.createId());
                App.horarioDTO.setIdUsuario(App.usuario.getIdUsuario());
                App.horarioDTO.setDataInicial(textInpTextInicioAdministracao.getText().toString());
                App.horarioDTO.setHorarioInicial(textInpTextHorarioPrimeiraDose.getText().toString());
                App.horarioDTO.setIntervalo(textInpTextHorarioPrimeiraDose.getText().toString());
                App.horarioDTO.setNrDoses(Integer.parseInt(textInpTextDosesDia.getText().toString()));
                App.horarioDTO.setQtdePorDose(Integer.parseInt(textInpTextQtdeDose.getText().toString()));
                App.horarioDTO.setAtivo(swAtivo.isChecked() ? "SIM" : "NAO");

                GerenteServicos gerenteServicos = new GerenteServicos(MedicamentoActivity.this);
                gerenteServicos.incluirMedicamento(App.medicamentoDTO, App.horarioDTO);

            }
        });
    }

    @Override
    public void atualizarDataListener(int day, int month, int year) {

        Date data = DataUtil.encodeDateByDiaMesAno(day, month, year);
        TextInputEditText textInpTextInicioAdministracao = findViewById(R.id.textInpTextInicioAdministracao);
        textInpTextInicioAdministracao.setText(DataUtil.convertDateToString(data));

        Log.i("Dia ",data.toString());
    }

    @Override
    public void atualizarHoraListener(int hourOfDay, int minute) {
        Date hora = DataUtil.encodeTimeByHoraMinuto(hourOfDay, minute);

        TextInputEditText textInpTextHorarioPrimeiraDose =  findViewById(R.id.textInpTextHorarioPrimeiraDose);
        textInpTextHorarioPrimeiraDose.setText(DataUtil.convertTimeToString(hora));

        Log.i("Hora ",hora.toString());
    }

    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {}

    @Override
    public void executarAcao(int numeroAcao, String[] parametros) {
      setResult(Activity.RESULT_OK, null);
      finish();
    }
}


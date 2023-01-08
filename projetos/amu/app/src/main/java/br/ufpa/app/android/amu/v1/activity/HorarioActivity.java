package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa.adapter.IntervaloAdapter;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.fragments.TimePickerFragment;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.interfaces.PickTimeListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;
import br.ufpa.app.android.amu.v1.util.StringUtil;

public class HorarioActivity extends AppCompatActivity implements PickTimeListener, GerenteServicosListener {

    TextInputEditText textInpTextInicioAdministracao;
    TextInputEditText textInpTextHorarioPrimeiraDose;
    TextInputEditText textInpTextDosesDia;
    TextInputEditText textInpTextQtdeDose;
    SwitchCompat swAtivo;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        textInpTextInicioAdministracao = findViewById(R.id.textInpTextInicioAdministracao);
        textInpTextHorarioPrimeiraDose =  findViewById(R.id.textInpTextHorarioPrimeiraDose);
        textInpTextDosesDia = findViewById(R.id.textInpTextDosesDia);
        textInpTextQtdeDose = findViewById(R.id.textInpTextQtdeDose);
        swAtivo = findViewById(R.id.swAtivo);

        textInpTextHorarioPrimeiraDose.setOnClickListener(view -> {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });

        textInpTextInicioAdministracao.addTextChangedListener(DataUtil.mask("##/##/####", textInpTextInicioAdministracao));
        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            textInpTextInicioAdministracao.setText("");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());

            textInpTextInicioAdministracao.setText(sdf.format(calendar.getTime()));
        };

        textInpTextInicioAdministracao.setOnFocusChangeListener((view, b) -> {
            if (b) {
                new DatePickerDialog(HorarioActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        Spinner spIntervalos = findViewById(R.id.spIntervalos);
        //spIntervalos.setAdapter(adapter);
        spIntervalos.setAdapter(new IntervaloAdapter(this, Constantes.intervalos));

        findViewById(R.id.btnConfirmar).setOnClickListener(view -> {

            if (Objects.requireNonNull(textInpTextInicioAdministracao.getText()).toString().isEmpty()) {
                Toast.makeText(HorarioActivity.this,
                        "Escolha uma data de Inicio de Administração do Medicamento !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (DataUtil.isDataInvalida(textInpTextInicioAdministracao.getText().toString())) {
                Toast.makeText(HorarioActivity.this,
                        "Data de Inicio de Administração do Medicamento inválida !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (Objects.requireNonNull(textInpTextHorarioPrimeiraDose.getText()).toString().isEmpty()) {
                Toast.makeText(HorarioActivity.this,
                        "Escolha uma Hora Inicio da Primeira Dose !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (spIntervalos.getSelectedItem()==null) {
                Toast.makeText(HorarioActivity.this,
                        "Escolha um intervalo entre as doses !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (Objects.requireNonNull(textInpTextDosesDia.getText()).toString().isEmpty()) {
                Toast.makeText(HorarioActivity.this,
                        "Preencha o numero de doses diárias !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (Objects.requireNonNull(textInpTextQtdeDose.getText()).toString().isEmpty()) {
                Toast.makeText(HorarioActivity.this,
                        "Preencha Quantidade por dose !",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (BuildConfig.DEBUG) {
                Log.i("Dados Cadastrados ", "Medicamentos");
                Log.i("Data Inicio ", textInpTextInicioAdministracao.getText().toString());
                Log.i("Hora Inicio ", textInpTextHorarioPrimeiraDose.getText().toString());
                Log.i("Intervalo ", spIntervalos.getSelectedItem().toString());
                Log.i("Dose/Dia ", textInpTextDosesDia.getText().toString());
                Log.i("Qtde Dose ", textInpTextQtdeDose.getText().toString());
                Log.i("Ativo ", swAtivo.isChecked() ? "SIM" : "NAO");
            }

            boolean inclusao = App.horarioDTO == null;

            if (inclusao) {
                App.horarioDTO = new HorarioDTO();
                App.horarioDTO.setIdHorario(StringUtil.createId());
                App.horarioDTO.setIdUsuario(App.usuario.getIdUsuario());
                App.horarioDTO.setIdMedicamento(App.medicamentoDTO.getIdMedicamento());
            }

            App.horarioDTO.setDataInicial(textInpTextInicioAdministracao.getText().toString());
            App.horarioDTO.setHorarioInicial(textInpTextHorarioPrimeiraDose.getText().toString());
            App.horarioDTO.setIntervalo(spIntervalos.getSelectedItem().toString());
            App.horarioDTO.setNrDoses(Integer.parseInt(textInpTextDosesDia.getText().toString()));
            App.horarioDTO.setQtdePorDose(Integer.parseInt(textInpTextQtdeDose.getText().toString()));
            App.horarioDTO.setAtivo(swAtivo.isChecked() ? "SIM" : "NAO");

            GerenteServicos gerenteServicos = new GerenteServicos(HorarioActivity.this);

            if (inclusao)
                gerenteServicos.incluirHorario(App.horarioDTO);
            else
                gerenteServicos.alterarHorario(App.horarioDTO);

        });

        if (App.horarioDTO != null) {
            textInpTextInicioAdministracao.setText(App.horarioDTO.getDataInicial());
            textInpTextHorarioPrimeiraDose.setText(App.horarioDTO.getHorarioInicial());
            spIntervalos.setSelection(StringUtil.findByValor(Constantes.intervalos, App.horarioDTO.getIntervalo()));
            textInpTextDosesDia.setText(String.valueOf(App.horarioDTO.getNrDoses()));
            textInpTextQtdeDose.setText(String.valueOf(App.horarioDTO.getQtdePorDose()));
            swAtivo.setChecked(App.horarioDTO.getAtivo().equals("SIM"));
        }
    }

    @Override
    public void carregarLista(int numeroAcao, List<MedicamentoDTO> lista) {

    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {
        if (numeroAcao == Constantes.ACAO_REGISTRAR_HORARIO) {
            setResult(Activity.RESULT_OK, null);
            finish();
        }
        else
        if (numeroAcao == Constantes.ACAO_ALTERAR_HORARIO) {
            setResult(Activity.RESULT_OK, null);
            finish();
        }
    }

    @Override
    public void atualizarHoraListener(int hourOfDay, int minute) {
        Date hora = DataUtil.encodeTimeByHoraMinuto(hourOfDay, minute);

        TextInputEditText textInpTextHorarioPrimeiraDose =  findViewById(R.id.textInpTextHorarioPrimeiraDose);
        textInpTextHorarioPrimeiraDose.setText(DataUtil.convertTimeToString(hora));

        if (BuildConfig.DEBUG)
            Log.i("Hora ",hora.toString());
    }
}
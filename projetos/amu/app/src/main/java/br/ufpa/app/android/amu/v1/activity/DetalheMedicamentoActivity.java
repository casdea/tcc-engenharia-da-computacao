package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.fragments.EstoquesFragment;
import br.ufpa.app.android.amu.v1.fragments.HorariosFragment;
import br.ufpa.app.android.amu.v1.fragments.UtilizacoesFragment;
import br.ufpa.app.android.amu.v1.helper.PaletaCoresActivity;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;

public class DetalheMedicamentoActivity extends AppCompatActivity implements GerenteServicosListener, View.OnClickListener {

    private TextInputEditText textInpTextApelido;
    private TextInputEditText textInpTextQtdeEmbalagem;
    private TextView txvCorSelecionada;
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
                        findViewById(R.id.btnAlterar).setEnabled(camposAlterados());
                        //use setBackground(android.graphics.drawable.Drawable)
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_medicamento);

        TextView txvNomeComercial = findViewById(R.id.txvNomeComercial);
        txvCorSelecionada = findViewById(R.id.txvCorSelecionada);
        textInpTextApelido = findViewById(R.id.textInpTextApelido);
        textInpTextQtdeEmbalagem = findViewById(R.id.textInpTextQtdeEmbalagem);

        txvNomeComercial.setText(App.medicamentoDTO.getNomeComercial());
        textInpTextApelido.setText(App.medicamentoDTO.getNomeFantasia());
        textInpTextQtdeEmbalagem.setText(String.valueOf(App.medicamentoDTO.getQtdeEmbalagem()));

        Button btnAlterar = findViewById(R.id.btnAlterar);
        btnAlterar.setEnabled(false);
        btnAlterar.setOnClickListener(this);

        Button btnUtilizar = findViewById(R.id.btnUtilizar);
        btnUtilizar.setOnClickListener(this);

        cor = App.medicamentoDTO.getCor();

        if (App.medicamentoDTO.isCorValida())
            txvCorSelecionada.setBackground(new ColorDrawable(Color.parseColor(App.medicamentoDTO.getCor())));

        textInpTextApelido.addTextChangedListener(getWatcherTexto());

        textInpTextQtdeEmbalagem.addTextChangedListener(getWatcherTexto());

        findViewById(R.id.txvCorSelecionada).setOnClickListener(this);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Horários", HorariosFragment.class)
                .add("Utilizações", UtilizacoesFragment.class)
                .add("Estoque", EstoquesFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    @NonNull
    private TextWatcher getWatcherTexto() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                findViewById(R.id.btnAlterar).setEnabled(camposAlterados());
            }
        };
    }

    private boolean camposAlterados() {
        boolean a1 = !App.medicamentoDTO.getNomeFantasia().equals(textInpTextApelido.getText().toString());
        boolean a2 = !String.valueOf(App.medicamentoDTO.getQtdeEmbalagem()).equals(textInpTextQtdeEmbalagem.getText().toString());
        boolean a3 = (cor != null && App.medicamentoDTO.getCor() != null && !App.medicamentoDTO.getCor().equals(cor));

        return a1 || a2 || a3;
    }

    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {

    }

    @Override
    public void executarAcao(int numeroAcao, String[] parametros) {
        if (numeroAcao == Constantes.ACAO_ALTERAR_MEDICAMENTO) {
            setResult(Activity.RESULT_OK, null);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txvCorSelecionada) {
            selecionarCor();
        } else if (view.getId() == R.id.btnAlterar) {
            alterarMedicamento();
  /*
            HorariosFragment horariosFragment = (HorariosFragment) adapter.getItem(0);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(horariosFragment);

            //transaction.replace(R.id.idFragmentoHorario, horariosFragment);
            transaction.addToBackStack(null);

            transaction.commit();
*/
        } else if (view.getId() == R.id.btnUtilizar) {
            abrirDialog(view);
        }
    }

    private void selecionarCor() {
        Intent intent = new Intent(DetalheMedicamentoActivity.this, PaletaCoresActivity.class);
        selecionarCorActivityResultLauncher.launch(intent);
    }

    private void alterarMedicamento() {
        TextInputEditText textInpTextApelido = findViewById(R.id.textInpTextApelido);
        TextInputEditText textInpTextQtdeEmbalagem = findViewById(R.id.textInpTextQtdeEmbalagem);

        if (textInpTextApelido.getText().toString().isEmpty()) {
            Toast.makeText(DetalheMedicamentoActivity.this,
                    "Preencha o apelido do medicamento !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (textInpTextQtdeEmbalagem.getText().toString().isEmpty()) {
            Toast.makeText(DetalheMedicamentoActivity.this,
                    "Preencha Quantidade por Embalagem !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Log.i("Dados Cadastrados ", "Medicamentos");
        Log.i("Apelido do Medicamento ", textInpTextApelido.getText().toString());
        Log.i("Qtde Embalagem ", textInpTextQtdeEmbalagem.getText().toString());

        App.medicamentoDTO.setNomeFantasia(textInpTextApelido.getText().toString());
        App.medicamentoDTO.setCor(cor);
        App.medicamentoDTO.setIdUsuario(App.usuario.getIdUsuario());
        App.medicamentoDTO.setQtdeEmbalagem(Integer.parseInt(textInpTextQtdeEmbalagem.getText().toString()));
        App.medicamentoDTO.setEstoques(new ArrayList<>());
        App.medicamentoDTO.setUtilizacoes(new ArrayList<>());

        GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
        gerenteServicos.alterarMedicamento(App.medicamentoDTO);
    }

    public void abrirDialog(View view){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( this );

        //Configurar titulo e mensagem
        dialog.setTitle("Utilização de Medicamento");
        dialog.setMessage("Confirme a Utilização do Medicamento ?");

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( android.R.drawable.ic_btn_speak_now );

        //Configura acoes para sim e nao
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.utilizacaoDTO = new UtilizacaoDTO();
                App.utilizacaoDTO.setIdUtilizacao("0");
                App.utilizacaoDTO.setIdUsuario(App.usuario.getIdUsuario());
                App.utilizacaoDTO.setIdMedicamento(App.medicamentoDTO.getIdMedicamento());
                App.utilizacaoDTO.setDataHora(DataUtil.convertDateTimeToString(new java.util.Date()));

                GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
                gerenteServicos.incluirUtilizacao(App.utilizacaoDTO);
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    return;
            }
        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();
    }
}
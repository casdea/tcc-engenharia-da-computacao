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
import android.view.MotionEvent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.fragments.EstoquesFragment;
import br.ufpa.app.android.amu.v1.fragments.HorariosFragment;
import br.ufpa.app.android.amu.v1.fragments.UtilizacoesFragment;
import br.ufpa.app.android.amu.v1.helper.PaletaCoresActivity;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;

public class DetalheMedicamentoActivity extends AppCompatActivity implements GerenteServicosListener, View.OnClickListener, View.OnTouchListener {

    private TextInputEditText textInpTextApelido;
    private TextInputEditText textInpTextQtdeEmbalagem;
    private TextInputEditText textInpTextQtdeEstoque;
    private TextView txvCorSelecionada;
    private String cor;
    private FragmentPagerItemAdapter adapter;
    private RecursoVozLifeCyCleObserver mRecursoVozObserver;

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

    private OnHorariosListener onHorariosListener;

    public interface OnHorariosListener {
        public void atualizarLista(View view);
    }

    private OnEstoquesListener onEstoqueListener;

    public interface OnEstoquesListener {
        public void atualizarLista(View view);
    }

    private OnUtilizacoesListener onUtilizacoesListener;

    public interface OnUtilizacoesListener {
        public void atualizarLista(View view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_medicamento);
        App.escutandoComando = false;

        findViewById(R.id.fundo).setOnClickListener(this);
        findViewById(R.id.layoutConfirmar).setOnClickListener(this);
        findViewById(R.id.textInpLayApelidoMedicamento).setOnClickListener(this);
        findViewById(R.id.textInpLayQtdeEmbalagem).setOnClickListener(this);
        findViewById(R.id.textInputLayoutEstoque).setOnClickListener(this);

        TextView txvNomeComercial = findViewById(R.id.txvNomeComercial);
        txvCorSelecionada = findViewById(R.id.txvCorSelecionada);
        textInpTextApelido = findViewById(R.id.textInpTextApelido);
        textInpTextQtdeEmbalagem = findViewById(R.id.textInpTextQtdeEmbalagem);
        textInpTextQtdeEstoque = findViewById(R.id.textInpTextQtdeEstoque);

        textInpTextApelido.setOnClickListener(this);
        textInpTextQtdeEmbalagem.setOnClickListener(this);
        textInpTextQtdeEstoque.setOnClickListener(this);

        txvNomeComercial.setText(App.medicamentoDTO.getNomeComercial());
        textInpTextApelido.setText(App.medicamentoDTO.getNomeFantasia());
        textInpTextQtdeEmbalagem.setText(String.valueOf(App.medicamentoDTO.getQtdeEmbalagem()));

        mRecursoVozObserver = new RecursoVozLifeCyCleObserver(getActivityResultRegistry(), DetalheMedicamentoActivity.this);
        getLifecycle().addObserver(mRecursoVozObserver);

        Button btnAlterar = findViewById(R.id.btnAlterar);
        btnAlterar.setEnabled(false);
        btnAlterar.setOnClickListener(this);

        Button btnUtilizar = findViewById(R.id.btnUtilizar);
        btnUtilizar.setOnClickListener(this);

        findViewById(R.id.imbAdicionar).setOnClickListener(this);
        findViewById(R.id.imbRemover).setOnClickListener(this);

        cor = App.medicamentoDTO.getCor();

        if (App.medicamentoDTO.isCorValida())
            txvCorSelecionada.setBackground(new ColorDrawable(Color.parseColor(App.medicamentoDTO.getCor())));

        textInpTextApelido.addTextChangedListener(getWatcherTexto());

        textInpTextQtdeEmbalagem.addTextChangedListener(getWatcherTexto());

        findViewById(R.id.txvCorSelecionada).setOnClickListener(this);

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Horários", HorariosFragment.class)
                .add("Utilizações", UtilizacoesFragment.class)
                .add("Estoque", EstoquesFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOnClickListener(this);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnClickListener(this);
        viewPagerTab.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                    mRecursoVozObserver.chamarItenteReconechimentoVoz();
            }
        });

        this.onHorariosListener = (OnHorariosListener) adapter.getItem(0);
        this.onUtilizacoesListener = (OnUtilizacoesListener) adapter.getItem(1);
        this.onEstoqueListener = (OnEstoquesListener) adapter.getItem(2);

        //App.tipoPerfil =  TipoPerfil.PCD_VISAO_REDUZIDA; // TipoPerfil.valueOf(App.usuario.getTipoPerfil());

        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            getSupportActionBar().hide();
            textInpTextApelido.setFocusable(false);
            textInpTextQtdeEmbalagem.setFocusable(false);
            textInpTextQtdeEstoque.setFocusable(false);
            btnAlterar.setEnabled(true);
        }

        App.integracaoUsuario.bemVindoFuncao(TipoFuncao.DETALHES_MEDICAMENTO);

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
    public void onClick(View view) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        } else {
            if (view.getId() == R.id.txvCorSelecionada) {
                selecionarCor();
            } else if (view.getId() == R.id.btnAlterar) {
                alterarMedicamento();
            } else if (view.getId() == R.id.btnUtilizar) {
                abrirDialogUtilizacao(view);
            } else if (view.getId() == R.id.imbAdicionar) {
                abrirDialogEntradaEstoque(view);
            } else if (view.getId() == R.id.imbRemover) {
                abrirDialogSaidaEstoque(view);
            }
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

    public void abrirDialogUtilizacao(View view) {

        if (App.integracaoUsuario.validarUtilizacaoMedicamento(App.listaHorarios) == false) return;

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configurar titulo e mensagem
        dialog.setTitle("Utilização de Medicamento");
        dialog.setMessage("Confirme a Utilização do Medicamento ?");

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon(android.R.drawable.ic_btn_speak_now);

        //Configura acoes para sim e nao
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sinalizarDoseRealizada();
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

    private void sinalizarDoseRealizada() {
        HorarioDTO horarioDTO = App.listaHorarios.get(App.listaHorarios.size() - 1);

        App.estoqueDTO = new EstoqueDTO();
        App.estoqueDTO.setIdEstoque("0");
        App.estoqueDTO.setIdUsuario(App.usuario.getIdUsuario());
        App.estoqueDTO.setIdMedicamento(App.medicamentoDTO.getIdMedicamento());
        App.estoqueDTO.setData(DataUtil.convertDateTimeToString(new java.util.Date()));
        App.estoqueDTO.setEntrada(0);
        App.estoqueDTO.setSaida(horarioDTO.getQtdePorDose());
        App.estoqueDTO.setSaldo(0);

        GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
        gerenteServicos.sinalizarDoseRealizada(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento(), App.estoqueDTO);
    }

    public void abrirDialogEntradaEstoque(View view) {

        TextInputEditText textInpTextQtdeEstoque = findViewById(R.id.textInpTextQtdeEstoque);

        if (textInpTextQtdeEstoque.getText().toString().isEmpty()) {
            Toast.makeText(DetalheMedicamentoActivity.this,
                    "Preencha a quantidade de compra do medicamento !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (textInpTextQtdeEstoque.getText().toString().equals("0")) {
            Toast.makeText(DetalheMedicamentoActivity.this,
                    "Preencha a quantidade de compra do medicamento !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configurar titulo e mensagem
        dialog.setTitle("Compra de Medicamento");
        dialog.setMessage("Confirme a Compra de Medicamento ?");

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon(android.R.drawable.ic_btn_speak_now);

        //Configura acoes para sim e nao
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.estoqueDTO = new EstoqueDTO();
                App.estoqueDTO.setIdEstoque("0");
                App.estoqueDTO.setIdUsuario(App.usuario.getIdUsuario());
                App.estoqueDTO.setIdMedicamento(App.medicamentoDTO.getIdMedicamento());
                App.estoqueDTO.setData(DataUtil.convertDateTimeToString(new java.util.Date()));
                App.estoqueDTO.setEntrada(Integer.parseInt(textInpTextQtdeEstoque.getText().toString()));
                App.estoqueDTO.setSaida(0);
                App.estoqueDTO.setSaldo(0);

                GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
                gerenteServicos.atualizarSaldoEstoque(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento(), App.estoqueDTO);

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


    public void abrirDialogSaidaEstoque(View view) {

        TextInputEditText textInpTextQtdeEstoque = findViewById(R.id.textInpTextQtdeEstoque);

        if (textInpTextQtdeEstoque.getText().toString().isEmpty()) {
            Toast.makeText(DetalheMedicamentoActivity.this,
                    "Preencha a quantidade de saída do medicamento !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (textInpTextQtdeEstoque.getText().toString().equals("0")) {
            Toast.makeText(DetalheMedicamentoActivity.this,
                    "Preencha a quantidade de saída do medicamento !",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configurar titulo e mensagem
        dialog.setTitle("Compra de Medicamento");
        dialog.setMessage("Confirme a Saída de Medicamento ?");

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon(android.R.drawable.ic_btn_speak_now);

        //Configura acoes para sim e nao
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.estoqueDTO = new EstoqueDTO();
                App.estoqueDTO.setIdEstoque("0");
                App.estoqueDTO.setIdUsuario(App.usuario.getIdUsuario());
                App.estoqueDTO.setIdMedicamento(App.medicamentoDTO.getIdMedicamento());
                App.estoqueDTO.setData(DataUtil.convertDateTimeToString(new java.util.Date()));
                App.estoqueDTO.setEntrada(0);
                App.estoqueDTO.setSaida(Integer.parseInt(textInpTextQtdeEstoque.getText().toString()));
                App.estoqueDTO.setSaldo(0);

                GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
                gerenteServicos.atualizarSaldoEstoque(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento(), App.estoqueDTO);
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

    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {
        if (numeroAcao == Constantes.ACAO_OBTER_LISTA_HORARIO_USUARIO_MEDICAMENTO) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HorariosFragment horariosFragment = (HorariosFragment) adapter.getItem(0);
            View view = findViewById(R.id.idFragmentoHorario);
            horariosFragment.atualizarLista(view);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (numeroAcao == Constantes.ACAO_OBTER_LISTA_UTILIZACAO_POR_USUARIO_MEDICAMENTO) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            UtilizacoesFragment utilizacoesFragment = (UtilizacoesFragment) adapter.getItem(1);
            View view = findViewById(R.id.idFragmentoUtilizacao);
            utilizacoesFragment.atualizarLista(view);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (numeroAcao == Constantes.ACAO_OBTER_LISTA_ESTOQUE_POR_USUARIO_MEDICAMENTO) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            EstoquesFragment estoquesFragment = (EstoquesFragment) adapter.getItem(2);
            View view = findViewById(R.id.idFragmentoEstoque);
            estoquesFragment.atualizarLista(view);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {
        if (numeroAcao == Constantes.ACAO_ALTERAR_MEDICAMENTO) {
            setResult(Activity.RESULT_OK, null);
            finish();
        } else if (numeroAcao == Constantes.ACAO_REGISTRAR_HORARIO) {
            GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
            gerenteServicos.obterListaHorariosByUsuarioMedicamento(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento());
        } else if (numeroAcao == Constantes.ACAO_REGISTRAR_ESTOQUE) {
            GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
            gerenteServicos.obterListaEstoquesByUsuarioMedicamento(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento());
        } else if (numeroAcao == Constantes.ACAO_ERRO_SEM_SALDO_ESTOQUE) {
            App.integracaoUsuario.saidaNegadaSemSaldo();
            return;
        } else if (numeroAcao == Constantes.ACAO_ATUALIZAR_SALDO_ESTOQUE) {
            GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
            gerenteServicos.incluirEstoque((EstoqueDTO) parametro);
        } else if (numeroAcao == Constantes.ACAO_REGISTRAR_UTILIZACAO) {

            App.utilizacaoDTO = new UtilizacaoDTO();
            App.utilizacaoDTO.setIdUtilizacao("0");
            App.utilizacaoDTO.setIdUsuario(App.usuario.getIdUsuario());
            App.utilizacaoDTO.setIdMedicamento(App.medicamentoDTO.getIdMedicamento());
            App.utilizacaoDTO.setDataHora(DataUtil.convertDateTimeToString(new java.util.Date()));

            GerenteServicos gerenteServicos = new GerenteServicos(DetalheMedicamentoActivity.this);
            gerenteServicos.incluirUtilizacao(App.utilizacaoDTO);

        } else if (numeroAcao == Constantes.ACAO_VOZ_TELA_ANTERIOR) {
            setResult(Activity.RESULT_OK, null);
            finish();
        } else if (numeroAcao == Constantes.ACAO_VOZ_DESCREVA_HORARIO) {
            App.integracaoUsuario.descrerverHorario(App.medicamentoDTO, App.listaHorarios);
        } else if (numeroAcao == Constantes.ACAO_CHAMAR_COMANDO_VOZ) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        } else if (numeroAcao == Constantes.ACAO_VOZ_DOSE_REALIZADA) {
            if (App.integracaoUsuario.validarUtilizacaoMedicamento(App.listaHorarios) == false)
                return;

            sinalizarDoseRealizada();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        }

        return false;
    }


}
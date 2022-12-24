package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.adapter.MedicamentoAdapter;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.helper.RecyclerItemClickListener;
import br.ufpa.app.android.amu.v1.integracao.classes.ComandosVoz;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoUsuario;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;
import br.ufpa.app.android.amu.v1.util.ThreadUtil;

public class PrincipalActivity extends AppCompatActivity implements GerenteServicosListener, View.OnClickListener, View.OnTouchListener {

    private RecyclerView recyclerView;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
    private TextView txvListaVazia;
    private RecursoVozLifeCyCleObserver mRecursoVozObserver;
/*
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            new GerenteServico(getApplicationContext()).atualizarLogMobile();

            if (App.tempoRestanteManutencao > 0)
                txvDadosOrdemServico.setText("Dados da Ordem de Serviço. Tempo Restante Manutenção: " + String.valueOf(App.tempoRestanteManutencao) + " minuto(s) ");
            else
                txvDadosOrdemServico.setText("Dados da Ordem de Serviço");

            txvDebug.setText(App.textoTempoRestanteManutencao);

            timerHandler.postDelayed(this, 500);
        }
    };
*/

    private ActivityResultLauncher<Intent> detalheMedicamentoActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        prepararLista();
                    }
                }
            });

    private ActivityResultLauncher<Intent> consultarAvisaActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        prepararLista();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        App.escutandoComando = false;

        App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
        App.integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico(App.fontesConsulta);


        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
            getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(this);
        recyclerView.setOnTouchListener(this);

        mRecursoVozObserver = new RecursoVozLifeCyCleObserver(getActivityResultRegistry(), PrincipalActivity.this);
        getLifecycle().addObserver(mRecursoVozObserver);

        txvListaVazia = findViewById(R.id.txvListaVazia);
        txvListaVazia.setVisibility(View.INVISIBLE);
        findViewById(R.id.floatingActionButton).setOnClickListener(this);
        findViewById(R.id.fundo).setOnClickListener(this);
        findViewById(R.id.cabecalho).setOnClickListener(this);

        App.integracaoUsuario.bemVindoFuncao(TipoFuncao.PESQUISA_MEDICAMENTOS);

        prepararLista();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        } else {
            if (v.getId() == R.id.floatingActionButton) {
                new TaskVerificarHorarioDose().execute();
                //enviarNotificacao("Horário de Médicamento","Tome o remédio DORFLEX");
                //incluirMedicamento();
            }
        }
    }


    private void enviarNotificacao(String titulo, String corpo){

        //Configuraçõe para notificação
        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        //Intent intent = new Intent(this, NotificacoesActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //Criar notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal)
                .setContentTitle( titulo )
                .setContentText( corpo )
                .setSmallIcon( R.drawable.ic_baseline_notifications_active_24 )
                .setSound( uriSom )
                .setAutoCancel( true );
                //.setContentIntent( pendingIntent );

        //Recupera notificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Verifica versão do Android a partir do Oreo para configurar canal de notificação
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            NotificationChannel channel = new NotificationChannel(canal, "canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel( channel );
        }

        //Envia notificação
        notificationManager.notify(0, notificacao.build() );

    }

    private void incluirMedicamento() {
        Intent intent = new Intent(PrincipalActivity.this, ConsultaAnvisaActivity.class);
        consultarAvisaActivityResultLauncher.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restaurarPerfil:
                App.tipoPerfil = TipoPerfil.valueOf(App.usuario.getTipoPerfil());
                App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
                App.integracaoUsuario.avisoSaidaPerfilAdmin();

                if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                    getSupportActionBar().hide();

                break;
        }
        switch (item.getItemId()) {
            case R.id.menuPerfil:
                startActivity(new Intent(this, UsuarioActivity.class));
                break;
        }
        switch (item.getItemId()) {
            case R.id.menuSair:
                autenticacao.signOut();
                startActivity(new Intent(this, BemVindoActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepararLista() {
        GerenteServicos gerenteServicos = new GerenteServicos(PrincipalActivity.this);
        gerenteServicos.obterListaMedicamentosByUsuario(App.usuario.getIdUsuario());
    }


    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {
        if (numeroAcao == Constantes.ACAO_OBTER_LISTA_MEDICAMENTO_POR_USUARIO) {
            this.listaMedicamentos = (List<MedicamentoDTO>) lista;

            txvListaVazia.setVisibility(View.INVISIBLE);

            if (this.listaMedicamentos.size() == 0) {

                if (App.integracaoUsuario.lerTexto())
                    App.integracaoUsuario.avisarListaVazia();
                else
                    txvListaVazia.setVisibility(View.VISIBLE);
            }

            MedicamentoAdapter medicamentoAdapter = new MedicamentoAdapter((List<MedicamentoDTO>) lista);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(medicamentoAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                    getApplicationContext(),
                    recyclerView,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
                                mRecursoVozObserver.chamarItenteReconechimentoVoz();
                            } else {
                                App.medicamentoDTO = (MedicamentoDTO) listaMedicamentos.get(position);
                                Intent intent = new Intent(PrincipalActivity.this, DetalheMedicamentoActivity.class);
                                detalheMedicamentoActivityResultLauncher.launch(intent);
                            }
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    }));
        }
    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {
        if (numeroAcao == Constantes.ACAO_VOZ_LISTA_MEDICAMENTOS) {
            App.integracaoUsuario.listarMedicamentos(listaMedicamentos);
        } else if (numeroAcao == Constantes.ACAO_VOZ_DESCREVA_MEDICAMENTO) {
            App.medicamentoDTO = App.integracaoUsuario.descrerverMedicamento(listaMedicamentos, (String) parametro);
            ThreadUtil.esperar(ThreadUtil.QUATRO_SEGUNDOS);

            if (App.medicamentoDTO != null) {
                Intent intent = new Intent(PrincipalActivity.this, DetalheMedicamentoActivity.class);
                detalheMedicamentoActivityResultLauncher.launch(intent);
            }
        } else if (numeroAcao == Constantes.ACAO_VOZ_ALTERNAR_PERFIL) {
            App.integracaoUsuario.avisoEntradaPerfilAdmin();
            App.tipoPerfil = TipoPerfil.COMUM;
            App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);

            getSupportActionBar().show();
        }
        else if (numeroAcao == Constantes.ACAO_VOZ_FECHAR_APP) {
            finish();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            AlertDialog alert_back = new AlertDialog.Builder(this).create();
            alert_back.setMessage("Deseja sair?");

            alert_back.setButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert_back.setButton2("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alert_back.show();
        }

    }

    public class TaskVerificarHorarioDose extends AsyncTask<Void,Void,List<HorarioDTO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<HorarioDTO> doInBackground(Void... voids) {

            GerenteServicos gerenteServicos = new GerenteServicos(PrincipalActivity.this);
            gerenteServicos.obterListaHorariosByUsuario(App.usuario.getIdUsuario());
            gerenteServicos.obterListaEstoquesByUsuario(App.usuario.getIdUsuario());
            gerenteServicos.obterListaUtilizacoesByUsuario(App.usuario.getIdUsuario());

            return App.listaHorarios;
        }

        @Override
        protected void onPostExecute(List<HorarioDTO> horarioDTOS) {
            super.onPostExecute(horarioDTOS);

            for (MedicamentoDTO medicamentoDTO : listaMedicamentos) {

                HorarioDTO horarioDTO = obterHorario(medicamentoDTO);

                if (horarioDTO == null) continue;

                Date dataHora = DataUtil.converteStringToTime(horarioDTO.getHorarioInicial());

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(List<HorarioDTO> horarioDTOS) {
            super.onCancelled(horarioDTOS);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        private HorarioDTO obterHorario(MedicamentoDTO medicamentoDTO) {
            for (HorarioDTO horarioDTO : App.listaHorarios) {
                if (horarioDTO.getIdMedicamento().equals(medicamentoDTO.getIdMedicamento())==false) continue;

                if (horarioDTO.getAtivo().equals("NÃO")) continue;
                if (DataUtil.convertStringToDate(horarioDTO.getDataInicial()).before(new java.util.Date())) continue;

                return horarioDTO;
            }

            return null;
        }

    }
}
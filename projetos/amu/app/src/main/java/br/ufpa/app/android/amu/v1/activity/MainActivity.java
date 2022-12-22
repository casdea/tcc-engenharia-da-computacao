
package br.ufpa.app.android.amu.v1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoUsuario;
import br.ufpa.app.android.amu.v1.util.App;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSpeak;
    private TextView txvStatusComando;
    private RecursoVozLifeCyCleObserver mRecursoVozObserver;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        App.escutandoComando = false;

        App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
        App.integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico(App.fontesConsulta);

        App.integracaoUsuario.bemVindo();

        mRecursoVozObserver = new RecursoVozLifeCyCleObserver(getActivityResultRegistry(), MainActivity.this);
        getLifecycle().addObserver(mRecursoVozObserver);

        findViewById(R.id.btnCadastroMedicamento).setOnClickListener(this);
        findViewById(R.id.btnConsultaMedicamento).setOnClickListener(this);
        findViewById(R.id.btnEstoqueMedicamento).setOnClickListener(this);
        findViewById(R.id.btnHorarioMedicamento).setOnClickListener(this);
        findViewById(R.id.btnUsoMedicamento).setOnClickListener(this);
        findViewById(R.id.btnPerfilUsuario).setOnClickListener(this);
        findViewById(R.id.btnSpeak).setOnClickListener(this);

        txvStatusComando = (TextView) findViewById(R.id.txvStatusComando);

        if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, 112);
        }
    }

    @Override
    public void onClick(View v) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            App.comandoAtualVoz = TipoFuncao.CHAMADA_TELA;
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        } else {
            if (v.getId() == R.id.btnCadastroMedicamento) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MedicamentoActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btnConsultaMedicamento) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ConsultaAnvisaActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btnPerfilUsuario) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UsuarioActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btnSpeak) {
                mRecursoVozObserver.chamarItenteReconechimentoVoz();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                autenticacao.signOut();
                startActivity(new Intent(this, BemVindoActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
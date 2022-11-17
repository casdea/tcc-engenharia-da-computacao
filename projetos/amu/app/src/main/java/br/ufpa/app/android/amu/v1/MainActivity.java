
package br.ufpa.app.android.amu.v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ufpa.app.android.amu.v1.integracao.classes.FontesConsulta;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoFuncao;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoUsuario;
import br.ufpa.app.android.amu.v1.modelo.Usuario;
import br.ufpa.app.android.amu.v1.util.App;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSpeak;
    private TextView txvStatusComando;
    private RecursoVozLifeCyCleObserver mRecursoVozObserver;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

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

        App.context = this;
        App.tipoPerfil = TipoPerfil.PCD_VISAO_REDUZIDA;
        App.fontesConsulta = FontesConsulta.ANVISA;

        setContentView(R.layout.activity_main);

        App.integracaoUsuario = new FactoryIntegracaoUsuario().createIntegracaoUsuario(App.tipoPerfil);
        App.integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico(App.fontesConsulta);

        App.integracaoUsuario.bemVindo();

        mRecursoVozObserver = new RecursoVozLifeCyCleObserver(getActivityResultRegistry());
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
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
        {
            App.comandoAtualVoz = TipoFuncao.CHAMADA_TELA;
            mRecursoVozObserver.chamarItenteReconechimentoVoz();
        }
        else {
            if (v.getId() == R.id.btnCadastroMedicamento) {

                /*DatabaseReference usuarios = referencia.child("usuarios");
                Usuario usuario = new Usuario(2, "RUI BARBOSA", "rui@gmail.com", "1234444", TipoPerfil.COMUM.name());
                usuarios.child(String.valueOf(usuario.getId())).setValue(usuario);
                */

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MedicamentoActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btnConsultaMedicamento) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ConsultaMedicamentoActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btnSpeak) {
                mRecursoVozObserver.chamarItenteReconechimentoVoz();
            }
        }
    }
 }
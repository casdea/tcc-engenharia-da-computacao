package br.ufpa.app.android.amu.v1.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.List;
import java.util.Objects;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;
import br.ufpa.app.android.amu.v1.helper.Base64Custom;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;

public class UsuarioActivity extends AppCompatActivity implements GerenteServicosListener {

    EditText campoNome, campoEmail, campoSenha;
    Button botaoCadastrar;
    UsuarioDTO usuarioDTO;
    RadioButton rbUsuarioComum, rbUsuarioIdoso, rbUsuarioSurdoMudo, rbUsuarioPerdaVisao, rbUsuarioTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        App.context = this;
/*
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
*/
        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
        rbUsuarioComum = findViewById(R.id.rbUsuarioComum);
        rbUsuarioIdoso = findViewById(R.id.rbUsuarioIdoso);
        rbUsuarioSurdoMudo = findViewById(R.id.rbUsuarioSurdoMudo);
        rbUsuarioPerdaVisao = findViewById(R.id.rbUsuarioPerdaVisao);
        rbUsuarioTea = findViewById(R.id.rbUsuarioTea);

        if (App.usuario != null)
        {
            campoNome.setText(App.usuario.getNome());
            campoEmail.setText(App.usuario.getEmail());
            //Essa senha é apenas uma mascara
            campoSenha.setText(R.string.mascara_senha);
            campoNome.setText(App.usuario.getNome());
            rbUsuarioComum.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.COMUM.name()));
            rbUsuarioIdoso.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.IDOSO.name()));
            rbUsuarioSurdoMudo.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.PCD_SURDO_MUDO.name()));
            rbUsuarioPerdaVisao.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.PCD_VISAO_REDUZIDA.name()));
            rbUsuarioTea.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.PCD_TEA.name()));

            campoEmail.setEnabled(false);
            campoSenha.setEnabled(false);
        }

        botaoCadastrar.setOnClickListener(v -> {

            String textoNome = campoNome.getText().toString();
            String textoEmail = campoEmail.getText().toString();
            String textoSenha = campoSenha.getText().toString();
            String tipoPerfil = "";
            App.tipoPerfil = null;

            if (rbUsuarioComum.isChecked()) App.tipoPerfil = TipoPerfil.COMUM;
            else
            if (rbUsuarioIdoso.isChecked()) App.tipoPerfil = TipoPerfil.IDOSO;
            else
            if (rbUsuarioSurdoMudo.isChecked()) App.tipoPerfil = TipoPerfil.PCD_SURDO_MUDO;
            else
            if (rbUsuarioPerdaVisao.isChecked()) App.tipoPerfil = TipoPerfil.PCD_VISAO_REDUZIDA;
            else
            if (rbUsuarioTea.isChecked()) App.tipoPerfil = TipoPerfil.PCD_TEA;

            if (App.tipoPerfil != null)
                tipoPerfil = App.tipoPerfil.name();

            //Validar se os campos foram preenchidos
            if (!validar(textoNome, textoEmail, textoSenha, tipoPerfil)) return;

            usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNome(textoNome);
            usuarioDTO.setEmail(textoEmail);
            usuarioDTO.setSenha(textoSenha);
            usuarioDTO.setTipoPerfil(App.tipoPerfil.name());
            cadastrarUsuario();

        });
    }
/*
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    boolean validar(String textoNome, String textoEmail, String textoSenha, String tipoPerfil) {
        if (textoNome.isEmpty()) {
            Toast.makeText(UsuarioActivity.this,
                    "Preencha o nome!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (textoEmail.isEmpty()) {
            Toast.makeText(UsuarioActivity.this,
                    "Preencha o email!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (textoSenha.isEmpty()) {
            Toast.makeText(UsuarioActivity.this,
                    "Preencha a senha!",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (tipoPerfil.isEmpty()) {
            Toast.makeText(UsuarioActivity.this,
                    "Selecione o tipo de Perfil!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void cadastrarUsuario() {
        if (App.usuario == null) {
            FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    usuarioDTO.getEmail(), usuarioDTO.getSenha()
            ).addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {
                    usuarioDTO.setIdUsuario(Base64Custom.codificarBase64(usuarioDTO.getEmail()));

                    GerenteServicos gerenteServicos = new GerenteServicos(UsuarioActivity.this);
                    App.usuario = gerenteServicos.incluirUsuario(usuarioDTO);
                    App.tipoPerfil = TipoPerfil.valueOf(App.usuario.getTipoPerfil());

                    finish();

                } else {

                    String excecao;
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Este conta já foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(UsuarioActivity.this,
                            excecao,
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            usuarioDTO.setIdUsuario(Base64Custom.codificarBase64(usuarioDTO.getEmail()));

            GerenteServicos gerenteServicos = new GerenteServicos(UsuarioActivity.this);
            App.usuario = gerenteServicos.alterarUsuario(usuarioDTO);
            App.tipoPerfil = TipoPerfil.valueOf(App.usuario.getTipoPerfil());

            finish();
        }
    }

    @Override
    public void carregarLista(int numeroAcao, List<MedicamentoDTO> lista) {}

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {

    }
}

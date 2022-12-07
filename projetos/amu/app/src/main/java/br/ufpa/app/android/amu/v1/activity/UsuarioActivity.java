package br.ufpa.app.android.amu.v1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.helper.Base64Custom;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;

public class UsuarioActivity extends AppCompatActivity implements GerenteServicosListener {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private UsuarioDTO usuarioDTO;
    private RadioButton rbUsuarioComum, rbUsuarioIdoso, rbUsuarioSurdoMudo, rbUsuarioPerdaVisao, rbUsuarioTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

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
            campoSenha.setText("nXha@89722");
            campoNome.setText(App.usuario.getNome());
            rbUsuarioComum.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.COMUM.name()));
            rbUsuarioIdoso.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.IDOSO.name()));
            rbUsuarioSurdoMudo.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.PCD_SURDO_MUDO.name()));
            rbUsuarioPerdaVisao.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.PCD_VISAO_REDUZIDA.name()));
            rbUsuarioTea.setChecked(App.usuario.getTipoPerfil().equals(TipoPerfil.PCD_TEA.name()));

            campoEmail.setEnabled(false);
            campoSenha.setEnabled(false);
        }

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (validar(textoNome, textoEmail, textoSenha, tipoPerfil) == false) return;

                usuarioDTO = new UsuarioDTO();
                usuarioDTO.setNome(textoNome);
                usuarioDTO.setEmail(textoEmail);
                usuarioDTO.setSenha(textoSenha);
                usuarioDTO.setTipoPerfil(App.tipoPerfil.name());
                cadastrarUsuario();

            }
        });
    }

    private boolean validar(String textoNome, String textoEmail, String textoSenha, String tipoPerfil) {
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
            ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        usuarioDTO.setIdUsuario(Base64Custom.codificarBase64(usuarioDTO.getEmail()));

                        GerenteServicos gerenteServicos = new GerenteServicos(UsuarioActivity.this);
                        App.usuario = gerenteServicos.incluirUsuario(usuarioDTO);
                        App.tipoPerfil = TipoPerfil.valueOf(App.usuario.getTipoPerfil());

                        finish();

                    } else {

                        String excecao = "";
                        try {
                            throw task.getException();
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
    public void carregarLista(int numeroAcao, List<?> lista) {}

    @Override
    public void executarAcao(int numeroAcao, String[] parametros) {

    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {

    }
}

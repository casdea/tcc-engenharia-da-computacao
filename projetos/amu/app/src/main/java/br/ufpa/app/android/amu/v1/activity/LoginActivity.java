package br.ufpa.app.android.amu.v1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.List;
import java.util.Objects;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class LoginActivity extends AppCompatActivity implements GerenteServicosListener {

    EditText campoEmail, campoSenha;
    TextView txvEsqueciMinhaSenha;
    Button botaoEntrar;
    UsuarioDTO usuarioDTO;
    FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        App.context = this;

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);
        txvEsqueciMinhaSenha = findViewById(R.id.txvEsqueciMinhaSenha);
        txvEsqueciMinhaSenha.setOnClickListener(view -> {
            String textoEmail = campoEmail.getText().toString();

            if (textoEmail.isEmpty()) {
                Toast.makeText(LoginActivity.this,
                        "Preencha o email!",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // [START send_password_reset]
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.sendPasswordResetEmail(textoEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "e-Mail enviado com sucesso. Após alterar a senha tente novamente !",
                                    Toast.LENGTH_LONG).show();

                            Log.d("Login de Usuario", "Email enviado. ");
                        }
                    });
        });


        botaoEntrar.setOnClickListener(v -> {

            String textoEmail = campoEmail.getText().toString();
            String textoSenha = campoSenha.getText().toString();

            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {

                    usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setEmail(textoEmail);
                    usuarioDTO.setSenha(textoSenha);
                    validarLogin();

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha a senha!",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LoginActivity.this,
                        "Preencha o email!",
                        Toast.LENGTH_LONG).show();
            }

        });

        campoEmail.setText("jose20@gmail.com");
        campoSenha.setText("85741100");
    }

    public void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuarioDTO.getEmail(),
                usuarioDTO.getSenha()
        ).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                GerenteServicos gerenteServicos = new GerenteServicos(LoginActivity.this);
                gerenteServicos.verificarUsuarioLogado();

            } else {

                String excecao;
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException e) {
                    excecao = "Usuário não está cadastrado.";
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                } catch (Exception e) {
                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                    e.printStackTrace();
                }

                Toast.makeText(LoginActivity.this,
                        excecao,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void carregarLista(int numeroAcao, List<MedicamentoDTO> lista) {
    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {
        if (numeroAcao == Constantes.ACAO_APRESENTAR_TELA_PRINCIPAL) {
            startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
        }
        else
        if (numeroAcao == Constantes.ACAO_APRESENTAR_TELA_BOAS_VINDAS) {
            autenticacao = (FirebaseAuth) parametro;
            autenticacao.signOut();
            startActivity(new Intent(LoginActivity.this, BemVindoActivity.class));
            finish();
        }
    }
}

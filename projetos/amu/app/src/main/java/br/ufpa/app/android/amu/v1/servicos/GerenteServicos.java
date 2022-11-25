package br.ufpa.app.android.amu.v1.servicos;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import br.ufpa.app.android.amu.v1.activity.BemVindoActivity;
import br.ufpa.app.android.amu.v1.activity.PrincipalActivity;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.factoryDao.FactoryDAO;
import br.ufpa.app.android.amu.v1.dao.helper.Base64Custom;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.dto.UsuarioDTO;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.util.App;

public class GerenteServicos {

    private DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

    public Usuario incluirUsuario(UsuarioDTO usuarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em);
        Usuario usuario = new Usuario(usuarioDTO);
        return factoryDAO.getUsuarioDao().create(usuario);
    }

    public Usuario alterarUsuario(UsuarioDTO usuarioDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em);
        Usuario usuario = new Usuario(usuarioDTO);
        return factoryDAO.getUsuarioDao().update(usuario);
    }

    public void verificarUsuarioLogado(AppCompatActivity atividade) {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        if( autenticacao.getCurrentUser() != null )
        {
            String emailUsuario = autenticacao.getCurrentUser().getEmail();
            String idUsuario = Base64Custom.codificarBase64(emailUsuario);

            DatabaseReference usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios").child(idUsuario);

            usuariosRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    App.usuario = snapshot.getValue( Usuario.class );

                    if (App.usuario != null) {
                        App.usuario.setIdUsuario(idUsuario);
                        App.tipoPerfil = TipoPerfil.valueOf(App.usuario.getTipoPerfil());
                        abrirTelaPrincipal(atividade);
                    }
                    else {
                        Toast.makeText(App.context,
                                "Usuário não tem mais um cadastrado válido !",
                                Toast.LENGTH_LONG).show();

                        abrirTelaBoasVindas(autenticacao, atividade);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void abrirTelaPrincipal(AppCompatActivity atividade){
        //App.context.startActivity(new Intent(atividade, MainActivity.class));
        App.context.startActivity(new Intent(atividade, PrincipalActivity.class));
    }

    public void abrirTelaBoasVindas(FirebaseAuth autenticacao, AppCompatActivity atividade) {
        autenticacao.signOut();
        App.context.startActivity(new Intent(atividade, BemVindoActivity.class));
        atividade.finish();
    }

    public Medicamento incluirMedicamento(MedicamentoDTO medicamentoDTO) {
        FactoryDAO factoryDAO = new FactoryDAO(em);
        Medicamento medicamento = new Medicamento(medicamentoDTO);
        return factoryDAO.getMedicamentosDao().create(medicamento);
    }

    public List<MedicamentoDTO> obterListaMedicamentosByUsuario(String idUsuario) {
        FactoryDAO factoryDAO = new FactoryDAO(em);
        return factoryDAO.getMedicamentosDao().findAllByUsuario(idUsuario);
    }
}

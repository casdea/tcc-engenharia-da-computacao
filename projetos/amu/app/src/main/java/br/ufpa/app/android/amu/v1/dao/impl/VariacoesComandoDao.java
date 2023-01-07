package br.ufpa.app.android.amu.v1.dao.impl;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IVariacoesComandoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.VariacoesComando;
import br.ufpa.app.android.amu.v1.dto.VariacoesComandoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class VariacoesComandoDao extends AbstractEntityDao<VariacoesComando> implements IVariacoesComandoDao {

    final GerenteServicosListener gerenteServicosListener;
    final AppCompatActivity atividade;

    public VariacoesComandoDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public Class<VariacoesComando> getClassImplement() {
        return VariacoesComando.class;
    }

    @Override
    public VariacoesComando create(VariacoesComando variacoesComando) {
        DatabaseReference variacoesComandoRef = em.child(variacoesComando.getNomeTabela());
        String chave = variacoesComandoRef.push().getKey();
        variacoesComando.setIdVariacaoComando(chave);
        variacoesComandoRef.child(Objects.requireNonNull(chave)).setValue(variacoesComando).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Variacao Comando Salvo !",
                        Toast.LENGTH_LONG).show();
                App.variacoesComandoDTO.setIdVariacaoComando(chave);
                gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_VARIACAO_COMANDO, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return variacoesComando;
    }

    @Override
    public VariacoesComando update(VariacoesComando variacoesComando) {
        DatabaseReference variacoesComandoRef = em.child(variacoesComando.getNomeTabela()).child(variacoesComando.getIdVariacaoComando());
        variacoesComandoRef.setValue(variacoesComando).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Variacao de Comando Salvo !",
                        Toast.LENGTH_LONG).show();
                gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_VARIACAO_COMANDO, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return variacoesComando;
    }

    @Override
    public void findAllByUsuarioIdComando(String idUsuario, String idComando) {
        App.listaVariacoesComandos = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query variacoesQuery = em.child("variacoesComandos").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    VariacoesComandoDTO variacoesComandoDTO = getVariacoesComandoDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    if (variacoesComandoDTO.getIdComando().equals(idComando)) {
                        App.listaVariacoesComandos.add(variacoesComandoDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_VARIACAO_POR_USUARIO_COMANDO,  null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        variacoesQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    VariacoesComandoDTO getVariacoesComandoDTO(DataSnapshot postSnapshot) {
        VariacoesComando variacoesComando = postSnapshot.getValue(VariacoesComando.class);

        VariacoesComandoDTO variacoesComandoDTO = new VariacoesComandoDTO();
        variacoesComandoDTO.setIdVariacaoComando(Objects.requireNonNull(variacoesComando).getIdVariacaoComando());
        variacoesComandoDTO.setIdComando(variacoesComando.getIdComando());
        variacoesComandoDTO.setIdUsuario(variacoesComando.getIdUsuario());
        variacoesComandoDTO.setTexto(variacoesComando.getTexto());

        return variacoesComandoDTO;
    }
}

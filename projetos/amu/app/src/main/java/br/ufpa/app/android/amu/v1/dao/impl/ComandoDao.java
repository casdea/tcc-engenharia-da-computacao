package br.ufpa.app.android.amu.v1.dao.impl;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IComandoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Comando;
import br.ufpa.app.android.amu.v1.dto.ComandoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class ComandoDao extends AbstractEntityDao<Comando> implements IComandoDao {

    final GerenteServicosListener gerenteServicosListener;

    public ComandoDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    @Override
    public Comando create(Comando comando) {
        DatabaseReference comandosRef = em.child(comando.getNomeTabela());
        String chave = comandosRef.push().getKey();
        comando.setIdComando(chave);
        comandosRef.child(Objects.requireNonNull(chave)).setValue(comando).addOnSuccessListener(unused -> {
            Toast.makeText(App.context,
                    "Registro Comando Salvo !",
                    Toast.LENGTH_LONG).show();
            App.comandoDTO.setIdComando(chave);
            gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_COMANDO, null);

        }).addOnFailureListener(e -> Toast.makeText(App.context,
                "Falha ao Registrar.Detalhes " + e.getMessage(),
                Toast.LENGTH_LONG).show());

        return comando;
    }

    @Override
    public Comando update(Comando comando) {
        DatabaseReference comandosRef = em.child(comando.getNomeTabela()).child(comando.getIdComando());
        comandosRef.setValue(comando).addOnSuccessListener(unused -> {
            Toast.makeText(App.context,
                    "Registro comando Salvo !",
                    Toast.LENGTH_LONG).show();
            gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_COMANDO, null);

        }).addOnFailureListener(e -> Toast.makeText(App.context,
                "Falha ao Registrar.Detalhes " + e.getMessage(),
                Toast.LENGTH_LONG).show());

        return comando;
    }

    @Override
    public void findAllByUsuario(String idUsuario) {
        App.listaComandos = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query comandosQuery = em.child("comandos").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComandoDTO comandoDTO = getComandoDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                       Log.i("Lendo dados ", postSnapshot.toString());

                    App.listaComandos.add(comandoDTO);
                    // TODO: handle the post
                }

                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_COMANDO_POR_USUARIO,  null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        comandosQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    ComandoDTO getComandoDTO(DataSnapshot postSnapshot) {
        Comando comando = postSnapshot.getValue(Comando.class);

        ComandoDTO comandoDTO = new ComandoDTO();
        comandoDTO.setIdComando(Objects.requireNonNull(comando).getIdComando());
        comandoDTO.setNrComando(comando.getNrComando());
        comandoDTO.setIdUsuario(comando.getIdUsuario());
        comandoDTO.setNomeComando(comando.getNomeComando());

        return comandoDTO;
    }
}

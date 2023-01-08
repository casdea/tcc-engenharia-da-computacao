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
import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IUtilizacaoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Utilizacao;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;

public class UtilizacaoDao extends AbstractEntityDao<Utilizacao> implements IUtilizacaoDao {

    final GerenteServicosListener gerenteServicosListener;
    final Callable callable;

    public UtilizacaoDao(DatabaseReference em, AppCompatActivity atividade, Callable callable) {
        super(em);
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
        this.callable = callable;
    }

    @Override
    public Utilizacao create(Utilizacao utilizacao) {
        DatabaseReference utilizacoesRef = em.child(utilizacao.getNomeTabela());
        String chave = utilizacoesRef.push().getKey();
        utilizacao.setIdUtilizacao(chave);
        utilizacoesRef.child(Objects.requireNonNull(chave)).setValue(utilizacao).addOnSuccessListener(unused -> {
            Toast.makeText(App.context,
                    "Registro Utilizacao Salvo !",
                    Toast.LENGTH_LONG).show();
            App.utilizacaoDTO.setIdUtilizacao(chave);
            App.listaUtilizacoes.add(App.utilizacaoDTO);
            gerenteServicosListener.carregarLista(Constantes.ACAO_UTLIZACAO_REMEDIO_CONCLUIDA, null);

        }).addOnFailureListener(e -> Toast.makeText(App.context,
                "Falha ao Registrar.Detalhes " + e.getMessage(),
                Toast.LENGTH_LONG).show());

        return utilizacao;
    }

    @Override
    public Utilizacao update(Utilizacao utilizacao) {
        DatabaseReference utilizacoesRef = em.child(utilizacao.getNomeTabela()).child(utilizacao.getIdUtilizacao());
        utilizacoesRef.setValue(utilizacao).addOnSuccessListener(unused -> {
            Toast.makeText(App.context,
                    "Registro utilizacao Salvo !",
                    Toast.LENGTH_LONG).show();
            gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_UTILIZACAO, null);

        }).addOnFailureListener(e -> Toast.makeText(App.context,
                "Falha ao Registrar.Detalhes " + e.getMessage(),
                Toast.LENGTH_LONG).show());

        return utilizacao;
    }

    @Override
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento) {
        App.listaUtilizacoes = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query utilizacoesQuery = em.child("utilizacoes").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UtilizacaoDTO utilizacaoDTO = getUtilizacaoDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    if (utilizacaoDTO.getIdMedicamento().equals(idMedicamento)) {
                        App.listaUtilizacoes.add(utilizacaoDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_UTILIZACAO_POR_USUARIO_MEDICAMENTO,  null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        utilizacoesQuery.addListenerForSingleValueEvent(evento);

    }

    @Override
    public void findAllByUsuario(String idUsuario) {
        App.listaUtilizacoes = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query utilizacoesQuery = em.child("utilizacoes").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UtilizacaoDTO utilizacaoDTO = getUtilizacaoDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    try {
                        if (!DataUtil.convertStringToDateTime(utilizacaoDTO.getDataHora()).before(DataUtil.getDataAtual()))
                            App.listaUtilizacoes.add(utilizacaoDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        utilizacoesQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    UtilizacaoDTO getUtilizacaoDTO(DataSnapshot postSnapshot) {
        Utilizacao utilizacao = postSnapshot.getValue(Utilizacao.class);

        UtilizacaoDTO utilizacaoDTO = new UtilizacaoDTO();
        utilizacaoDTO.setIdUtilizacao(Objects.requireNonNull(utilizacao).getIdUtilizacao());
        utilizacaoDTO.setIdMedicamento(utilizacao.getIdMedicamento());
        utilizacaoDTO.setIdUsuario(utilizacao.getIdUsuario());
        utilizacaoDTO.setDataHora(utilizacao.getDataHora());

        return utilizacaoDTO;
    }
}

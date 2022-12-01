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
import java.util.List;

import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IUtilizacaoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Utilizacao;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class UtilizacaoDao extends AbstractEntityDao<Utilizacao> implements IUtilizacaoDao {

    private GerenteServicosListener gerenteServicosListener;
    private AppCompatActivity atividade;

    public UtilizacaoDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public Class<Utilizacao> getClassImplement() {
        return Utilizacao.class;
    }

    @Override
    public Utilizacao create(Utilizacao utilizacao) {
        DatabaseReference utilizacoesRef = em.child(utilizacao.getNomeTabela());
        String chave = utilizacoesRef.push().getKey();
        utilizacao.setIdUtilizacao(chave);
        utilizacoesRef.child(chave).setValue(utilizacao).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Utilizacao Salvo !",
                        Toast.LENGTH_LONG).show();
                App.utilizacaoDTO.setIdUtilizacao(chave);
                gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_HORARIO, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return utilizacao;
    }

    @Override
    public Utilizacao update(Utilizacao utilizacao) {
        DatabaseReference utilizacoesRef = em.child(utilizacao.getNomeTabela()).child(utilizacao.getIdUtilizacao());
        utilizacoesRef.setValue(utilizacao).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro utilizacao Salvo !",
                        Toast.LENGTH_LONG).show();
                gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_HORARIO, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return utilizacao;
    }

    @Override
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento) {
        List<UtilizacaoDTO> listaUtilizacoes = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query horariosQuery = em.child("horarios").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UtilizacaoDTO utilizacaoDTO = getUtilizacaoDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (utilizacaoDTO.getIdMedicamento().equals(idMedicamento)) {
                        listaUtilizacoes.add(utilizacaoDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_MEDICAMENTO_POR_USUARIO_PRODUTO, listaUtilizacoes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        horariosQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    private UtilizacaoDTO getUtilizacaoDTO(DataSnapshot postSnapshot) {
        Utilizacao utilizacao = postSnapshot.getValue(Utilizacao.class);

        UtilizacaoDTO utilizacaoDTO = new UtilizacaoDTO();
        utilizacaoDTO.setIdUtilizacao(utilizacao.getIdUtilizacao());
        utilizacaoDTO.setIdMedicamento(utilizacao.getIdMedicamento());
        utilizacaoDTO.setIdUsuario(utilizacao.getIdUsuario());
        utilizacaoDTO.setDataHora(utilizacao.getDataHora());

        return utilizacaoDTO;
    }
}

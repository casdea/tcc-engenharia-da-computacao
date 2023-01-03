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
import java.util.concurrent.Callable;

import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IAlarmeDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Alarme;
import br.ufpa.app.android.amu.v1.dto.AlarmeDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;

public class AlarmeDao extends AbstractEntityDao<Alarme> implements IAlarmeDao {

    private GerenteServicosListener gerenteServicosListener;
    private AppCompatActivity atividade;
    private Callable proximoComando;

    public AlarmeDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public AlarmeDao(DatabaseReference em, AppCompatActivity atividade, Callable proximoComando) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
        this.proximoComando = proximoComando;
    }

    public Class<Alarme> getClassImplement() {
        return Alarme.class;
    }

    @Override
    public Alarme create(Alarme alarme) {
        DatabaseReference alarmesRef = em.child(alarme.getNomeTabela());
        String chave = alarmesRef.push().getKey();
        alarme.setIdAlarme(chave);
        alarmesRef.child(chave).setValue(alarme).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Alarme Salvo !",
                        Toast.LENGTH_LONG).show();
                App.alarmeDTO.setIdAlarme(chave);
                App.listaAlarmes.add(App.alarmeDTO);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return alarme;
    }

    @Override
    public Alarme update(Alarme alarme) {
        DatabaseReference alarmesRef = em.child(alarme.getNomeTabela()).child(alarme.getIdAlarme());
        alarmesRef.setValue(alarme).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Alarme Salvo !",
                        Toast.LENGTH_LONG).show();
                gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_ALARME_CONCLUIDO, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return alarme;
    }

    @Override
    public void findAllByUsuario(String idUsuario) {
        App.listaAlarmes = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query alarmesQuery = em.child("alarmes").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    AlarmeDTO alarmeDTO = getAlarmeDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (DataUtil.convertStringToDateTime(alarmeDTO.getDataHora()).before(DataUtil.getDataAtual())==false)
                        App.listaAlarmes.add(alarmeDTO);
                }

              //  gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_ALARME_HOJE, App.listaAlarmes);
                try {
                    proximoComando.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        alarmesQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    private AlarmeDTO getAlarmeDTO(DataSnapshot postSnapshot) {
        Alarme alarme = postSnapshot.getValue(Alarme.class);

        AlarmeDTO alarmeDTO = new AlarmeDTO();
        alarmeDTO.setIdAlarme(alarme.getIdAlarme());
        alarmeDTO.setIdMedicamento(alarme.getIdMedicamento());
        alarmeDTO.setIdUsuario(alarme.getIdUsuario());
        alarmeDTO.setDataHora(alarme.getDataHora());
        alarmeDTO.setTipoAlarme(alarme.getTipoAlarme());
        alarmeDTO.setDescricao(alarme.getDescricao());
        alarmeDTO.setTitulo(alarme.getTitulo());

        return alarmeDTO;
    }
}

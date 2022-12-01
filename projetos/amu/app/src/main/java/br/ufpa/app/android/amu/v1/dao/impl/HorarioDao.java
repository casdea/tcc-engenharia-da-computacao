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
import br.ufpa.app.android.amu.v1.dao.idao.IHorarioDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class HorarioDao extends AbstractEntityDao<Horario> implements IHorarioDao {

    private GerenteServicosListener gerenteServicosListener;
    private AppCompatActivity atividade;

    public HorarioDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public Class<Horario> getClassImplement() {
        return Horario.class;
    }

    @Override
    public Horario create(Horario horario) {
        DatabaseReference horariosRef = em.child(horario.getNomeTabela());
        String chave = horariosRef.push().getKey();
        horario.setIdHorario(chave);
        horariosRef.child(chave).setValue(horario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Horario Salvo !",
                        Toast.LENGTH_LONG).show();
                App.horarioDTO.setIdHorario(chave);
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

        return horario;
    }

    @Override
    public Horario update(Horario horario) {
        DatabaseReference horariosRef = em.child(horario.getNomeTabela()).child(horario.getIdHorario());
        horariosRef.setValue(horario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Horario Salvo !",
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

        return horario;
    }

    @Override
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento) {
        List<HorarioDTO> listaHorarios = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query horariosQuery = em.child("horarios").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    HorarioDTO horarioDTO = getHorarioDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (horarioDTO.getIdMedicamento().equals(idMedicamento)) {
                        listaHorarios.add(horarioDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_MEDICAMENTO_POR_USUARIO_PRODUTO, listaHorarios);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        horariosQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    private HorarioDTO getHorarioDTO(DataSnapshot postSnapshot) {
        Horario horario = postSnapshot.getValue(Horario.class);

        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setIdHorario(horario.getIdHorario());
        horarioDTO.setIdMedicamento(horario.getIdMedicamento());
        horarioDTO.setIdUsuario(horario.getIdUsuario());
        horarioDTO.setDataInicial(horario.getDataInicial());
        horarioDTO.setHorarioInicial(horario.getHorarioInicial());
        horarioDTO.setNrDoses(horario.getNrDoses());
        horarioDTO.setQtdePorDose(horario.getQtdePorDose());
        horarioDTO.setAtivo(horario.getAtivo());

        return horarioDTO;
    }
}

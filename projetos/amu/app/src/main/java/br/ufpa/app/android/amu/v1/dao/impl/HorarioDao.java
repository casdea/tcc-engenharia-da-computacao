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
import br.ufpa.app.android.amu.v1.dao.idao.IHorarioDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class HorarioDao extends AbstractEntityDao<Horario> implements IHorarioDao {

    final GerenteServicosListener gerenteServicosListener;
    final Callable proximoComando;

    public HorarioDao(DatabaseReference em, AppCompatActivity atividade, Callable proximoComando) {
        super(em);
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
        this.proximoComando = proximoComando;
    }

    @Override
    public Horario create(Horario horario) {
        DatabaseReference horariosRef = em.child(horario.getNomeTabela());
        String chave = horariosRef.push().getKey();
        horario.setIdHorario(chave);
        horariosRef.child(Objects.requireNonNull(chave)).setValue(horario).addOnSuccessListener(unused -> {
            Toast.makeText(App.context,
                    "Registro Horario Salvo !",
                    Toast.LENGTH_LONG).show();
            App.horarioDTO.setIdHorario(chave);
            gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_HORARIO, null);

        }).addOnFailureListener(e -> Toast.makeText(App.context,
                "Falha ao Registrar.Detalhes " + e.getMessage(),
                Toast.LENGTH_LONG).show());

        return horario;
    }

    @Override
    public Horario update(Horario horario) {
        DatabaseReference horariosRef = em.child(horario.getNomeTabela()).child(horario.getIdHorario());
        horariosRef.setValue(horario).addOnSuccessListener(unused -> {
            Toast.makeText(App.context,
                    "Registro Horario Salvo !",
                    Toast.LENGTH_LONG).show();
            gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_HORARIO, null);

        }).addOnFailureListener(e -> Toast.makeText(App.context,
                "Falha ao Registrar.Detalhes " + e.getMessage(),
                Toast.LENGTH_LONG).show());

        return horario;
    }

    @Override
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento) {
        App.listaHorarios = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query horariosQuery = em.child("horarios").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    HorarioDTO horarioDTO = getHorarioDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    if (horarioDTO.getIdMedicamento().equals(idMedicamento)) {
                        App.listaHorarios.add(horarioDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_HORARIO_USUARIO_MEDICAMENTO, null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        horariosQuery.addListenerForSingleValueEvent(evento);

    }

    @Override
    public void findAllByUsuario(String idUsuario) {
        App.listaHorarios = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query horariosQuery = em.child("horarios").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    HorarioDTO horarioDTO = getHorarioDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    if (horarioDTO.getAtivo().equals("SIM"))
                        App.listaHorarios.add(horarioDTO);
                    // TODO: handle the post
                }

                //gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_HORARIO_ATIVO, App.listaHorarios);
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

        horariosQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    HorarioDTO getHorarioDTO(DataSnapshot postSnapshot) {
        Horario horario = postSnapshot.getValue(Horario.class);

        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setIdHorario(Objects.requireNonNull(horario).getIdHorario());
        horarioDTO.setIdMedicamento(horario.getIdMedicamento());
        horarioDTO.setIdUsuario(horario.getIdUsuario());
        horarioDTO.setDataInicial(horario.getDataInicial());
        horarioDTO.setHorarioInicial(horario.getHorarioInicial());
        horarioDTO.setIntervalo(horario.getIntervalo());
        horarioDTO.setNrDoses(horario.getNrDoses());
        horarioDTO.setQtdePorDose(horario.getQtdePorDose());
        horarioDTO.setAtivo(horario.getAtivo());

        return horarioDTO;
    }
}

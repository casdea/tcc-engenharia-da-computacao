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
import br.ufpa.app.android.amu.v1.dao.idao.IMedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;

public class MedicamentoDao extends AbstractEntityDao<Medicamento> implements IMedicamentoDao {

    private GerenteServicosListener gerenteServicosListener;
    private AppCompatActivity atividade;

    public MedicamentoDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public Class<Usuario> getClassImplement() {
        return Usuario.class;
    }

    @Override
    public Medicamento create(Medicamento medicamento) {
        DatabaseReference medicamentosRef = em.child(medicamento.getNomeTabela());
        String chave = medicamentosRef.push().getKey();
        medicamento.setIdMedicamento(chave);
        medicamentosRef.child(chave).setValue(medicamento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Salvo !",
                        Toast.LENGTH_LONG).show();
                gerenteServicosListener.executarAcao(1, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes "+e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return medicamento;
    }

    @Override
    public Medicamento update(Medicamento medicamento) {
        DatabaseReference medicamentosRef = em.child(medicamento.getNomeTabela()).child(medicamento.getIdMedicamento());
        medicamentosRef.setValue(medicamento);

        return medicamento;
    }

    @Override
    public void findAllByUsuario(String idUsuario) {
        List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query medicamentosQuery = em.child("medicamentos").orderByChild("idUsuario").equalTo(App.usuario.getIdUsuario());

        medicamentosQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Medicamento medicamento = postSnapshot.getValue(Medicamento.class);
                    MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
                    medicamentoDTO.setNomeComercial(medicamento.getNomeComercial());
                    medicamentoDTO.setNomeFantasia(medicamento.getNomeFantasia());
                    medicamentoDTO.setFabricante(medicamento.getFabricante());
                    listaMedicamentos.add(medicamentoDTO);
                    Log.i("Lendo dados ",postSnapshot.toString());
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(listaMedicamentos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

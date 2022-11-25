package br.ufpa.app.android.amu.v1.dao.impl;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.ufpa.app.android.amu.v1.dao.idao.IMedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.util.App;

public class MedicamentoDao extends AbstractEntityDao<Medicamento> implements IMedicamentoDao {

    public MedicamentoDao(DatabaseReference em) {
        super(em);
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
    public List<MedicamentoDTO> findAllByUsuario(String idUsuario) {
        return null;
    }
}

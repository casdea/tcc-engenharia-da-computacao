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
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dao.modelo.Usuario;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

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
    public Medicamento create(Medicamento medicamento, Horario horario) {
        DatabaseReference medicamentosRef = em.child(medicamento.getNomeTabela());
        String chave = medicamentosRef.push().getKey();
        medicamento.setIdMedicamento(chave);
        medicamentosRef.child(chave).setValue(medicamento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Salvo !",
                        Toast.LENGTH_LONG).show();

                App.medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());

                DatabaseReference horariosRef = em.child(horario.getNomeTabela());
                String chaveHorario = horariosRef.push().getKey();
                horario.setIdHorario(chaveHorario);
                horario.setIdMedicamento(medicamento.getIdMedicamento());
                horariosRef.child(chaveHorario).setValue(horario).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_MEDICAMENTO, null);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return medicamento;
    }

    @Override
    public Medicamento update(Medicamento medicamento) {
        DatabaseReference medicamentosRef = em.child(medicamento.getNomeTabela()).child(medicamento.getIdMedicamento());
        medicamentosRef.setValue(medicamento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Salvo !",
                        Toast.LENGTH_LONG).show();

                gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_MEDICAMENTO, null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        return medicamento;
    }

    @Override
    public void findAllByUsuario(String idUsuario) {
        List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query medicamentosQuery = em.child("medicamentos").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    MedicamentoDTO medicamentoDTO = getMedicamentoDTO(postSnapshot);

                    listaMedicamentos.add(medicamentoDTO);
                    Log.i("Lendo dados ", postSnapshot.toString());
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_MEDICAMENTO_POR_USUARIO, listaMedicamentos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        medicamentosQuery.addListenerForSingleValueEvent(evento);
    }

    @Override
    public void findByUsuarioIdProduto(String idUsuario, String idProduto) {
        List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query medicamentosQuery = em.child("medicamentos").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    MedicamentoDTO medicamentoDTO = getMedicamentoDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (medicamentoDTO.getIdProdutoAnvisa().equals(idProduto)) {
                        listaMedicamentos.add(medicamentoDTO);
                        break;
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_MEDICAMENTO_POR_USUARIO_PRODUTO, listaMedicamentos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        medicamentosQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    private MedicamentoDTO getMedicamentoDTO(DataSnapshot postSnapshot) {
        Medicamento medicamento = postSnapshot.getValue(Medicamento.class);
        MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
        medicamentoDTO.setNomeComercial(medicamento.getNomeComercial());
        medicamentoDTO.setNomeFantasia(medicamento.getNomeFantasia());
        medicamentoDTO.setFabricante(medicamento.getFabricante());
        medicamentoDTO.setCor(medicamento.getCor());
        medicamentoDTO.setPrincipioAtivo(medicamento.getPrincipioAtivo());
        medicamentoDTO.setFormaApresentacao(medicamento.getFormaApresentacao());
        medicamentoDTO.setComposicao(medicamento.getComposicao());
        medicamentoDTO.setViaAdministracao(medicamento.getViaAdministracao());
        medicamentoDTO.setPublicoAlvo(medicamento.getPublicoAlvo());

        medicamentoDTO.setTextoParaQueIndicado(medicamento.getTextoParaQueIndicado());
        medicamentoDTO.setTextoComoFunciona(medicamento.getTextoComoFunciona());
        medicamentoDTO.setTextoComoUsar(medicamento.getTextoComoUsar());
        medicamentoDTO.setTextoSeEsquecerQueFazer(medicamento.getTextoSeEsquecerQueFazer());
        medicamentoDTO.setPublicoAlvo(medicamento.getPublicoAlvo());
        medicamentoDTO.setQtdeEmbalagem(medicamento.getQtdeEmbalagem());
        //Identificadores
        medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());
        medicamentoDTO.setIdProdutoAnvisa(medicamento.getIdProdutoAnvisa());
        medicamentoDTO.setDataProdutoAnvisa(medicamento.getDataProdutoAnvisa());
        medicamentoDTO.setIdUsuario(medicamento.getIdUsuario());
        //Listas
        medicamentoDTO.setEstoques(new ArrayList<>());
        medicamentoDTO.setUtilizacoes(new ArrayList<>());

/*        if (medicamento.getHorarios() == null) {
            for (Horario horario : medicamento.getHorarios()) {
                HorarioDTO horarioDTO = new HorarioDTO();
                horarioDTO.setIdHorario(horario.getIdHorario());
                horarioDTO.setIdMedicamento(horario.getIdMedicamento());
                horarioDTO.setIdUsuario(horario.getIdUsuario());
                horarioDTO.setDataInicial(horario.getDataInicial());
                horarioDTO.setHorarioInicial(horario.getHorarioInicial());
                horarioDTO.setNrDoses(horario.getNrDoses());
                horarioDTO.setQtdePorDose(horario.getQtdePorDose());
                horarioDTO.setAtivo(horario.getAtivo());
                medicamentoDTO.getHorarios().add(horarioDTO);
            }
        }
*/
        return medicamentoDTO;
    }
}

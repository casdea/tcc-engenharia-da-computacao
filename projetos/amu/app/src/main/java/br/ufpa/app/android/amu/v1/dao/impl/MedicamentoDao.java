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

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IMedicamentoDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;
import br.ufpa.app.android.amu.v1.util.DataUtil;

public class MedicamentoDao extends AbstractEntityDao<Medicamento> implements IMedicamentoDao {

    final GerenteServicosListener gerenteServicosListener;

    public MedicamentoDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    @Override
    public void create(Medicamento medicamento, Horario horario) {
        DatabaseReference medicamentosRef = em.child(medicamento.getNomeTabela());
        String chave = medicamentosRef.push().getKey();
        medicamento.setIdMedicamento(chave);
        medicamentosRef.child(chave).setValue(medicamento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                App.medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());

                DatabaseReference horariosRef = em.child(horario.getNomeTabela());
                String chaveHorario = horariosRef.push().getKey();
                horario.setIdHorario(chaveHorario);
                horario.setIdMedicamento(medicamento.getIdMedicamento());
                horariosRef.child(chaveHorario).setValue(horario).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        App.estoqueDTO = new EstoqueDTO();
                        App.estoqueDTO.setIdUsuario(medicamento.getIdUsuario());
                        App.estoqueDTO.setIdEstoque("0");
                        App.estoqueDTO.setIdMedicamento(medicamento.getIdMedicamento());
                        App.estoqueDTO.setDataHora(DataUtil.convertDateTimeToString(new java.util.Date()));
                        App.estoqueDTO.setEntrada(medicamento.getQtdeEmbalagem());
                        App.estoqueDTO.setSaida(0);
                        App.estoqueDTO.setSaldo(medicamento.getQtdeEmbalagem());

                        Estoque estoque = new Estoque(App.estoqueDTO);

                        DatabaseReference estoqueRef = em.child(estoque.getNomeTabela());
                        String chaveEstoque = estoqueRef.push().getKey();
                        estoque.setIdEstoque(chaveEstoque);
                        estoque.setIdMedicamento(medicamento.getIdMedicamento());
                        estoqueRef.child(chaveEstoque).setValue(estoque).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(App.context,
                                        "Registro de Estoque Salvo !",
                                        Toast.LENGTH_LONG).show();
                                gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_MEDICAMENTO, null);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(App.context,
                                        "Falha ao Registrar Estoque.Detalhes " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(App.context,
                                "Falha ao Registrar Horario.Detalhes " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar Medicamento.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

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
                    if (BuildConfig.DEBUG)
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

                    if (BuildConfig.DEBUG)
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
    MedicamentoDTO getMedicamentoDTO(DataSnapshot postSnapshot) {
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

        return medicamentoDTO;
    }
}

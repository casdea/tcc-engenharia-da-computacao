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

import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IEstoqueDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class EstoqueDao extends AbstractEntityDao<Estoque> implements IEstoqueDao {

    private GerenteServicosListener gerenteServicosListener;
    private AppCompatActivity atividade;

    public EstoqueDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.atividade = atividade;
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    public Class<Estoque> getClassImplement() {
        return Estoque.class;
    }

    @Override
    public Estoque create(Estoque estoque) {
        DatabaseReference estoquesRef = em.child(estoque.getNomeTabela());
        String chave = estoquesRef.push().getKey();
        estoque.setIdEstoque(chave);
        estoquesRef.child(chave).setValue(estoque).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Estoque Salvo !",
                        Toast.LENGTH_LONG).show();
                App.estoqueDTO.setIdEstoque(chave);
                gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_ESTOQUE, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return estoque;
    }

    @Override
    public Estoque update(Estoque estoque) {
        DatabaseReference estoquesRef = em.child(estoque.getNomeTabela()).child(estoque.getIdEstoque());
        estoquesRef.setValue(estoque).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(App.context,
                        "Registro Estoque Salvo !",
                        Toast.LENGTH_LONG).show();
                gerenteServicosListener.executarAcao(Constantes.ACAO_ALTERAR_ESTOQUE, null);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(App.context,
                        "Falha ao Registrar.Detalhes " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return estoque;
    }

    @Override
    public void findAllByUsuarioIdMedicamento(String idUsuario, String idMedicamento) {
            App.listaEstoques = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query estoquesQuery = em.child("estoques").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    EstoqueDTO estoqueDTO = getEstoqueDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (estoqueDTO.getIdMedicamento().equals(idMedicamento)) {
                        App.listaEstoques.add(estoqueDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_ESTOQUE_POR_USUARIO_MEDICAMENTO, App.listaEstoques);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        estoquesQuery.addListenerForSingleValueEvent(evento);

    }

    public void atualizarSaldoEstoque(String idUsuario, String idMedicamento, EstoqueDTO movtoEstoqueDTO) {
        List<EstoqueDTO> listaEstoques = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query estoquesQuery = em.child("estoques").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EstoqueDTO estoqueDTO = null;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    estoqueDTO = getEstoqueDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    // TODO: handle the post
                }

                if (estoqueDTO != null) {
                    movtoEstoqueDTO.setSaldo(estoqueDTO.getSaldo() + movtoEstoqueDTO.getEntrada() - movtoEstoqueDTO.getSaida());
                }
                else {
                    movtoEstoqueDTO.setSaldo(movtoEstoqueDTO.getSaldo() + movtoEstoqueDTO.getEntrada() - movtoEstoqueDTO.getSaida());
                }

                if (movtoEstoqueDTO.getSaldo() < 0) {
                    gerenteServicosListener.executarAcao(Constantes.ACAO_ERRO_SEM_SALDO_ESTOQUE,movtoEstoqueDTO);
                }
                else {
                    gerenteServicosListener.executarAcao(Constantes.ACAO_ATUALIZAR_SALDO_ESTOQUE,movtoEstoqueDTO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        estoquesQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    private EstoqueDTO getEstoqueDTO(DataSnapshot postSnapshot) {
        Estoque estoque = postSnapshot.getValue(Estoque.class);

        EstoqueDTO estoqueDTO = new EstoqueDTO();
        estoqueDTO.setIdEstoque(estoque.getIdEstoque());
        estoqueDTO.setIdMedicamento(estoque.getIdMedicamento());
        estoqueDTO.setIdUsuario(estoque.getIdUsuario());
        estoqueDTO.setData(estoque.getData());
        estoqueDTO.setEntrada(estoque.getEntrada());
        estoqueDTO.setSaida(estoque.getSaida());
        estoqueDTO.setSaldo(estoque.getSaldo());

        return estoqueDTO;
    }
}

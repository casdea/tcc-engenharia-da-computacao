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
import java.util.Objects;

import br.ufpa.app.android.amu.v1.BuildConfig;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.idao.IEstoqueDao;
import br.ufpa.app.android.amu.v1.dao.infraestrutura.AbstractEntityDao;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class EstoqueDao extends AbstractEntityDao<Estoque> implements IEstoqueDao {

    final GerenteServicosListener gerenteServicosListener;

    public EstoqueDao(DatabaseReference em, AppCompatActivity atividade) {
        super(em);
        this.gerenteServicosListener = (GerenteServicosListener) atividade;
    }

    @Override
    public Estoque create(Estoque estoque) {
        DatabaseReference estoquesRef = em.child(estoque.getNomeTabela());
        String chave = estoquesRef.push().getKey();
        estoque.setIdEstoque(chave);
        estoquesRef.child(Objects.requireNonNull(chave)).setValue(estoque).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                    if (BuildConfig.DEBUG)
                       Log.i("Lendo dados ", postSnapshot.toString());

                    if (estoqueDTO.getIdMedicamento().equals(idMedicamento)) {
                        App.listaEstoques.add(estoqueDTO);
                    }
                    // TODO: handle the post
                }
                gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_ESTOQUE_POR_USUARIO_MEDICAMENTO, null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        estoquesQuery.addListenerForSingleValueEvent(evento);

    }

    public void atualizarSaldoEstoque(String idUsuario, EstoqueDTO movtoEstoqueDTO) {
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query estoquesQuery = em.child("estoques").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EstoqueDTO estoqueDTO = null;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    estoqueDTO = getEstoqueDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    // TODO: handle the post
                }

                if (estoqueDTO != null) {
                    movtoEstoqueDTO.setSaldo(estoqueDTO.getSaldo() + movtoEstoqueDTO.getEntrada() - movtoEstoqueDTO.getSaida());
                } else {
                    movtoEstoqueDTO.setSaldo(movtoEstoqueDTO.getSaldo() + movtoEstoqueDTO.getEntrada() - movtoEstoqueDTO.getSaida());
                }

                if (movtoEstoqueDTO.getSaldo() < 0) {
                    gerenteServicosListener.executarAcao(Constantes.ACAO_ERRO_SEM_SALDO_ESTOQUE, movtoEstoqueDTO);
                } else {
                    Estoque estoque = new Estoque(movtoEstoqueDTO);

                    DatabaseReference estoquesRef = em.child("estoques");
                    String chave = estoquesRef.push().getKey();
                    estoque.setIdEstoque(chave);
                    estoquesRef.child(Objects.requireNonNull(chave)).setValue(estoque).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            movtoEstoqueDTO.setIdEstoque(chave);
                            App.listaEstoques.add(movtoEstoqueDTO);

                            gerenteServicosListener.carregarLista(Constantes.ACAO_OBTER_LISTA_ESTOQUE_POR_USUARIO_MEDICAMENTO, null);
                            gerenteServicosListener.executarAcao(Constantes.ACAO_AVISAR_SALDO_ATUALIZADO, movtoEstoqueDTO);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            gerenteServicosListener.executarAcao(Constantes.ACAO_ERRO_AO_ATUALIZAR_SALDO_ESTOQUE, "Não foi possivel atualizar o estoque.");
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        estoquesQuery.addListenerForSingleValueEvent(evento);

    }

    public void sinalizarDoseRealizada(String idUsuario, EstoqueDTO movtoEstoqueDTO) {
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query estoquesQuery = em.child("estoques").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EstoqueDTO estoqueDTO = null;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    estoqueDTO = getEstoqueDTO(postSnapshot);

                    if (BuildConfig.DEBUG)
                        Log.i("Lendo dados ", postSnapshot.toString());

                    // TODO: handle the post
                }

                if (estoqueDTO != null) {
                    movtoEstoqueDTO.setSaldo(estoqueDTO.getSaldo() + movtoEstoqueDTO.getEntrada() - movtoEstoqueDTO.getSaida());
                } else {
                    movtoEstoqueDTO.setSaldo(movtoEstoqueDTO.getSaldo() + movtoEstoqueDTO.getEntrada() - movtoEstoqueDTO.getSaida());
                }

                if (movtoEstoqueDTO.getSaldo() < 0) {
                    gerenteServicosListener.executarAcao(Constantes.ACAO_ERRO_SEM_SALDO_ESTOQUE, movtoEstoqueDTO);
                } else {
                    Estoque estoque = new Estoque(movtoEstoqueDTO);

                    DatabaseReference estoquesRef = em.child("estoques");
                    String chave = estoquesRef.push().getKey();
                    estoque.setIdEstoque(chave);
                    estoquesRef.child(Objects.requireNonNull(chave)).setValue(estoque).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            movtoEstoqueDTO.setIdEstoque(chave);
                            App.listaEstoques.add(movtoEstoqueDTO);
                            gerenteServicosListener.executarAcao(Constantes.ACAO_REGISTRAR_UTILIZACAO, movtoEstoqueDTO);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            gerenteServicosListener.executarAcao(Constantes.ACAO_ERRO_AO_ATUALIZAR_SALDO_ESTOQUE, "Não foi possivel atualizar o estoque.");
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        estoquesQuery.addListenerForSingleValueEvent(evento);

    }

    @NonNull
    EstoqueDTO getEstoqueDTO(DataSnapshot postSnapshot) {
        Estoque estoque = postSnapshot.getValue(Estoque.class);

        EstoqueDTO estoqueDTO = new EstoqueDTO();
        estoqueDTO.setIdEstoque(Objects.requireNonNull(estoque).getIdEstoque());
        estoqueDTO.setIdMedicamento(estoque.getIdMedicamento());
        estoqueDTO.setIdUsuario(estoque.getIdUsuario());
        estoqueDTO.setDataHora(estoque.getDataHora());
        estoqueDTO.setEntrada(estoque.getEntrada());
        estoqueDTO.setSaida(estoque.getSaida());
        estoqueDTO.setSaldo(estoque.getSaldo());

        return estoqueDTO;
    }
}

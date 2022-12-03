package br.ufpa.app.android.amu.v1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.adapter.EstoquesRecyclerViewAdapter;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.modelo.Estoque;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.helper.RecyclerItemClickListener;
import br.ufpa.app.android.amu.v1.util.App;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstoquesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstoquesFragment extends Fragment implements DetalheMedicamentoActivity.OnEstoquesListener {
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstoquesFragment newInstance(String param1, String param2) {
        EstoquesFragment fragment = new EstoquesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estoque_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        atualizarLista(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento(), getView().getContext());
    }

    private void atualizarLista(String idUsuario, String idMedicamento, Context context) {
        App.listaEstoques = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query estoquesQuery = em.child("estoques").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                App.listaEstoques = new ArrayList<>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    EstoqueDTO estoqueDTO = getEstoqueDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (estoqueDTO.getIdMedicamento().equals(idMedicamento)) {
                        App.listaEstoques.add(estoqueDTO);
                    }
                    // TODO: handle the post
                }

                EstoquesRecyclerViewAdapter estoquesRecyclerViewAdapter = new EstoquesRecyclerViewAdapter((List<EstoqueDTO>) App.listaEstoques);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context); //getView().getContext()
                if (recyclerView == null) recyclerView = App.viewEstoque.findViewById(R.id.recyclerView);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(estoquesRecyclerViewAdapter);

                App.viewEstoque = getView();

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

    @Override
    public void atualizarLista(Context context) {
        atualizarLista(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento(), context);
    }
}
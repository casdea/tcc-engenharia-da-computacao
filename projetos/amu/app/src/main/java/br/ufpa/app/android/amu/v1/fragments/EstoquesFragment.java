package br.ufpa.app.android.amu.v1.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstoquesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstoquesFragment extends Fragment implements DetalheMedicamentoActivity.OnEstoquesListener, GerenteServicosListener, View.OnClickListener, View.OnTouchListener {
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GerenteServicosListener gerenteServicosListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        gerenteServicosListener = (GerenteServicosListener) activity;
    }

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
                        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                            gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                            gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, position);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                            gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, i);
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
        GerenteServicos gerenteServicos = new GerenteServicos((DetalheMedicamentoActivity) getActivity());
        gerenteServicos.obterListaEstoquesByUsuarioMedicamento(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento());
    }

    @Override
    public void atualizarLista(View view) {
        EstoquesRecyclerViewAdapter estoquesRecyclerViewAdapter = new EstoquesRecyclerViewAdapter((List<EstoqueDTO>) App.listaEstoques);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        if (recyclerView == null) recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(estoquesRecyclerViewAdapter);
    }

    @Override
    public void carregarLista(int numeroAcao, List<?> lista) {

    }

    @Override
    public void executarAcao(int numeroAcao, Object parametro) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
            gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, 0);
        }

        return false;
    }
}
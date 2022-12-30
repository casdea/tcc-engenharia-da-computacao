package br.ufpa.app.android.amu.v1.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.adapter.EstoquesRecyclerViewAdapter;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;
import br.ufpa.app.android.amu.v1.helper.RecyclerItemClickListener;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class EstoquesFragment extends Fragment implements DetalheMedicamentoActivity.OnEstoquesListener, GerenteServicosListener, View.OnClickListener, View.OnTouchListener {
    private RecyclerView recyclerView;

    private GerenteServicosListener gerenteServicosListener;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        gerenteServicosListener = (GerenteServicosListener) activity;
    }

    public EstoquesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estoque_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(this);
        recyclerView.setOnTouchListener(this);

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
        recyclerView.invalidate();
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
package br.ufpa.app.android.amu.v1.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.activity.DetalheMedicamentoActivity;
import br.ufpa.app.android.amu.v1.activity.HorarioActivity;
import br.ufpa.app.android.amu.v1.adapter.HorariosRecyclerViewAdapter;
import br.ufpa.app.android.amu.v1.helper.RecyclerItemClickListener;
import br.ufpa.app.android.amu.v1.integracao.classes.TipoPerfil;
import br.ufpa.app.android.amu.v1.interfaces.GerenteServicosListener;
import br.ufpa.app.android.amu.v1.servicos.GerenteServicos;
import br.ufpa.app.android.amu.v1.util.App;
import br.ufpa.app.android.amu.v1.util.Constantes;

public class HorariosFragment extends Fragment implements DetalheMedicamentoActivity.OnHorariosListener, GerenteServicosListener, View.OnClickListener, View.OnTouchListener {
    private RecyclerView recyclerView;

    GerenteServicosListener gerenteServicosListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        gerenteServicosListener = (GerenteServicosListener) context;
    }

    public HorariosFragment() {
        // Required empty public constructor
    }

    final ActivityResultLauncher<Intent> mnutencaoHorarioActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_horario_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(this);
        //recyclerView.setOnTouchListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA))
                            gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, position);
                        else {
                            App.horarioDTO = App.listaHorarios.get(position);
                            Intent intent = new Intent(view.getContext(), HorarioActivity.class);
                            mnutencaoHorarioActivityResultLauncher.launch(intent);
                        }
                    }

                    @Override
                    public void onLongItemClick(int position) {
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
                if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
                    gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, 0);
                }
                else {
                    App.horarioDTO = null;
                    Intent intent = new Intent(view.getContext(), HorarioActivity.class);
                    mnutencaoHorarioActivityResultLauncher.launch(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        GerenteServicos gerenteServicos = new GerenteServicos((DetalheMedicamentoActivity) getActivity());
        gerenteServicos.obterListaHorariosByUsuarioMedicamento(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento());
    }

    @Override
    public void atualizarLista(View view) {
        HorariosRecyclerViewAdapter horariosRecyclerViewAdapter = new HorariosRecyclerViewAdapter(App.listaHorarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        if (recyclerView == null) recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(horariosRecyclerViewAdapter);
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
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (App.tipoPerfil.equals(TipoPerfil.PCD_VISAO_REDUZIDA)) {
                    gerenteServicosListener.executarAcao(Constantes.ACAO_CHAMAR_COMANDO_VOZ, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
            default:
                break;
        }
        return true;
    }
}
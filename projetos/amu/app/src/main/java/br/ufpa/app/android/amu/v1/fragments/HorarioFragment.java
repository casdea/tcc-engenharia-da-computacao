package br.ufpa.app.android.amu.v1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.adapter.HorarioRecyclerViewAdapter;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;

/**
 * A fragment representing a list of Items.
 */
public class HorarioFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HorarioFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HorarioFragment newInstance(int columnCount) {
        HorarioFragment fragment = new HorarioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horario_list, container, false);

        List<HorarioDTO> listaHorarios = new ArrayList<>();
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setDataInicial("01/12/2022");
        horarioDTO.setHorarioInicial("08:00");
        horarioDTO.setNrDoses(3);
        horarioDTO.setQtdePorDose(1);
        horarioDTO.setAtivo("SIM");
        listaHorarios.add(horarioDTO);

        horarioDTO = new HorarioDTO();
        horarioDTO.setDataInicial("05/12/2022");
        horarioDTO.setHorarioInicial("09:00");
        horarioDTO.setNrDoses(4);
        horarioDTO.setQtdePorDose(2);
        horarioDTO.setAtivo("NÃO");
        listaHorarios.add(horarioDTO);

        horarioDTO = new HorarioDTO();
        horarioDTO.setDataInicial("10/12/2022");
        horarioDTO.setHorarioInicial("10:00");
        horarioDTO.setNrDoses(3);
        horarioDTO.setQtdePorDose(1);
        horarioDTO.setAtivo("NÃO");
        listaHorarios.add(horarioDTO);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new HorarioRecyclerViewAdapter(listaHorarios));
        }
        return view;
    }
}
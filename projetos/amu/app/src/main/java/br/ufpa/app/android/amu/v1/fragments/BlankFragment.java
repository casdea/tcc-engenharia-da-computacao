package br.ufpa.app.android.amu.v1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.adapter.BlankRecyclerViewAdapter;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
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
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
        View view  = inflater.inflate(R.layout.fragment_blank, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });


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

        BlankRecyclerViewAdapter blankRecyclerViewAdapter = new BlankRecyclerViewAdapter((List<HorarioDTO>) listaHorarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(blankRecyclerViewAdapter);

        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new HorarioRecyclerViewAdapter(listaHorarios));
        } */

        return view;
    }
}
package br.ufpa.app.android.amu.v1.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.activity.HorarioActivity;
import br.ufpa.app.android.amu.v1.adapter.HorariosRecyclerViewAdapter;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.modelo.Horario;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.helper.RecyclerItemClickListener;
import br.ufpa.app.android.amu.v1.util.App;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HorariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorariosFragment extends Fragment {
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HorariosFragment() {
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
    public static HorariosFragment newInstance(String param1, String param2) {
        HorariosFragment fragment = new HorariosFragment();
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

    private ActivityResultLauncher<Intent> mnutencaoHorarioActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_horario_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        App.horarioDTO = App.listaHorarios.get(position);
                        Intent intent = new Intent(view.getContext(), HorarioActivity.class);
                        mnutencaoHorarioActivityResultLauncher.launch(intent);
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
                App.horarioDTO = null;
                Intent intent = new Intent(view.getContext(), HorarioActivity.class);
                mnutencaoHorarioActivityResultLauncher.launch(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        atualizarLista(App.usuario.getIdUsuario(), App.medicamentoDTO.getIdMedicamento());
    }

    private void atualizarLista(String idUsuario, String idMedicamento) {
        App.listaHorarios = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query horariosQuery = em.child("horarios").orderByChild("idUsuario").equalTo(idUsuario);

        ValueEventListener evento = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                App.listaHorarios = new ArrayList<>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    HorarioDTO horarioDTO = getHorarioDTO(postSnapshot);

                    Log.i("Lendo dados ", postSnapshot.toString());

                    if (horarioDTO.getIdMedicamento().equals(idMedicamento)) {
                        App.listaHorarios.add(horarioDTO);
                    }
                    // TODO: handle the post
                }

                HorariosRecyclerViewAdapter blankRecyclerViewAdapter = new HorariosRecyclerViewAdapter((List<HorarioDTO>) App.listaHorarios);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(blankRecyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        horariosQuery.addListenerForSingleValueEvent(evento);

    }

    private HorarioDTO getHorarioDTO(DataSnapshot postSnapshot) {
        Horario horario = postSnapshot.getValue(Horario.class);

        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setIdHorario(horario.getIdHorario());
        horarioDTO.setIdMedicamento(horario.getIdMedicamento());
        horarioDTO.setIdUsuario(horario.getIdUsuario());
        horarioDTO.setDataInicial(horario.getDataInicial());
        horarioDTO.setHorarioInicial(horario.getHorarioInicial());
        horarioDTO.setIntervalo(horario.getIntervalo());
        horarioDTO.setNrDoses(horario.getNrDoses());
        horarioDTO.setQtdePorDose(horario.getQtdePorDose());
        horarioDTO.setAtivo(horario.getAtivo());

        return horarioDTO;
    }

}
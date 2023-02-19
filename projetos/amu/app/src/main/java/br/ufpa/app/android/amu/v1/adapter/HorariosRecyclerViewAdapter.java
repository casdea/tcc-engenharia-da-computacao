package br.ufpa.app.android.amu.v1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class HorariosRecyclerViewAdapter extends RecyclerView.Adapter<HorariosRecyclerViewAdapter.HorarioViewHolder> {

    private final List<HorarioDTO> listaHistoricos;

    public HorariosRecyclerViewAdapter(List<HorarioDTO> listaHistoricos) {
        this.listaHistoricos = listaHistoricos;
    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_horario, parent, false);

        return new HorarioViewHolder(itemlista);

    }

    @Override
    public void onBindViewHolder(final HorarioViewHolder holder, int position) {
        holder.txvInicioAdministracao.setText(listaHistoricos.get(position).getDataInicial());
        holder.txvHorarioInicio.setText(listaHistoricos.get(position).getHorarioInicial());
        holder.txvDosesPorDia.setText(String.valueOf(listaHistoricos.get(position).getNrDoses()));
        holder.txvIntervalo.setText(String.valueOf(listaHistoricos.get(position).getIntervalo()));
        holder.txvQtdePorDose.setText(String.valueOf(listaHistoricos.get(position).getQtdePorDose()));
        holder.txvStatusHorario.setText(listaHistoricos.get(position).getAtivo().equals("SIM") ? "Status Horário: SIM" : "Status Horário: NÃO");
    }

    @Override
    public int getItemCount() {
        return listaHistoricos.size();
    }

    public static class HorarioViewHolder extends RecyclerView.ViewHolder {
        public final TextView txvInicioAdministracao;
        public final TextView txvHorarioInicio;
        public final TextView txvIntervalo;
        public final TextView txvDosesPorDia;
        public final TextView txvQtdePorDose;
        public final TextView txvStatusHorario;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txvInicioAdministracao = itemView.findViewById(R.id.txvInicioAdministracao);
            txvHorarioInicio = itemView.findViewById(R.id.txvHorarioInicio);
            txvIntervalo = itemView.findViewById(R.id.txvIntervalo);
            txvDosesPorDia = itemView.findViewById(R.id.txvDosesPorDia);
            txvQtdePorDose = itemView.findViewById(R.id.txvQtdePorDose);
            txvStatusHorario = itemView.findViewById(R.id.txvStatusHorario);
        }
    }
}
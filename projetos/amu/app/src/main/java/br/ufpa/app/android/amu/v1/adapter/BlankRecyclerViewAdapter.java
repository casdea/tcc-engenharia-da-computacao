package br.ufpa.app.android.amu.v1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.fragments.placeholder.PlaceholderContent.PlaceholderItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BlankRecyclerViewAdapter extends RecyclerView.Adapter<BlankRecyclerViewAdapter.HorarioViewHolder> {

    private final List<HorarioDTO> listaHistoricos;

    public BlankRecyclerViewAdapter(List<HorarioDTO> listaHistoricos) {
        this.listaHistoricos = listaHistoricos;
    }

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

        holder.txvQtdePorDose.setText(String.valueOf(listaHistoricos.get(position).getQtdePorDose()));
        holder.swAtivo.setChecked(listaHistoricos.get(position).getAtivo().equals("SIM"));
    }

    @Override
    public int getItemCount() {
        return listaHistoricos.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {
        public final TextView txvInicioAdministracao;
        public final TextView txvHorarioInicio;
        public final TextView txvDosesPorDia;
        public final TextView txvQtdePorDose;
        public final Switch swAtivo;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txvInicioAdministracao = itemView.findViewById(R.id.txvInicioAdministracao);
            txvHorarioInicio = itemView.findViewById(R.id.txvHorarioInicio);
            txvDosesPorDia = itemView.findViewById(R.id.txvDosesPorDia);
            txvQtdePorDose = itemView.findViewById(R.id.txvQtdePorDose);
            swAtivo = itemView.findViewById(R.id.swAtivo);
        }
    }
}
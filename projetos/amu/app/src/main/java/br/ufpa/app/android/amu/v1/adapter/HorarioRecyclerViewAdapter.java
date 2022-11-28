package br.ufpa.app.android.amu.v1.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.databinding.FragmentHorarioBinding;
import br.ufpa.app.android.amu.v1.dto.HorarioDTO;
import br.ufpa.app.android.amu.v1.fragments.placeholder.PlaceholderContent.PlaceholderItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HorarioRecyclerViewAdapter extends RecyclerView.Adapter<HorarioRecyclerViewAdapter.ViewHolder> {

    private final List<HorarioDTO> listaHistoricos;

    public HorarioRecyclerViewAdapter(List<HorarioDTO> listaHistoricos) {
        this.listaHistoricos = listaHistoricos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentHorarioBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txvInicioAdministracao;
        public final TextView txvHorarioInicio;
        public final TextView txvDosesPorDia;
        public final TextView txvQtdePorDose;
        public final Switch swAtivo;
/*
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvInicioAdministracao = itemView.findViewById(R.id.txvInicioAdministracao);
            txvHorarioInicio = itemView.findViewById(R.id.txvHorarioInicio);
            txvDosesPorDia = itemView.findViewById(R.id.txvDosesPorDia);
            txvQtdePorDose = itemView.findViewById(R.id.txvQtdePorDose);
            swAtivo = itemView.findViewById(R.id.swAtivo);
        }
*/
        public ViewHolder(FragmentHorarioBinding binding) {
            super(binding.getRoot());
            txvInicioAdministracao = binding.txvInicioAdministracao;
            txvHorarioInicio = binding.txvHorarioInicio;
            txvDosesPorDia = binding.txvDosesPorDia;
            txvQtdePorDose = binding.txvQtdePorDose;
            swAtivo = binding.swAtivo;

        }
    }
}
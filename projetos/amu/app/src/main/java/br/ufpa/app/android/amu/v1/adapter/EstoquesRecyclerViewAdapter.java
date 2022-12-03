package br.ufpa.app.android.amu.v1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.EstoqueDTO;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class EstoquesRecyclerViewAdapter extends RecyclerView.Adapter<EstoquesRecyclerViewAdapter.HorarioViewHolder> {

    private final List<EstoqueDTO> lista;

    public EstoquesRecyclerViewAdapter(List<EstoqueDTO> lista) {
        this.lista = lista;
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_estoque, parent, false);

        return new HorarioViewHolder(itemlista);

    }

    @Override
    public void onBindViewHolder(final HorarioViewHolder holder, int position) {
        holder.saldoParte1.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
        holder.saldoParte2.setVisibility(position >= 1 ? View.VISIBLE : View.INVISIBLE);

        holder.txvDataHora.setText(lista.get(position).getData());
        holder.txvEntrada.setText(String.valueOf(lista.get(position).getEntrada()));
        holder.txvSaida.setText(String.valueOf(lista.get(position).getSaida()));
        holder.txvSaldo.setText(String.valueOf(lista.get(position).getSaldo()));
        holder.txvDataHora2.setText(lista.get(position).getData());
        holder.txvEntrada2.setText(String.valueOf(lista.get(position).getEntrada()));
        holder.txvSaida2.setText(String.valueOf(lista.get(position).getSaida()));
        holder.txvSaldo2.setText(String.valueOf(lista.get(position).getSaldo()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {

        public androidx.constraintlayout.widget.ConstraintLayout saldoParte1;
        public androidx.constraintlayout.widget.ConstraintLayout saldoParte2;
        public final TextView txvDataHora;
        public final TextView txvEntrada;
        public final TextView txvSaida;
        public final TextView txvSaldo;
        public final TextView txvDataHora2;
        public final TextView txvEntrada2;
        public final TextView txvSaida2;
        public final TextView txvSaldo2;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            saldoParte1 = itemView.findViewById(R.id.saldoParte1);
            saldoParte2 = itemView.findViewById(R.id.saldoParte2);
            txvDataHora = itemView.findViewById(R.id.txvDataHora);
            txvEntrada = itemView.findViewById(R.id.txvEntrada);
            txvSaida = itemView.findViewById(R.id.txvSaida);
            txvSaldo = itemView.findViewById(R.id.txvSaldo);
            txvDataHora2 = itemView.findViewById(R.id.txvDataHora2);
            txvEntrada2 = itemView.findViewById(R.id.txvEntrada2);
            txvSaida2 = itemView.findViewById(R.id.txvSaida2);
            txvSaldo2 = itemView.findViewById(R.id.txvSaldo2);
        }
    }
}
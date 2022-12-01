package br.ufpa.app.android.amu.v1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.UtilizacaoDTO;
import br.ufpa.app.android.amu.v1.fragments.placeholder.PlaceholderContent.PlaceholderItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UtilizacoesRecyclerViewAdapter extends RecyclerView.Adapter<UtilizacoesRecyclerViewAdapter.HorarioViewHolder> {

    private final List<UtilizacaoDTO> listaUtilizacoes;

    public UtilizacoesRecyclerViewAdapter(List<UtilizacaoDTO> listaUtilizacoes) {
        this.listaUtilizacoes = listaUtilizacoes;
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_utilizacao, parent, false);

        return new HorarioViewHolder(itemlista);

    }

    @Override
    public void onBindViewHolder(final HorarioViewHolder holder, int position) {
        holder.txvDataHora.setText(listaUtilizacoes.get(position).getDataHora());
    }

    @Override
    public int getItemCount() {
        return listaUtilizacoes.size();
    }

    public class HorarioViewHolder extends RecyclerView.ViewHolder {
        public final TextView txvDataHora;

        public HorarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txvDataHora = itemView.findViewById(R.id.txvDataHora);
        }
    }
}
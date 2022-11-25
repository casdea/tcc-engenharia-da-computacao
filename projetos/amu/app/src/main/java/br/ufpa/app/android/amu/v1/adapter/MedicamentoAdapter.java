package br.ufpa.app.android.amu.v1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHoder> {

    List<MedicamentoDTO> listaMedicamentos;

    public MedicamentoAdapter(List<MedicamentoDTO> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    @NonNull
    @Override
    public MedicamentoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoViewHoder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHoder holder, int position) {
        holder.txvNomeMedicamento.setText(listaMedicamentos.get(position).getNomeComercial());
        holder.txvNomeFantasia.setText(listaMedicamentos.get(position).getNomeFantasia());
        holder.txtNomeFabricante.setText(listaMedicamentos.get(position).getFabricante());
    }

    @Override
    public int getItemCount() {
        return listaMedicamentos.size();
    }

    public class MedicamentoViewHoder extends RecyclerView.ViewHolder {

        TextView txvNomeMedicamento;
        TextView txvNomeFantasia;
        TextView txtNomeFabricante;

        public MedicamentoViewHoder(@NonNull View itemView) {
            super(itemView);
            txvNomeMedicamento = itemView.findViewById(R.id.txvNomeMedicamento);
            txvNomeFantasia = itemView.findViewById(R.id.txvNomeFantasia);
            txtNomeFabricante = itemView.findViewById(R.id.txtNomeFabricante);
        }
    }

}

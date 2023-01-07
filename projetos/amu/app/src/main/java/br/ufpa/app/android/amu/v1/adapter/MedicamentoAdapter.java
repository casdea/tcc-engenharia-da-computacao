package br.ufpa.app.android.amu.v1.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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

    final List<MedicamentoDTO> listaMedicamentos;

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

        if (listaMedicamentos.get(position).getCor() != null && !listaMedicamentos.get(position).getCor().equals(""))
        {
            try {
                holder.fundo.setBackground(new ColorDrawable(Color.parseColor(listaMedicamentos.get(position).getCor())));
            }
            catch (Exception e)
            {
                Log.e("Erro ao Definir cor",e.getMessage());
            }
        }
        holder.txvNomeMedicamento.setText(listaMedicamentos.get(position).getNomeComercial());
        holder.txvNomeFantasia.setText(listaMedicamentos.get(position).getNomeFantasia());
        holder.txtNomeFabricante.setText(listaMedicamentos.get(position).getFabricante());
    }

    @Override
    public int getItemCount() {
        return listaMedicamentos.size();
    }

    public static class MedicamentoViewHoder extends RecyclerView.ViewHolder {

        final androidx.constraintlayout.widget.ConstraintLayout fundo;
        final TextView txvNomeMedicamento;
        final TextView txvNomeFantasia;
        final TextView txtNomeFabricante;

        public MedicamentoViewHoder(@NonNull View itemView) {
            super(itemView);
            fundo = itemView.findViewById(R.id.fundo);
            txvNomeMedicamento = itemView.findViewById(R.id.txvNomeMedicamento);
            txvNomeFantasia = itemView.findViewById(R.id.txvNomeFantasia);
            txtNomeFabricante = itemView.findViewById(R.id.txtNomeFabricante);
        }
    }

}

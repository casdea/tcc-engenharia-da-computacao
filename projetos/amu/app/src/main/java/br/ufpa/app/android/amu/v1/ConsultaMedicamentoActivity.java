package br.ufpa.app.android.amu.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa.IntegracaoBularioEletronicoAnvisa;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;

public class ConsultaMedicamentoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_medicamento);

        Button btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPesquisar) {
            EditText edtNomeComercial = (EditText) findViewById(R.id.edtNomeComercial);

            IntegracaoBularioEletronico integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico("ANVISA");

            List<MedicamentoRetDTO> lista = integracaoBularioEletronico.consultarDadosMedicamentos(this, edtNomeComercial.getText().toString());

            ListView lvMedicamentos = (ListView) findViewById(R.id.lvMedicamentos);

            ConsultaMedicamentosAdapter adapter = new ConsultaMedicamentosAdapter(ConsultaMedicamentoActivity.this, (ArrayList) lista);
            lvMedicamentos.setAdapter(adapter);
            lvMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;

                }

            });

        }
    }

    public class ConsultaMedicamentosAdapter extends ArrayAdapter<MedicamentoRetDTO> {
        public ConsultaMedicamentosAdapter(Context context, ArrayList<MedicamentoRetDTO> lista) {
            super(context, 0, lista);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final MedicamentoRetDTO medicamentosRetDTO = getItem(position);
            final ViewHolder holder;

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_medicamento, parent, false);

                holder = new ViewHolder();
                convertView.setTag(holder);

                holder.txvNomeComercial = (TextView) convertView.findViewById(R.id.txvNomeComercial);
                holder.txvNomeLaboratorio = (TextView) convertView.findViewById(R.id.txvNomeLaboratorio);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.txvNomeComercial.setText(medicamentosRetDTO.getNomeComercial());
            holder.txvNomeLaboratorio.setText(medicamentosRetDTO.getNomeLaboratorio());

            return convertView;
        }

        class ViewHolder {
            TextView txvNomeComercial;
            TextView txvNomeLaboratorio;
            int ref;
        }

    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
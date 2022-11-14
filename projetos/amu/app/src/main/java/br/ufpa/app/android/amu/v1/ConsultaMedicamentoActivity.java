package br.ufpa.app.android.amu.v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import br.ufpa.app.android.amu.v1.integracao.dto.ConsultarMedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.dto.MedicamentoRetDTO;
import br.ufpa.app.android.amu.v1.integracao.factory.FactoryIntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.integracao.interfaces.IntegracaoBularioEletronico;
import br.ufpa.app.android.amu.v1.util.App;

public class ConsultaMedicamentoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_medicamento);

        Button btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(this);

        ActivityCompat.requestPermissions(ConsultaMedicamentoActivity.this, PERMISSIONS, 112);
        App.context = this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPesquisar) {
            EditText edtNomeComercial = (EditText) findViewById(R.id.edtNomeComercial);
            TextView txvStatusConsulta = (TextView) findViewById(R.id.txvStatusConsulta);
            txvStatusConsulta.setText("");

            final IntegracaoBularioEletronico integracaoBularioEletronico = new FactoryIntegracaoBularioEletronico().createIntegracaoBularioEletronico("ANVISA");

            ConsultarMedicamentoRetDTO consultarMedicamentoRetDTO = integracaoBularioEletronico.consultarDadosMedicamentos(this, edtNomeComercial.getText().toString());

            if (consultarMedicamentoRetDTO.isOperacaoExecutada() == false)
            {
                txvStatusConsulta.setText(consultarMedicamentoRetDTO.getMensagemExecucao());
            }

            ListView lvMedicamentos = (ListView) findViewById(R.id.lvMedicamentos);

            ConsultaMedicamentosAdapter adapter = new ConsultaMedicamentosAdapter(ConsultaMedicamentoActivity.this, (ArrayList) consultarMedicamentoRetDTO.getMedicamentos());
            lvMedicamentos.setAdapter(adapter);
            lvMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MedicamentoRetDTO medicamentoRetDTO = consultarMedicamentoRetDTO.getMedicamentos().get(position);

                    Log.v(TAG, "obterTextoBula() Method invoked ");

                    if (!hasPermissions(ConsultaMedicamentoActivity.this, PERMISSIONS)) {

                        Log.v(TAG, "obterTextoBula() Method DON'T HAVE PERMISSIONS ");
                        txvStatusConsulta.setText("Você não tem permissão de leitura ou escrita no armazenamento");

                        Toast t = Toast.makeText(getApplicationContext(), "You don't have write access !", Toast.LENGTH_LONG);
                        t.show();

                    } else {
                        Log.v(TAG, "obterTextoBula() Method HAVE PERMISSIONS ");

                        String bula = integracaoBularioEletronico.obterTextoBula(medicamentoRetDTO);

                        Log.v(TAG, "obterTextoBula() Method completed ");

                        //Intent intent = new Intent();
                        //intent.putExtra("texto", bula);
                        //intent.setClass(ConsultaMedicamentoActivity.this, DetalheMedicamentoActivity.class);
                        //startActivity(intent);
                    }
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
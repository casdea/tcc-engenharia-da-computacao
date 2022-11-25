package br.ufpa.app.android.amu.v1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.app.android.amu.v1.R;
import br.ufpa.app.android.amu.v1.adapter.MedicamentoAdapter;
import br.ufpa.app.android.amu.v1.dao.config.ConfiguracaoFirebase;
import br.ufpa.app.android.amu.v1.dao.modelo.Medicamento;
import br.ufpa.app.android.amu.v1.dto.MedicamentoDTO;
import br.ufpa.app.android.amu.v1.util.App;

public class PrincipalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        recyclerView = findViewById(R.id.recyclerView);

        carregarLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                autenticacao.signOut();
                startActivity(new Intent(this, BemVindoActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregarLista() {
        //GerenteServicos gerenteServicos = new GerenteServicos();
        //List<MedicamentoDTO> listaMedicamentos = gerenteServicos.obterListaMedicamentosByUsuario(App.usuario.getIdUsuario());
        List<MedicamentoDTO> listaMedicamentos = new ArrayList<>();
        DatabaseReference em = ConfiguracaoFirebase.getFirebaseDatabase();

        Query medicamentosQuery = em.child("medicamentos").orderByChild("idUsuario").equalTo(App.usuario.getIdUsuario());

        medicamentosQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Medicamento medicamento = postSnapshot.getValue(Medicamento.class);
                    MedicamentoDTO medicamentoDTO = new MedicamentoDTO();
                    medicamentoDTO.setNomeComercial(medicamento.getNomeComercial());
                    medicamentoDTO.setNomeFantasia(medicamento.getNomeFantasia());
                    medicamentoDTO.setFabricante(medicamento.getFabricante());
                    listaMedicamentos.add(medicamentoDTO);
                    Log.i("Lendo dados ",postSnapshot.toString());
                    // TODO: handle the post
                }

                MedicamentoAdapter medicamentoAdapter = new MedicamentoAdapter(listaMedicamentos);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(medicamentoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
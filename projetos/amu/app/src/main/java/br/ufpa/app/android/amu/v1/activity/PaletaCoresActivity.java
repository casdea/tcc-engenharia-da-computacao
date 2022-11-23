package br.ufpa.app.android.amu.v1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.ufpa.app.android.amu.v1.R;

public class PaletaCoresActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] idViews = new int[]
            {R.id.txvCor1, R.id.txvCor2, R.id.txvCor3, R.id.txvCor4, R.id.txvCor5, R.id.txvCor6, R.id.txvCor7,
                    R.id.txvCor8, R.id.txvCor9, R.id.txvCor10};

    private String[] cores = new String[]
            {"#4CAF50", "#01C9D5", "#FF9800","#BD88FD","#03A9F4","#17090909","#FBC02D","#FFEB3B","#9E9D24","#CC534E"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paleta_cores);

        findViewById(R.id.txvCor1).setOnClickListener(this);
        findViewById(R.id.txvCor2).setOnClickListener(this);
        findViewById(R.id.txvCor3).setOnClickListener(this);
        findViewById(R.id.txvCor4).setOnClickListener(this);
        findViewById(R.id.txvCor5).setOnClickListener(this);
        findViewById(R.id.txvCor6).setOnClickListener(this);
        findViewById(R.id.txvCor7).setOnClickListener(this);
        findViewById(R.id.txvCor8).setOnClickListener(this);
        findViewById(R.id.txvCor9).setOnClickListener(this);
        findViewById(R.id.txvCor10).setOnClickListener(this);
    }

    private int findIndexById(int id)
    {
        for (int i = 0; i < idViews.length; i++) {
            if (idViews[i]==id) return i;
        }

        return -1;
    }

    @Override
    public void onClick(View view) {

        int index = findIndexById(view.getId());

        if (index == -1) {
            Toast.makeText(this, "Não foi possivel determinar a cor do componente", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("cor", cores[index]);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
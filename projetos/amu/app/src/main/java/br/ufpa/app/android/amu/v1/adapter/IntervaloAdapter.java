package br.ufpa.app.android.amu.v1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IntervaloAdapter extends BaseAdapter {
	private final Context ctx;
	private final String[] lista;
	
	
	public IntervaloAdapter(Context context, String[] lista){
		this.ctx = context;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lista[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView tv = new TextView(ctx);
		tv.setText(lista[position]);
		tv.setTextColor(Color.parseColor("#333105"));
		
		return tv;
	}

}

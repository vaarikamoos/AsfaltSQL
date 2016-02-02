package com.androidopentutorials.sqlite.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidopentutorials.sqlite.R;
import com.androidopentutorials.sqlite.to.TeeProov;

public class TproovListAdapter extends ArrayAdapter<TeeProov> {

	private Context context;
	List<TeeProov> teeProovs;

	//
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	
	public TproovListAdapter(Context context, List<TeeProov> teeProovs) {
		super(context, R.layout.list_item_teeproov, teeProovs);
		this.context = context;
		this.teeProovs = teeProovs;
	}

	private class ViewHolder {
		TextView tprIdTxt;
		TextView tprKohtTxt;
		TextView tprKPTxt;
		TextView tprNrTxt;
		//
		TextView tprVotukohtTxt;

		TextView tprObjNameTxt;
	}

	@Override
	public int getCount() {
		return teeProovs.size();
	}

	@Override
	public TeeProov getItem(int position) {
		return teeProovs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_teeproov, null);
			holder = new ViewHolder();
			
			holder.tprIdTxt = (TextView) convertView
					.findViewById(R.id.txt_tpr_id);
			holder.tprKohtTxt = (TextView) convertView
					.findViewById(R.id.txt_tpr_koht);
			holder.tprKPTxt = (TextView) convertView
					.findViewById(R.id.txt_tpr_kp);
			holder.tprNrTxt = (TextView) convertView
					.findViewById(R.id.txt_tpr_nr);
			///
			holder.tprVotukohtTxt = (TextView) convertView
					.findViewById(R.id.etxt_tprVotuKoht);

			holder.tprObjNameTxt = (TextView) convertView
					.findViewById(R.id.txt_tpr_obj);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TeeProov teeProov = (TeeProov) getItem(position);
		holder.tprIdTxt.setText(teeProov.getId() + "");
		holder.tprKohtTxt.setText(teeProov.getName());
		holder.tprNrTxt.setText(teeProov.getProoviNr() + "");
		//
		holder.tprVotukohtTxt.setText(teeProov.getPrVotuKoht());

		holder.tprObjNameTxt.setText(teeProov.getTeeObjekt().getName());
		
		holder.tprKPTxt.setText(formatter.format(teeProov.getProoviKp()));
		
		return convertView;
	}

	@Override
	public void add(TeeProov teeProov) {
		teeProovs.add(teeProov);
		notifyDataSetChanged();
		super.add(teeProov);
	}

	@Override
	public void remove(TeeProov teeProov) {
		teeProovs.remove(teeProov);
		notifyDataSetChanged();
		super.remove(teeProov);
	}	
}


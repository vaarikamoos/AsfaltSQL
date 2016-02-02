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
import com.androidopentutorials.sqlite.to.TeeObjekt;

public class TobjektListAdapter extends ArrayAdapter<TeeObjekt> {

    private Context context;
    List<TeeObjekt> teeObjekts;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public TobjektListAdapter(Context context, List<TeeObjekt> teeObjekts) {
        super(context, R.layout.list_item_teeobjekt, teeObjekts);
        this.context = context;
        this.teeObjekts = teeObjekts;
    }

    private class ViewHolder {
        TextView tobjIdTxt;
        TextView tobjNameTxt;
        TextView tobjObjektiNimetusTxt;
        TextView tobjTeeNimetusTxt;
        TextView tobjTeeNrTxt;
        TextView tobjAlgusKmTxt;
        TextView tobjLoppKmTxt;
    }

    @Override
    public int getCount() {
        return teeObjekts.size();
    }

    @Override
    public TeeObjekt getItem(int position) {
        return teeObjekts.get(position);
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
            convertView = inflater.inflate(R.layout.list_item_teeobjekt, null);
            holder = new ViewHolder();

            // // TODO: 02.02.2016
            // tagumised pooled on vaja xml-s teha
            holder.tobjIdTxt = (TextView) convertView
                    .findViewById(R.id.txt_tobj_id);
            holder.tobjNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_tobj_name);
            holder.tobjObjektiNimetusTxt = (TextView) convertView
                    .findViewById(R.id.txt_tobj_objektiNimetus);
            holder.tobjTeeNimetusTxt = (TextView) convertView
                    .findViewById(R.id.txt_tobj_teeNimetus);
//            holder.tobjTeeNrTxt = (TextView) convertView
//                    .findViewById(R.id.xxx);
//            holder.tobjAlgusKmTxt = (TextView) convertView
//                    .findViewById(R.id.xxx);
//            holder.tobjLoppKmTxt = (TextView) convertView
//                    .findViewById(R.id.xxx);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TeeObjekt teeObjekt = (TeeObjekt) getItem(position);
        holder.tobjIdTxt.setText(teeObjekt.getId() + "");
        holder.tobjNameTxt.setText(teeObjekt.getName());
        holder.tobjObjektiNimetusTxt.setText(teeObjekt.getObjektiNimetus());
        holder.tobjTeeNimetusTxt.setText(teeObjekt.getTeeNimetus());
        holder.tobjTeeNrTxt.setText(teeObjekt.getTeeNr() + "");
        holder.tobjAlgusKmTxt.setText(teeObjekt.getAlgusKm() + "");
        holder.tobjLoppKmTxt.setText(teeObjekt.getLoppKm() + "");

        return convertView;
    }

    @Override
    public void add(TeeObjekt teeObjekt) {
        teeObjekts.add(teeObjekt);
        notifyDataSetChanged();
        super.add(teeObjekt);
    }

    @Override
    public void remove(TeeObjekt teeObjekt) {
        teeObjekts.remove(teeObjekt);
        notifyDataSetChanged();
        super.remove(teeObjekt);
    }


}






















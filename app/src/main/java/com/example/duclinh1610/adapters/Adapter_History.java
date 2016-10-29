package com.example.duclinh1610.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duclinh1610.model.Info_History;
import com.example.duclinh1610.quanlithuvien.R;

import java.util.ArrayList;

public class Adapter_History extends BaseAdapter {
    Context context;
    ArrayList<Info_History> arrHistory;
    LayoutInflater mInflater;

    public Adapter_History(Context context, ArrayList<Info_History> arrHistory) {
        this.context = context;
        this.arrHistory = arrHistory;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_item_history, parent, false);
            holder = new ViewHolder();
            //lấy các control ra
            holder.txtmaSV = (TextView) convertView.findViewById(R.id.txtmaSV);
            holder.txttenND = (TextView) convertView.findViewById(R.id.txttenND);
            holder.txtngayMuon = (TextView) convertView.findViewById(R.id.txtngayMuon);
            holder.imgUser = (ImageView) convertView.findViewById(R.id.imgUser);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //lấy Sách thứ position
        Info_History history = arrHistory.get(position);

        holder.txtmaSV.setText(history.getMaSV());
        holder.txttenND.setText(history.getTenND());
        holder.txtngayMuon.setText(history.getNgayMuon());
        return convertView;
    }

    static class ViewHolder{
        TextView txtmaSV;
        TextView txttenND;
        TextView txtngayMuon;
        ImageView imgUser;
    }
}

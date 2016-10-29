package com.example.duclinh1610.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duclinh1610.model.Info_User;
import com.example.duclinh1610.quanlithuvien.R;

import java.util.ArrayList;

public class Adapter_User extends BaseAdapter {
    Context context;
    ArrayList<Info_User> arrUser;
    LayoutInflater mInflater;

    public Adapter_User(Context context, ArrayList<Info_User> arrUser) {
        this.context = context;
        this.arrUser = arrUser;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrUser.size();
    }

    @Override
    public Object getItem(int position) {
        return arrUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_item_user, parent, false);
            holder = new ViewHolder();
            //lấy các control ra
            holder.txtmaSV = (TextView) convertView.findViewById(R.id.txtmaSV);
            holder.txttenND = (TextView) convertView.findViewById(R.id.txttenND);
            holder.txtngaySinh = (TextView) convertView.findViewById(R.id.txtngaySinh);
            holder.imgUser = (ImageView) convertView.findViewById(R.id.imgUser);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //lấy Sách thứ position
        Info_User user = arrUser.get(position);
        Log.e("abc",user.getMaSV());
        holder.txtmaSV.setText(user.getMaSV());
        holder.txttenND.setText(user.getTenND());
        holder.txtngaySinh.setText(user.getNgaySinh());
        return convertView;
    }

    static class ViewHolder{
        TextView txtmaSV;
        TextView txttenND;
        TextView txtngaySinh;
        ImageView imgUser;
    }
}

package com.example.duclinh1610.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duclinh1610.model.Info_Book;
import com.example.duclinh1610.quanlithuvien.R;

import java.util.ArrayList;


public class Adapter_Sach extends BaseAdapter{
    Context context;
    ArrayList<Info_Book> arrBook;
    LayoutInflater mInflater;

    public Adapter_Sach(Context context, ArrayList<Info_Book> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrBook.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_item_sach, parent, false);
            holder = new ViewHolder();
            //lấy các control ra
            holder.txtMaSach = (TextView) convertView.findViewById(R.id.txtmaSach);
            holder.txtTenSach = (TextView) convertView.findViewById(R.id.txtTenSach);
            holder.txtTenTG = (TextView) convertView.findViewById(R.id.txtTenTG);
            holder.imgSach = (ImageView) convertView.findViewById(R.id.imgSach);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //lấy Sách thứ position
        Info_Book book = arrBook.get(position);
        holder.txtMaSach.setText(book.getMaSach());
        holder.txtTenSach.setText(book.getTenSach());
        holder.txtTenTG.setText(book.getTacGia());
        return convertView;
    }

    static class ViewHolder{
        TextView txtMaSach;
        TextView txtTenSach;
        TextView txtTenTG;
        ImageView imgSach;
    }
}

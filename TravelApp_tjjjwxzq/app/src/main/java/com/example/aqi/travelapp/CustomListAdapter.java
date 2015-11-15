package com.example.aqi.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Samuel on 4/11/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<NewsItem> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<NewsItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.locationView = (TextView) convertView.findViewById(R.id.title);
            holder.addressView = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.locationView.setText(listData.get(position).getLocation());
        holder.addressView.setText("By, " + listData.get(position).getAddress());
        return convertView;
    }

    static class ViewHolder {
        TextView locationView;
        TextView addressView;
    }
}


package com.example.a.appgridlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<String> listCountry;
    private ArrayList<String> listCountryCode;
    private Context context;

    public GridViewAdapter(ArrayList<String> listCountry, ArrayList<String> listCountryCode, Context context) {
        this.listCountry = listCountry;
        this.listCountryCode = listCountryCode;
        this.context = context;
    }

    static class ViewHolder {

        TextView textViewCountry;
        TextView textViewCountryCode;

    }

    @Override
    public int getCount() {
        return listCountry.size();
    }

    @Override
    public Object getItem(int position) {
        return listCountry.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.gridview_row, null);
            viewHolder = new ViewHolder();

            viewHolder.textViewCountryCode = (TextView) convertView.findViewById(R.id.textViewCountryCode);
            viewHolder.textViewCountry = (TextView) convertView.findViewById(R.id.textViewCountry);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.textViewCountryCode.setText(listCountryCode.get(position));
        viewHolder.textViewCountry.setText(listCountry.get(position));

        return convertView;
    }
}

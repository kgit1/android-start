package com.konggit.appviewholderwithlisview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ViewHolderAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public ViewHolderAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    static class ViewHolder{

        TextView textItem;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();

            //save link to textView in viewHolder
            viewHolder.textItem = (TextView) convertView.findViewById(R.id.textItem);

            //save link to viewHolder in view
            convertView.setTag(viewHolder);

        }else{

            viewHolder=(ViewHolder)convertView.getTag();

        }

        viewHolder.textItem.setText(String.valueOf(getItem(position)));

        return convertView;
    }
}

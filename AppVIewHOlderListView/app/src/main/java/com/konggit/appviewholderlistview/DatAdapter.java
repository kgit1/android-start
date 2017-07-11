package com.konggit.appviewholderlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by erik on 12.07.2017.
 */

public class DatAdapter extends BaseAdapter {

    private Context context;
    private String[] data;

    public DatAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    static class ViewHolder{

        TextView textItem;

    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    //convertView - old view which we reuse to save memory
    //if there ara no old view - we create and inflate new
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){

            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();

            //save link to textView in viewHolder
            viewHolder.textItem = (TextView) convertView.findViewById(R.id.listItem);

            //save link to viewHolder in view
            convertView.setTag(viewHolder);

        }else{

            viewHolder=(ViewHolder) convertView.getTag();

        }

        viewHolder.textItem.setText(String.valueOf(getItem(position)));
        return  convertView;
    }
}

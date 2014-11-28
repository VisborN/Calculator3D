package com.dudkovlad.Calc3d;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by vlad on 27.11.2014.
 */
public class HistoryListAdapter extends ArrayAdapter<HistoryItem> {

    private ArrayList<HistoryItem> objects;

    Context mContext;
    LayoutInflater lInflater;


    public HistoryListAdapter(Context context, int textViewResourceId, ArrayList<HistoryItem> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        mContext = context;

        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
/*
    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public String getItem(int position) {
        return Data.history_src.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }*/

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item;
        if (convertView == null) {

            item = lInflater.inflate(R.layout.history_item, parent, false);
        } else {
            item = convertView;
        }
        ((TextView)item.findViewById(R.id.history_src)).setText(objects.get(position).getHistory_src());
        ((TextView)item.findViewById(R.id.history_result)).setText(objects.get(position).getHistory_result());
        ((TextView)item.findViewById(R.id.history_degrees_radians)).setText(((Boolean)objects.get(position).getHistory_degrees()).toString());
        ((TextView)item.findViewById(R.id.history_num_system)).setText(objects.get(position).getHistory_num_sys() + "");


        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(Data.colors[Data.color_theme][7]));
        states.addState(new int[]{}, new ColorDrawable(Data.colors[Data.color_theme][9]));

        item.setBackgroundDrawable(states);


        item.setOnClickListener(((MainActivity) mContext).myClickListener);
        item.setOnLongClickListener(((MainActivity) mContext).myClickListener);
        return item;
    }


}

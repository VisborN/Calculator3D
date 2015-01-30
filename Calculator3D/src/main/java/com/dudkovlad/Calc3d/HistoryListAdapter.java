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



    @Override
    public long getItemId(int position) {
        return position;
    }

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
        if (!objects.get(position).getHistory_radians())
            ((TextView)item.findViewById(R.id.history_degrees_radians)).setText("degrees");
        else
            ((TextView)item.findViewById(R.id.history_degrees_radians)).setText("radians");

        switch (objects.get(position).getHistory_num_sys())
        {
            case 1: ((TextView)item.findViewById(R.id.history_num_system)).setText("unary");break;
            case 2: ((TextView)item.findViewById(R.id.history_num_system)).setText("bin");break;
            case 3: ((TextView)item.findViewById(R.id.history_num_system)).setText("tern");break;
            case 4: ((TextView)item.findViewById(R.id.history_num_system)).setText("quatern");break;
            case 5: ((TextView)item.findViewById(R.id.history_num_system)).setText("quinary");break;
            case 6: ((TextView)item.findViewById(R.id.history_num_system)).setText("senary");break;
            case 7: ((TextView)item.findViewById(R.id.history_num_system)).setText("septenary");break;
            case 8: ((TextView)item.findViewById(R.id.history_num_system)).setText("octal");break;
            case 9: ((TextView)item.findViewById(R.id.history_num_system)).setText("nonary");break;
            case 10: ((TextView)item.findViewById(R.id.history_num_system)).setText("dec");break;
            case 16: ((TextView)item.findViewById(R.id.history_num_system)).setText("hex");break;
            case 24: ((TextView)item.findViewById(R.id.history_num_system)).setText("quadrivigesimal");break;
            case 60: ((TextView)item.findViewById(R.id.history_num_system)).setText("sexagesimal");break;
            default: ((TextView)item.findViewById(R.id.history_num_system)).setText(objects.get(position).getHistory_num_sys() + "");break;
        }


        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(Data.colors[Data.color_theme][7]));
        states.addState(new int[]{}, new ColorDrawable(Data.colors[Data.color_theme][9]));

        item.setBackgroundDrawable(states);


        item.setOnClickListener(((MainActivity) mContext).myClickListener);
        item.setOnLongClickListener(((MainActivity) mContext).myClickListener);
        ((TextView)item.findViewById(R.id.history_src)).setHint(((Integer)position).toString()); //это костыль для onClickListener чтобы он понимал с каким элементом списка работает

        return item;
    }


}

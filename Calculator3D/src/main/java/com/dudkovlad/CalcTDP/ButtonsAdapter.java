package com.dudkovlad.CalcTDP;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Created by vlad on 14.02.14.
 */
public class ButtonsAdapter extends BaseAdapter
{
    Context mContext;
    int tab_position;
    LayoutInflater lInflater;

    public ButtonsAdapter(Context c, int _tab_position) {
        mContext = c;
        tab_position = _tab_position;


        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Data.pages_columnline_count[tab_position][0]*Data.pages_columnline_count[tab_position][1];
    }

    @Override
    public String getItem(int position) {
        return Data.pages_button_value[tab_position][position];
    }

    @Override
    public long getItemId(int position) {
        return position*tab_position;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View button_;
        if (convertView == null) {

            button_ = lInflater.inflate(R.layout.button_for_adapter, parent, false);
        } else {
            button_ = convertView;
        }
        ((Button)button_.findViewById(R.id.button_in_button_for_adapter)).setText(Data.pages_button_value[tab_position][position]);

        ((Button)button_.findViewById(R.id.button_in_button_for_adapter))
                .setHeight(parent.getHeight() / (Data.pages_columnline_count[tab_position][1]));

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(Data.colors[Data.color_theme][7]));
        states.addState(new int[]{}, new ColorDrawable(Data.colors[Data.color_theme][9]));

        //button_.findViewById(R.id.button_in_button_for_adapter).setBackgroundDrawable(states);


        button_.findViewById(R.id.button_in_button_for_adapter).
                setOnClickListener(((MainActivity)mContext).myClickListener );
        button_.setId(position*tab_position);
        return button_;
    }


}
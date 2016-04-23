package com.dudkovlad.CalcTDP;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vlad on 12.09.2014.
 */
public class EnteringFragment extends Fragment  {


    PagesAdapter mPagesAdapter;
    ViewPager mViewPager;
    TextView equation_view;
    TextView result_view;
    LinearLayout mainLay, equationLay, resultLay;
    ImageButton settings_button, delete_button;
    Toolbar toolbar;
    Context context;



    static EnteringFragment newInstance(Context _context) {
        EnteringFragment thisfragment = new EnteringFragment();

        thisfragment.context = _context;



        return thisfragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        StateListDrawable del_but_states = Create_state_list_drawable_from_colors_and_image(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_for_redact_new_changed_to_min),
                Data.colors[Data.color_theme][9], Data.colors[Data.color_theme][7]
        );

        StateListDrawable settings_but_states = Create_state_list_drawable_from_colors_and_image(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_settings),
                Data.colors[Data.color_theme][8], Data.colors[Data.color_theme][4]
        );


        mainLay = (LinearLayout)inflater.inflate(R.layout.main_fragment, container, false);
        mainLay.setBackgroundColor(Data.colors[Data.color_theme][9]);

        equationLay = (LinearLayout)mainLay.findViewById(R.id.equationlay);
        resultLay   = (LinearLayout)mainLay.findViewById(R.id.resultlay);

        mViewPager =    (ViewPager) mainLay.findViewById(R.id.mviewpager);


        equation_view = (TextView)mainLay.findViewById(R.id.equation_view);//todo make edittext
        result_view = (TextView)mainLay.findViewById(R.id.resultview);
        delete_button = (ImageButton)mainLay.findViewById(R.id.delete_button);
        settings_button = (ImageButton)mainLay.findViewById(R.id.settings_button);

        Toolbar toolbar = (Toolbar) mainLay.findViewById(R.id.my_awesome_toolbar);
        ((MainActivity)context).setSupportActionBar(toolbar);


        equation_view.setBackgroundColor(Data.colors[Data.color_theme][6]);
        equation_view.setHorizontallyScrolling(true);
        equation_view.setGravity(Gravity.END);
        equation_view.setTextSize(20);
        equation_view.setTextColor(0x99000000);
        equation_view.setLines(2);
        if (Data.history_items.size()>0)
            equation_view.setText(Data.history_items.get(0).getHistory_src());


        settings_button.setImageDrawable(settings_but_states); //todo make color changing
        settings_button.setBackgroundColor(Data.colors[Data.color_theme][6]);

        delete_button.setImageDrawable(del_but_states);/*getResources().getDrawable(R.drawable.ic_for_redact)*/
        if (Data.del_but_show)
            delete_button.setLayoutParams(new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.WRAP_CONTENT, Data.del_but_prcnt));
        else
            delete_button.setLayoutParams(new LinearLayout.LayoutParams(
                    0,LinearLayout.LayoutParams.WRAP_CONTENT, 0));

        delete_button.setBackgroundColor(Data.colors[Data.color_theme][8]);
        //result_view.setHapticFeedbackEnabled(true);




        result_view.setLayoutParams(new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.MATCH_PARENT/*delete_button.getHeight()*/, 100-Data.del_but_prcnt));
        //result_view.setHapticFeedbackEnabled(true);
        result_view.setBackgroundColor(Data.colors[Data.color_theme][8]);
        result_view.setLines(2);
        result_view.setTextSize(20); //todo find how to calculate text size

        if (Data.history_items.size()>0)
            result_view.setText(Data.history_items.get(0).getHistory_result());









        mPagesAdapter = new PagesAdapter(context, getChildFragmentManager());
        mViewPager.setAdapter(mPagesAdapter);
        mViewPager.setCurrentItem(Data.pages_mainpage-1);


        if (!Data.del_but_show) {

            result_view.setOnClickListener(((MainActivity)context).myClickListener);

            result_view.setOnLongClickListener(((MainActivity)context).myClickListener);
        }
        else
        {
            delete_button.setOnClickListener(((MainActivity)context).myClickListener);

            delete_button.setOnLongClickListener(((MainActivity)context).myClickListener);
        }


        settings_button.setOnClickListener(((MainActivity)context).myClickListener);





        return mainLay;
    }


    private StateListDrawable Create_state_list_drawable_from_colors_and_image (Bitmap image, int color_up, int color_down)
    {
        int [] pix_arr_up = new int [image.getHeight()*image.getWidth()];
        image.getPixels(pix_arr_up,0,image.getWidth(),0,0,image.getWidth(),image.getHeight());
        int [] pix_arr_down = pix_arr_up.clone();
        color_up = color_up - 0xFF000000;
        color_down = color_down - 0xFF000000;
        for(int i = 0; i < pix_arr_up.length; i++)
        {
            pix_arr_up[i]= pix_arr_up[i] | color_up;
            pix_arr_down [i] = pix_arr_down[i] | color_down;
        }

        Bitmap icon_bmp_down = Bitmap.createBitmap(image.getWidth(),image.getHeight(),Bitmap.Config.ARGB_8888);
        icon_bmp_down.setPixels(pix_arr_down, 0, image.getWidth(), 0, 0,image.getWidth(), image.getHeight());
        image = Bitmap.createBitmap(image.getWidth(),image.getHeight(),Bitmap.Config.ARGB_8888);
        image.setPixels(pix_arr_up, 0,  image.getWidth(), 0, 0,image.getWidth(), image.getHeight());

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                new BitmapDrawable(getResources(),icon_bmp_down));/*
        states.addState(new int[] {android.R.attr.state_focused},
                getResources().getDrawable(R.drawable.focused));*/
        states.addState(new int[] { },new BitmapDrawable(getResources(),image));

        return states;

    }

}



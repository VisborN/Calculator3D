package com.dudkovlad.Calc3d;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
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


    MyCalc data_del;
    PagesAdapter mPagesAdapter;
    ViewPager mViewPager;
    TextView equation_view;
    TextView result_view;
    TextView debugview;
    LinearLayout mainLay, equationLay, resultLay;
    ImageButton settings_button, delete_button;
    Context context;



    static EnteringFragment newInstance(Context _context) {
        EnteringFragment thisfragment = new EnteringFragment();

        thisfragment.context = _context;



        return thisfragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Data.Load_Data (context.getSharedPreferences("main_prefs", 0));


        StateListDrawable del_but_states = Create_state_list_drawable_from_colors_and_image(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_for_redact_new_changed_to_min),
                Data.del_but_color_up, 0x00372317
        );

        StateListDrawable settings_but_states = Create_state_list_drawable_from_colors_and_image(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_settings),
                0x00633F29, 0x00000000
        );



        mainLay = (LinearLayout)inflater.inflate(R.layout.main_fragment, container, false);
        mainLay.setBackgroundColor(0x633F29);

        equationLay = (LinearLayout)mainLay.findViewById(R.id.equationlay);
        resultLay   = (LinearLayout)mainLay.findViewById(R.id.resultlay);

        mViewPager =    (ViewPager) mainLay.findViewById(R.id.mviewpager);


        equation_view = (TextView)mainLay.findViewById(R.id.equation_view);
        result_view = (TextView)mainLay.findViewById(R.id.resultview);
        debugview = (TextView)mainLay.findViewById(R.id.debugview);
        delete_button = (ImageButton)mainLay.findViewById(R.id.delete_button);
        settings_button = (ImageButton)mainLay.findViewById(R.id.settings_button);


        data_del = new MyCalc(equation_view, result_view, debugview);
        MainActivity.data_del = data_del;

        equation_view.setBackgroundColor(0xff372317);
        equation_view.setHorizontallyScrolling(true);
        equation_view.setGravity(Gravity.END);
        equation_view.setTextSize(20);
        equation_view.setLines(2);


        settings_button.setImageDrawable(settings_but_states); //todo make color changing
        settings_button.setBackgroundColor(0xff372317);

        delete_button.setImageDrawable(del_but_states);/*getResources().getDrawable(R.drawable.ic_for_redact)*/
        if (Data.del_but_show)
            delete_button.setLayoutParams(new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.WRAP_CONTENT, Data.del_but_prcnt));
        else
            delete_button.setLayoutParams(new LinearLayout.LayoutParams(
                    0,LinearLayout.LayoutParams.WRAP_CONTENT, 0));

        delete_button.setBackgroundColor(0xff633F29);
        //result_view.setHapticFeedbackEnabled(true);




        result_view.setLayoutParams(new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.MATCH_PARENT/*delete_button.getHeight()*/, 100-Data.del_but_prcnt));
        //result_view.setHapticFeedbackEnabled(true);
        result_view.setBackgroundColor(0xff633F29);
        result_view.setLines(2);
        result_view.setTextSize(20); //todo find how to calculate text size




        debugview.setTextSize(10);
        debugview.setBackgroundColor(0xff000000);
        debugview.setLines(2);





        mPagesAdapter = new PagesAdapter(context, getFragmentManager());
        mViewPager.setAdapter(mPagesAdapter);
        mViewPager.setCurrentItem(Data.pages_mainpage-1);


        if (!Data.del_but_show) {
            result_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data_del.DelFromEquation();
                }


            });//todo make edittext

            result_view.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    data_del.DelAllFromEquation();
                    return true;
                }

            });//todo make edittext
        }
        else
        {
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data_del.DelFromEquation();
                }


            });//todo make edittext

            delete_button.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    data_del.DelAllFromEquation();
                    return true;
                }

            });//todo make edittext
        }


        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).go_to_graphics_fragment();
            }


        });//todo make edittext





        return mainLay;
    }


    private StateListDrawable Create_state_list_drawable_from_colors_and_image (Bitmap image, int color_up, int color_down)
    {
        int [] pix_arr_up = new int [image.getHeight()*image.getWidth()];
        image.getPixels(pix_arr_up,0,image.getWidth(),0,0,image.getWidth(),image.getHeight());
        int [] pix_arr_down = pix_arr_up.clone();
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



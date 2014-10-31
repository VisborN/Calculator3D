package com.dudkovlad.Calc3d;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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

    public EnteringFragment ()
    {
        context = MainActivity.context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Data.Load_Data (context.getSharedPreferences("main_prefs", 0));


        Bitmap icon_bmp_up =  BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_for_redact_new_changed_to_min);

        int [] pix_arr_up = new int [icon_bmp_up.getHeight()*icon_bmp_up.getWidth()];
        icon_bmp_up.getPixels(pix_arr_up,0,icon_bmp_up.getWidth(),0,0,icon_bmp_up.getWidth(),icon_bmp_up.getHeight());
        int [] pix_arr_down = pix_arr_up.clone();
        for(int i = 0; i < pix_arr_up.length; i++)
        {
            pix_arr_up[i]= pix_arr_up[i] | Data.del_but_color_up;
            pix_arr_down [i] = pix_arr_down[i] | Data.del_but_color_down;
        }

        Bitmap icon_bmp_down = Bitmap.createBitmap(icon_bmp_up.getWidth(),icon_bmp_up.getHeight(),Bitmap.Config.ARGB_8888);
        icon_bmp_down.setPixels(pix_arr_down, 0, icon_bmp_up.getWidth(), 0, 0,icon_bmp_up.getWidth(), icon_bmp_up.getHeight());
        icon_bmp_up = Bitmap.createBitmap(icon_bmp_up.getWidth(),icon_bmp_up.getHeight(),Bitmap.Config.ARGB_8888);
        icon_bmp_up.setPixels(pix_arr_up, 0,  icon_bmp_up.getWidth(), 0, 0,icon_bmp_up.getWidth(), icon_bmp_up.getHeight());

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed},
                new BitmapDrawable(getResources(),icon_bmp_down));/*
        states.addState(new int[] {android.R.attr.state_focused},
                getResources().getDrawable(R.drawable.focused));*/
        states.addState(new int[] { },new BitmapDrawable(getResources(),icon_bmp_up));


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


        delete_button.setImageDrawable(states);/*getResources().getDrawable(R.drawable.ic_for_redact)*/
        if (Data.del_but_show)
            delete_button.setLayoutParams(new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.WRAP_CONTENT, Data.del_but_prcnt));
        else
            delete_button.setLayoutParams(new LinearLayout.LayoutParams(
                    0,LinearLayout.LayoutParams.WRAP_CONTENT, 0));

        delete_button.setBackground(new ColorDrawable(0xff633F29));
        result_view.setHapticFeedbackEnabled(true);




        result_view.setLayoutParams(new LinearLayout.LayoutParams(
                0,LinearLayout.LayoutParams.MATCH_PARENT/*delete_button.getHeight()*/, 100-Data.del_but_prcnt));
        result_view.setHapticFeedbackEnabled(true);
        result_view.setBackgroundColor(0xff633F29);
        result_view.setLines(2);
        result_view.setTextSize(20); //todo find how to calculate text size




        debugview.setTextSize(10);
        debugview.setBackgroundColor(0xff000000);
        debugview.setLines(3);





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





        return mainLay;
    }


}



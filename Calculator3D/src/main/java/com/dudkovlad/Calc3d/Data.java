package com.dudkovlad.Calc3d;

import android.content.SharedPreferences;

/**
 * Created by vlad on 27.09.2014.
 */
public class Data {

    public static SharedPreferences prefs;



    public static String  history_src[];
    public static String  history_result[];
    public static int     history_num_sys[];
    public static boolean history_degrees[];
    public static int     history_count,
            pages_count, pages_mainpage;
    public static int     pages_columnline_count[][];
    public static String  pages_button_value[][];
    public static boolean del_but_show, pages_vertical_slide;
    public static int del_but_prcnt, del_but_color_up, del_but_color_down;
    public static boolean result_show_float;

    public static void Load_Data(SharedPreferences __prefs)
    {

        prefs = __prefs;

        boolean first_run = prefs.getBoolean("first_run", true);
        history_count = prefs.getInt("history_count",0);
        history_src = new String[history_count];
        history_result = new String[history_count];
        String tmp;
        for(int i = 0; i < history_count; i++)
        {
            tmp = String.valueOf(i);
            history_src[i]      = prefs.getString("history_src"     +tmp, "");
            history_result[i]   = prefs.getString("history_result"  +tmp, "");
            history_num_sys[i]  = prefs.getInt("history_num_sys"    +tmp, 10);
            history_degrees[i]  = prefs.getBoolean("history_degrees"+tmp, true);
        }
        result_show_float = prefs.getBoolean("result_show_float", false);

        del_but_show = prefs.getBoolean("del_but_show", false);
        del_but_prcnt = prefs.getInt("del_but_prcnt", 25);
        del_but_color_up = prefs.getInt("del_but_color_up",     0x00B97A57);
        del_but_color_down = prefs.getInt("del_but_color_down", 0x009F6342);

        pages_vertical_slide = prefs.getBoolean("pages_count", false);
        pages_count = prefs.getInt("pages_count", 3);
        pages_columnline_count = new int [pages_count][2];
        pages_mainpage = prefs.getInt("pages_mainpage", 2);

        int max=0;
        for (int i = 0; !first_run&&i < pages_count; i++)
        {
            tmp = String.valueOf(i);
            pages_columnline_count[i][0] = prefs.getInt("pages_columnline_count"+tmp+"/"+"0", 4);
            pages_columnline_count[i][1] = prefs.getInt("pages_columnline_count"+tmp+"/"+"1", 4);
            if(pages_columnline_count[i][0]*pages_columnline_count[i][1]>max)
                max = pages_columnline_count[i][0]*pages_columnline_count[i][1];
        }
        if(!first_run)
            pages_button_value = new String [pages_count][max];


        for (int i = 0; !first_run&&i < pages_count; i++)
        {
            tmp = String.valueOf(i);
            for (int j = 0; j < pages_columnline_count[i][0] * pages_columnline_count[i][1]; j++) {
                pages_button_value[i][j] =
                        prefs.getString("pages_button_value" + tmp + "/" + String.valueOf(j), "");
            }
        }


        if (first_run)
        {
            pages_columnline_count = new int [][]
                    {
                        {4,4},{4,4},{4,4}
                    };
            pages_button_value = new String[][]
                    {
                            {
                                    "Sin(","Cos(","Tan(","i",
                                    "Ln(","Log(","π","e",
                                    "%","!","√","^",
                                    "(",")","|","Y"
                            },
                            {
                                    "7","8","9","÷",
                                    "4","5","6","×",
                                    "1","2","3","-",
                                    ".","0","=","+"
                            },
                            {
                                    "∩","∪","∆","\\",
                                    "bin","oct","dec","hex",
                                    "A","B","C","",
                                    "D","E","F",""
                            }
                    };

        }


    }




    public static void Save ()
    {

/*
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("first_run", false);

        editor.apply();*/
    }

}

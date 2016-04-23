package com.dudkovlad.CalcTDP;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by vlad on 27.09.2014.
 */
public class Data {

    public static SharedPreferences prefs;



    public static ArrayList<HistoryItem>  history_items;
    public static int     pages_count, pages_mainpage;
    public static int     pages_columnline_count[][];
    public static String  pages_button_value[][];
    public static boolean del_but_show, pages_vertical_slide;
    public static int del_but_prcnt;
    public static int color_theme;
    public static boolean result_show_float;

    public static void Load_Data(SharedPreferences __prefs, Context context)
    {

        prefs = __prefs;

        boolean first_run = prefs.getBoolean("first_run", true);
        int history_count = prefs.getInt("history_count",1);
        history_items = new ArrayList<>(history_count);
        HistoryItem histitem;
        for(int i = 0; i < history_count; i++)
        {
            histitem =
                new HistoryItem(
                    prefs.getString("history_src"     + i, "2+2"),
                    prefs.getString("history_result"  + i, "4"),
                    prefs.getInt("history_num_sys"    + i, 10),
                    prefs.getBoolean("history_radians"+ i, true));
            history_items.add(histitem);
        }
        result_show_float = prefs.getBoolean("result_show_float", false);

        color_theme = prefs.getInt("color_theme", GRAY);

        del_but_show = prefs.getBoolean("del_but_show", true);
        del_but_prcnt = prefs.getInt("del_but_prcnt", 25);

        pages_vertical_slide = prefs.getBoolean("pages_vertical_slide", false);
        pages_count = prefs.getInt("pages_count", 3);
        pages_columnline_count = new int [pages_count][2];
        pages_mainpage = prefs.getInt("pages_mainpage", 2);

        int max=0;
        for (int i = 0; !first_run&&i < pages_count; i++)
        {
            pages_columnline_count[i][0] = prefs.getInt("pages_columnline_count"+i+"/"+"0", 4);
            pages_columnline_count[i][1] = prefs.getInt("pages_columnline_count"+i+"/"+"1", 4);
            if(pages_columnline_count[i][0]*pages_columnline_count[i][1]>max)
                max = pages_columnline_count[i][0]*pages_columnline_count[i][1];
        }
        if(!first_run)
            pages_button_value = new String [pages_count][max];


        for (int i = 0; !first_run&&i < pages_count; i++)
        {
            for (int j = 0; j < pages_columnline_count[i][0] * pages_columnline_count[i][1]; j++) {
                pages_button_value[i][j] =
                        prefs.getString("pages_button_value" + i + "/" + String.valueOf(j), "");
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
                                    "bin","oct","5 + ((1 + 2) × 4) - 3=14","Cos(1+3/4^2)+(8+2×5)÷(1+3×2-4)=6.373979630824",
                                    "A","B","(8+2×5)÷(1+3×2-4)=6","³√",
                                    "D","E","|5+|5-10|-30|=20","X^2+Y^2=Z"
                            }
                    };

        }


    }

    public static void Save ()
    {


        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("first_run", false);



        editor.putInt("history_count", history_items.size());
        for(int i = 0; i < history_items.size(); i++)
        {
            editor.putString("history_src"     +i, history_items.get(i).getHistory_src());
            editor.putString("history_result"     +i, history_items.get(i).getHistory_result());
            editor.putInt("history_num_sys"     +i, history_items.get(i).getHistory_num_sys());
            editor.putBoolean("history_radians"     +i, history_items.get(i).getHistory_radians());
        }

        editor.putBoolean("result_show_float", result_show_float);


        editor.putInt("color_theme", color_theme);


        editor.putBoolean("del_but_show", del_but_show);
        editor.putInt("del_but_prcnt", del_but_prcnt);

        editor.putBoolean("pages_vertical_slide", pages_vertical_slide);
        editor.putInt("pages_count", pages_count);
        editor.putInt("pages_mainpage", pages_mainpage);

        for (int i = 0; i < pages_count; i++)
        {
            editor.putInt("pages_columnline_count"+i+"/"+"0", pages_columnline_count[i][0]);
            editor.putInt("pages_columnline_count"+i+"/"+"1", pages_columnline_count[i][1]);

            for (int j = 0; j < pages_columnline_count[i][0] * pages_columnline_count[i][1]; j++)
                editor.putString("pages_button_value" + i + "/" + String.valueOf(j), pages_button_value[i][j]);

        }



        editor.apply();
    }


    public static void HistoryNext (HistoryItem item)
    {
        if ((history_items.size() > 0 && !history_items.get(0).getHistory_src().equals(""))||history_items.size() == 0)
            history_items.add(0, item);
        else
            HistoryChange(item);

        for (int i = history_items.size()-1; i > 0; i--)
        {
            if ( item.equals(history_items.get(i)) )
                history_items.remove(i);
        }
    }


    public static void HistoryRemove (int i)
    {
        if (history_items.size() > i)
            history_items.remove(i);
    }

    public static void HistoryChange (HistoryItem item) {
        if (history_items.size() > 0) {
            history_items.remove(0);
            history_items.add(0, item);

            for (int i = history_items.size()-1; i > 0; i--)
            {
                if ( item.equals(history_items.get(i)) )
                    history_items.remove(i);
            }
        }
        else HistoryNext(item);


    }


    public static int RED           = 0;
    public static int PINK          = 1;
    public static int PURPLE        = 2;
    public static int DEEP_PURPLE   = 3;
    public static int INDIGO        = 4;
    public static int BLUE          = 5;
    public static int LIGHT_BLUE    = 6;
    public static int CYAN          = 7;
    public static int TEAL          = 8;
    public static int GREEN         = 9;
    public static int LIGHT_GREEN   = 10;
    public static int LIME          = 11;
    public static int YELLOW        = 12;
    public static int AMBER         = 13;
    public static int ORANGE        = 14;
    public static int DEEP_ORANGE   = 15;
    public static int BROWN         = 16;
    public static int GRAY          = 17;
    public static int BLUE_GRAY     = 18;
    public static int BLACK_WHITE   = 19;
    public static int OWN           = 20;


    public static int[][] colors = new int [][]
            {
        {0xFFFFEBEE, 0xFFFFCDD2, 0xFFEF9A9A, 0xFFE57373, 0xFFEF5350, 0xFFF44336, 0xFFE53935, 0xFFD32F2F, 0xFFC62828, 0xFFB71C1C, 0xFFFF8A80, 0xFFFF5252, 0xFFFF1744, 0xFFD50000},
        {0xFFFCE4EC, 0xFFF8BBD0, 0xFFF48FB1, 0xFFF06292, 0xFFEC407A, 0xFFE91E63, 0xFFD81B60, 0xFFC2185B, 0xFFAD1457, 0xFF880E4F, 0xFFFF80AB, 0xFFFF4081, 0xFFF50057, 0xFFC51162},
        {0xFFF3E5F5, 0xFFE1BEE7, 0xFFCE93D8, 0xFFBA68C8, 0xFFAB47BC, 0xFF9C27B0, 0xFF8E24AA, 0xFF7B1FA2, 0xFF6A1B9A, 0xFF4A148C, 0xFFEA80FC, 0xFFE040FB, 0xFFD500F9, 0xFFAA00FF},
        {0xFFEDE7F6, 0xFFD1C4E9, 0xFFB39DDB, 0xFF9575CD, 0xFF7E57C2, 0xFF673AB7, 0xFF5E35B1, 0xFF512DA8, 0xFF4527A0, 0xFF311B92, 0xFFB388FF, 0xFF7C4DFF, 0xFF651FFF, 0xFF6200EA},
        {0xFFE8EAF6, 0xFFC5CAE9, 0xFF9FA8DA, 0xFF7986CB, 0xFF5C6BC0, 0xFF3F51B5, 0xFF3949AB, 0xFF303F9F, 0xFF283593, 0xFF1A237E, 0xFF8C9EFF, 0xFF536DFE, 0xFF3D5AFE, 0xFF304FFE},
        {0xFFE3F2FD, 0xFFBBDEFB, 0xFF90CAF9, 0xFF64B5F6, 0xFF42A5F5, 0xFF2196F3, 0xFF1E88E5, 0xFF1976D2, 0xFF1565C0, 0xFF0D47A1, 0xFF82B1FF, 0xFF448AFF, 0xFF2979FF, 0xFF2962FF},
        {0xFFE1F5FE, 0xFFB3E5FC, 0xFF81D4FA, 0xFF4FC3F7, 0xFF29B6F6, 0xFF03A9F4, 0xFF039BE5, 0xFF0288D1, 0xFF0277BD, 0xFF01579B, 0xFF80D8FF, 0xFF40C4FF, 0xFF00B0FF, 0xFF0091EA},
        {0xFFE0F7FA, 0xFFB2EBF2, 0xFF80DEEA, 0xFF4DD0E1, 0xFF26C6DA, 0xFF00BCD4, 0xFF00ACC1, 0xFF0097A7, 0xFF00838F, 0xFF006064, 0xFF84FFFF, 0xFF18FFFF, 0xFF00E5FF, 0xFF00B8D4},
        {0xFFE0F2F1, 0xFFB2DFDB, 0xFF80CBC4, 0xFF4DB6AC, 0xFF26A69A, 0xFF009688, 0xFF00897B, 0xFF00796B, 0xFF00695C, 0xFF004D40, 0xFFA7FFEB, 0xFF64FFDA, 0xFF1DE9B6, 0xFF00BFA5},
        {0xFFE8F5E9, 0xFFC8E6C9, 0xFFA5D6A7, 0xFF81C784, 0xFF66BB6A, 0xFF4CAF50, 0xFF43A047, 0xFF388E3C, 0xFF2E7D32, 0xFF1B5E20, 0xFFB9F6CA, 0xFF69F0AE, 0xFF00E676, 0xFF00C853},
        {0xFFF1F8E9, 0xFFDCEDC8, 0xFFC5E1A5, 0xFFAED581, 0xFF9CCC65, 0xFF8BC34A, 0xFF7CB342, 0xFF689F38, 0xFF558B2F, 0xFF33691E, 0xFFCCFF90, 0xFFB2FF59, 0xFF76FF03, 0xFF64DD17},
        {0xFFF9FBE7, 0xFFF0F4C3, 0xFFE6EE9C, 0xFFDCE775, 0xFFD4E157, 0xFFCDDC39, 0xFFC0CA33, 0xFFAFB42B, 0xFF9E9D24, 0xFF827717, 0xFFF4FF81, 0xFFEEFF41, 0xFFC6FF00, 0xFFAEEA00},
        {0xFFFFFDE7, 0xFFFFF9C4, 0xFFFFF59D, 0xFFFFF176, 0xFFFFEE58, 0xFFFFEB3B, 0xFFFDD835, 0xFFFBC02D, 0xFFF9A825, 0xFFF57F17, 0xFFFFFF8D, 0xFFFFFF00, 0xFFFFEA00, 0xFFFFD600},
        {0xFFFFF8E1, 0xFFFFECB3, 0xFFFFE082, 0xFFFFD54F, 0xFFFFCA28, 0xFFFFC107, 0xFFFFB300, 0xFFFFA000, 0xFFFF8F00, 0xFFFF6F00, 0xFFFFE57F, 0xFFFFD740, 0xFFFFC400, 0xFFFFAB00},
        {0xFFFFF3E0, 0xFFFFE0B2, 0xFFFFCC80, 0xFFFFB74D, 0xFFFFA726, 0xFFFF9800, 0xFFFB8C00, 0xFFF57C00, 0xFFEF6C00, 0xFFE65100, 0xFFFFD180, 0xFFFFAB40, 0xFFFF9100, 0xFFFF6D00},
        {0xFFFBE9E7, 0xFFFFCCBC, 0xFFFFAB91, 0xFFFF8A65, 0xFFFF7043, 0xFFFF5722, 0xFFF4511E, 0xFFE64A19, 0xFFD84315, 0xFFBF360C, 0xFFFF9E80, 0xFFFF6E40, 0xFFFF3D00, 0xFFDD2C00},
        {0xFFEFEBE9, 0xFFD7CCC8, 0xFFBCAAA4, 0xFFA1887F, 0xFF8D6E63, 0xFF795548, 0xFF6D4C41, 0xFF5D4037, 0xFF4E342E, 0xFF3E2723},
        {0xFFFAFAFA, 0xFFF5F5F5, 0xFFEEEEEE, 0xFFE0E0E0, 0xFFBDBDBD, 0xFF9E9E9E, 0xFF757575, 0xFF616161, 0xFF424242, 0xFF212121},
        {0xFFECEFF1, 0xFFCFD8DC, 0xFFB0BEC5, 0xFF90A4AE, 0xFF78909C, 0xFF607D8B, 0xFF546E7A, 0xFF455A64, 0xFF37474F, 0xFF263238},
        {0xFF000000, 0xFFFFFFFF},
        {0xFFFFF8E1, 0xFFFFECB3, 0xFFFFE082, 0xFFFFD54F, 0xFFFFCA28, 0xFFFFC107, 0xFFFFB300, 0xFFFFA000, 0xFFFF8F00, 0xFFFF6F00, 0xFFFFE57F, 0xFFFFD740, 0xFFFFC400, 0xFFFFAB00},
            };



}

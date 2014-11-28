package com.dudkovlad.Calc3d;

/**
 * Created by vlad on 27.11.2014.
 */
public class HistoryItem {

    private static String history_src;
    private static String  history_result;
    private static int history_num_sys;
    private static boolean history_degrees;


    public HistoryItem(String src, String result, int num_sys, boolean degrees){
        history_src = src;
        history_result = result;
        history_num_sys = num_sys;
        history_degrees = degrees;
    }

    public String getHistory_src() {
        return history_src;
    }

    public void setHistory_src(String in) {
        history_src = in;
    }

    public String getHistory_result() {
        return history_result;
    }

    public void setHistory_result(String in) {
        history_result = in;
    }

    public int getHistory_num_sys() {
        return history_num_sys;
    }

    public void setHistory_src(int in) {
        history_num_sys = in;
    }

    public boolean getHistory_degrees() {
        return history_degrees;
    }

    public void setHistory_src(boolean in) {
        history_degrees = in;
    }

}

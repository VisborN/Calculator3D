package com.dudkovlad.Calc3d;

/**
 * Created by vlad on 27.11.2014.
 */
public class HistoryItem {

    private String history_src;
    private String  history_result;
    private int history_num_sys;
    private boolean history_radians;


    public HistoryItem(String src, String result, int num_sys, boolean degrees){
        history_src = src;
        history_result = result;
        history_num_sys = num_sys;
        history_radians = degrees;
    }

    public void set (String src, String result, int num_sys, boolean degrees)
    {
        history_src = src;
        history_result = result;
        history_num_sys = num_sys;
        history_radians = degrees;
    }

    public boolean equals (HistoryItem item1)
    {
        return  item1.getHistory_src().equals(history_src)&&
                item1.getHistory_result().equals(history_result)&&
                item1.getHistory_num_sys() == history_num_sys&&
                item1.getHistory_radians() == history_radians;
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

    public void setHistory_sys(int in) {
        history_num_sys = in;
    }

    public boolean getHistory_radians() {
        return history_radians;
    }

    public void setHistory_radians(boolean in) {
        history_radians = in;
    }

}

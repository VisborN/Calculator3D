package com.dudkovlad.Calc3d;

import android.widget.TextView;

import com.dudkovlad.Calc3d.Parser.Parser;


/**
 * Created by vlad on 16.02.14.
 */
public class MyCalc {

    String equation;
    String result;
    TextView equation_view;
    TextView resultview;
    Parser equation_parse;
    public TextView debugview;

    public MyCalc ( TextView equation_view_, TextView resultview_, TextView debugview_ )
    {
       

        equation_parse = new Parser();

        equation = "";
        equation_view = equation_view_;
        resultview = resultview_;
        debugview = debugview_;


    }


    void AddToEquation (String button_text)
    {
        equation_view.setText(equation_view.getText().toString()+button_text);

        Result ();
    }

    void DelFromEquation ()
    {
        String eq_now = equation_view.getText().toString();
        if (eq_now.isEmpty()) equation_view.setText("");
        else if (eq_now.length()==1) equation_view.setText("");
        else equation_view.setText(eq_now.substring(0,eq_now.length()-1));
        Result ();
        
    }

    void DelAllFromEquation ()
    {
        equation_view.setText("");

        Result ();



    }

    void Result ()
    {
        debugview.setText("");
        equation = equation_view.getText().toString();

        result = equation_parse.INIT(equation);
        if (!result.equals("0")) {
            if(!result.equals(""))
                debugview.setText(debugview.getText().toString() + "\nError: " + result);
            result = "";
        }else {
            long start = System.nanoTime();
            result = equation_parse.Result();
            long end = System.nanoTime();
            MainActivity.data_del.debugview.setText("runtime: " + (end - start) + " ns or " + ((end - start) / 1000000) + " ms   " + 33333333 / (end - start) + " iterations per frame");
        }

        if (!result.isEmpty()&&result.charAt(0)=='$') {
            debugview.setText(debugview.getText().toString() + "\nError: " + result);
            result = "";
        }
        resultview.setText(result);

    }



}

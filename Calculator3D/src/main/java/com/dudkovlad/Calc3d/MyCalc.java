package com.dudkovlad.Calc3d;

import android.content.Context;
import android.content.res.Resources;
import android.view.HapticFeedbackConstants;
import android.widget.EditText;
import android.widget.TextView;

import com.dudkovlad.Calc3d.Parser.Parser;


/**
 * Created by vlad on 16.02.14.
 */
public class MyCalc {

    Resources res;
    String equation;
    String result;
    TextView equation_view;
    TextView resultview;
    Parser equation_parse;
    TextView debugview;

    public MyCalc (Context mContext, TextView equation_view_, TextView resultview_, TextView debugview_ )
    {
        res = mContext.getResources();

       

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
        resultview.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

        Result ();



    }

    void Result ()
    {

        equation = equation_view.getText().toString();
        debugview.setText("");
        long start = System.nanoTime();
        result  = equation_parse.Result(equation);
        long end = System.nanoTime();

        debugview.setText(debugview.getText().toString() + "runtime: "+Long.toString(end-start)+" ns or "+Long.toString((end-start)/1000000)+" ms");

        if (result.equals("Error")) result = "";
        resultview.setText(result);

    }









    





/*
    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, etText.getText().toString());
        ed.commit();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }*/
/*
    private void SaveButton (int page, int id)
    {


    }


    private void LoadButton (int page, int id)
    {



    }


    public int getNumPages ()
    {

    }

    public int getNumButtons (int page)
    {


    }

    public int getNumColumns (int page)
    {

    }
*/

}

package com.dudkovlad.CalcTDP.Parser;

import com.dudkovlad.CalcTDP.Data;

/**
 * Created by vlad on 16.06.2014.
 */
public  class MyFunc {
    static public double Factorial(double num) {
        double fact = 1;
        if (num < 0)
            return Double.NaN;
        else if (num % 1 == 0)
            for (int i = 1; i <= num; i++)
                fact = i * fact;
        else return Float.NaN;  //TODO make complex factorial
        /*else {
            double tmp1 = Math.sqrt(2*Math.PI/num);
            double tmp2 = num + 1.0/(12 * num - 1.0/(10*num));
            tmp2 = Math.pow(num/Math.E, num);
            tmp2 = Math.pow(tmp2/Math.E, num);
            fact = tmp1 * tmp2;
            fact =  fact * Math.pow(((int)num)+1, (Double.doubleToLongBits(num) & ((1L << 52) - 1)));
        }*/
        return fact;
    }

    static public float Factorial(float num) {
        float fact = 1;
        if (num < 0)
            return Float.NaN;
        else if (num % 1 == 0)
            for (int i = 1; i <= num; i++)
                fact = i * fact;
        else return Float.NaN;  //TODO make complex factorial
        /*else {
            double tmp1 = Math.sqrt(2*Math.PI/num);
            double tmp2 = num + 1.0/(12 * num - 1.0/(10*num));
            tmp2 = Math.pow(num/Math.E, num);
            tmp2 = Math.pow(tmp2/Math.E, num);
            fact = tmp1 * tmp2;
            fact =  fact * Math.pow(((int)num)+1, (Double.doubleToLongBits(num) & ((1L << 52) - 1)));
        }*/
        return fact;
    }

    public static String Double_to_String(double in) {

        Long outl;
        if (((Double) in).equals((double) (outl = ((Double) in).longValue())))
            return Long.toString(outl);   //max long 1E17
        if (Data.result_show_float)
            return Float.toString((float) in);       //max float 7    E38


        return Double.toString(in);     //max double 14   E308

    }

    public static String Float_to_String(float in) {

        Long outl;
        if (((Float) in).equals((float) (outl = ((Float) in).longValue())))
            return Long.toString(outl);   //max long 1E17

        return Double.toString(in);     //max double 14   E308

    }


}

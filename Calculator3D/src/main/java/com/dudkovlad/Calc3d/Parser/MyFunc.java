package com.dudkovlad.Calc3d.Parser;

import com.dudkovlad.Calc3d.MainActivity;

/**
 * Created by vlad on 16.06.2014.
 */
public class MyFunc {
    static double Factorial(double num) {
        double fact=1;
        if (num%1==0)
            for (int i=1; i<=(int)num; i++) {
                fact*=i;
            }
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


    public static String[] Cut_put (String[] arr1, String[] arr2, int ind1, int ind2)
    {
        if (ind1> ind2){
            //MainActivity.debugview.setText("Error: " + "$20" + " ");
            return new String[]{"Error"};
        }
        String[] out = new String [arr1.length - ind2 - 1 + ind1 + arr2.length ];
        System.arraycopy(arr1, 0, out, 0, ind1);
        System.arraycopy(arr2, 0, out, ind1, arr2.length);
        System.arraycopy(arr1, ind2 + 1, out, ind1 + arr2.length, arr1.length - 1 - ind2 );
        return out;
    }

    public static Element_of_equation[] Cut_put (Element_of_equation[] arr1, Element_of_equation[] arr2, int ind1, int ind2)
    {
        if (ind1> ind2) return new Element_of_equation[]{new Element_of_equation("$22")};

        Element_of_equation[] out = new Element_of_equation[arr1.length - ind2 - 1 + ind1 + arr2.length ];

        System.arraycopy(arr1, 0, out, 0, ind1);
        System.arraycopy(arr2, 0, out, ind1, arr2.length);
        System.arraycopy(arr1, ind2 + 1, out, ind1 + arr2.length, arr1.length - 1 - ind2 );
        return out;
    }




    public static String Double_to_String (double in)
    {
/*
        int outi;
        if (in%1==0) {

            if (((Double) in).equals((double) (outi = ((Double) in).intValue())))
                return Integer.toString(outi); //max integer 2147483647
        }
        return Float.toString((float)in);     //max float 7    E38
    */
        Long outl;
        if (in%1==0) {

            if (((Double) in).equals((double) (outl = ((Double) in).longValue())))
                return Long.toString(outl); //max long 1E17
        }
        return Double.toString((float)in);     //max double 14   E38

    }





    public static String[] Take_part (String[] arr, int ind1, int ind2 )
    {
        if (ind1> ind2) {
            //MainActivity.debugview.setText("Error: " + "$21" + " ");
            return new String[]{"Error"};
        }
        String[] out = new String[ind2 - ind1 + 1];
        System.arraycopy(arr, ind1, out, 0, ind2 - ind1 + 1);
        return out;
    }



    public static Element_of_equation[] Take_part (Element_of_equation[] arr, int ind1, int ind2 )
    {
        if (ind1> ind2) return new Element_of_equation[]{new Element_of_equation("$33")};

        Element_of_equation[] out = new Element_of_equation[ind2 - ind1 + 1];
        System.arraycopy(arr, ind1, out, 0, ind2 - ind1 + 1);
        return out;
    }
}

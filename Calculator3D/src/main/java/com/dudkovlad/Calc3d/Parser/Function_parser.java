package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 11.10.2014.
 */
public class Function_parser {


    public static short Funcname_to_Funcint (String in)
    {
        short out = 0;
        if (in.equals("Sin"))
            out = Const.SIN;
        else if (in.equals("Cos"))
            out = Const.COS;
        else if (in.equals("Tan"))
            out = Const.TAN;
        else if (in.equals("Log"))
            out = Const.LOG;
        else if (in.equals("Ln"))
            out = Const.LN;
        else if (in.equals("Abs"))
            out = Const.ABS;
        else if (in.equals("Sqrt"))
            out = Const.SQRT;
        else if (in.equals("Cbrt"))
            out = Const.CBRT;
        else  if (in.equals("!"))
            out = Const.FACTORIAL;
        else  if (in.equals("√"))
            out = Const.ROOT;

        return out;
    }

    public static String Funcint_to_Funcname (short in)
    {
        String out = "Error";
        switch(in)
        {
            case Const.SIN: out = "Sin";break;
            case Const.COS: out = "Sin";break;
            case Const.TAN: out = "Sin";break;
            case Const.LOG: out = "Sin";break;
            case Const.LN: out = "Sin";break;
            case Const.ABS: out = "Sin";break;
            case Const.SQRT: out = "Sin";break;
            case Const.CBRT: out = "Sin";break;
            case Const.FACTORIAL: out = "Sin";break;
            case Const.ROOT: out = "Sin";break;
        }

        return out;
    }




}

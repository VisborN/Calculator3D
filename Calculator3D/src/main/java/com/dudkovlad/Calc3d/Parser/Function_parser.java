package com.dudkovlad.Calc3d.Parser;

import android.content.Context;

/**
 * Created by vlad on 11.10.2014.
 */
public class Function_parser {


    public static byte Funcname_to_Funcint (String in)
    {
        byte out = 0;
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
        else if (in.equals("√"))
            out = Const.SQRT;
        else if (in.equals("³√"))
            out = Const.CBRT;
        else  if (in.equals("!"))
            out = Const.FACTORIAL;
        else  if (in.equals("Root"))
            out = Const.ROOT;

        return out;
    }

    public static String Funcint_to_Funcname (byte in)
    {
        String out = "Error";
        switch(in)
        {
            case Const.SIN:         out = "Sin";break;
            case Const.COS:         out = "Cos";break;
            case Const.TAN:         out = "Tan";break;
            case Const.LOG:         out = "Log";break;
            case Const.LN:          out = "LN";break;
            case Const.ABS:         out = "Abs";break;
            case Const.SQRT:        out = "√";break;
            case Const.CBRT:        out = "³√";break;
            case Const.FACTORIAL:   out = "!";break;
            case Const.ROOT:        out = "Root";break;
        }

        return out;
    }

    public static Element_of_equation Run_func (byte type, Element_of_equation in1)
    {

        Element_of_equation output=new Element_of_equation(Const.ERROR);
        if (type == Const.ERROR||in1.type==Const.ERROR)
            return output;
        if (!in1.is_float){
            if (in1.type==Const.REAL) {
                output.type = Const.REAL;
                switch (type)
                {
                    case Const.SIN: output.real = Math.sin(in1.real);break;
                    case Const.COS: output.real = Math.cos(in1.real);break;
                    case Const.TAN: output.real = Math.tan(in1.real);break;
                    case Const.LOG: output.real = Math.log10(in1.real);break;
                    case Const.LN:  output.real = Math.log(in1.real);break;
                    case Const.ABS: output.real = Math.abs(in1.real);break;
                    case Const.SQRT:
                        if (in1.real >= 0)
                            output.real = Math.sqrt(in1.real);
                        else
                        {
                            output.type = Const.COMPLEX;
                            output.comp = new Complex(0,1).times( Math.sqrt(in1.real));
                        }break;
                    case Const.CBRT: output.real = Math.cbrt(in1.real);break;
                    case Const.FACTORIAL: output.real = MyFunc.Factorial(in1.real);break;
                    default: output.type = Const.ERROR;break;
                }
            }
            else if(in1.type == Const.COMPLEX){
                output.type = Const.COMPLEX;
                switch (type)
                {
                    case Const.SIN:     output.comp = in1.comp.sin();break;
                    case Const.COS:     output.comp = in1.comp.cos();break;
                    case Const.TAN:     output.comp = in1.comp.tan();break;
                    case Const.LOG:     output.comp = in1.comp.log10();break;
                    case Const.LN:      output.comp = in1.comp.ln();break;
                    case Const.ABS:     output.real = in1.comp.abs();output.type = Const.REAL;break;
                    case Const.SQRT:    output.comp = in1.comp.pow(0.5);break;
                    case Const.CBRT:    output.comp = in1.comp.pow(1.0 / 3.0);break;
                    case Const.FACTORIAL:output.type = Const.ERROR; break;
                    default:            output.type = Const.ERROR;break;
                }
            }
        }
        else
        {
            output.is_float = true;
            if (in1.type==Const.REAL) {
                output.type = Const.REAL;
                switch (type)
                {
                    case Const.SIN: output.realf = (float)Math.sin(in1.realf);break;
                    case Const.COS: output.realf = (float)Math.cos(in1.realf);break;
                    case Const.TAN: output.realf = (float)Math.tan(in1.realf);break;
                    case Const.LOG: output.realf = (float)Math.log10(in1.realf);break;
                    case Const.LN:  output.realf = (float)Math.log(in1.realf);break;
                    case Const.ABS: output.realf = Math.abs(in1.realf);break;
                    case Const.SQRT:
                        if (in1.realf >= 0)
                            output.realf = (float)Math.sqrt(in1.realf);
                        else
                        {
                            output.type = Const.COMPLEX;
                            output.compf = new Complex64(0,1).times( (float)Math.sqrt(in1.real));
                        }break;
                    case Const.CBRT: output.realf = (float)Math.cbrt(in1.realf);break;
                    case Const.FACTORIAL: output.realf = MyFunc.Factorial(in1.realf);break;
                    default: output.type = Const.ERROR;break;
                }
            }
            else if(in1.type == Const.COMPLEX){
                output.type = Const.COMPLEX;
                switch (type)
                {
                    case Const.SIN:     output.compf = in1.compf.sin();break;
                    case Const.COS:     output.compf = in1.compf.cos();break;
                    case Const.TAN:     output.compf = in1.compf.tan();break;
                    case Const.LOG:     output.compf = in1.compf.log10();break;
                    case Const.LN:      output.compf = in1.compf.ln();break;
                    case Const.ABS:     output.realf = in1.compf.abs();output.type = Const.REAL;break;
                    case Const.SQRT:    output.compf = in1.compf.pow(0.5f);break;
                    case Const.CBRT:    output.compf = in1.compf.pow(1.0f / 3.0f);break;
                    case Const.FACTORIAL:output.type = Const.ERROR; break;
                    default:            output.type = Const.ERROR;break;
                }
            }
        }

        if (output.type == Const.ERROR) return new Element_of_equation("$15");
        if(!output.is_float && output.type == Const.COMPLEX ) {
            if (Math.abs(output.comp.re) < 0.0000000000001) output.comp.re = 0;
            if (Math.abs(output.comp.im) < 0.0000000000001) output.comp.im = 0;
        }
        if (!output.is_float && output.type == Const.REAL && Math.abs(output.real)< 0.0000000000001 ) output.real = 0;

        return output;

    }




}

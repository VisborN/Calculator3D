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
        String out;
        switch(in)
        {
            case Const.SIN:         out = "Sin";break;
            case Const.COS:         out = "Cos";break;
            case Const.TAN:         out = "Tan";break;
            case Const.LOG:         out = "Log";break;
            case Const.LN:          out = "Ln";break;
            case Const.ABS:         out = "Abs";break;
            case Const.SQRT:        out = "√";break;
            case Const.CBRT:        out = "³√";break;
            case Const.FACTORIAL:   out = "!";break;
            case Const.ROOT:        out = "Root";break;
            default: throw new IllegalArgumentException("Funcint_to_Funcname in is\'nt func");
        }

        return out;
    }

    public static Element_of_equation Run_func (byte type, Element_of_equation in1)
    {

        if (in1.is_float){
            if (in1.type==Const.REAL)
                switch (type)
                {
                    case Const.SIN: in1.realf = (float)Math.sin(in1.realf);break;
                    case Const.COS: in1.realf = (float)Math.cos(in1.realf);break;
                    case Const.TAN: in1.realf = (float)Math.tan(in1.realf);break;
                    case Const.LOG: in1.realf = (float)Math.log10(in1.realf);break;
                    case Const.LN:  in1.realf = (float)Math.log(in1.realf);break;
                    case Const.ABS: in1.realf = Math.abs(in1.realf);break;
                    case Const.SQRT:
                        if (in1.realf >= 0)
                            in1.realf = (float)Math.sqrt(in1.realf);
                        else
                        {
                            in1.type = Const.COMPLEX;
                            in1.compf = new Complex64(0,1).times( (float)Math.sqrt(in1.real));
                        }break;
                    case Const.CBRT: in1.realf = (float)Math.cbrt(in1.realf);break;
                    case Const.FACTORIAL: in1.realf = MyFunc.Factorial(in1.realf);break;
                    default: throw new IllegalArgumentException("Run_func type isn\'t function");
                }
            else if(in1.type == Const.COMPLEX)
                switch (type)
                {
                    case Const.SIN:     in1.compf = in1.compf.sin();break;
                    case Const.COS:     in1.compf = in1.compf.cos();break;
                    case Const.TAN:     in1.compf = in1.compf.tan();break;
                    case Const.LOG:     in1.compf = in1.compf.log10();break;
                    case Const.LN:      in1.compf = in1.compf.ln();break;
                    case Const.ABS:     in1.realf = in1.compf.abs();in1.type = Const.REAL;break;
                    case Const.SQRT:    in1.compf = in1.compf.pow(0.5f);break;
                    case Const.CBRT:    in1.compf = in1.compf.pow(1.0f / 3.0f);break;
                    case Const.FACTORIAL:throw new IllegalArgumentException("Run_func factorial of complex value"); //todo add factorial of complex
                    default:            throw new IllegalArgumentException("Run_func type isn\'t function");
                }
        }
        else
        {
            if (in1.type==Const.REAL) {
                switch (type)
                {
                    case Const.SIN: in1.real = Math.sin(in1.real);break;
                    case Const.COS: in1.real = Math.cos(in1.real);break;
                    case Const.TAN: in1.real = Math.tan(in1.real);break;
                    case Const.LOG: in1.real = Math.log10(in1.real);break;
                    case Const.LN:  in1.real = Math.log(in1.real);break;
                    case Const.ABS: in1.real = Math.abs(in1.real);break;
                    case Const.SQRT:
                        if (in1.real >= 0)
                            in1.real = Math.sqrt(in1.real);
                        else
                        {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex(0,1).times( Math.sqrt(in1.real));
                        }break;
                    case Const.CBRT: in1.real = Math.cbrt(in1.real);break;
                    case Const.FACTORIAL: in1.real = MyFunc.Factorial(in1.real);break;
                    default: throw new IllegalArgumentException("Run_func type isn\'t function");
                }
                if ( Math.abs(in1.real) < 0.000000000001)
                    in1.real = 0;
            }
            else if(in1.type == Const.COMPLEX){
                switch (type)
                {
                    case Const.SIN:     in1.comp = in1.comp.sin();break;
                    case Const.COS:     in1.comp = in1.comp.cos();break;
                    case Const.TAN:     in1.comp = in1.comp.tan();break;
                    case Const.LOG:     in1.comp = in1.comp.log10();break;
                    case Const.LN:      in1.comp = in1.comp.ln();break;
                    case Const.ABS:     in1.real = in1.comp.abs();in1.type = Const.REAL;break;
                    case Const.SQRT:    in1.comp = in1.comp.pow(0.5);break;
                    case Const.CBRT:    in1.comp = in1.comp.pow(1.0 / 3.0);break;
                    case Const.FACTORIAL:throw new IllegalArgumentException("Run_func factorial of complex value"); //todo add factorial of complex
                    default:            throw new IllegalArgumentException("Run_func type isn\'t function");
                }
                if (Math.abs(in1.comp.Re()) < 0.000000000001) in1.comp.Re(0);
                if (Math.abs(in1.comp.Im()) < 0.000000000001) in1.comp.Im(0);
            }
        }

        return in1;

    }




    public static Element_of_equation Run_func2 (byte type,Element_of_equation in1, Element_of_equation in2 )
    {
        if (in1.is_float&&in2.is_float){
            float out;
            if (in1.type == Const.REAL&& in2.type == Const.REAL){
                switch (type) {
                    case Const.PLUS: in1.realf = in1.realf + in2.realf; break;
                    case Const.MINUS: in1.realf = in1.realf - in2.realf; break;
                    case Const.DIV: in1.realf = in1.realf / in2.realf; break;
                    case Const.MULT: in1.realf = in1.realf * in2.realf; break;
                    case Const.PRCNT: in1.realf = in1.realf % in2.realf; break;
                    case Const.POW:
                        out = (float)Math.pow(in1.realf, in2.realf);
                        if (Float.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.compf = new Complex64(in1.realf).pow(in2.realf);
                        }else in1.realf = out;
                        break;
                    case Const.ROOT:
                        out = (float)Math.pow(in2.realf, 1/in1.realf);
                        if (Float.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.compf = new Complex64(in2.realf).pow(1 / in1.realf);
                        }else in1.realf = out;
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function");
                }

            }
            else {
                if (in1.type == Const.COMPLEX && in2.type != Const.COMPLEX) {
                    in2.type = Const.COMPLEX;
                    in2.compf = new Complex64(in2.realf);
                }
                else if (in1.type != Const.COMPLEX && in2.type == Const.COMPLEX){
                    in1.type = Const.COMPLEX;
                    in1.compf = new Complex64(in2.realf);
                }
                switch (type) {
                    case Const.PLUS: in1.compf = in1.compf.plus(in2.compf);break;
                    case Const.MINUS: in1.compf = in1.compf.minus(in2.compf);break;
                    case Const.DIV: in1.compf = in1.compf.divides(in2.compf);break;
                    case Const.MULT: in1.compf = in1.compf.times(in2.compf);break;
                    case Const.POW:in1.compf = in1.compf.pow(in2.compf);break;
                    case Const.ROOT:in1.compf = in2.compf.pow(Complex64.divides1(in1.compf));break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function");
                }
            }
        }
        else if (!in1.is_float&&!in2.is_float) {
            double out;
            if (in1.type == Const.REAL&& in2.type == Const.REAL){
                switch (type) {
                    case Const.PLUS: in1.real = in1.real + in2.real; break;
                    case Const.MINUS: in1.real = in1.real - in2.real; break;
                    case Const.DIV: in1.real = in1.real / in2.real; break;
                    case Const.MULT: in1.real = in1.real * in2.real; break;
                    case Const.PRCNT: in1.real = in1.real % in2.real; break;
                    case Const.POW:
                        out = Math.pow(in1.real, in2.real);
                        if (Double.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex(in1.real).pow(in2.real);
                        }else in1.real = out;
                        break;
                    case Const.ROOT:
                        out = Math.pow(in2.real, 1/in1.real);
                        if (Double.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex(in2.real).pow(1 / in1.real);
                        }else in1.real = out;
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function");
                }
                if (Math.abs(in1.real)< 0.000000000001 ) in1.real = 0;

            }
            else {
                if (in1.type == Const.COMPLEX && in2.type != Const.COMPLEX) {
                    in2.type = Const.COMPLEX;
                    in2.comp = new Complex(in2.real);
                }
                else if (in1.type != Const.COMPLEX && in2.type == Const.COMPLEX){
                    in1.type = Const.COMPLEX;
                    in1.comp = new Complex(in1.real);
                }
                switch (type) {
                    case Const.PLUS: in1.comp = in1.comp.plus(in2.comp);break;
                    case Const.MINUS: in1.comp = in1.comp.minus(in2.comp);break;
                    case Const.DIV: in1.comp = in1.comp.divides(in2.comp);break;
                    case Const.MULT: in1.comp = in1.comp.times(in2.comp);break;
                    case Const.POW:in1.comp = in1.comp.pow(in2.comp);break;
                    case Const.ROOT:in1.comp = in2.comp.pow(Complex.divides1(in1.comp));break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function");
                }
                if (Math.abs(in1.comp.Re()) < 0.000000000001) in1.comp.Re(0);
                if (Math.abs(in1.comp.Im()) < 0.000000000001) in1.comp.Im(0);
            }

        }else throw new IllegalArgumentException("Run_func2 is float something happened");
        return in1;

    }




}

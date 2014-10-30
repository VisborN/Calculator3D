package com.dudkovlad.Calc3d.Parser;

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
        else if (in.equals("³√"))
            out = Const.CBRT;
        else  if (in.equals("Root"))
            out = Const.ROOT;
        else if(in.length() ==1)
            switch (in.charAt(0))
            {
                case '|': out = Const.ABSBR;break;
                case '!': out = Const.FACTORIAL; break;
                case '+': out = Const.PLUS;break;
                case '-': out = Const.MINUS;break;
                case ':':
                case '/':
                case '÷': out = Const.DIV;break;
                case '*':
                case '×': out = Const.MULT;break;
                case '^': out = Const.POW;break;
                case '√': out = Const.SQRT;break;
                case '%': out = Const.PRCNT;break;
                case ';':
                case ',': out = Const.COMMA;break;
                case '(': out = Const.LBR;break;
                case ')': out = Const.RBR;break;
                case '=': out = Const.EQUAL;break;
                case '>': out = Const.MORE;break;
                case '<': out = Const.LESS;break;
                case '.': throw new IllegalArgumentException("Funcname_to_Funcint point is alone");
                case '∩': out = Const.AND;break;
                case '∪': out = Const.OR;break;
                case '∆': out = Const.XOR;break;
                case '\\': out = Const.DIFF;break;
                default:throw new IllegalArgumentException("Funcname_to_Funcint in is\'nt operator");
            }

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
            case Const.CBRT:        out = "³√";break;
            case Const.ROOT:        out = "Root";break;
            case Const.ABSBR:       out = "|";break;
            case Const.FACTORIAL:   out = "!"; break;
            case Const.PLUS:        out = "+";break;
            case Const.MINUS:       out = "-";break;
            case Const.DIV:         out = "÷";break;
            case Const.MULT:        out = "×";break;
            case Const.POW:         out = "^";break;
            case Const.SQRT:        out = "√";break;
            case Const.PRCNT:       out = "%";break;
            case Const.COMMA:       out = ";";break;
            case Const.LBR:         out = "(";break;
            case Const.RBR:         out = ")";break;
            case Const.EQUAL:       out = "=";break;
            case Const.MORE:        out = ">";break;
            case Const.LESS:        out = "<";break;
            case Const.AND:         out = "∩";break;
            case Const.OR:          out = "∪";break;
            case Const.XOR:         out = "∆";break;
            case Const.DIFF:        out = "\\";break;
            default: throw new IllegalArgumentException("Funcint_to_Funcname in is\'nt func "+in);
        }

        return out;
    }

    public static Element_of_equation Run_func (byte type, Element_of_equation in1)
    {

        if (in1.is_float){
            if (in1.type==Const.REAL)
                switch (type)
                {
                    case Const.SIN: in1.real = Math.sin((float)(Float)in1.real);break;
                    case Const.COS: in1.real = Math.cos((float)(Float)in1.real);break;
                    case Const.TAN: in1.real = Math.tan((float)(Float)in1.real);break;
                    case Const.LOG: in1.real = Math.log10((float)(Float)in1.real);break;
                    case Const.LN:  in1.real = Math.log((float)(Float)in1.real);break;
                    case Const.ABS: in1.real = Math.abs((float)(Float)in1.real);break;
                    case Const.SQRT:
                        if ((float)(Float)in1.real >= 0)
                            in1.real = Math.sqrt((float)(Float)in1.real);
                        else
                        {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex64(0,1).times( (float)Math.sqrt((float)(Float)in1.real));
                        }break;
                    case Const.CBRT: in1.real = Math.cbrt((float)(Float)in1.real);break;
                    case Const.FACTORIAL: in1.real = MyFunc.Factorial((float)(Float)in1.real);break;
                    default: throw new IllegalArgumentException("Run_func type isn\'t function "+type);
                }
            else if(in1.type == Const.COMPLEX)
                switch (type)
                {
                    case Const.SIN:     in1.comp = in1.comp.sin();break;
                    case Const.COS:     in1.comp = in1.comp.cos();break;
                    case Const.TAN:     in1.comp = in1.comp.tan();break;
                    case Const.LOG:     in1.comp = in1.comp.log10();break;
                    case Const.LN:      in1.comp = in1.comp.ln();break;
                    case Const.ABS:     in1.real = in1.comp.abs();in1.type = Const.REAL;break;
                    case Const.SQRT:    in1.comp = in1.comp.pow(0.5f);break;
                    case Const.CBRT:    in1.comp = in1.comp.pow(1.0f / 3.0f);break;
                    case Const.FACTORIAL:throw new IllegalArgumentException("Run_func factorial of complex value"); //todo add factorial of complex
                    default:            throw new IllegalArgumentException("Run_func type isn\'t function "+type);
                }
        }
        else
        {
            if (in1.type==Const.REAL) {
                switch (type)
                {
                    case Const.SIN: in1.real = Math.sin((double)(Double)in1.real);break;
                    case Const.COS: in1.real = Math.cos((double)(Double)in1.real);break;
                    case Const.TAN: in1.real = Math.tan((double)(Double)in1.real);break;
                    case Const.LOG: in1.real = Math.log10((double)(Double)in1.real);break;
                    case Const.LN:  in1.real = Math.log((double)(Double)in1.real);break;
                    case Const.ABS: in1.real = Math.abs((double)(Double)in1.real);break;
                    case Const.SQRT:
                        if ((double)(Double)in1.real >= 0)
                            in1.real = Math.sqrt((double)(Double)in1.real);
                        else
                        {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex128(0,1).times( Math.sqrt((double)(Double)in1.real));
                        }break;
                    case Const.CBRT: in1.real = Math.cbrt((double)(Double)in1.real);break;
                    case Const.FACTORIAL: in1.real = MyFunc.Factorial((double)(Double)in1.real);break;
                    default: throw new IllegalArgumentException("Run_func type isn\'t function "+type);
                }
                if ( Math.abs((double)(Double)in1.real) < 0.000000000001)
                    in1.real = 0d;
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
                    default:            throw new IllegalArgumentException("Run_func type isn\'t function "+type);
                }
                if (Math.abs(in1.comp.Re()) < 0.000000000001) in1.comp.Re(0d);
                if (Math.abs(in1.comp.Im()) < 0.000000000001) in1.comp.Im(0d);
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
                    case Const.PLUS: in1.real = (float)(Float)in1.real + (float)(Float)in2.real; break;
                    case Const.MINUS: in1.real = (float)(Float)in1.real - (float)(Float)in2.real; break;
                    case Const.DIV: in1.real = (float)(Float)in1.real / (float)(Float)in2.real; break;
                    case Const.MULT: in1.real = (float)(Float)in1.real * (float)(Float)in2.real; break;
                    case Const.PRCNT: in1.real = (float)(Float)in1.real % (float)(Float)in2.real; break;
                    case Const.EQUAL: in1.real = Math.abs((float)(Float)in1.real - (float)(Float)in2.real); break;
                    case Const.MORE:
                        if((float)(Float)in1.real - (float)(Float)in2.real>0)
                            in1.real = (Float)0f;
                        else in1.real = Float.POSITIVE_INFINITY;
                        break;
                    case Const.LESS:
                        if((float)(Float)in1.real - (float)(Float)in2.real<0)
                            in1.real = (Float)0f;
                        else in1.real = Float.POSITIVE_INFINITY;
                        break;
                    case Const.AND: in1.real =Math.max((float)(Float)in1.real, (float)(Float)in2.real);break;
                    case Const.OR: in1.real =Math.min((float)(Float)in1.real, (float)(Float)in2.real);break;
                    case Const.XOR:
                        if ((float)(Float)in1.real==0&&(float)(Float)in2.real!=0)
                            break;
                        else
                            if ((float)(Float)in2.real==0&&(float)(Float)in1.real!=0)
                                in1.real = (Float)0f;
                        else
                            if ((float)(Float)in1.real ==0)
                                in1.real = Float.POSITIVE_INFINITY;//todo may be you whant replace it whith negative infinity
                        else in1.real = Float.POSITIVE_INFINITY;
                        break;
                    case Const.DIFF:
                        if ((float)(Float)in1.real==0&&(float)(Float)in2.real==0)
                            in1.real = Float.POSITIVE_INFINITY;//todo may be you whant replace it whith negative infinity
                        break;
                    case Const.POW:
                        out = (float)Math.pow((float)(Float)in1.real, (float)(Float)in2.real);
                        if (Float.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex64((float)(Float)in1.real).pow((float)(Float)in2.real);
                        }else in1.real = out;
                        break;
                    case Const.ROOT:
                        out = (float)Math.pow((float)(Float)in2.real, 1/(float)(Float)in1.real);
                        if (Float.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex64((float)(Float)in2.real).pow(1 / (float)(Float)in1.real);
                        }else in1.real = out;
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function "+type);
                }

            }
            else {
                if (in1.type == Const.COMPLEX && in2.type != Const.COMPLEX) {
                    in2.type = Const.COMPLEX;
                    in2.comp = new Complex64((float)(Float)in2.real);
                }
                else if (in1.type != Const.COMPLEX && in2.type == Const.COMPLEX){
                    in1.type = Const.COMPLEX;
                    in1.comp = new Complex64((float)(Float)in2.real);
                }
                switch (type) {
                    case Const.PLUS: in1.comp = in1.comp.plus(in2.comp);break;
                    case Const.MINUS: in1.comp = in1.comp.minus(in2.comp);break;
                    case Const.DIV: in1.comp = in1.comp.divides(in2.comp);break;
                    case Const.MULT: in1.comp = in1.comp.times(in2.comp);break;
                    case Const.POW:in1.comp = in1.comp.pow(in2.comp);break;
                    case Const.ROOT:in1.comp = in2.comp.pow(in1.comp.divides1());break;
                    case Const.EQUAL: in1.real = Math.abs((float)(Float)in1.real - (float)(Float)in2.real); break;
                    case Const.MORE:
                        if(in1.comp.abs() - in2.comp.abs()>0)
                            in1.real = (Float)0f;
                        else in1.real = Float.POSITIVE_INFINITY;
                        in1.type = Const.REAL;
                        break;
                    case Const.LESS:
                        if(in1.comp.abs() - in2.comp.abs()<0)
                            in1.real = (Float)0f;
                        else in1.real = Float.POSITIVE_INFINITY;
                        in1.type = Const.REAL;
                        break;
                    case Const.AND: in1.real =Math.max(in1.comp.abs(), in2.comp.abs());
                        in1.type = Const.REAL;break;
                    case Const.OR: in1.real =Math.min(in1.comp.abs(), in2.comp.abs());
                        in1.type = Const.REAL;break;
                    case Const.XOR:
                        if (in1.comp.abs()==0&&in2.comp.abs()!=0)
                            in1.real = (Float)0f;
                        else
                        if (in2.comp.abs()==0&&in1.comp.abs()!=0)
                            in1.real = (Float)0f;
                        else
                        if (in1.comp.abs() ==0)
                            in1.real = Float.POSITIVE_INFINITY;//todo may be you whant replace it whith negative infinity
                        else in1.real = Float.POSITIVE_INFINITY;
                        in1.type = Const.REAL;
                        break;
                    case Const.DIFF:
                        if (in1.comp.abs()==0&&in2.comp.abs()==0)
                            in1.real = Float.POSITIVE_INFINITY;//todo may be you whant replace it whith negative infinity
                        in1.type = Const.REAL;
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function "+type);
                }
            }
        }
        else if (!in1.is_float&&!in2.is_float) {
            double out;
            if (in1.type == Const.REAL&& in2.type == Const.REAL){
                switch (type) {
                    case Const.PLUS: in1.real = (double)(Double)in1.real + (double)(Double)in2.real; break;
                    case Const.MINUS: in1.real = (double)(Double)in1.real - (double)(Double)in2.real; break;
                    case Const.DIV: in1.real = (double)(Double)in1.real / (double)(Double)in2.real; break;
                    case Const.MULT: in1.real = (double)(Double)in1.real * (double)(Double)in2.real; break;
                    case Const.PRCNT: in1.real = (double)(Double)in1.real % (double)(Double)in2.real; break;
                    case Const.POW:
                        out = Math.pow((double)(Double)in1.real, (double)(Double)in2.real);
                        if (Double.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex128((double)(Double)in1.real).pow((double)(Double)in2.real);
                        }else in1.real = out;
                        break;
                    case Const.ROOT:
                        out = Math.pow((double)(Double)in2.real, 1/(double)(Double)in1.real);
                        if (Double.isNaN(out)) {
                            in1.type = Const.COMPLEX;
                            in1.comp = new Complex128((double)(Double)in2.real).pow(1 / (double)(Double)in1.real);
                        }else in1.real = out;
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function "+type);
                }
                if (Math.abs((double)(Double)in1.real)< 0.000000000001 ) in1.real = 0d;

            }
            else {
                if (in1.type == Const.COMPLEX && in2.type != Const.COMPLEX) {
                    in2.type = Const.COMPLEX;
                    in2.comp = new Complex128((double)(Double)in2.real);
                }
                else if (in1.type != Const.COMPLEX && in2.type == Const.COMPLEX){
                    in1.type = Const.COMPLEX;
                    in1.comp = new Complex128((double)(Double)in1.real);
                }
                switch (type) {
                    case Const.PLUS: in1.comp = in1.comp.plus(in2.comp);break;
                    case Const.MINUS: in1.comp = in1.comp.minus(in2.comp);break;
                    case Const.DIV: in1.comp = in1.comp.divides(in2.comp);break;
                    case Const.MULT: in1.comp = in1.comp.times(in2.comp);break;
                    case Const.POW:in1.comp = in1.comp.pow(in2.comp);break;
                    case Const.ROOT:in1.comp = in2.comp.pow(in1.comp.divides1());break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function "+type);
                }
                if (Math.abs(in1.comp.Re()) < 0.000000000001) in1.comp.Re(0d);
                if (Math.abs(in1.comp.Im()) < 0.000000000001) in1.comp.Im(0d);
            }

        }else throw new IllegalArgumentException("Run_func2 is float something happened");
        return in1;

    }




}

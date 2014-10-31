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

    public static Token Run_func (byte type, Token in1)
    {

        if (in1.Is_float()){
            if (in1.Type()==Const.REAL)
                switch (type)
                {
                    case Const.SIN: in1.set(Math.sin(in1.getf()));break;
                    case Const.COS: in1.set(Math.cos(in1.getf()));break;
                    case Const.TAN: in1.set(Math.tan(in1.getf()));break;
                    case Const.LOG: in1.set(Math.log10(in1.getf()));break;
                    case Const.LN:  in1.set(Math.log(in1.getf()));break;
                    case Const.ABS: in1.set(Math.abs(in1.getf()));break;
                    case Const.SQRT:
                        if ((float)(Float)in1.getf() >= 0)
                            in1.set(Math.sqrt(in1.getf()));
                        else
                            in1.set(new Complex64(0,1).times( Math.sqrt(in1.getf())));
                        break;
                    case Const.CBRT: in1.set(Math.cbrt(in1.getf()));break;
                    case Const.FACTORIAL: in1.set(MyFunc.Factorial(in1.getf()));break;
                    default: throw new IllegalArgumentException("Run_func type isn\'t function4 "+type);
                }
            else if(in1.Type() == Const.COMPLEX)
                switch (type)
                {
                    case Const.SIN:     in1.set(in1.getc().sin());break;
                    case Const.COS:     in1.set(in1.getc().cos());break;
                    case Const.TAN:     in1.set(in1.getc().tan());break;
                    case Const.LOG:     in1.set(in1.getc().log10());break;
                    case Const.LN:      in1.set(in1.getc().ln());break;
                    case Const.ABS:     in1.set(in1.getc().abs());break;
                    case Const.SQRT:    in1.set(in1.getc().pow(0.5f));break;
                    case Const.CBRT:    in1.set(in1.getc().pow(1.0f / 3.0f));break;
                    case Const.FACTORIAL:throw new IllegalArgumentException("Run_func factorial of complex value"); //todo add factorial of complex
                    default:            throw new IllegalArgumentException("Run_func type isn\'t function3 "+type);
                }
        }
        else
        {
            if (in1.Type()==Const.REAL) {
                switch (type)
                {
                    case Const.SIN: in1.set(Math.sin(in1.getd()));break;
                    case Const.COS: in1.set(Math.cos(in1.getd()));break;
                    case Const.TAN: in1.set(Math.tan(in1.getd()));break;
                    case Const.LOG: in1.set(Math.log10(in1.getd()));break;
                    case Const.LN:  in1.set(Math.log(in1.getd()));break;
                    case Const.ABS: in1.set(Math.abs(in1.getd()));break;
                    case Const.SQRT:
                        if (in1.getd() >= 0)
                            in1.set(Math.sqrt(in1.getd()));
                        else
                            in1.set(new Complex128(0,1).times( Math.sqrt(in1.getd())));
                        break;
                    case Const.CBRT: in1.set(Math.cbrt(in1.getd()));break;
                    case Const.FACTORIAL: in1.set(MyFunc.Factorial(in1.getd()));break;
                    default: throw new IllegalArgumentException("Run_func type isn\'t function2 "+type);
                }
                if ( Math.abs(in1.getd()) < 0.000000000001)
                    in1.set(0d);
            }
            else if(in1.Type() == Const.COMPLEX){
                switch (type)
                {
                    case Const.SIN:     in1.set(in1.getc().sin());break;
                    case Const.COS:     in1.set(in1.getc().cos());break;
                    case Const.TAN:     in1.set(in1.getc().tan());break;
                    case Const.LOG:     in1.set(in1.getc().log10());break;
                    case Const.LN:      in1.set(in1.getc().ln());break;
                    case Const.ABS:     in1.set(in1.getc().abs());break;
                    case Const.SQRT:    in1.set(in1.getc().pow(0.5));break;
                    case Const.CBRT:    in1.set(in1.getc().pow(1.0 / 3.0));break;
                    case Const.FACTORIAL:throw new IllegalArgumentException("Run_func factorial of complex value"); //todo add factorial of complex
                    default:            throw new IllegalArgumentException("Run_func type isn\'t function1 "+type);
                }
                if (Math.abs(in1.getc().Re()) < 0.000000000001) in1.getc().Re(0d);//todo check how it work
                if (Math.abs(in1.getc().Im()) < 0.000000000001) in1.getc().Im(0d);
            }
        }

        return in1;

    }




    public static Token Run_func2 (byte type,Token in1, Token in2 )
    {
        if (in1.Is_float()&&in2.Is_float()){
            float out;
            if (in1.Type() == Const.REAL&& in2.Type() == Const.REAL){
                switch (type) {
                    case Const.PLUS: in1.set(in1.getf() + in2.getf()); break;
                    case Const.MINUS: in1.set(in1.getf() - in2.getf()); break;
                    case Const.DIV: in1.set(in1.getf() / in2.getf()); break;
                    case Const.MULT: in1.set(in1.getf() * in2.getf()); break;
                    case Const.PRCNT: in1.set(in1.getf() % in2.getf()); break;
                    case Const.EQUAL: in1.set(Math.abs(in1.getf() - in2.getf())); break;
                    case Const.MORE:
                        if(in1.getf() - in2.getf()>0)
                            in1.set(0f);
                        else in1.set(Float.POSITIVE_INFINITY);
                        break;
                    case Const.LESS:
                        if(in1.getf() - in2.getf()<0)
                            in1.set(0f);
                        else in1.set(Float.POSITIVE_INFINITY);
                        break;
                    case Const.AND: in1.set(Math.max(in1.getf(), in2.getf()));break;
                    case Const.OR: in1.set(Math.min(in1.getf(), in2.getf()));break;
                    case Const.XOR:
                        if (in1.getf()==0&&in2.getf()!=0)
                            break;
                        else
                            if (in2.getf()==0&&in1.getf()!=0)
                                in1.set(0f);
                        else
                            if (in1.getf() ==0)
                                in1.set(Float.POSITIVE_INFINITY);//todo may be you whant replace it whith negative infinity
                        else in1.set(Float.POSITIVE_INFINITY);
                        break;
                    case Const.DIFF:
                        if (in1.getf()==0&&in2.getf()==0)
                            in1.set(Float.POSITIVE_INFINITY);//todo may be you whant replace it whith negative infinity
                        break;
                    case Const.POW:
                        out = (float)Math.pow(in1.getf(), in2.getf());
                        if (Float.isNaN(out))
                            in1.set(new Complex64(in1.getf()).pow(in2.getf()));
                        else in1.set(out);
                        break;
                    case Const.ROOT:
                        out = (float)Math.pow(in2.getf(), 1/in1.getf());
                        if (Float.isNaN(out))
                            in1.set(new Complex64(in2.getf()).pow(1 / in1.getf()));
                        else in1.set(out);
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function2 "+type);
                }

            }
            else {
                if (in1.Type() == Const.COMPLEX && in2.Type() != Const.COMPLEX)
                    in2.set(new Complex64(in2.getf()));
                else if (in1.Type() != Const.COMPLEX && in2.Type() == Const.COMPLEX)
                    in1.set(new Complex64(in2.getf()));
                switch (type) {
                    case Const.PLUS: in1.set(in1.getc().plus(in2.getc()));break;
                    case Const.MINUS: in1.set(in1.getc().minus(in2.getc()));break;
                    case Const.DIV: in1.set(in1.getc().divides(in2.getc()));break;
                    case Const.MULT: in1.set(in1.getc().times(in2.getc()));break;
                    case Const.POW:in1.set(in1.getc().pow(in2.getc()));break;
                    case Const.ROOT:in1.set(in2.getc().pow(in1.getc().divides1()));break;
                    case Const.EQUAL: in1.set(Math.abs(in1.getc().abs() - in2.getc().abs())); break;
                    case Const.MORE:
                        if(in1.getc().abs() - in2.getc().abs()>0)
                            in1.set(0f);
                        else in1.set(Float.POSITIVE_INFINITY);
                        break;
                    case Const.LESS:
                        if(in1.getc().abs() - in2.getc().abs()<0)
                            in1.set(0f);
                        else in1.set(Float.POSITIVE_INFINITY);
                        break;
                    case Const.AND: in1.set(Math.max(in1.getc().abs(), in2.getc().abs()));break;
                    case Const.OR: in1.set(Math.min(in1.getc().abs(), in2.getc().abs()));break;
                    case Const.XOR:
                        if (in1.getc().abs() == 0&&in2.getc().abs() != 0)
                            in1.set(0f);
                        else
                        if (in2.getc().abs() == 0&&in1.getc().abs() != 0)
                            in1.set(0f);
                        else
                        if (in1.getc().abs() == 0)
                            in1.set(Float.POSITIVE_INFINITY);//todo may be you whant replace it whith negative infinity
                        else in1.set(Float.POSITIVE_INFINITY);
                        break;
                    case Const.DIFF:
                        if (in1.getc().abs()==0&&in2.getc().abs()==0)
                            in1.set(Float.POSITIVE_INFINITY);//todo may be you whant replace it whith negative infinity
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function1 "+type);
                }
            }
        }
        else if (!in1.Is_float()&&!in2.Is_float()) {
            double out;
            if (in1.Type() == Const.REAL&& in2.Type() == Const.REAL){
                switch (type) {
                    case Const.PLUS: in1.set(in1.getd() + in2.getd()); break;
                    case Const.MINUS: in1.set(in1.getd() - in2.getd()); break;
                    case Const.DIV: in1.set(in1.getd() / in2.getd()); break;
                    case Const.MULT: in1.set(in1.getd() * in2.getd()); break;
                    case Const.PRCNT: in1.set(in1.getd() % in2.getd()); break;
                    case Const.POW:
                        out = Math.pow(in1.getd(), in2.getd());
                        if (Double.isNaN(out))
                            in1.set(new Complex128(in1.getd()).pow(in2.getd()));
                        else in1.set(out);
                        break;
                    case Const.ROOT:
                        out = Math.pow(in2.getd(), 1/in1.getd());
                        if (Double.isNaN(out))
                            in1.set(new Complex128(in2.getd()).pow(1 / in1.getd()));
                        else in1.set(out);
                        break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function3 "+type);
                }
                if (Math.abs(in1.getd())< 0.000000000001 ) in1.set(0d);

            }
            else {
                if (in1.Type() == Const.COMPLEX && in2.Type() != Const.COMPLEX)
                    in2.set(new Complex128(in2.getd()));
                else if (in1.Type() != Const.COMPLEX && in2.Type() == Const.COMPLEX)
                    in1.set(new Complex128(in1.getd()));
                switch (type) {
                    case Const.PLUS:    in1.set(in1.getc().plus(in2.getc()));break;
                    case Const.MINUS:   in1.set(in1.getc().minus(in2.getc()));break;
                    case Const.DIV:     in1.set(in1.getc().divides(in2.getc()));break;
                    case Const.MULT:    in1.set(in1.getc().times(in2.getc()));break;
                    case Const.POW:     in1.set(in1.getc().pow(in2.getc()));break;
                    case Const.ROOT:    in1.set(in2.getc().pow(in1.getc().divides1()));break;
                    default: throw new IllegalArgumentException("Run_func2 type isn\'t function4 "+type);
                }
                if (Math.abs(in1.getc().Re()) < 0.000000000001) in1.getc().Re(0d);
                if (Math.abs(in1.getc().Im()) < 0.000000000001) in1.getc().Im(0d);
            }

        }else throw new IllegalArgumentException("Run_func2 is float something happened");
        return in1;

    }




}

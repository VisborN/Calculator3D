package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 16.01.2015.
 */
public  final class DelegatesList {
/*
    public static final int COTAN   = 0;
    public static final int ARCSIN  = 1;
    public static final int ARCCOS  = 2;
    public static final int ARCTAN  = 3;
    public static final int ARCCOTAN = 4;
    public static final int SINH    = 5;
    public static final int COSH    = 6;
    public static final int TANH    = 7;
    public static final int ARCSINH = 8;
    public static final int ARCCOSH = 9;
    public static final int ARCTANH = 10;
    public static final int SIN     = 11;
    public static final int COS     = 12;
    public static final int TAN     = 13;
    public static final int LOG     = 14;
    public static final int LN      = 15;
    public static final int ABS     = 16;
    public static final int SQRT    = 17;
    public static final int CBRT    = 18;
    public static final int FACTORIAL = 19;
    public static final int PLUS    = 20;
    public static final int MINUS   = 21;
    public static final int DIV     = 22;
    public static final int MULT    = 23;
    public static final int PRCNT   = 24;
    public static final int EQUAL   = 25;
    public static final int MORE    = 26;
    public static final int LESS    = 27;
    public static final int AND     = 28;
    public static final int OR      = 29;
    public static final int XOR     = 30;
    public static final int DIFF    = 31;
    public static final int POW     = 32;
    public static final int ROOT    = 33;*/

    public static final MyDelegate [] array_of_functions =
            {       new COTAN(),    new ARCSIN(),           new ARCCOS(),
                    new ARCTAN(),   new ARCCOTAN(),         new SINH(),
                    new COSH(),     new TANH(),             new ARCSINH(),
                    new ARCCOSH(),  new ARCTANH(),          new SIN(),
                    new COS(),      new TAN(),              new LOG(),
                    new LN(),       new ABS(),              new SQRT(),
                    new CBRT(),     new FACTORIAL(),        new PLUS(),
                    new MINUS(),    new DIV(),              new MULT(),
                    new PRCNT(),    new EQUAL(),            new MORE(),
                    new LESS(),     new AND(),              new OR(),
                    new XOR(),      new DIFF(),             new POW(),
                    new ROOT(),           };






    private static class COTAN extends MyDelegate {


        public String toString ()
        {
            return "Ctg";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(1/Math.tan(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().cotan());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(1/Math.tan(in1.getd()));
        }
    }

    private static class ARCSIN extends MyDelegate {


        public String toString ()
        {
            return "Arcsin";
        }
        public int getNumArguments ()
        {
            return 1;
        }

        protected void funcF (Token in1)
        {
            in1.set(Math.asin(in1.getf()));
            if (!in1.Is_radians())
                in1.set(in1.getf() * 180 / Math.PI);
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arcsin());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.asin(in1.getd()));
            if (!in1.Is_radians())
                in1.set(in1.getd() * 180 / Math.PI);
        }
    }

    private static class ARCCOS extends MyDelegate {

        public String toString ()
        {
            return "Arccos";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.acos(in1.getf()));
            if (!in1.Is_radians())
                in1.set(in1.getf() * 180 / Math.PI);
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arccos());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.acos(in1.getd()));
            if (!in1.Is_radians())
                in1.set(in1.getd() * 180 / Math.PI);
        }
    }

    private static class ARCTAN extends MyDelegate {

        public String toString ()
        {
            return "Arctg";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.atan(in1.getf()));
            if (!in1.Is_radians())
                in1.set(in1.getf() * 180 / Math.PI);
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arctan());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.atan(in1.getd()));
            if (!in1.Is_radians())
                in1.set(in1.getd() * 180 / Math.PI);
        }
    }

    private static class ARCCOTAN extends MyDelegate {

        public String toString ()
        {
            return "Arcctg";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.atan(1/in1.getf()));
            if (!in1.Is_radians())
                in1.set(in1.getf() * 180 / Math.PI);
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arccotan());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.atan(1/in1.getd()));
            if (!in1.Is_radians())
                in1.set(in1.getd() * 180 / Math.PI);
        }
    }

    private static class SINH extends MyDelegate {

        public String toString ()
        {
            return "Sinh";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(Math.sinh(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().sinh());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(Math.sinh(in1.getd()));
        }
    }

    private static class COSH extends MyDelegate {

        public String toString ()
        {
            return "Cosh";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(Math.cosh(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().cosh());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(Math.cosh(in1.getd()));
        }
    }

    private static class TANH extends MyDelegate {

        public String toString ()
        {
            return "Tgh";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(Math.tanh(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().tanh());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(Math.tanh(in1.getd()));
        }
    }

    private static class ARCSINH extends MyDelegate {

        public String toString ()
        {
            return "Arsh";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(new Complex64(in1.getf()));
            in1.set(in1.getc().arcsinh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arcsinh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(new Complex128(in1.getd()));
            in1.set(in1.getc().arcsinh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
    }

    private static class ARCCOSH extends MyDelegate {

        public String toString ()
        {
            return "Arch";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(new Complex64(in1.getf()));
            in1.set(in1.getc().arccosh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arccosh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(new Complex128(in1.getd()));
            in1.set(in1.getc().arccosh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
    }

    private static class ARCTANH extends MyDelegate {

        public String toString ()
        {
            return "Arcth";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(new Complex64(in1.getf()));
            in1.set(in1.getc().arctanh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().arctanh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
        protected void funcD (Token in1)
        {
            in1.set(new Complex128(in1.getd()));
            in1.set(in1.getc().arctanh());
            if (!in1.Is_radians())
                in1.set(in1.getc().times(180 / Math.PI));
        }
    }





    private static class SIN extends MyDelegate {

        public String toString ()
        {
            return "Sin";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(Math.sin(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().sin());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(Math.sin(in1.getd()));
        }
    }


    private static class COS extends MyDelegate {

        public String toString ()
        {
            return "Cos";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(Math.cos(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().cos());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(Math.cos(in1.getd()));
        }
    }


    private static class TAN extends MyDelegate {

        public String toString ()
        {
            return "Tg";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getf() * Math.PI / 180);
            in1.set(Math.tan(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getc().times(Math.PI/180));
            in1.set(in1.getc().tan());
        }
        protected void funcD (Token in1)
        {
            if (!in1.Is_radians())
                in1.set(in1.getd() * Math.PI / 180);
            in1.set(Math.tan(in1.getd()));
        }
    }


    private static class LOG extends MyDelegate {

        public String toString ()
        {
            return "Log";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.log10(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().log10());
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.log10(in1.getd()));
        }
    }


    private static class LN extends MyDelegate {

        public String toString ()
        {
            return "Ln";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.log(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().ln());
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.log(in1.getd()));
        }
    }


    private static class ABS extends MyDelegate {

        public String toString ()
        {
            return "Abs";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.abs(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().abs());
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.abs(in1.getd()));
        }
    }


    private static class SQRT extends MyDelegate {

        public String toString ()
        {
            return "√";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            if ((float)(Float)in1.getf() >= 0)
                in1.set(Math.sqrt(in1.getf()));
            else
                in1.set(new Complex64(0,1).times( Math.sqrt(in1.getf())));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().pow(0.5f));
        }
        protected void funcD (Token in1)
        {
            if (in1.getd() >= 0)
                in1.set(Math.sqrt(in1.getd()));
            else
                in1.set(new Complex128(0,1).times( Math.sqrt(in1.getd())));
        }
    }


    private static class CBRT extends MyDelegate {

        public String toString ()
        {
            return "³√";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(Math.cbrt(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            in1.set(in1.getc().pow(1.0f / 3.0f));
        }
        protected void funcD (Token in1)
        {
            in1.set(Math.cbrt(in1.getd()));
        }
    }


    private static class FACTORIAL extends MyDelegate {

        public String toString ()
        {
            return "!";
        }
        public int getNumArguments ()
        {
            return 1;
        }


        protected void funcF (Token in1)
        {
            in1.set(MyFunc.Factorial(in1.getf()));
        }
        protected void funcC (Token in1)
        {
            throw new IllegalArgumentException("Run_func factorial of complex value"); //todo add factorial of complex
        }
        protected void funcD (Token in1)
        {
            in1.set(MyFunc.Factorial(in1.getd()));
        }
    }




    private static class PLUS extends MyDelegate {

        public String toString ()
        {
            return "+";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(in1.getf() + in2.getf());
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(in1.getc().plus(in2.getc()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(in1.getd() + in2.getd());
        }
    }


    private static class MINUS extends MyDelegate {

        public String toString ()
        {
            return "-";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(in1.getf() - in2.getf());
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(in1.getc().minus(in2.getc()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(in1.getd() - in2.getd());
        }
    }


    private static class DIV extends MyDelegate {

        public String toString ()
        {
            return "÷";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(in1.getf() / in2.getf());
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(in1.getc().divides(in2.getc()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(in1.getd() / in2.getd());
        }
    }


    private static class MULT extends MyDelegate {

        public String toString ()
        {
            return "×";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(in1.getf() * in2.getf());
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(in1.getc().times(in2.getc()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(in1.getd() * in2.getd());
        }
    }


    private static class PRCNT extends MyDelegate {

        public String toString ()
        {
            return "%";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(in1.getf() % in2.getf());
        }
        protected void funcC (Token in1, Token in2)
        {
            //todo prcnt isnt defined
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(in1.getd() % in2.getd());
        }
    }


    private static class EQUAL extends MyDelegate {

        public String toString ()
        {
            return "=";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(Math.abs(in1.getf() - in2.getf()));
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(Math.abs(in1.getc().abs() - in2.getc().abs()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(Math.abs(in1.getd() - in2.getd()));
        }
    }


    private static class MORE extends MyDelegate {

        public String toString ()
        {
            return ">";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            if(in1.getf() - in2.getf()>0)
                in1.set(0f);
            else in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcC (Token in1, Token in2)
        {
            if(in1.getc().abs() - in2.getc().abs()>0)
                in1.set(0f);
            else in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcD (Token in1, Token in2)
        {
            if(in1.getd() - in2.getd()>0)
                in1.set(0d);
            else in1.set(Double.POSITIVE_INFINITY);
        }
    }


    private static class LESS extends MyDelegate {

        public String toString ()
        {
            return "<";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            if(in1.getf() - in2.getf()<0)
                in1.set(0f);
            else in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcC (Token in1, Token in2)
        {
            if(in1.getc().abs() - in2.getc().abs()<0)
                in1.set(0f);
            else in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcD (Token in1, Token in2)
        {
            if(in1.getd() - in2.getd()<0)
                in1.set(0d);
            else in1.set(Double.POSITIVE_INFINITY);
        }
    }


    private static class AND extends MyDelegate {

        public String toString ()
        {
            return "∩";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(Math.max(in1.getf(), in2.getf()));
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(Math.max(in1.getc().abs(), in2.getc().abs()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(Math.max(in1.getd(), in2.getd()));
        }
    }


    private static class OR extends MyDelegate {

        public String toString ()
        {
            return "∪";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            in1.set(Math.min(in1.getf(), in2.getf()));
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(Math.min(in1.getc().abs(), in2.getc().abs()));
        }
        protected void funcD (Token in1, Token in2)
        {
            in1.set(Math.min(in1.getd(), in2.getd()));
        }
    }


    private static class XOR extends MyDelegate {

        public String toString ()
        {
            return "∆";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            if (in1.getf()==0&&in2.getf()!=0)
                return;
            else
            if (in2.getf()==0&&in1.getf()!=0)
                in1.set(0f);
            else
            if (in1.getf() ==0)
                in1.set(Float.POSITIVE_INFINITY);//todo may be you whont replace it whith negative infinity
            else in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcC (Token in1, Token in2)
        {
            if (in1.getc().abs() == 0&&in2.getc().abs() != 0)
                in1.set(0f);
            else
            if (in2.getc().abs() == 0&&in1.getc().abs() != 0)
                in1.set(0f);
            else
            if (in1.getc().abs() == 0)
                in1.set(Float.POSITIVE_INFINITY);//todo may be you whant replace it whith negative infinity
            else in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcD (Token in1, Token in2)
        {
            if (in1.getd()==0&&in2.getd()!=0)
                return;
            else
            if (in2.getd()==0&&in1.getd()!=0)
                in1.set(0d);
            else
            if (in1.getd() ==0)
                in1.set(Double.POSITIVE_INFINITY);//todo may be you whont replace it whith negative infinity
            else in1.set(Double.POSITIVE_INFINITY);
        }
    }


    private static class DIFF extends MyDelegate {

        public String toString ()
        {
            return "\\";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            if (in1.getf()==0&&in2.getf()==0)
                in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcC (Token in1, Token in2)
        {
            if (in1.getc().abs()==0&&in2.getc().abs()==0)
                in1.set(Float.POSITIVE_INFINITY);
        }
        protected void funcD (Token in1, Token in2)
        {
            if (in1.getd()==0&&in2.getd()==0)
                in1.set(Double.POSITIVE_INFINITY);
        }
    }


    private static class POW extends MyDelegate {

        public String toString ()
        {
            return "^";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            float out = (float)Math.pow(in1.getf(), in2.getf());
            if (Float.isNaN(out))
                in1.set(new Complex64(in1.getf()).pow(in2.getf()));
            else in1.set(out);
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(in1.getc().pow(in2.getc()));
        }
        protected void funcD (Token in1, Token in2)
        {
            double out = Math.pow(in1.getd(), in2.getd());
            if (Double.isNaN(out))
                in1.set(new Complex128(in1.getd()).pow(in2.getd()));
            else in1.set(out);
        }
    }


    private static class ROOT extends MyDelegate {

        public String toString ()
        {
            return "Root";
        }
        public int getNumArguments ()
        {
            return 2;
        }


        protected void funcF (Token in1, Token in2)
        {
            float out = (float)Math.pow(in2.getf(), 1/in1.getf());
            if (Float.isNaN(out))
                in1.set(new Complex64(in2.getf()).pow(1 / in1.getf()));
            else in1.set(out);
        }
        protected void funcC (Token in1, Token in2)
        {
            in1.set(in2.getc().pow(in1.getc().divides1()));
        }
        protected void funcD (Token in1, Token in2)
        {

            double out = Math.pow(in2.getd(), 1/in1.getd());
            if (Double.isNaN(out))
                in1.set(new Complex128(in2.getd()).pow(1 / in1.getd()));
            else in1.set(out);
        }
    }
}

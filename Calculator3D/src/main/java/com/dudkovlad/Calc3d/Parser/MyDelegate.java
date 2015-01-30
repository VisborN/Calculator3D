package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 08.01.2015.
 */
public abstract class MyDelegate {




    public boolean equals (String comparewith)
    {
        if (comparewith.equals("Cot"))
            return this.toString().equals("Ctg");
        if (comparewith.equals("Tan"))
            return this.toString().equals("Tg");
        if (comparewith.equals(":")||comparewith.equals("/"))
            return this.toString().equals("÷");
        if (comparewith.equals("*"))
            return this.toString().equals("×");
        return this.toString().equals(comparewith);

    }

    public String toString ()
    {
        return "";
    }
    public int getNumArguments ()
    {
        return 1;
    }

    public Token Run (Token in1)
    {

        if (in1.Is_float()){
            if (in1.Type()==Const.REAL)
                funcF(in1);
            else if(in1.Type() == Const.COMPLEX)
                funcC(in1);
        }
        else
        {
            if (in1.Type()==Const.REAL) {
                funcD(in1);
                if ( Math.abs(in1.getd()) < 0.000000000001)
                    in1.set(0d);
            }
            else if(in1.Type() == Const.COMPLEX){
                funcC(in1);
                if (Math.abs(in1.getc().Re()) < 0.000000000001) in1.getc().Re(0d);
                if (Math.abs(in1.getc().Im()) < 0.000000000001) in1.getc().Im(0d);
            }
        }
        return in1;
    }


    public Token Run (Token in1, Token in2)
    {
        if (in1.Is_float()&&in2.Is_float()){
            if (in1.Type() == Const.REAL&& in2.Type() == Const.REAL){
                funcF(in1, in2);
            }
            else {
                if (in1.Type() == Const.COMPLEX && in2.Type() != Const.COMPLEX)
                    in2.set(new Complex64(in2.getf()));
                else if (in1.Type() != Const.COMPLEX && in2.Type() == Const.COMPLEX)
                    in1.set(new Complex64(in2.getf()));
                funcC(in1, in2);
            }
        }
        else if (!in1.Is_float()&&!in2.Is_float()) {
            if (in1.Type() == Const.REAL&& in2.Type() == Const.REAL){
                funcD(in1, in2);
                if (Math.abs(in1.getd())< 0.000000000001 ) in1.set(0d);
            }
            else {
                if (in1.Type() == Const.COMPLEX && in2.Type() != Const.COMPLEX)
                    in2.set(new Complex128(in2.getd()));
                else if (in1.Type() != Const.COMPLEX && in2.Type() == Const.COMPLEX)
                    in1.set(new Complex128(in1.getd()));
                funcC(in1, in2);
                if (Math.abs(in1.getc().Re()) < 0.000000000001) in1.getc().Re(0d);
                if (Math.abs(in1.getc().Im()) < 0.000000000001) in1.getc().Im(0d);
            }
        }else throw new IllegalArgumentException("Technical Error #1");
        return in1;
    }

    /*public Token Run (Token in1, Token in2, Token in3)
    {
        throw new NullPointerException("run undefined function of MyDelegate" + this.toString());
    }
*///todo three arguments function and infinity arguments function



    protected void funcC (Token in1)
    {
        throw new NullPointerException("Technical Error #1 " + this.toString());
    }
    protected void funcD (Token in1)
    {
        throw new NullPointerException("Technical Error #2 " + this.toString());
    }
    protected void funcF (Token in1)
    {
        throw new NullPointerException("Technical Error #3 " + this.toString());
    }

    protected void funcC (Token in1, Token in2)
    {
        throw new NullPointerException("Technical Error #4 " + this.toString());
    }
    protected void funcD (Token in1, Token in2)
    {
        throw new NullPointerException("Technical Error #5 " + this.toString());
    }
    protected void funcF (Token in1, Token in2)
    {
        throw new NullPointerException("Technical Error #6 " + this.toString());
    }

/*
    public void funcC (Token in1, Token in2, Token in3)
    {
        throw new NullPointerException("Technical Error #7 " + this.toString());
    }
    public void funcD (Token in1, Token in2, Token in3)
    {
        throw new NullPointerException("Technical Error #8 " + this.toString());
    }
    public void funcF (Token in1, Token in2, Token in3)
    {
        throw new NullPointerException("Technical Error #9 " + this.toString());
    }*/



}

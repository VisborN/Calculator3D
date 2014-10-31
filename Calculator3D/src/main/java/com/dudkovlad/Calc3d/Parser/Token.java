package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 31.10.2014.
 */
public abstract class Token {


    public abstract String      toString ();


    public abstract Complex     getc ();

    public abstract float       getf ();

    public abstract double      getd ();


    public abstract void        set (float in);

    public abstract void        set (double in);

    public abstract void        set (Complex in);



    public abstract boolean     Is_float ();


    public abstract byte        Type ();

    public abstract byte        Stype ();

    public abstract byte        Priority ();

    public abstract Token       tofloat ();


}

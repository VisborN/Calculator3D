package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 31.10.2014.
 */
public abstract class Token {

    public MyDelegate Func;



    public abstract Complex     getc ();

    public abstract float       getf ();

    public abstract double      getd ();


    public abstract void        set (float in);

    public abstract void        set (double in);

    public abstract void        set (Complex in);



    public abstract boolean     Is_float ();


    public abstract void        Set_radians (boolean radians_);

    public abstract boolean     Is_radians ();


    public abstract byte        Type ();

    public abstract byte        Stype ();

    public abstract byte        Priority ();

    public abstract Token       tofloat ();


}

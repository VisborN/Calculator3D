package com.dudkovlad.CalcTDP.Parser;

/**
 * Created by vlad on 31.10.2014.
 */
public class Token_f extends Token{
    private float       real;
    private Complex     comp;
    private byte        type = 0;
    private byte        stype = 0;
    private byte        priority = 0;
    private boolean     radians = true;




    public Token_f (String some, int num_system, boolean radians){
        throw new IllegalArgumentException("Technical Error #11");
    }

    @Override
    public String toString (){
        throw new IllegalArgumentException("Technical Error #10");
    }

    Token_f(float _real)
    {
        real = _real;
        type = Const.REAL;
        stype = Const.NUM;
    }

    Token_f (Complex64 _comp)
    {
        comp = _comp;
        type = Const.COMPLEX;
        stype = Const.NUM;

    }


    @Override
    public Complex getc ()
    {
        return comp;
    }

    @Override
    public float getf ()
    {
        return real;
    }

    @Override
    public double getd ()
    {
        return real;
    }

    @Override
    public boolean Is_float (){
        return true;
    }


    @Override
    public byte Type (){
        return type;
    }

    @Override
    public byte Stype (){
        return stype;
    }

    @Override
    public byte Priority (){
        return priority;
    }


    @Override
    public void set (float in)
    {
        real = in;
    }

    @Override
    public void set (double in)
    {
        real = (float)in;
    }

    @Override
    public  void set (Complex in)
    {
        comp = in;
    }



    @Override
    public Token tofloat ()
    {
        return this;

    }


    public void Set_radians (boolean radians){this.radians = radians;}
    public boolean Is_radians ()
    {
        return radians;
    }

}
package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 31.10.2014.
 */
public class Token_f extends Token{
    private float      real;
    private Complex     comp;
    private boolean     is_float;
    private byte        type = 0;
    private byte        stype = 0;
    private byte        priority = 0;




    public Token_f (String some){
        char in0 = some.charAt(0);
        if (in0 == '$'||some.equals("Error"))
            throw new IllegalArgumentException("create elem-eq " + some);
        else
        if ('0' <= in0&&in0 <= '9') {

            type = Const.REAL;
            is_float = true;

            real = Float.valueOf(some);
            if (((Float)real).isNaN())
                throw new IllegalArgumentException("create elem-eq real is NaN");

        }else
        if(some.equals("³√"))
            type = Const.CBRT;
        else
        if (some.equals(">="))
            type = Const.MORE_OR_EQUAL;
        else
        if (some.equals("<="))
            type = Const.LESS_OR_EQUAL;
        else
        if (some.length()>1)
            type = Function_parser.Funcname_to_Funcint(some);
        else
            switch (in0){
                case 'π': type = Const.REAL; real = (float)Math.PI; is_float = true; break;
                case 'e': type = Const.REAL; real = (float)Math.E; is_float = true; break;
                case 'i': type = Const.COMPLEX; comp = new Complex64(0,1);is_float = true;break;
                case 'X': type = Const.X;break;
                case 'Y': type = Const.Y;break;
                case 'Z': type = Const.Z;break;
                case 'C': type = Const.C;break;
                case 'T': type = Const.T;break;
                default:type = Function_parser.Funcname_to_Funcint(in0+"");
            }

        if(Const.NUM<type&&type<Const.END_NUM)
            stype = Const.NUM;
        else
        if(type== Const.ABSBR||type==Const.RBR||type==Const.LBR||type==Const.COMMA)
            stype = type;
        else
        if(Const.OPER<type&&type<Const.END_OPER)
        {
            stype = Const.OPER;
            if (type<Const.OPER_PRIOR1)
                priority = 2;
            else
            if (type<Const.OPER_PRIOR2)
                priority = 3;
            else priority = 4;
        }
        else
        if(Const.X<=type&&type<=Const.T){
            stype = Const.VAR;
        }
        else
        if(Const.COMPARE<type&&type<Const.END_COMPARE){
            stype = Const.COMPARE;
            priority=2;
        }
        else
        if(Const.FUNC<type&&type<Const.END_FUNC){
            stype = Const.FUNC;
            priority=5;
        }
        else
        if(Const.SET<type&&type<Const.END_SET) {
            stype = Const.SET;
            priority=1;
        }

    }

    public Token_f (byte _type){
        type = _type;

        if(Const.NUM<type&&type<Const.END_NUM)
            throw new IllegalArgumentException("create elem-eq by byte in is num");
        else
        if(type== Const.ABSBR||type==Const.RBR||type==Const.LBR||type==Const.COMMA)
            stype = type;
        else
        if(Const.OPER<type&&type<Const.END_OPER)
        {
            stype = Const.OPER;
            if (type<Const.OPER_PRIOR1)
                priority = 2;
            else
            if (type<Const.OPER_PRIOR2)
                priority = 3;
            else priority = 4;
        }
        else
        if(Const.X<=type&&type<=Const.T){
            stype = Const.VAR;
        }
        else
        if(Const.COMPARE<type&&type<Const.END_COMPARE){
            stype = Const.COMPARE;
            priority=5;
        }
        else
        if(Const.FUNC<type&&type<Const.END_FUNC){
            stype = Const.FUNC;
            priority=1;
        }
        else
        if(Const.SET<type&&type<Const.END_SET) {
            stype = Const.SET;
            priority=6;
        }

    }

    @Override
    public String toString (){
        String out;
        switch (type)
        {
            case Const.REAL:
                throw new IllegalArgumentException("toString in Token_f");
            case Const.LBR:     out = "(";break;
            case Const.RBR:     out = ")";break;
            case Const.ABSBR:   out = "|";break;
            case Const.X:       out = "X";break;
            case Const.Y:       out = "Y";break;
            case Const.Z:       out = "Z";break;
            case Const.C:       out = "C";break;
            case Const.T:       out = "T";break;
            case Const.PLUS:    out = "+";break;
            case Const.MINUS:   out = "-";break;
            case Const.DIV:     out = "÷";break;
            case Const.MULT:    out = "×";break;
            case Const.POW:     out = "^";break;
            case Const.ROOT:    out = "√";break;
            case Const.PRCNT:   out = "%";break;
            case Const.COMPLEX: out = comp.toString();break;
            case Const.EQUAL:   out = "=";break;
            case Const.MORE:    out = ">";break;
            case Const.LESS:    out = "<";break;
            case Const.AND:     out = "∩";break;
            case Const.OR:      out = "∪";break;
            case Const.XOR:     out = "∆";break;
            case Const.DIFF:    out = "\\";break;
            default: out = Function_parser.Funcint_to_Funcname(type);
        }
        return out+" ";
    }

    Token_f(float _real)
    {
        real = _real;
        type = Const.REAL;
        stype = Const.NUM;
        is_float = true;
    }

    Token_f (Complex64 _comp)
    {
        comp = _comp;
        type = Const.COMPLEX;
        stype = Const.NUM;

        is_float = true;
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
        return is_float;
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

}
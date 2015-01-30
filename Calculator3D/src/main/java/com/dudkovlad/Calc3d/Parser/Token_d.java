package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 31.10.2014.
 */
public class Token_d extends Token{
    private double      real;
    private Complex     comp;
    private byte        type = 0;
    private byte        stype = 0;
    private byte        priority = -1;
    private boolean     radians = true;
    private int         num_system = 10;



    public Token_d (String some, int num_system, boolean radians){

        this.num_system = num_system;// todo make num_system works
        char in0 = some.charAt(0);
        if (in0 == '$'||some.equals("Error"))
            throw new IllegalArgumentException("Technical Error #14");
        else
        if ('0' <= in0&&in0 <= '9') {

            type = Const.REAL;

            real = Double.valueOf(some);
            if (((Double)real).isNaN())
                throw new IllegalArgumentException("Illegal number format");

        }
        else
        if (some.length()>1) {
            for (int i = 0; i < 34; i++)
                if (DelegatesList.array_of_functions[i].equals(some))
                    Func = DelegatesList.array_of_functions[i];

            stype = Const.FUNC;
            priority = 5;
        }
        else
            switch (in0){
                case '!':
                case '√':
                    for (int i = 0; i < 34; i++)
                        if (DelegatesList.array_of_functions[i].equals(some))
                            Func = DelegatesList.array_of_functions[i];

                    stype = Const.FUNC;
                    priority = 5;
                    break;
                case '+':
                case '-':
                    for (int i = 0; i < 34; i++)
                        if (DelegatesList.array_of_functions[i].equals(some))
                            Func = DelegatesList.array_of_functions[i];
                    stype = Const.FUNC;
                    priority = 2;
                    break;
                case ':':
                case '/':
                case '÷':
                case '*':
                case '×':
                case '%':
                    for (int i = 0; i < 34; i++)
                        if (DelegatesList.array_of_functions[i].equals(some))
                            Func = DelegatesList.array_of_functions[i];
                    stype = Const.FUNC;
                    priority = 3;
                    break;
                case '^':
                    for (int i = 0; i < 34; i++)
                        if (DelegatesList.array_of_functions[i].equals(some))
                            Func = DelegatesList.array_of_functions[i];
                    stype = Const.FUNC;
                    priority = 4;
                    break;

                case '=':
                case '>':
                case '<':
                    for (int i = 0; i < 34; i++)
                        if (DelegatesList.array_of_functions[i].equals(some))
                            Func = DelegatesList.array_of_functions[i];
                    stype = Const.FUNC;
                    priority=1;
                    break;
                case '∩':
                case '∪':
                case '∆':
                case '\\':
                    for (int i = 0; i < 34; i++)
                        if (DelegatesList.array_of_functions[i].equals(some))
                            Func = DelegatesList.array_of_functions[i];
                    stype = Const.FUNC;
                    priority=0;
                    break;
                case 'π': type = Const.REAL; real = Math.PI; break;
                case 'e': type = Const.REAL; real = Math.E;break;
                case 'i': type = Const.COMPLEX; comp = new Complex128(0,1);break;
                case ';':
                case '|': type = Const.ABSBR;break;
                case ',': type = Const.COMMA;break;
                case '(': type = Const.LBR;break;
                case ')': type = Const.RBR;break;
                case '.': throw new IllegalArgumentException("too many points");
                case 'X': type = Const.X;break;
                case 'Y': type = Const.Y;break;
                case 'Z': type = Const.Z;break;
                case 'C': type = Const.C;break;
                case 'T': type = Const.T;break;
                default:throw new IllegalArgumentException("Illegal character in expression");
            }

        if(Const.NUM<type&&type<Const.END_NUM)
            stype = Const.NUM;
        else
        if(type== Const.ABSBR||type==Const.RBR||type==Const.LBR||type==Const.COMMA)
            stype = type;
        else
        if(Const.X<=type&&type<=Const.T){
            stype = Const.VAR;
        }else type = stype;
        this.radians = radians;
    }

    public Token_d (byte _type)
    {
        type = _type;

        if(type== Const.ABSBR||type==Const.RBR||type==Const.LBR||type==Const.COMMA)
            stype = type;
        else
        if(Const.X<=type&&type<=Const.T){
            stype = Const.VAR;
        }
        else
            throw new IllegalArgumentException("Technical Error #12");

    }

    @Override
    public String toString ()
    {
        String out;
        switch (type)
        {
            case Const.REAL:    out = MyFunc.Double_to_String(real);break;//todo make num_system
            case Const.LBR:     out = "(";break;
            case Const.RBR:     out = ")";break;
            case Const.ABSBR:   out = "|";break;
            case Const.X:       out = "X";break;
            case Const.Y:       out = "Y";break;
            case Const.Z:       out = "Z";break;
            case Const.C:       out = "C";break;
            case Const.T:       out = "T";break;
            case Const.COMPLEX: out = comp.toString();break;//todo make num_system
            case Const.FUNC:    out = Func.toString();break;
            default: throw new IllegalArgumentException("Technical Error #13");
        }
        return out+" ";
    }

    Token_d(double _real)
    {
        real = _real;
        type = Const.REAL;
        stype = Const.NUM;
    }

    Token_d (Complex128 _comp)
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
        return (float)real;
    }

    @Override
    public double getd ()
    {
        return real;
    }

    @Override
    public boolean Is_float (){
        return false;
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
        type = Const.REAL;
    }

    @Override
    public void set (double in)
    {
        real = in;
        type = Const.REAL;
    }

    @Override
    public  void set (Complex in)
    {
        comp = in;
        type = Const.COMPLEX;
    }


    @Override
    public Token tofloat ()
    {
        if (type == Const.REAL)
            return new Token_f((float)real);
        else
        if (type == Const.COMPLEX)
            return new Token_f(comp.toComplex64());
        else
            return this;

    }

    public void Set_radians (boolean radians){this.radians = radians;}
    public boolean Is_radians ()
    {
        return radians;
    }

}
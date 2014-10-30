package com.dudkovlad.Calc3d.Parser;


/**
 * Created by vlad on 21.05.2014.
 */
public class Element_of_equation <T extends Number, E extends Complex>{//todo make generics
    E           comp;
    T           real;
    boolean     is_float;

    char        var;

    byte        type;
    byte        stype = 0;
    byte        priority;





    Element_of_equation(T _real)
    {
        real = _real;
        type = Const.REAL;
        stype = Const.NUM;
        is_float = real instanceof Float;
    }

    Element_of_equation (E _comp)
    {
        comp = _comp;
        type = Const.COMPLEX;
        stype = Const.NUM;

        is_float = comp instanceof Complex64;
    }

    @SuppressWarnings("unchecked")
    Element_of_equation (String some)
    {
        char in0 = some.charAt(0);
        if (in0 == '$'||some.equals("Error"))
            throw new IllegalArgumentException("create elem-eq " + some);
        else
            if ('0' <= in0&&in0 <= '9') {

                type = Const.REAL;
                is_float = false;

                real = (T)Double.valueOf(some);
                if (((Double)real).isNaN())
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
            if (some.length()==1&&'A'<=in0&&in0<='Z')
                {type = Const.VAR; var = in0;}
        else
            switch (in0){                                                  //todo move to functionparser funcnametofuncint
                case 'π': type = Const.REAL; real = (T)(Double)Math.PI; break;
                case 'e': type = Const.REAL; real = (T)(Double)Math.E;break;
                case 'i': type = Const.COMPLEX; comp = (E)(new Complex128(0,1));is_float = false;break;

                default:type = Function_parser.Funcname_to_Funcint(in0+"");
            }

        if(Const.NUM<type&&type<Const.END_NUM)
            stype = Const.NUM;
        else
        if(type == Const.VAR||type== Const.ABSBR||type==Const.RBR||type==Const.LBR||type==Const.COMMA)
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



    public static Element_of_equation String_to_Element (String some)
    {
        return new Element_of_equation(some);
    }


    Element_of_equation (byte _type)
    {
        type = _type;

        if(Const.NUM<type&&type<Const.END_NUM)
            throw new IllegalArgumentException("create elem-eq by byte in is num");
        else
        if(type == Const.VAR||type== Const.ABSBR||type==Const.RBR||type==Const.LBR||type==Const.COMMA)
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

    Element_of_equation (Element_of_equation in)
    {
        type = in.type;
        is_float = in.is_float;
        stype = in.stype;
        priority = in.priority;
        switch (type)
        {
            case Const.COMPLEX: comp    = (E)in.comp;break;
            case Const.REAL:    real    = (T)in.real;break;
            case Const.VAR:     var     = in.var;break;
        }
    }




    public String toString ()
    {
        String out;
        switch (type)
        {
            case Const.REAL:    out = MyFunc.Double_to_String((Double)real);break;
            case Const.LBR:     out = "(";break;
            case Const.RBR:     out = ")";break;
            case Const.ABSBR:   out = "|";break;
            case Const.VAR:     out = var+"";break;
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





}


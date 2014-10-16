package com.dudkovlad.Calc3d.Parser;


import com.dudkovlad.Calc3d.MainActivity;

import java.lang.reflect.Type;

/**
 * Created by vlad on 21.05.2014.
 */
public class Element_of_equation {
    Complex     comp;
    Complex64   compf;
    double      real;
    float       realf;
    boolean     is_float;

    char        var;

    byte        type;
    byte        stype = 0;
    byte        priority;





    Element_of_equation(double _real)
    {
        real = _real;
        type = Const.REAL;
        stype = Const.NUM;
        is_float = false;
    }

    Element_of_equation (Complex _comp)
    {
        comp = _comp;
        type = Const.COMPLEX;
        stype = Const.NUM;
        is_float = false;
    }

    Element_of_equation(float _real)
    {
        realf = _real;
        type = Const.REAL;
        stype = Const.NUM;
        is_float = true;
    }

    Element_of_equation (Complex64 _comp)
    {
        compf = _comp;
        type = Const.COMPLEX;
        stype = Const.NUM;
        is_float = true;
    }

    Element_of_equation (String some)
    {
        char in0 = some.charAt(0);
        if (in0 == '$') {
            type = Const.ERROR;
            MainActivity.data_del.debugview.setText("Error: " + some + " ");
            return;
        }else
            if (some.equals("Error")) {
                type = Const.ERROR;
                return;
            }
        else
            if ('0' <= in0&&in0 <= '9') {

                type = Const.REAL;

                real = Double.valueOf(some);
                if (((Double)real).isNaN()) type = Const.ERROR;
                is_float = false;
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
            switch (in0){
                case 'π': type = Const.REAL; real = Math.PI; break;
                case 'e': type = Const.REAL; real = Math.E;break;
                case 'i': type = Const.COMPLEX; comp = new Complex(0,1);is_float = false;break;
                case '(': type = Const.LPAR;break;
                case ')': type = Const.RPAR;break;
                case '|': type = Const.VLINE;break;
                case '!': type = Function_parser.Funcname_to_Funcint("!"); break;
                case '+': type = Const.PLUS;break;
                case '-': type = Const.MINUS;break;
                case '÷': type = Const.DIV;break;
                case '×': type = Const.MULT;break;
                case '^': type = Const.POW;break;
                case '√': type = Const.SQRT;break;
                case '%': type = Const.PRCNT;break;
                case '[': type = Const.LBR;break;
                case ']': type = Const.RBR;break;
                case '=': type = Const.EQUAL;break;
                case '>': type = Const.MORE;break;
                case '<': type = Const.LESS;break;
                case '.': type = Const.ERROR;break;
                case '∩': type = Const.AND;break;
                case '∪': type = Const.OR;break;
                case '∆': type = Const.XOR;break;
                case '\\': type = Const.DIFF;break;
            }

        if(Const.NUM<type&&type<Const.NUM)
            stype = Const.NUM;
        else
            if(Const.OPER<type&&type<Const.END_OPER)
            {
                stype = Const.OPER;
                if (type<Const.OPER_PRIOR1)
                    priority = 0;
                else
                if (type<Const.OPER_PRIOR2)
                    priority = 1;
                else priority = 2;
            }
        else
            if(Const.RBRACK<type&&type<Const.END_RBRACK)
                stype = Const.RBRACK;
        else
            if(Const.LBRACK<type&&type<Const.END_LBRACK)
                stype = Const.LBRACK;
        else
            if(Const.COMPARE<type&&type<Const.END_COMPARE)
                stype = Const.COMPARE;
        else
            if(Const.FUNC<type&&type<Const.END_FUNC)
                stype = Const.FUNC;
        else
            if(Const.FUNCRE<type&&type<Const.END_FUNCRE)
                stype = Const.FUNCRE;
        else
            if(Const.SET<type&&type<Const.END_SET)
                stype = Const.SET;
    }



    public static Element_of_equation String_to_Element (String some)
    {
        return new Element_of_equation(some);
    }


    Element_of_equation (byte _type)
    {
        type = _type;
        if(Const.NUM<type&&type<Const.END_NUM)
            stype = Const.NUM;
        else
        if(Const.OPER<type&&type<Const.END_OPER)
        {
            stype = Const.OPER;
            if (type<Const.OPER_PRIOR1)
                priority = 0;
            else
            if (type<Const.OPER_PRIOR2)
                priority = 1;
            else priority = 2;
        }
        else
        if(Const.RBRACK<type&&type<Const.END_RBRACK)
            stype = Const.RBRACK;
        else
        if(Const.LBRACK<type&&type<Const.END_LBRACK)
            stype = Const.LBRACK;
        else
        if(Const.COMPARE<type&&type<Const.END_COMPARE)
            stype = Const.COMPARE;
        else
        if(Const.FUNC<type&&type<Const.END_FUNC)
            stype = Const.FUNC;
        else
        if(Const.FUNCRE<type&&type<Const.END_FUNCRE)
            stype = Const.FUNCRE;
        else
        if(Const.SET<type&&type<Const.END_SET)
            stype = Const.SET;


        if (stype == Const.NUM) type = Const.ERROR;

    }

    Element_of_equation (Element_of_equation in)
    {
        type = in.type;
        is_float = in.is_float;
        stype = in.stype;
        priority = in.priority;
        if(!in.is_float)
            switch (type)
            {
                case Const.COMPLEX: comp    = in.comp;break;
                case Const.REAL:    real    = in.real;break;
                case Const.VAR:     var     = in.var;break;
            }
        else
            switch (type)
            {
                case Const.COMPLEX: compf    = in.compf;break;
                case Const.REAL:    realf    = in.realf;break;
                case Const.VAR:     var     = in.var;break;
            }
    }




    public String toString ()
    {
        String out;
        if ((out = Function_parser.Funcint_to_Funcname(type)).equals( "Error"))
            switch (type)
            {
                case Const.REAL:    out = MyFunc.Double_to_String(real);break;
                case Const.LPAR:    out = "(";break;
                case Const.RPAR:    out = ")";break;
                case Const.VLINE:   out = "|";break;
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
            }
        return out;
    }



}


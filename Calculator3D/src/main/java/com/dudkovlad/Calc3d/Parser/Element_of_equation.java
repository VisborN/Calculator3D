package com.dudkovlad.Calc3d.Parser;


import com.dudkovlad.Calc3d.MainActivity;

import java.lang.reflect.Type;

/**
 * Created by vlad on 21.05.2014.
 */
public class Element_of_equation {
    Complex comp;
    short  func;
    double  real;

    short   type;
    char    var;
    short   stype;





    Element_of_equation(double _real)
    {
        real = _real;
        type = Const.REAL;
    }

    Element_of_equation (Complex _comp)
    {
        comp = _comp;
        type = Const.COMPLEX;
    }

    Element_of_equation (String some)
    {
        char in0 = some.charAt(0);
        if (in0 == '$') {
            type = Const.ERROR;
            MainActivity.data_del.debugview.setText("Error: " + some + " ");
        }else
            if (some.equals("Error")) type = Const.ERROR;
        else
            if ('0' <= in0&&in0 <= '9') {

                type = Const.REAL;

                real = Double.valueOf(some);
                if (((Double)real).isNaN()) type = Const.ERROR;
        }else
            if (some.equals(">="))
                {type = Const.COMPARE; stype = Const.MORE_OR_EQUAL;}
        else
            if (some.equals("<="))
                {type = Const.COMPARE; stype = Const.LESS_OR_EQUAL;}
        else
            if (some.length()>1)
                {type = Const.FUNC; func = Function_parser.Funcname_to_Funcint(some);}
        else
            if (some.length()==1&&'A'<=in0&&in0<='Z')
                {type = Const.VAR; var = in0;}
        else
            switch (in0){
                case 'π': type = Const.REAL; real = Math.PI; break;
                case 'e': type = Const.REAL; real = Math.E;break;
                case 'i': type = Const.COMPLEX; comp = new Complex(0,1);break;
                case '(': type = Const.LPAR;break;
                case ')': type = Const.RPAR;break;
                case '|': type = Const.VLINE;break;
                case '!': type = Const.FUNCRE;func = Function_parser.Funcname_to_Funcint("!"); break;
                case '+': type = Const.PLUS;break;
                case '-': type = Const.MINUS;break;
                case '÷': type = Const.DIV;break;
                case '×': type = Const.MULT;break;
                case '^': type = Const.POW;break;
                case '√': type = Const.ROOT;break;
                case '%': type = Const.PRCNT;break;
                case '[': type = Const.LBR;break;
                case ']': type = Const.RBR;break;
                case '=': type = Const.COMPARE; stype = Const.EQUAL;break;
                case '>': type = Const.COMPARE; stype = Const.MORE;break;
                case '<': type = Const.COMPARE; stype = Const.LESS;break;
                case '.': type = Const.ERROR;break;
                case '∩': type = Const.SET; stype = Const.AND;break;
                case '∪': type = Const.SET; stype = Const.OR;break;
                case '∆': type = Const.SET; stype = Const.XOR;break;
                case '\\': type = Const.SET; stype = Const.DIFF;break;


            }
    }



    public static Element_of_equation String_to_Element (String some)
    {
        return new Element_of_equation(some);
    }


    Element_of_equation (short _type)
    {
        type = _type;
        if (type >=Const.FUNC&& type <= Const.COMPLEX) type = Const.ERROR;

    }

    Element_of_equation (Element_of_equation in)
    {
        type = in.type;
        switch (type)
        {
            case Const.COMPLEX: comp    = in.comp;break;
            case Const.REAL:    real    = in.real;break;
            case Const.FUNC:    func    = in.func;break;
            case Const.FUNCRE:  func    = in.func;break;
            case Const.VAR:     var     = in.var;break;
            case Const.COMPARE:
            case Const.SET:     stype   = in.stype;break;
        }
    }

    public static String Element_to_String (Element_of_equation in)
    {
        String out = "Error";
        switch (in.type)
        {
            case Const.REAL:    out = MyFunc.Double_to_String(in.real);break;
            case Const.FUNC:    out = Function_parser.Funcint_to_Funcname(in.func);break;
            case Const.FUNCRE:  out = Function_parser.Funcint_to_Funcname(in.func);break;
            case Const.LPAR:    out = "(";break;
            case Const.RPAR:    out = ")";break;
            case Const.VLINE:   out = "|";break;
            case Const.VAR:     out = in.var+"";break;
            case Const.PLUS:    out = "+";break;
            case Const.MINUS:   out = "-";break;
            case Const.DIV:     out = "÷";break;
            case Const.MULT:    out = "×";break;
            case Const.POW:     out = "^";break;
            case Const.ROOT:    out = "√";break;
            case Const.PRCNT:   out = "%";break;
            case Const.COMPLEX: out = in.comp.toString();break;
            case Const.COMPARE:
                switch (in.stype)
                {
                    case Const.EQUAL: out = "=";break;
                    case Const.MORE: out = ">";break;
                    case Const.LESS: out = "<";break;
                    case Const.AND: out = "∩";break;
                    case Const.OR: out = "∪";break;
                    case Const.XOR: out = "∆";break;
                    case Const.DIFF: out = "\\";break;
                }
        }
        return out;
    }

    public static Element_of_equation[] String_to_Elementarr (String in)
    {
        if (in.isEmpty()) return new Element_of_equation[]{new Element_of_equation("$32")};
        String temp_s = in.charAt(0)+"";
        char temp_c=0, temp_cm=0, temp_cp=0;
        int in_length  = in.length();
        Element_of_equation [] out = new Element_of_equation[in_length];
        int j = -1;
        int type=0;
        int num=0;
        int num2=0;
        if (in_length == 1) {
            out[0] = String_to_Element(temp_s);
            j = 0;
        }
        else {
            for (int i = 1; i < in_length; i++) {

                if (i < in_length-1)
                    temp_cp = in.charAt(i + 1);
                else temp_cp = 0;
                temp_cm = in.charAt(i - 1);
                temp_c = in.charAt(i);
                if (type == 0)
                {
                    temp_s = temp_cm+"";
                    if(temp_cm=='@'|| '0'<=temp_cm && temp_cm <= '9' )
                        type = 1;                                                                       //type = 1 is num
                    else if ( 'A' <= temp_cm && temp_cm <= 'Z' )                                        //type = 2 is func
                        type = 2;                                                                       //type = 3 is operator with more then one signs
                    else if (temp_cm=='<' || temp_cm == '>')                                            //type = 0 is operator
                        type = 3;
                    else type = 0;
                }
                if (type==1&&(('0'<=temp_c&&temp_c<='9')||
                        (temp_c=='E'&&(temp_cp=='-'||'0'<=temp_cp&&temp_cp<='9'||temp_cp=='@'))||
                        (temp_cm=='@'&&'A'<=temp_c&&temp_c<='Z'||temp_c=='@')||
                        (temp_c=='.'&&(temp_cp=='@'||('0'<=temp_cp&&temp_cp<='9'))))) {
                    if (temp_c == '.') {
                        num++;
                        if (num > 1)
                            return new Element_of_equation[]{new Element_of_equation("$too many dots  ")};
                    }
                    if (temp_c=='E'&& temp_cm != '@'){
                        num2++;
                        if (num2 > 1)
                            return new Element_of_equation[]{new Element_of_equation("$too many E  ")};

                    }
                    temp_s = temp_s + temp_c;
                    if(temp_cp==0) {
                        j++;
                        out[j] = String_to_Element(temp_s);
                        if (out[j].type == Const.ERROR) return new Element_of_equation[]{out[j]};
                    }
                }
                else if (type == 3&&temp_c=='='&&(temp_cm == '<'||temp_cm == '>'))
                {
                    temp_s = temp_s + temp_c;
                }
                else if (type == 2&&( 'a' <= temp_c && temp_c <= 'z' || 'A' <= temp_c && temp_c <= 'Z'
                                    || '0' <= temp_c && temp_c <= '9' || temp_c == '.')) {
                    temp_s = temp_s + temp_c;
                    if(temp_cp==0) {
                        j++;
                        out[j] = String_to_Element(temp_s);
                        if (out[j].type == Const.ERROR) return new Element_of_equation[]{out[j]};
                    }
                }
                else {
                    j++;
                    out[j] = String_to_Element(temp_s);
                    if (out[j].type==Const.ERROR)return new Element_of_equation[]{out[j]};
                    type = 0;
                    num = 0;
                    if(temp_cp==0) {
                        j++;
                        out[j] = String_to_Element(temp_c+"");
                        if (out[j].type == Const.ERROR) return new Element_of_equation[]{out[j]};
                    }
                }
            }
        }




        out = MyFunc.Take_part(out,0,j);

        return out;
    }


    public static String Elementarr_to_String (Element_of_equation [] arrin)
    {
        String out = "";
        for (int i = 0; i < arrin.length; i++)
        {
            if (arrin[i].type==Const.ERROR)
            {
                return "Error";
            }
            out = out + Element_to_String(arrin[i]);
        }
        return out;
    }

}


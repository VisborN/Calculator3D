package com.dudkovlad.Calc3d.Parser;

import java.util.ArrayDeque;

/**
 * Created by vlad on 16.10.2014.
 */
public class Equation {

    private Element_of_equation [] equation;
    private short [] index;
    private short length;

    public Equation (String in)
    {

        if (in.isEmpty()) equation = new Element_of_equation[]{new Element_of_equation("$32")};
        String temp_s = in.charAt(0)+"";
        char temp_c, temp_cm, temp_cp;
        int in_length  = in.length();
        equation = new Element_of_equation[in_length];
        int j = -1;
        int type=0;
        int num=0;
        int num2=0;
        if (in_length == 1) {
            equation[0] = Element_of_equation.String_to_Element(temp_s);
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
                    else if (temp_cm=='<' || temp_cm == '>' || temp_cm == '³')                                            //type = 0 is operator
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
                            equation = new Element_of_equation[]{new Element_of_equation("$too many dots  ")};
                    }
                    if (temp_c=='E'&& temp_cm != '@'){
                        num2++;
                        if (num2 > 1)
                            equation = new Element_of_equation[]{new Element_of_equation("$too many E  ")};

                    }
                    temp_s = temp_s + temp_c;
                    if(temp_cp==0) {
                        j++;
                        equation[j] = Element_of_equation.String_to_Element(temp_s);
                        if (equation[j].type == Const.ERROR) equation = new Element_of_equation[]{equation[j]};
                    }
                }
                else if (type == 3&&((temp_c=='='&&(temp_cm == '<'||temp_cm == '>'))||(temp_c == '√'&&temp_cm == '³')))
                {
                    temp_s = temp_s + temp_c;
                }
                else if (type == 2&&( 'a' <= temp_c && temp_c <= 'z' || 'A' <= temp_c && temp_c <= 'Z'
                        || '0' <= temp_c && temp_c <= '9' || temp_c == '.')) {
                    temp_s = temp_s + temp_c;
                    if(temp_cp==0) {
                        j++;
                        equation[j] = Element_of_equation.String_to_Element(temp_s);
                        if (equation[j].type == Const.ERROR) equation = new Element_of_equation[]{equation[j]};
                    }
                }
                else {
                    j++;
                    equation[j] = Element_of_equation.String_to_Element(temp_s);
                    if (equation[j].type==Const.ERROR) equation = new Element_of_equation[]{equation[j]};
                    type = 0;
                    num = 0;
                    if(temp_cp==0) {
                        j++;
                        equation[j] = Element_of_equation.String_to_Element(temp_c+"");
                        if (equation[j].type == Const.ERROR) equation = new Element_of_equation[]{equation[j]};
                    }
                }
            }
        }




        equation = MyFunc.Take_part(equation,0,j);
        index  = new short [equation.length];
        length = (short)equation.length;
        for (int i = 0; i < length; i++)
            index [i] = (short)i;

    }

    public void Add (Element_of_equation in, int index_, boolean after_else_before)
    {
        if (after_else_before)
        {
            MyFunc.Cut_put(equation, new Element_of_equation[]{equation[index_], in}, index_, index_);
        }else
        {
            MyFunc.Cut_put(equation, new Element_of_equation[]{in, equation[index_]}, index_, index_);
        }
    }


    public void Delete ( int index_)
    {
        length--;
        for(int i=index_;i<length;i++)
            index[i]++;
    }

    public void Delete ( int index1, int index2)
    {
        index2 -= index1-1;//012345678  1 4  05678
        length-= index2;
        for(int i = index1; i<length;i++)
            index[i]+= index2;

    }


    public void Replace (Element_of_equation in, int index_)
    {
        equation [index[index_]] = in;
    }

    public void Replace (Element_of_equation in, int index1, int index2)
    {
        equation [index[index1]] = in;
        index2 -= index1;
        length -= index2;
        for (int i = index1+1; i < length; i++)
        {
            index[i] += index2;//123456789  12  56789
        }

    }



    public Element_of_equation Get (int index_)
    {
        return equation[index[index_]];
    }

    public int Length()
    {
        return length;
    }


    public String toString ()
    {
        String out = "";
        for (int i = 0; i < length; i++)
        {
            if (equation[index[i]].type==Const.ERROR)
            {
                return "Error";
            }
            out = out + equation[index[i]].toString();
        }
        return out;
    }

}

package com.dudkovlad.Calc3d.Parser;


import android.media.Image;

import com.dudkovlad.Calc3d.MainActivity;

/**
 * Created by vlad on 04.05.2014.
 */
public class Parser {


    String equation_string;
    Equation equation;
    Element_of_equation[][] equations;
    int[] signs;
    int num_of_variables;

    public Parser ()
    {

        INIT("Error");
    }

    public Parser (String equation)
    {
        INIT(equation);
    }

    public String Result (String equation_)
    {
        INIT(equation_);
        return Result();
    }

    private void INIT (String equation_)
    {
        equation_string = equation_;
        equation = new Equation(equation_string);
    }

    public String Result ()
    {
        long start = System.nanoTime();
        Polynomial (equation,0,equation.Length());
        long end = System.nanoTime();
        MainActivity.data_del.debugview.setText(MainActivity.data_del.debugview.getText().toString() + "runtime: "+(end-start)+" ns or "+((end-start)/1000000)+" ms   " + 33333333/(end-start) + " iterations per frame");



        if (equation.Get(0).type == Const.LBR) {
            equation.Delete(0);
            equation.Delete(equation.Length()-1);
        }

        for (int i = 0; i < equation.Length(); i++)
            if (equation.Get(i).type == Const.LBR)
                equation.Get(i).type = Const.LPAR;
            else if (equation.Get(i).type == Const.RBR)
                equation.Get(i).type = Const.RPAR;

        return equation.toString();
    }


/*
    public Element_of_equation [] forLoop (double v1, double v2, double v3)
    {
        Element_of_equation[] equation_optim = equation_optim_;

        for (int i = 0; i < equation_optim.length; i++)
        {
            switch (equation_optim[i].type) {
                case Const.X: equation_optim[i].real = X;break;
                case Const.Y: equation_optim[i].real = Y;break;
                case Const.Z: equation_optim[i].real = Z;break;
                case Const.ERROR: new Element_of_equation("$1");
            }
        }




        return Polynomial (equation_optim);
    }
*/

    private void Polynomial (Equation arr, int begin, int end)
    {



        Brackets_inarr(arr, begin, end);
        Vlines_inarr(arr, begin, end);
        Func_and_re_inarr(arr, begin, end);
        OPERATORS_inarr(arr, begin, end, 1 );
        OPERATORS_inarr(arr, begin, end, 2 );
        OPERATORS_inarr(arr, begin, end, 3);

        if (arr.Length() != 1) {
            arr.Add(new Element_of_equation(Const.LBR),0,false);
            arr.Add(new Element_of_equation(Const.RBR),arr.Length()-1, true);
        }
    }

    //-------------------------------------------------------------------------------------------------------------




    private void Brackets_inarr (Equation arr, int begin, int end)
    {
        int b1 = 0;
        int b2 = 0;
        for (short i = 0; i < arr.Length(); i++) {
            switch (arr.Get(i).type) {
                case Const.LPAR:
                    b1++;
                    break;
                case Const.RPAR:
                    b2++;
                    break;
            }
        }

        if (b1!=b2) {
            if (b1 > b2){
                for(int i = b1-b2; i > 0; i--)
                    arr.Add(new Element_of_equation(Const.RPAR),arr.Length()-1,true);
            }else if (b2 > b1){
                for(int i = b2-b1; i > 0; i--)
                    arr.Add(new Element_of_equation(Const.LPAR),0,false);
                b1=b2;
            }
            else{
                arr.Replace(new Element_of_equation("$2"),0);
                return;
            }
        }
        if (b1 !=0)
            for (int j = 0, i = -1; j < arr.Length()&& b1 != 0; j++) {
                switch (arr.Get(j).type) {
                    case Const.LPAR:
                        i = j;
                        break;
                    case Const.RPAR:
                        if (i == -1){
                            arr.Replace(new Element_of_equation("$3"),0);
                            return;
                        }
                        else {
                            if (i + 1 == j&&i > 0) {
                                arr.Delete(i,j);
                                break;
                            } else
                            if (i + 1 == j&&j < arr.Length()-1) {
                                arr.Delete(i,j);
                                break;
                            } else
                            if (i + 1 == j) {
                                arr.Replace(new Element_of_equation("$31"),0);
                                return;
                            } else {
                                Polynomial(arr, i + 1, j - 1);
                                arr.Delete(i);
                                arr.Delete(j);
                            }

                            if (arr.Get(i).type == Const.ERROR){
                                arr.Replace(new Element_of_equation("$4"),0);
                                return;
                            }
                            i = -1;
                            j = -1;
                            b1--;
                            break;
                        }
                }
            }

        for (int i = 0; i < arr.Length(); i++)
            if (arr.Get(i).type==Const.RPAR||arr.Get(i).type==Const.LPAR)
                b1++;
        if (b1 > 0)  arr.Replace(new Element_of_equation("$5"),0);


    }

    //-------------------------------------------------------------------------------------------------------------

    private void Vlines_inarr (Equation arr, int begin, int end) {
        int bm  = 0;
        int bml = 0;
        int b = 0;
/*

        for (int i = 0; i < arr.Length(); i++) {
            switch (arr.Get(i).type) {
                case Const.VLINE:
                    if (bml==0)
                        bm++;
                    break;
                case Const.LBR:
                case Const.LPAR:
                    bml++;break;
                case Const.RBR:
                case Const.RPAR:
                    bml--;break;
            }
        }



        if (bm % 2 != 0) arr = MyFunc.Cut_put(arr, new Element_of_equation[]{arr.Get(arr.Length() - 1), new Element_of_equation(Const.VLINE)}, arr.Length() - 1, arr.Length() - 1);расширение;
*/



        bml = -1;

        for (int i = 0, j = bm / 2; i < arr.Length() && j > 0; i++)
            if (b == 0&&arr.Get(i).type == Const.VLINE) {
                if (bml == -1 && i < arr.Length() - 1) {
                    if (!((arr.Get(i + 1).stype == Const.OPER&& arr.Get(i + 1).type != Const.PLUS&& arr.Get(i + 1).type != Const.MINUS) ||
                            arr.Get(i + 1).stype == Const.RBRACK  ||
                            arr.Get(i+1).stype == Const.FUNCRE)) {
                        bml = i;
                    } else  {
                        arr.Replace(new Element_of_equation("$7"),0);
                        return;
                    }
                } else if (i > 0 && !(arr.Get(i - 1).stype == Const.OPER
                        ||  arr.Get(i - 1).stype == Const.LBRACK || arr.Get(i-1).stype == Const.FUNC)) {
                    if (bml == i - 1) {
                        if (bml > 0)
                            arr.Replace(arr.Get(bml-1),bml-1, i);
                        else if (i + 1 < arr.Length())
                            arr.Replace(arr.Get(i+1),bml,i);
                        else {
                            arr.Replace(new Element_of_equation("$8"),0);
                            return;
                        }
                    }
                    arr.Replace(new Element_of_equation(Const.ABS), bml);
                    Polynomial(arr, bml + 1, i - 1);
                    arr.Delete(i);
                    j--;
                    bml = -1;
                    i = -1;


                } else if (i < arr.Length() - 1 && !
                        ((arr.Get(i + 1).stype == Const.OPER&& arr.Get(i + 1).type != Const.PLUS&& arr.Get(i + 1).type != Const.MINUS) ||
                        arr.Get(i + 1).stype == Const.RBRACK ||
                        arr.Get(i+1).stype == Const.FUNCRE)) {
                    bml = i;
                } else {
                    arr.Replace(new Element_of_equation("$9"),0);
                    return;
                }
            }
            else if (arr.Get(i).stype == Const.LBRACK) b++;
            else if (arr.Get(i).stype == Const.RBRACK) b--;


        bm = 0;
        for (int i = 0; i < arr.Length(); i++)
            if (arr.Get(i).type == Const.VLINE)
                bm++;
        if (bm > 0) arr.Replace(new Element_of_equation("$10"),0);//well done

    }
    //-------------------------------------------------------------------------------------------------------------

    private void Func_and_re_inarr (Equation arr, int begin, int end)
    {
        int bm = 0;
        for (int i = 0; i < arr.Length(); i++)
            if (bm==0&& arr.Get(i).stype == Const.FUNC) {
                if (i < arr.Length() - 1 && arr.Get(i + 1).stype == Const.NUM)
                    arr.Replace(Function_parser.Run_func(arr.Get(i).type, arr.Get(i+1)), i, i + 1);
                else if (i >= arr.Length() - 1 || arr.Get(i + 1).stype != Const.LBRACK &&
                        arr.Get(i).stype != Const.FUNC && arr.Get(i + 1).type != Const.VAR) {
                    arr.Replace(new Element_of_equation("$11"),0);
                    return;
                }
            } else if (bm==0&&arr.Get(i).stype == Const.FUNCRE ){
                if (i > 0 && arr.Get(i - 1).stype == Const.NUM) {
                    arr.Replace(Function_parser.Run_func(arr.Get(i).type, arr.Get(i - 1)), i - 1, i);
                    i--;
                }
                else if (i <= 0 || arr.Get(i - 1).stype != Const.LBRACK &&
                        arr.Get(i).stype !=Const.FUNCRE && arr.Get(i - 1).type != Const.VAR) {
                    arr.Replace(new Element_of_equation("$12"),0);
                    return;
                }
            }
            else if (arr.Get(i).stype == Const.LBRACK) bm++;
            else if (arr.Get(i).stype == Const.RBRACK) bm--;

    }

    //-------------------------------------------------------------------------------------------------------------

    private void OPERATORS_inarr (Equation arr, int begin, int end, int prioritet_level)
    {
        int bm = 0;
        int[] operator;
        switch (prioritet_level) {
            case 1:
                operator = new int[]{Const.PRCNT, Const.ROOT, Const.POW};
                break;
            case 2:
                for (int i = begin; i < end; i++)
                    if (   (arr.Get(i).type  == Const.VAR||
                            arr.Get(i).stype == Const.RBRACK ||
                            arr.Get(i).stype == Const.FUNCRE ||
                            arr.Get(i).stype == Const.NUM     )&&
                           (arr.Get(i + 1).type  == Const.VAR ||
                            arr.Get(i + 1).stype == Const.LBRACK ||
                            arr.Get(i + 1).stype == Const.FUNC ||
                            arr.Get(i + 1).stype == Const.NUM))
                        arr.Add(new Element_of_equation(Const.MULT),i,true);
                operator = new int[]{Const.MULT, Const.DIV};
                break;
            case 3:
                if (arr.Get(0).type == Const.MINUS || arr.Get(0).type == Const.PLUS)
                    arr.Add(new Element_of_equation((double) 0), 0, false);
                operator = new int[]{Const.PLUS, Const.MINUS};
                break;
            default:
                operator = new int[]{Const.ERROR};
                break;
        }
        for (int i = 1; i < arr.Length() - 1; i++)
            for (int j = 0; j < operator.length; j++)
                if (bm == 0 && arr.Get(i).type == operator[j]) {
                    if (arr.Get(i - 1).stype == Const.NUM && arr.Get(i + 1).stype == Const.NUM) {
                        arr.Replace(Function_parser.Run_func2(arr.Get(i).type, arr.Get(i - 1), arr.Get(i + 1)),i - 1,i + 1);
                        i--;
                    } else if ( arr.Get(i + 1).stype != Const.LBRACK &&
                                arr.Get(i + 1).stype != Const.FUNC &&
                                arr.Get(i - 1).stype != Const.LBRACK &&
                                arr.Get(i - 1).stype != Const.FUNCRE &&
                                arr.Get(i + 1).type  != Const.VAR &&
                                arr.Get(i - 1).type  != Const.VAR) {
                        arr.Replace(new Element_of_equation("$oper " + prioritet_level + " " + arr.Get(i + 1).type + " " + arr.Get(i - 1).type), 0);
                        return;
                    }

                }
                else if (arr.Get(i).stype == Const.LBRACK) bm++;
                else if (arr.Get(i).stype == Const.RBRACK) bm--;

        if (arr.Get(arr.Length()-1).stype == Const.OPER)
            arr.Replace(new Element_of_equation("$14"),0);
    }

}

package com.dudkovlad.Calc3d.Parser;


import android.media.Image;

/**
 * Created by vlad on 04.05.2014.
 */
public class Parser {


    String equation_;
    Element_of_equation[] equation_optim_;
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

    public String Result (String equation)
    {
        INIT(equation);
        return Result();
    }

    private void INIT (String equation)
    {
        equation_ = equation;
        equation_optim_ = Element_of_equation.String_to_Elementarr(equation_);
    }

    public String Result ()
    {
        equation_optim_ = Polynomial (equation_optim_);


        if (equation_optim_[0].type == Const.LBR)
            equation_optim_ = MyFunc.Cut_put(new Element_of_equation[1], MyFunc.Take_part(equation_optim_, 1, equation_optim_.length - 2), 0, 0);

        for (int i = 0; i < equation_optim_.length; i++)
            if (equation_optim_[i].type == Const.LBR)
                equation_optim_[i].type = Const.LPAR;
            else if (equation_optim_[i].type == Const.RBR)
                equation_optim_[i].type = Const.RPAR;

        return Element_of_equation.Elementarr_to_String(equation_optim_);
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

    private Element_of_equation[] Polynomial (Element_of_equation[] arr)
    {



        arr = Brackets_inarr(arr);
        arr = Vlines_inarr(arr);
        arr = Func_and_re_inarr(arr);
        arr = OPERATORS_inarr(arr, 1 );
        arr = OPERATORS_inarr(arr, 2 );
        arr = OPERATORS_inarr(arr, 3 );

        if (arr.length != 1) arr = MyFunc.Cut_put(new Element_of_equation[]{new Element_of_equation(Const.LBR), new Element_of_equation(Const.ERROR), new Element_of_equation(Const.RBR)}, arr, 1, 1);
        return arr;
    }

    //-------------------------------------------------------------------------------------------------------------




    private Element_of_equation[] Brackets_inarr (Element_of_equation[] arr)
    {
        int b1 = 0;
        int b2 = 0;



        for (int i = 0; i < arr.length; i++) {
            switch (arr[i].type) {
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
                    arr = MyFunc.Cut_put(arr, new Element_of_equation[]{arr[arr.length - 1], new Element_of_equation(Const.RPAR)}, arr.length - 1, arr.length - 1);
                b2 = b1;
            }else if (b2 > b1){
                for(int i = b2-b1; i > 0; i--)
                    arr = MyFunc.Cut_put(arr, new Element_of_equation[]{new Element_of_equation(Const.RPAR), arr[0]}, 0, 0);
                b1=b2;
            }
            else return new Element_of_equation[]{new Element_of_equation("$2")};
        }
        if (b1 !=0)
            for (int j = 0, i = -1; j < arr.length&& b1 != 0; j++) {
                switch (arr[j].type) {
                    case Const.LPAR:
                        i = j;
                        break;
                    case Const.RPAR:
                        if (i == -1)
                            return new Element_of_equation[]{new Element_of_equation("$3")};
                        else {
                            if (i + 1 == j&&i > 0) {
                                arr = MyFunc.Cut_put(arr, MyFunc.Take_part(arr, i - 1, i - 1), i - 1, j);
                                break;
                            } else
                            if (i + 1 == j&&j < arr.length-1) {
                                arr = MyFunc.Cut_put(arr, MyFunc.Take_part(arr, j + 1, j + 1), i, j + 1);
                                break;
                            } else
                            if (i + 1 == j) {
                                arr = new Element_of_equation[]{new Element_of_equation("$31")};
                                break;
                            } else
                                arr = MyFunc.Cut_put(arr, Polynomial(MyFunc.Take_part(arr, i + 1, j - 1)), i, j);

                            if (arr[i].type == Const.ERROR)
                                return new Element_of_equation[]{new Element_of_equation("$4")};
                            i = -1;
                            j = -1;
                            b1--;
                            break;
                        }
                }
            }

        for (int i = 0; i < arr.length; i++)
            if (arr[i].type==Const.RPAR||arr[i].type==Const.LPAR)
                b1++;
        if (b1 > 0) return new Element_of_equation[]{new Element_of_equation("$5")};


        return arr;
    }

    //-------------------------------------------------------------------------------------------------------------

    private Element_of_equation[] Vlines_inarr (Element_of_equation[] arr) {
        int bm  = 0;
        int bml = 0;
        int b = 0;

        for (int i = 0; i < arr.length; i++) {
            switch (arr[i].type) {
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



        if (bm % 2 != 0) arr = MyFunc.Cut_put(arr, new Element_of_equation[]{arr[arr.length - 1], new Element_of_equation(Const.VLINE)}, arr.length - 1, arr.length - 1);



        bml = -1;

        for (int i = 0, j = bm / 2; i < arr.length && j > 0; i++)
            if (b == 0&&arr[i].type == Const.VLINE) {
                if (bml == -1 && i < arr.length - 1) {
                    if (!((arr[i + 1].type >= Const.DIV && arr[i + 1].type <= Const.PRCNT) || arr[i + 1].type == Const.RPAR || arr[i + 1].type == Const.RBR || arr[i + 1].type == Const.FUNCRE)) {
                        bml = i;
                    } else return new Element_of_equation[]{new Element_of_equation("$7")};
                } else if (i > 0 && !((arr[i - 1].type >= Const.PLUS && arr[i - 1].type <= Const.PRCNT) || arr[i - 1].type == Const.LPAR || arr[i - 1].type == Const.LBR || arr[i - 1].type == Const.FUNC)) {
                    if (bml == i - 1) {
                        if (bml > 0)
                            arr = MyFunc.Cut_put(arr, MyFunc.Take_part(arr, bml - 1, bml - 1), bml - 1, i);
                        else if (i + 1 < arr.length)
                            arr = MyFunc.Cut_put(arr, MyFunc.Take_part(arr, i + 1, i + 1), bml, i + 1);
                        else return new Element_of_equation[]{new Element_of_equation("$8")};
                    }
                    arr = MyFunc.Cut_put(arr, new Element_of_equation[]{new Element_of_equation("Abs")}, bml, bml);
                    arr = MyFunc.Cut_put(arr, Polynomial(MyFunc.Take_part(arr, bml + 1, i - 1)), bml + 1, i);
                    j--;
                    bml = -1;
                    i = -1;


                } else if (i < arr.length - 1 && !((arr[i + 1].type >= Const.DIV && arr[i + 1].type <= Const.PRCNT) ||
                        arr[i + 1].type == Const.RPAR || arr[i + 1].type == Const.RBR || arr[i + 1].type == Const.FUNCRE)) {
                    bml = i;
                } else return new Element_of_equation[]{new Element_of_equation("$9")};
            }
            else if (arr[i].type == Const.LBR||arr[i].type == Const.LPAR) b++;
            else if (arr[i].type == Const.RBR||arr[i].type == Const.RPAR) b--;


        bm = 0;
        for (int i = 0; i < arr.length; i++)
            if (arr[i].type == Const.VLINE)
                bm++;
        if (bm > 0) return new Element_of_equation[]{new Element_of_equation("$10")};//well done

        return arr;
    }
    //-------------------------------------------------------------------------------------------------------------

    private Element_of_equation[] Func_and_re_inarr (Element_of_equation[] arr)
    {
        int bm = 0;
        for (int i = 0; i < arr.length; i++)
            if (bm==0&&arr[i].type == Const.FUNC) {
                if (i < arr.length - 1 && (arr[i + 1].type == Const.REAL ||arr[i + 1].type == Const.COMPLEX))
                    arr = MyFunc.Cut_put(arr, new Element_of_equation[]{Function(MyFunc.Take_part(arr, i, i + 1))}, i, i + 1);
                else if (i >= arr.length - 1 || arr[i + 1].type != Const.LPAR && arr[i + 1].type != Const.LBR && arr[i + 1].type != Const.FUNC&& arr[i + 1].type != Const.VAR)
                    return new Element_of_equation[]{new Element_of_equation("$11")};
            } else if (bm==0&&arr[i].type == Const.FUNCRE) {
                if (i > 0 && (arr[i - 1].type == Const.REAL ||arr[i - 1].type == Const.COMPLEX)) {
                    arr = MyFunc.Cut_put(arr,
                            new Element_of_equation[]{
                                Function(new Element_of_equation[]{
                                    new Element_of_equation(arr[i]), new Element_of_equation(arr[i - 1])
                                })
                            }, i - 1, i);
                    i--;
                }
                else if (i <= 0 || arr[i - 1].type != Const.LPAR &&arr[i - 1].type != Const.LBR && arr[i - 1].type != Const.FUNCRE&& arr[i - 1].type != Const.VAR)
                    return new Element_of_equation[]{new Element_of_equation("$12")};
            }
            else if (arr[i].type == Const.LBR||arr[i].type == Const.LPAR) bm++;
            else if (arr[i].type == Const.RBR||arr[i].type == Const.RPAR) bm--;

        return arr;
    }

    //-------------------------------------------------------------------------------------------------------------

    private Element_of_equation[] OPERATORS_inarr (Element_of_equation[] arr, int prioritet_level)
    {
        int bm = 0;
        int[] operator;
        switch (prioritet_level) {
            case 1:
                operator = new int[]{Const.PRCNT, Const.ROOT, Const.POW};
                break;
            case 2:
                for (int i = 0; i < arr.length-1; i++)
                if ((arr[i].type == Const.VAR|| arr[i].type == Const.RBR || arr[i].type == Const.RPAR ||
                        arr[i].type == Const.FUNCRE || arr[i].type == Const.REAL || arr[i].type == Const.COMPLEX)&&
                    (arr[i+1].type == Const.VAR || arr[i + 1].type == Const.LBR || arr[i + 1].type == Const.LPAR ||
                        arr[i + 1].type == Const.FUNC || arr[i + 1].type == Const.REAL || arr[i + 1].type == Const.COMPLEX))
                        arr = MyFunc.Cut_put(arr, new Element_of_equation[]{arr[i], new Element_of_equation(Const.MULT), arr[i + 1]}, i, i + 1);
                operator = new int[]{Const.MULT, Const.DIV};
                break;
            case 3:
                if (arr[0].type == Const.MINUS || arr[0].type == Const.PLUS)
                    arr = MyFunc.Cut_put(new Element_of_equation[]{new Element_of_equation((double) 0), new Element_of_equation(Const.ERROR)}, arr, 1, 1);
                operator = new int[]{Const.PLUS, Const.MINUS};
                break;
            default:
                operator = new int[]{Const.ERROR};
                break;
        }
        for (int i = 1; i < arr.length - 1; i++)
            for (int j = 0; j < operator.length; j++)
                if (bm == 0 && arr[i].type == operator[j]) {
                    if ((arr[i - 1].type == Const.REAL || arr[i - 1].type == Const.COMPLEX) && (arr[i + 1].type == Const.REAL || arr[i + 1].type == Const.COMPLEX)) {
                        arr = MyFunc.Cut_put(arr, new Element_of_equation[]{Operator(MyFunc.Take_part(arr, i - 1, i + 1))}, i - 1, i + 1);
                        i--;
                    } else if ( arr[i + 1].type != Const.LPAR && arr[i + 1].type != Const.LBR &&
                                arr[i + 1].type != Const.FUNC && arr[i - 1].type != Const.LPAR &&
                                arr[i - 1].type != Const.LBR && arr[i - 1].type != Const.FUNCRE &&
                                arr[i + 1].type != Const.VAR && arr[i - 1].type != Const.VAR)
                        return new Element_of_equation[]{new Element_of_equation("$oper "+prioritet_level+ "   ")};

                }
                else if (arr[i].type == Const.LBR || arr[i].type == Const.LPAR) bm++;
                else if (arr[i].type == Const.RBR || arr[i].type == Const.RPAR) bm--;
        int temp = arr[arr.length-1].type;
        if (temp==Const.MINUS||temp==Const.PRCNT||temp==Const.ROOT||temp==Const.POW||temp==Const.MULT||temp==Const.DIV||temp==Const.PLUS)
            return new Element_of_equation[]{new Element_of_equation("$14")};
        return arr;
    }

  //-------------------------------------------------------------------------------------------------------------

    private Element_of_equation Function (Element_of_equation[] arr)
    {
        Element_of_equation output=new Element_of_equation(Const.ERROR);
        if (arr[0].type == Const.ERROR||arr[1].type==Const.ERROR||(arr[0].type!=Const.FUNC&&arr[0].type!=Const.FUNCRE))
            return output;
        if (arr[1].type==Const.REAL) {
            output.type = Const.REAL;
            if (arr[0].func.equals("Sin"))
                output.real = Math.sin(arr[1].real);
            else if (arr[0].func.equals("Cos"))
                output.real = Math.cos(arr[1].real);
            else if (arr[0].func.equals("Tan"))
                output.real = Math.tan(arr[1].real);
            else if (arr[0].func.equals("Log"))
                output.real = Math.log10(arr[1].real);
            else if (arr[0].func.equals("Ln"))
                output.real = Math.log(arr[1].real);
            else if (arr[0].func.equals("Abs"))
                output.real = Math.abs(arr[1].real);
            else if (arr[0].func.equals("Sqrt"))
                if (arr[1].real >= 0)
                    output.real = Math.sqrt(arr[1].real);
                else
                {
                    output.type = Const.COMPLEX;
                    output.comp = new Complex(0,1).times( Math.sqrt(arr[1].real));
                }
            else if (arr[0].func.equals("Cbrt"))
                output.real = Math.cbrt(arr[1].real);
            else  if (arr[0].func.equals("!")){
                if (arr[1].real>=0)
                    output.real = MyFunc.Factorial(arr[1].real);
            }
            else output.type = Const.ERROR;
        }else if(arr[1].type == Const.COMPLEX){
            output.type = Const.COMPLEX;
            if (arr[0].func.equals("Sin"))
                output.comp = arr[1].comp.sin();
            else if (arr[0].func.equals("Cos"))
                output.comp = arr[1].comp.cos();
            else if (arr[0].func.equals("Tan"))
                output.comp = arr[1].comp.tan();
            else if (arr[0].func.equals("Log"))
                output.comp = arr[1].comp.log10();
            else if (arr[0].func.equals("Ln"))
                output.comp = arr[1].comp.ln();
            else if (arr[0].func.equals("Abs"))
                output.comp = new Complex(arr[1].comp.abs(), 0);
            else if (arr[0].func.equals("Sqrt"))
                output.comp = arr[1].comp.pow(0.5);
            else if (arr[0].func.equals("Cbrt"))
                output.comp = arr[1].comp.pow(1.0 / 3.0);
            else  if (arr[0].func.equals("!"))
                output.type = Const.ERROR;   //TODO factorial of complex value
            else output.type = Const.ERROR;
        }
        if (output.type == Const.ERROR) return new Element_of_equation("$15");
        if( output.type == Const.COMPLEX ) {
            if (Math.abs(output.comp.re) < 0.0000000000001) output.comp.re = 0;
            if (Math.abs(output.comp.im) < 0.0000000000001) output.comp.im = 0;
        }
        if (output.type == Const.REAL && Math.abs(output.real)< 0.0000000000001 ) output.real = 0;

        return output;
    }


    //-------------------------------------------------------------------------------------------------------------

    private Element_of_equation Operator (Element_of_equation[] arr )
    {
        if (arr [0].type == Const.ERROR || arr [1].type == Const.ERROR || arr [2].type == Const.ERROR)
            return new Element_of_equation ("$16");
        Element_of_equation output = new Element_of_equation("Error");
        double out;
        if (arr [0].type == Const.REAL&& arr [2].type == Const.REAL){
            output.type = Const.REAL;
            switch (arr[1].type) {
                case Const.PLUS: output.real = arr[0].real + arr[2].real; break;
                case Const.MINUS: output.real = arr[0].real - arr[2].real; break;
                case Const.DIV: output.real = arr[0].real / arr[2].real; break;
                case Const.MULT: output.real = arr[0].real * arr[2].real; break;
                case Const.PRCNT: output.real = arr[0].real % arr[2].real; break;
                case Const.POW:
                    out = Math.pow(arr[0].real, arr[2].real);
                    if (Double.isNaN(out)) {
                        output.type = Const.COMPLEX;
                        output.comp = new Complex(arr[0].real).pow(arr[2].real);
                    }else output.real = out;
                    break;
                case Const.ROOT:
                    out = Math.pow(arr[2].real, 1/arr[0].real);
                    if (Double.isNaN(out)) {
                        output.type = Const.COMPLEX;
                        output.comp = new Complex(arr[2].real).pow(1 / arr[0].real);
                    }else output.real = out;
                    break;
            }
            if (Math.abs(output.real)< 0.0000000000001 ) output.real = 0;

        }
        else {
            output.type = Const.COMPLEX;
            if (arr[0].type == Const.COMPLEX && arr[2].type == Const.COMPLEX)
                switch (arr[1].type) {
                    case Const.PLUS:
                        output.comp = arr[0].comp.plus(arr[2].comp);
                        break;
                    case Const.MINUS:
                        output.comp = arr[0].comp.minus(arr[2].comp);
                        break;
                    case Const.DIV:
                        output.comp = arr[0].comp.divides(arr[2].comp);
                        break;
                    case Const.MULT:
                        output.comp = arr[0].comp.times(arr[2].comp);
                        break;
                    case Const.POW:
                        output.comp = arr[0].comp.pow(arr[2].comp);
                        break;
                    case Const.ROOT:
                        output.comp = arr[2].comp.pow(new Complex(1).divides(arr[0].comp));
                        break;
                }
            else if (arr[0].type == Const.COMPLEX && arr[2].type != Const.COMPLEX)
                switch (arr[1].type) {
                    case Const.PLUS:
                        output.comp = arr[0].comp.plus(new Complex(arr[2].real, 0));
                        break;
                    case Const.MINUS:
                        output.comp = arr[0].comp.minus(new Complex(arr[2].real, 0));
                        break;
                    case Const.DIV:
                        output.comp = arr[0].comp.divides(new Complex(arr[2].real, 0));
                        break;
                    case Const.MULT:
                        output.comp = arr[0].comp.times(new Complex(arr[2].real, 0));
                        break;
                    case Const.POW:
                        output.comp = arr[0].comp.pow(arr[2].real);
                        break;
                    case Const.ROOT:
                        output.comp = new Complex(arr[2].real).pow(new Complex(1).divides(arr[0].comp));
                        break;
                }
            else if (arr[0].type != Const.COMPLEX && arr[2].type == Const.COMPLEX)
                switch (arr[1].type) {
                    case Const.PLUS:
                        output.comp = arr[2].comp.plus(new Complex(arr[0].real, 0));
                        break;
                    case Const.MINUS:
                        output.comp = new Complex(arr[0].real, 0).minus(arr[2].comp);
                        break;
                    case Const.DIV:
                        output.comp = new Complex(arr[0].real, 0).divides(arr[2].comp);
                        break;
                    case Const.MULT:
                        output.comp = arr[2].comp.times(new Complex(arr[0].real, 0));
                        break;
                    case Const.POW:
                        output.comp = new Complex(arr[0].real).pow(arr[2].comp);
                    case Const.ROOT:
                        output.comp = arr[2].comp.pow(new Complex(1).divides(new Complex(arr[0].real)));
                        break;
                }
            else output.type = Const.ERROR;
            if( output.type == Const.COMPLEX ) {
                if (Math.abs(output.comp.re) < 0.0000000000001) output.comp.re = 0;
                if (Math.abs(output.comp.im) < 0.0000000000001) output.comp.im = 0;
            }
        }

        return output;

    }

}

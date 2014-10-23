package com.dudkovlad.Calc3d.Parser;



import android.content.Context;

import com.dudkovlad.Calc3d.MainActivity;

import java.util.ArrayList;

/**
 * Created by vlad on 04.05.2014.
 */
public class Parser {


    String equation_string;
    ArrayList<Element_of_equation> equation;
    int [] positions_of_X;
    int [] positions_of_Y;
    int [] positions_of_Z;
    int [] positions_of_C;
    int [] positions_of_T;
    Element_of_equation[][] equations;
    int[] signs;
    int num_of_variables;

    public Parser ()
    {

        INIT("0");//todo if here is error someting is wrong
    }

    public String Result (String equation_)
    {
        String out;

        if ((out = INIT(equation_)).isEmpty()||out.charAt(0)=='$')
        {
            return out;
        }
        return Result();
    }

    public String INIT (String equation_)
    {
        if (equation_.isEmpty())
            return "";
        try {
            equation_string = equation_;
            equation = Element_of_equation.getArrlistFrom(equation_);
            return "0";

        }catch (IllegalArgumentException e)
        //}catch (Throwable e)
        {
            return "$" + e.toString();
        }
    }

    public String Result ()
    {

        try {

            Polynomial(equation, 0, equation.size() - 1);
        }catch (IllegalArgumentException e)
        //}catch (Throwable e)
        {
            return "$" + e.toString();
        }

        if (equation.size()>1) {
            if(equation.get(0).type == Const.LBR) {
                equation.remove(0);
                equation.remove(equation.size() - 1);
            }
        }

        for (int i = 0; i < equation.size(); i++) // todo it will not work
            if (equation.get(i).type == Const.LBR)
                equation.get(i).type = Const.LPAR;
            else if (equation.get(i).type == Const.RBR)
                equation.get(i).type = Const.RPAR;

        return Element_of_equation.Arr_toString(equation);
    }



    public float forLoop (float X, float Y, float Z, float T)
    {
        ArrayList<Element_of_equation> equation_temp = (ArrayList<Element_of_equation>)equation.clone();

        for (int elem_of_arr:positions_of_X)
            equation_temp.set(elem_of_arr, new Element_of_equation (X));
        for (int elem_of_arr:positions_of_Y)
            equation_temp.set(elem_of_arr, new Element_of_equation (Y));
        for (int elem_of_arr:positions_of_Z)
            equation_temp.set(elem_of_arr, new Element_of_equation (Z));
        for (int elem_of_arr:positions_of_T)
            equation_temp.set(elem_of_arr, new Element_of_equation (T));


        Polynomialf(equation_temp, 0, equation_temp.size() - 1);

        if(equation_temp.get(0).type==Const.COMPLEX)
            return equation_temp.get(0).compf.Re();
        else
            return equation_temp.get(0).realf;
    }

    public Complex64 forLoop (float X, float Y, float T)
    {
        ArrayList<Element_of_equation> equation_temp = (ArrayList<Element_of_equation>)equation.clone();

        for (int elem_of_arr:positions_of_X)
            equation_temp.set(elem_of_arr, new Element_of_equation (X));
        for (int elem_of_arr:positions_of_Y)
            equation_temp.set(elem_of_arr, new Element_of_equation (Y));
        for (int elem_of_arr:positions_of_T)
            equation_temp.set(elem_of_arr, new Element_of_equation (T));


        Polynomialf(equation_temp, 0, equation_temp.size() - 1);

        if(equation_temp.get(0).type==Const.COMPLEX)
            return equation_temp.get(0).compf;
        else
            return new Complex64(equation_temp.get(0).realf);
    }

    public Complex64 forLoop (Complex64 X, float T)
    {
        ArrayList<Element_of_equation> equation_temp = (ArrayList<Element_of_equation>)equation.clone();

        for (int elem_of_arr:positions_of_X)
            equation_temp.set(elem_of_arr, new Element_of_equation(X));
        for (int elem_of_arr:positions_of_T)
            equation_temp.set(elem_of_arr, new Element_of_equation (T));


        Polynomialf(equation_temp, 0, equation_temp.size() - 1);

        return equation_temp.get(0).compf;
    }


    private int Polynomialf (ArrayList<Element_of_equation> arr, int begin, int end)
    {

        int end_ = end;
        /*
        end+=Brackets_inarrf(arr, begin, end);
        end+=Func_and_re_inarrf(arr, begin, end);
        end+=OPERATORS_inarrf(arr, begin, end, 1 );
        end+=OPERATORS_inarrf(arr, begin, end, 2 );
        end+=OPERATORS_inarrf(arr, begin, end, 3);
        *///todo uncomment when final functions

        return end - end_;
    }

    private int Polynomial (ArrayList<Element_of_equation> arr, int begin, int end)
    {

        int end_ = end;

        end+=Brackets_inarr(arr, begin, end);
        end+=Vlines_inarr(arr, begin, end);
        end+=Func_and_re_inarr(arr, begin, end);
        end+=OPERATORS_inarr(arr, begin, end, 1 );
        end+=OPERATORS_inarr(arr, begin, end, 2 );
        end+=OPERATORS_inarr(arr, begin, end, 3);

        if (end-begin > 0) {
            arr.add(0,new Element_of_equation(Const.LBR));
            arr.add(new Element_of_equation(Const.RBR));
            end+=2;
        }

        return end - end_;
    }

    //-------------------------------------------------------------------------------------------------------------




    private int Brackets_inarr (ArrayList<Element_of_equation> arr, int begin, int end)
    {
        int end_ = end;
        int b1 = 0;
        int b2 = 0;
        for (int i = begin; i <= end; i++) {
            switch (arr.get(i).type) {
                case Const.LPAR:
                    b1++;
                    break;
                case Const.RPAR:
                    b2++;
                    break;
            }
        }

        if (b1!=b2)
            if (b1 > b2)
                for(int i = b1-b2; i > 0; i--){
                    arr.add(end+1,new Element_of_equation(Const.RPAR));
                    end++;
                }
            else if (b2 > b1){
                for(int i = b2-b1; i > 0; i--) {
                    arr.add(begin,new Element_of_equation(Const.LPAR));
                    end++;
                }
                b1=b2;
            }

        if (b1 !=0)
            for (int j = begin, i = -1; j <=end&& b1 != 0; j++) {
                switch (arr.get(j).type) {
                    case Const.LPAR:
                        i = j;
                        break;
                    case Const.RPAR:
                        if (i == -1)
                            throw new IllegalArgumentException("brackets in arr rpar without lpar");
                        else {
                            if (i + 1 == j) {
                                arr.remove(j);
                                arr.remove(i);
                                end-=2;
                                break;
                            }
                            else {
                                arr.remove(j);
                                arr.remove(i);
                                end+=Polynomial(arr, i, j - 2);
                                end-=2;
                            }

                            i = -1;
                            j = -1;
                            b1--;
                            break;
                        }
                }
            }

        for (int i = begin; i <=end; i++)
            if (arr.get(i).type==Const.RPAR||arr.get(i).type==Const.LPAR)
                b1++;
        if (b1 > 0)  throw new IllegalArgumentException("brackets in arr don\'t all brackets used");

        return end - end_;
    }


    private int Brackets_inarrf (ArrayList<Element_of_equation> arr, int begin, int end)
    {
        int end_ = end;

        for (int j = begin, i = -1; j <=end; j++)
            switch (arr.get(j).type) {
                case Const.LPAR:
                    i = j;
                    break;
                case Const.RPAR:
                    arr.remove(j);
                    arr.remove(i);
                    end+=Polynomial(arr, i, j - 2);
                    end-=2;

                    i = -1;
                    j = -1;
                    break;
            }
        return end - end_;
    }

    //-------------------------------------------------------------------------------------------------------------

    private int Vlines_inarr (ArrayList<Element_of_equation> arr, int begin, int end) {

        int bm  = 0;
        int bml = 0;
        int b = 0;

        int end_ = end;
        for (int i = begin; i <= end; i++) {
            switch (arr.get(i).type) {
                case Const.VLINE:if (bml==0)bm++;break;
                case Const.LBR:  bml++;break;
                case Const.LPAR: bml++;break;
                case Const.RBR:  bml--;break;
                case Const.RPAR: bml--;break;
                default: break;
            }
        }



        if ((bm % 2 )== 1) {
            arr.add(end+1, new Element_of_equation(Const.VLINE));
            end++;
            bm++;
        }//todo wy it don't work



        bml = -1;

        for (int i = begin, j = bm / 2; i <= end && j > 0; i++)
            if (b == 0&&arr.get(i).type == Const.VLINE) {
                if (bml == -1 && i < end) {
                    if (!((arr.get(i + 1).stype == Const.OPER&& arr.get(i + 1).type != Const.PLUS&& arr.get(i + 1).type != Const.MINUS) ||
                            arr.get(i + 1).stype == Const.RBRACK  ||
                            arr.get(i+1).stype == Const.FUNCRE)) {
                        bml = i;
                    } else  throw new IllegalArgumentException("vlines in arr @1122");
                } else if (i > 0 && !(arr.get(i - 1).stype == Const.OPER
                        ||  arr.get(i - 1).stype == Const.LBRACK || arr.get(i-1).stype == Const.FUNC)) {
                    if (bml == i - 1)
                        throw new IllegalArgumentException("vlines in arr vlines are side by side");
                    arr.remove(i);
                    end--;
                    arr.set(bml, new Element_of_equation(Const.ABS));
                    end += Polynomial(arr, bml + 1, i - 1);


                    j--;
                    bml = -1;
                    i = -1;

                } else if (i < end && !
                        ((arr.get(i + 1).stype == Const.OPER&& arr.get(i + 1).type != Const.PLUS&& arr.get(i + 1).type != Const.MINUS) ||
                        arr.get(i + 1).stype == Const.RBRACK ||
                        arr.get(i+1).stype == Const.FUNCRE)) {
                    bml = i;
                } else
                    throw new IllegalArgumentException("vlines in arr unreachable @1124");
            }
            else if (arr.get(i).stype == Const.LBRACK) b++;
            else if (arr.get(i).stype == Const.RBRACK) b--;


        bm = 0;
        for (int i = begin; i <= end; i++)
            if (arr.get(i).type == Const.VLINE)
                bm++;
        if (bm > 0) throw new IllegalArgumentException("vlines in arr not all vlines used");//well done
        return end - end_;
    }
    //-------------------------------------------------------------------------------------------------------------

    private int Func_and_re_inarr (ArrayList<Element_of_equation> arr, int begin, int end)
    {
        int bm = 0;
        int end_ = end;
        for (int i = begin; i <= end; i++)
            if (bm==0&& arr.get(i).stype == Const.FUNC) {
                if (i < end && arr.get(i + 1).stype == Const.NUM) {
                    arr.set(i + 1, Function_parser.Run_func(arr.get(i).type, arr.get(i + 1)));
                    arr.remove(i);
                    end--;
                }
                else if (i >= end || arr.get(i + 1).stype != Const.LBRACK &&
                        arr.get(i).stype != Const.FUNC && arr.get(i + 1).type != Const.VAR)
                    throw new IllegalArgumentException("func and re in arr @1244");
            } else if (bm==0&&arr.get(i).stype == Const.FUNCRE ){
                if (i > 0 && arr.get(i - 1).stype == Const.NUM) {
                    arr.set(i, Function_parser.Run_func(arr.get(i).type, arr.get(i - 1)));
                    arr.remove(i-1);
                    end--;
                    i--;
                }
                else if (i <= 0 || arr.get(i - 1).stype != Const.LBRACK &&
                        arr.get(i).stype !=Const.FUNCRE && arr.get(i - 1).type != Const.VAR)
                    throw new IllegalArgumentException("func and re in arr @1245");
            }
            else if (arr.get(i).stype == Const.LBRACK) bm++;
            else if (arr.get(i).stype == Const.RBRACK) bm--;

        return end - end_;
    }

    private int Func_and_re_inarrf (ArrayList<Element_of_equation> arr, int begin, int end)
    {
        int end_ = end;
        for (int i = begin; i <= end; i++)
            if (arr.get(i).stype == Const.FUNC) {
                arr.set(i + 1, Function_parser.Run_func(arr.get(i).type, arr.get(i + 1)));
                arr.remove(i);
                end--;
            } else if (arr.get(i).stype == Const.FUNCRE ){
                arr.set(i, Function_parser.Run_func(arr.get(i).type, arr.get(i - 1)));
                arr.remove(i-1);
                end--;
                i--;
            }

        return end - end_;
    }

    //-------------------------------------------------------------------------------------------------------------

    private int OPERATORS_inarr (ArrayList<Element_of_equation> arr, int begin, int end, int prioritet_level)
    {
        int end_ = end;
        int bm = 0;
        int[] operator;
        switch (prioritet_level) {
            case 1:
                operator = new int[]{Const.PRCNT, Const.ROOT, Const.POW};
                break;
            case 2:
                for (int i = begin; i < end; i++)
                    if (   (arr.get(i).type  == Const.VAR||
                            arr.get(i).stype == Const.RBRACK ||
                            arr.get(i).stype == Const.FUNCRE ||
                            arr.get(i).stype == Const.NUM     )&&
                           (arr.get(i + 1).type  == Const.VAR ||
                            arr.get(i + 1).stype == Const.LBRACK ||
                            arr.get(i + 1).stype == Const.FUNC ||
                            arr.get(i + 1).stype == Const.NUM)) {

                        arr.add(i+1, new Element_of_equation(Const.MULT));
                        end++;
                    }
                operator = new int[]{Const.MULT, Const.DIV};
                break;
            case 3:
                if (arr.get(begin).type == Const.MINUS || arr.get(begin).type == Const.PLUS) {
                    arr.add( begin, new Element_of_equation((double) 0));
                    end++;
                }
                operator = new int[]{Const.PLUS, Const.MINUS};
                break;
            default:
                throw new IllegalArgumentException("oper in arr prior level is illegal");
        }
        for (int i = begin+1; i < end; i++)
            for (int j = 0; j < operator.length; j++)
                if (bm == 0 && arr.get(i).type == operator[j]) {
                    if (arr.get(i - 1).stype == Const.NUM && arr.get(i + 1).stype == Const.NUM) {
                        arr.set(i + 1, Function_parser.Run_func2(arr.get(i).type, arr.get(i - 1), arr.get(i + 1)));
                        arr.remove(i);
                        arr.remove(i-1);

                        end-=2;
                        i--;
                    } else if ( arr.get(i + 1).stype != Const.LBRACK &&
                                arr.get(i + 1).stype != Const.FUNC &&
                                arr.get(i - 1).stype != Const.LBRACK &&
                                arr.get(i - 1).stype != Const.FUNCRE &&
                                arr.get(i + 1).type  != Const.VAR &&
                                arr.get(i - 1).type  != Const.VAR)
                        throw new IllegalArgumentException("oper in arr near oper is  " +arr.get(i - 1).type + arr.get(i).type + arr.get(i + 1).type);

                }
                else if (arr.get(i).stype == Const.LBRACK) bm++;
                else if (arr.get(i).stype == Const.RBRACK) bm--;

        if (arr.get(end).stype == Const.OPER)
            throw new IllegalArgumentException("oper in arr last is oper");

        return end-end_;
    }

}

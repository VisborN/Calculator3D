package com.dudkovlad.Calc3d.Parser;



import java.util.ArrayList;

/**
 * Created by vlad on 04.05.2014.
 */
public class Parser {


    String equation_string;
    ArrayList<Element_of_equation> equation;
    ArrayList<Element_of_equation> equation_temp;
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
            equation = Equation.Create(equation_);
            equation = Equation.toPolishNot(equation);
            return "0";

            //}catch (IllegalArgumentException e)
        }catch (Throwable e)
        {
            return "$" + e.toString();
        }
    }

    public String Result ()
    {

        try {
            equation = Calculate_op_and_func(equation,false);
            return Equation.toString(equation);

            //}catch (IllegalArgumentException e)
        }catch (Throwable e)
        {
            return "$" + e.toString();
        }
    }


    public float forLoop (float X, float Y, float Z, float C, float T)
    {
        equation_temp = equation;
        for (int elem_of_arr:positions_of_X)
            equation_temp.set(elem_of_arr, new Element_of_equation<Float,Complex64> (X));
        for (int elem_of_arr:positions_of_Y)
            equation_temp.set(elem_of_arr, new Element_of_equation<Float,Complex64> (Y));
        for (int elem_of_arr:positions_of_Z)
            equation_temp.set(elem_of_arr, new Element_of_equation<Float,Complex64> (Z));
        for (int elem_of_arr:positions_of_T)
            equation_temp.set(elem_of_arr, new Element_of_equation<Float,Complex64> (C));
        for (int elem_of_arr:positions_of_T)
            equation_temp.set(elem_of_arr, new Element_of_equation<Float,Complex64> (T));


        Calculate_op_and_func(equation_temp, true);

        if(equation_temp.get(0).type==Const.COMPLEX)
            return (float)equation_temp.get(0).comp.Re();
        else
            return (float)(Float)equation_temp.get(0).real;
    }

//    private int Polynomialf (ArrayList<Element_of_equation> arr, int begin, int end)
//    {
//
//        int end_ = end;
//        //
//        //end+=Brackets_inarrf(arr, begin, end);
//        //end+=Func_and_re_inarrf(arr, begin, end);
//        //end+=OPERATORS_inarrf(arr, begin, end, 1 );
//        //end+=OPERATORS_inarrf(arr, begin, end, 2 );
//        //end+=OPERATORS_inarrf(arr, begin, end, 3);
//        //
//        //todo uncomment when final functions
//
//        return end - end_;
//    }


    //-------------------------------------------------------------------------------------------------------------

/*



    private int Brackets_inarr (ArrayList<Element_of_equation> arr, int begin, int end)
    {
        int end_ = end;
        int b1 = 0;
        int b2 = 0;
        for (int i = begin; i <= end; i++) {
            switch (arr.get(i).type) {
                case Const.LBR:
                    b1++;
                    break;
                case Const.RBR:
                    b2++;
                    break;
            }
        }

        if (b1!=b2)
            if (b1 > b2)
                for(int i = b1-b2; i > 0; i--){
                    arr.add(end+1,new Element_of_equation(Const.RBR));
                    end++;
                }
            else if (b2 > b1){
                for(int i = b2-b1; i > 0; i--) {
                    arr.add(begin,new Element_of_equation(Const.LBR));
                    end++;
                }
                b1=b2;
            }

        if (b1 !=0)
            for (int j = begin, i = -1; j <=end&& b1 != 0; j++) {
                switch (arr.get(j).type) {
                    case Const.LBR:
                        i = j;
                        break;
                    case Const.RBR:
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
            if (arr.get(i).type==Const.RBR||arr.get(i).type==Const.LBR)
                b1++;
        if (b1 > 0)  throw new IllegalArgumentException("brackets in arr don\'t all brackets used");

        return end - end_;
    }

    private int Vlines_inarr (ArrayList<Element_of_equation> arr, int begin, int end) {

        int bm  = 0;
        int bml = 0;
        int b = 0;

        int end_ = end;
        for (int i = begin; i <= end; i++) {
            switch (arr.get(i).type) {
                case Const.ABSBR:if (bml==0)bm++;break;
                case Const.LBR:  bml++;break;
                case Const.RBR:  bml--;break;
                default: break;
            }
        }



        if ((bm % 2 )== 1) {
            arr.add(end+1, new Element_of_equation(Const.ABSBR));
            end++;
            bm++;
        }



        bml = -1;

        for (int i = begin, j = bm / 2; i <= end && j > 0; i++)
            if (b == 0&&arr.get(i).type == Const.ABSBR) {
                if (bml == -1 && i < end) {
                    if (!((arr.get(i + 1).stype == Const.OPER&& arr.get(i + 1).type != Const.PLUS&& arr.get(i + 1).type != Const.MINUS) ||
                            arr.get(i + 1).stype == Const.RBR  ||
                            arr.get(i+1).type == Const.FACTORIAL)) {
                        bml = i;
                    } else  throw new IllegalArgumentException("vlines in arr @1122");
                } else if (i > 0 && !(arr.get(i - 1).stype == Const.OPER
                        ||  arr.get(i - 1).stype == Const.LBR || arr.get(i-1).stype == Const.FUNC)) {
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
                                arr.get(i + 1).stype == Const.RBR ||
                                arr.get(i+1).type == Const.FACTORIAL)) {
                    bml = i;
                } else
                    throw new IllegalArgumentException("vlines in arr unreachable @1124");
            }
            else if (arr.get(i).stype == Const.LBR) b++;
            else if (arr.get(i).stype == Const.RBR) b--;


        bm = 0;
        for (int i = begin; i <= end; i++)
            if (arr.get(i).type == Const.ABSBR)
                bm++;
        if (bm > 0) throw new IllegalArgumentException("vlines in arr not all vlines used");//well done
        return end - end_;
    }

*/

    //-------------------------------------------------------------------------------------------------------------


    private ArrayList<Element_of_equation> Calculate_op_and_func (ArrayList<Element_of_equation> arr, boolean with_compare_and_set)
    {
        ArrayList<Element_of_equation>stack = new ArrayList<Element_of_equation>(arr.size()/3*2+1);
        for (Element_of_equation current:arr)
        {
            switch (current.stype)
            {
                case Const.VAR:
                case Const.NUM: stack.add(current);break;
                case Const.FUNC:
                    if (stack.size()<1)
                        throw new IllegalArgumentException("Calculate_op_and_func few nums");
                    if (stack.get(stack.size()-1).stype!=Const.NUM)
                        stack.add(current);
                    else
                        stack.add(Function_parser.Run_func(current.type, stack.remove(stack.size()-1)));
                    break;

                case Const.SET:
                case Const.COMPARE:
                    if(!with_compare_and_set)
                    {
                        stack.add(current);
                        break;
                    }
                //case Const.FUNC2:todo add more than one arguments functions
                case Const.OPER:
                    if(stack.size()<2)
                        throw new IllegalArgumentException("Calculate_op_and_func few nums");
                    if (stack.get(stack.size()-1).stype!=Const.NUM||stack.get(stack.size()-2).stype!=Const.NUM)
                        stack.add(current);
                    else
                    stack.add(Function_parser.Run_func2(current.type,
                        stack.remove(stack.size()-2), stack.remove(stack.size()-1)));
                    break;


            }
        }
        if (with_compare_and_set&&stack.size()>1)
            throw new IllegalArgumentException("Calculate_op_and_func too many nums");
        return stack;
    }


}

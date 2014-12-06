package com.dudkovlad.Calc3d.Parser;



import java.util.ArrayList;

/**
 * Created by vlad on 04.05.2014.
 */
public class Parser {


    String equation_string;
    ArrayList<Token> equation;
    ArrayList<Token> equation_temp;
    int [] positions_of_X;
    int [] positions_of_Y;
    int [] positions_of_Z;
    int [] positions_of_C;
    int [] positions_of_T;
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
            return "equation is empty";
        try {
            equation_string = equation_;
            equation = Equation.Create(equation_);
            S_M_A_R_T(equation,0,equation.size()-1);
            //equation = Equation.toPolishNot(equation);
            return "all is ok";

            //}catch (IllegalArgumentException e)
            }catch (Throwable e)
            //}catch (Error e)
        {
            return "$" + e.toString();
        }
    }

    public String Result ()
    {

        try {
            //equation = Calculate_op_and_func(equation,false);
            //setPositions_Arrays();
            equation_string = Equation.toString(equation);
            //Convert_to_Float();
            return equation_string;

            //}catch (IllegalArgumentException e)
            }catch (Throwable e)
            //}catch (Error e)
        {
            return "$" + e.toString();
        }
    }


    public float forLoop (float X, float Y, float Z, float C, float T)
    {
        equation_temp = equation;
        for (int elem_of_arr:positions_of_X)
            equation_temp.set(elem_of_arr, new Token_f(X));
        for (int elem_of_arr:positions_of_Y)
            equation_temp.set(elem_of_arr, new Token_f(Y));
        for (int elem_of_arr:positions_of_Z)
            equation_temp.set(elem_of_arr, new Token_f(Z));
        for (int elem_of_arr:positions_of_C)
            equation_temp.set(elem_of_arr, new Token_f(C));
        for (int elem_of_arr:positions_of_T)
            equation_temp.set(elem_of_arr, new Token_f(T));


        equation_temp = Calculate_op_and_func(equation_temp, true);

        if(equation_temp.size()==0)
            return Float.POSITIVE_INFINITY;
        if(equation_temp.get(0).Type()==Const.COMPLEX)
            return (float)equation_temp.get(0).getc().Re();
        else
        if(equation_temp.get(0).Type()==Const.REAL)
            return equation_temp.get(0).getf();
        else
            return Float.POSITIVE_INFINITY;
    }


    private void setPositions_Arrays ()
    {
        int x = 0, y = 0, z = 0, c = 0, t = 0;
        for(Token current:equation)
        {
            switch (current.Type())
            {
                case Const.X:x++;break;
                case Const.Y:y++;break;
                case Const.Z:z++;break;
                case Const.C:c++;break;
                case Const.T:t++;break;
            }
        }
        positions_of_X = new int [x];
        positions_of_Y = new int [y];
        positions_of_Z = new int [z];
        positions_of_C = new int [c];
        positions_of_T = new int [t];

        for(int  i =0; i< equation.size(); i++)
        {
            switch (equation.get(i).Type())
            {
                case Const.X:positions_of_X[--x]=i;break;
                case Const.Y:positions_of_Y[--y]=i;break;
                case Const.Z:positions_of_Z[--z]=i;break;
                case Const.C:positions_of_C[--c]=i;break;
                case Const.T:positions_of_T[--t]=i;break;
            }
        }

    }

    private void Convert_to_Float()
    {
        for(int i = 0; i<equation.size(); i++)
        {
            if(equation.get(i).Stype()==Const.NUM)
                equation.set(i, equation.get(i).tofloat());
        }
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


    private ArrayList<Token> Calculate_op_and_func (ArrayList<Token> arr, boolean with_compare_and_set)
    {
        ArrayList<Token>stack = new ArrayList<Token>(arr.size()/3*2+1);
        for (Token current:arr)
        {
            switch (current.Stype())
            {
                case Const.VAR:
                case Const.NUM: stack.add(current);break;
                case Const.FUNC:
                    if (stack.size()<1)
                        throw new IllegalArgumentException("Calculate_op_and_func few nums");
                    if (stack.get(stack.size()-1).Stype()!=Const.NUM)
                        stack.add(current);
                    else
                        stack.add(Function_parser.Run_func(current.Type(), stack.remove(stack.size()-1)));
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
                    if (stack.get(stack.size()-1).Stype()!=Const.NUM||stack.get(stack.size()-2).Stype()!=Const.NUM)
                        stack.add(current);
                    else
                        stack.add(Function_parser.Run_func2(current.Type(),
                                stack.remove(stack.size()-2), stack.remove(stack.size()-1)));
                    break;


            }
        }
        if (with_compare_and_set&&stack.size()>1)
            throw new IllegalArgumentException("Calculate_op_and_func too many nums");
        return stack;
    }


    //-------------------------------------------------------------------------------------------------------------



    private int S_M_A_R_T (ArrayList<Token> arr, int begin, int end) {//todo maybe after I will optimize that


        int end_ = end;

        if(arr.get(begin).Type()==Const.MINUS||arr.get(begin).Type()==Const.PLUS) {
            arr.add(begin, new Token_d(0d));
            end++;
        }


        int b1 = 0;
        int b2 = 0;
        for (int i = begin; i <= end; i++) {
            switch (arr.get(i).Type()) {
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
                    arr.add(end+1,new Token_d(Const.RBR));
                    end++;
                }
            else if (b2 > b1){
                for(int i = b2-b1; i > 0; i--) {
                    arr.add(begin,new Token_d(Const.LBR));
                    end++;
                }
            }



        int bm  = 0;
        int bml = 0;
        for (int i = begin; i <= end; i++) {
            switch (arr.get(i).Type()) {
                case Const.ABSBR:if (bml==0)bm++;break;
                case Const.LBR:  bml++;break;
                case Const.RBR:  bml--;break;
                default: break;
            }
        }




        if ((bm % 2 )== 1) {
            arr.add(end+1, new Token_d(Const.ABSBR));
            end++;
            bm++;
        }




        bml = -1;
        b1 = -1;
        b2 = 0;

        for (int i = begin; i <= end; i++)
            if (b2 == 0&&arr.get(i).Type() == Const.ABSBR) {
                if (bml == -1 && i < end) {
                    if (!((arr.get(i + 1).Stype() == Const.OPER&& arr.get(i + 1).Type() != Const.PLUS&& arr.get(i + 1).Type() != Const.MINUS) ||
                            arr.get(i + 1).Stype() == Const.RBR  ||
                            arr.get(i+1).Type() == Const.FACTORIAL)) {
                        bml = i;
                    } else  throw new IllegalArgumentException("vlines in arr @1122");
                } else if (i > 0 && !(arr.get(i - 1).Stype() == Const.OPER
                        ||  arr.get(i - 1).Stype() == Const.LBR || arr.get(i-1).Stype() == Const.FUNC)) {
                    if (bml == i - 1)
                        throw new IllegalArgumentException("vlines in arr vlines are side by side");
                    if (bml == -1)
                        throw new IllegalArgumentException("vlines in arr right vline without left");
                    arr.set(bml, new Token_d(Const.ABS));
                    arr.set(i, new Token_d(Const.RBR));
                    arr.add(bml+1, new Token_d(Const.LBR));
                    end++;
                    end += S_M_A_R_T(arr, bml + 2, i);
                    i=begin-1;
                    b1 = -1;
                    b2 = 0;

                    bml = -1;

                } else if (i < end &&
                        !((arr.get(i + 1).Stype() == Const.OPER&& arr.get(i + 1).Type() != Const.PLUS&& arr.get(i + 1).Type() != Const.MINUS) ||
                                arr.get(i + 1).Stype() == Const.RBR ||
                                arr.get(i+1).Type() == Const.FACTORIAL)) {
                    bml = i;
                } else
                    throw new IllegalArgumentException("vlines in arr unreachable @1124");
            }
            else if ( arr.get(i).Stype() == Const.LBR) {
                if (b2 == 0)
                    b1 = i;
                b2++;
            }
            else if (arr.get(i).Stype() == Const.RBR){
                if (b2 == 1){
                    if (b1 == -1)
                        throw new IllegalArgumentException("brackets in arr rpar without lpar");
                    else {
                        int k;
                        if (b1 + 1 == i) {
                            arr.remove(i);
                            arr.remove(b1);
                            end -= 2;
                            i -= 2;
                        } else {
                            k = S_M_A_R_T(arr, b1 + 1, i - 1);
                            end += k;
                            i += k;
                        }
                        b1 = -1;
                    }
                }
                b2--;
            }


        bm = 0;
        for (int i = begin; i <= end; i++)
            if (arr.get(i).Type() == Const.ABSBR)
                bm++;
        if (bm > 0) throw new IllegalArgumentException("vlines in arr not all vlines used");//well done

        for (int i = begin; i < end; i++)
            if (   (arr.get(i).Stype()  == Const.VAR||
                    arr.get(i).Stype() == Const.RBR ||
                    arr.get(i).Type() == Const.FACTORIAL ||       //todo I hope that factorial is the only function with prefix argument
                    arr.get(i).Stype() == Const.NUM     )&&
                    (arr.get(i + 1).Stype()  == Const.VAR ||
                            arr.get(i + 1).Stype() == Const.LBR ||
                            arr.get(i + 1).Stype() == Const.FUNC ||
                            arr.get(i + 1).Stype() == Const.NUM)) {

                arr.add(i+1, new Token_d(Const.MULT));
                end++;
            }

        return end - end_;
    }


}

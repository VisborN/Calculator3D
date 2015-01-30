package com.dudkovlad.Calc3d.Parser;



import java.util.ArrayList;

/**
 * Created by vlad on 04.05.2014.
 */
public class Parser {


    String equation_string;
    ArrayList<Token> equation_arr_list;
    Token [] equation_arr;
    Token [] stack;
    int size_of_stack=0;
    Token[] equation_temp;
    int [] positions_of_X;
    int [] positions_of_Y;
    int [] positions_of_Z;
    int [] positions_of_C;
    int [] positions_of_T;
    int num_of_variables;

    public Parser ()
    {

        INIT("0", 10, true);//todo if here is error someting is wrong
    }

    public String Result (String equation_, int num_system, boolean radians)
    {
        String out;

        if ((out = INIT(equation_, num_system, radians)).isEmpty()||out.charAt(0)=='$')
        {
            return out;
        }
        return Result();
    }

    public String INIT (String equation_, int num_system, boolean radians)
    {
        if (equation_.isEmpty())
            return "equation is empty";
        try {
            equation_string = equation_;
            equation_arr_list = Equation.Create(equation_, num_system, radians);
            S_M_A_R_T(equation_arr_list,0,equation_arr_list.size()-1);
            equation_arr_list = Equation.toPolishNot(equation_arr_list);
            equation_arr = new Token[equation_arr_list.size()];
            equation_arr_list.toArray(equation_arr);
            stack = new Token[equation_arr.length];
            size_of_stack = 0;
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
            Calculate_op_and_func(equation_arr,false);
            equation_arr = new Token[size_of_stack];
            System.arraycopy(stack,0,equation_arr,0,size_of_stack);
            stack = new Token[equation_arr.length];
            size_of_stack = 0;
            setPositions_Arrays();
            equation_string = Equation.toString(equation_arr);
            Convert_to_Float();
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
        equation_temp = equation_arr;
        for (int elem_of_arr:positions_of_X)
            equation_temp[elem_of_arr] = new Token_f(X);
        for (int elem_of_arr:positions_of_Y)
            equation_temp[elem_of_arr] = new Token_f(Y);
        for (int elem_of_arr:positions_of_Z)
            equation_temp[elem_of_arr] = new Token_f(Z);
        for (int elem_of_arr:positions_of_C)
            equation_temp[elem_of_arr] = new Token_f(C);
        for (int elem_of_arr:positions_of_T)
            equation_temp[elem_of_arr] = new Token_f(T);


        Calculate_op_and_func(equation_temp, true);

        equation_temp = new Token[size_of_stack];
        System.arraycopy(stack,0,equation_temp,0,size_of_stack);

        if(equation_temp.length==0)
            return Float.POSITIVE_INFINITY;
        if(equation_temp[0].Type()==Const.COMPLEX)
            return (float)equation_temp[0].getc().abs();
        else
        if(equation_temp[0].Type()==Const.REAL)
            return equation_temp[0].getf();
        else
            return Float.POSITIVE_INFINITY;
    }


    private void setPositions_Arrays ()
    {
        int x = 0, y = 0, z = 0, c = 0, t = 0;
        for(Token current:equation_arr)
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

        for(int  i =0; i< equation_arr.length; i++)
        {
            switch (equation_arr[i].Type())
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
        for(int i = 0; i<equation_arr.length; i++)
        {
            if(equation_arr[i].Stype()==Const.NUM)
                equation_arr[i] =  equation_arr[i].tofloat();
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


    private void Calculate_op_and_func (Token[] arr, boolean with_compare_and_set)
    {
        size_of_stack = 0;
        for (Token current:arr)
        {
            switch (current.Stype())
            {
                case Const.VAR://todo make replace here for dont create array
                case Const.NUM:
                    stack[size_of_stack] = current;
                    size_of_stack++;
                    break;
                case Const.FUNC:
                    if (current.Priority()==5) {
                        if (size_of_stack < 1)
                            throw new IllegalArgumentException("Calculate_op_and_func few nums");
                        if (stack[size_of_stack - 1].Stype() != Const.NUM) {
                            stack[size_of_stack] = current;
                            size_of_stack++;
                        }
                        else{
                            stack[size_of_stack-1] = current.Func.Run(stack[size_of_stack - 1]);
                        }
                    }
                    else
                    if (current.Priority()>=2&&current.Priority()<=4) {
                        if (size_of_stack < 2)
                            throw new IllegalArgumentException("Calculate_op_and_func few nums"+current.toString());
                        if (stack[size_of_stack - 1].Stype() != Const.NUM || stack[size_of_stack - 2].Stype() != Const.NUM){
                            stack[size_of_stack] = current;
                            size_of_stack++;
                        }
                        else{
                            stack[size_of_stack-2] = current.Func.Run(stack[size_of_stack - 2], stack[size_of_stack - 1]);
                            size_of_stack--;
                        }
                    }else {
                        if (!with_compare_and_set) {
                            stack[size_of_stack] = current;
                            size_of_stack++;
                        }//todo with compare and seet dont do its functions
                    }
                    break;


            }
        }
        if (with_compare_and_set&&size_of_stack>1)
            throw new IllegalArgumentException("Calculate_op_and_func too many nums");
    }


    //-------------------------------------------------------------------------------------------------------------



    private int S_M_A_R_T (ArrayList<Token> arr, int begin, int end) {//todo maybe after I will optimize that


        int end_ = end;

        if(arr.get(begin).Stype() == Const.FUNC && (arr.get(begin).Func.equals("-")||arr.get(begin).Func.equals("+"))) {
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
        }




        bml = -1;
        b1 = -1;
        b2 = 0;

        for (int i = begin; i <= end; i++)
            if (b2 == 0&&arr.get(i).Type() == Const.ABSBR) {
                if (bml == -1 && i < end) {
                    if (!((arr.get(i + 1).Priority()>=3&&arr.get(i + 1).Priority()<=4) ||
                            arr.get(i + 1).Stype() == Const.RBR  ||
                            (arr.get(i+1).Stype() == Const.FUNC&& arr.get(i+1).Func.equals("!")))) {
                        bml = i;
                    } else  throw new IllegalArgumentException("vlines in arr @1122");
                } else if (i > 0 && !((arr.get(i - 1).Priority()>=2&&arr.get(i - 1).Priority()<=4)
                        ||  arr.get(i - 1).Stype() == Const.LBR || (arr.get(i-1).Stype() == Const.FUNC&&arr.get(i-1).Priority()==5))) {
                    if (bml == i - 1)
                        throw new IllegalArgumentException("vlines in arr vlines are side by side");
                    if (bml == -1)
                        throw new IllegalArgumentException("vlines in arr right vline without left");
                    arr.set(bml, new Token_d("Abs", 10, true));
                    arr.set(i, new Token_d(Const.RBR));
                    arr.add(bml+1, new Token_d(Const.LBR));
                    end++;
                    end += S_M_A_R_T(arr, bml + 2, i);
                    i=begin-1;
                    b1 = -1;
                    b2 = 0;

                    bml = -1;

                } else if (i < end &&
                        !((arr.get(i + 1).Priority()>=3&&arr.get(i + 1).Priority()<=4) ||
                                arr.get(i + 1).Stype() == Const.RBR ||
                                (arr.get(i+1).Stype() == Const.FUNC&& arr.get(i+1).Func.equals("!")))) {
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
            if (   (    arr.get(i).Stype()  == Const.VAR||
                        arr.get(i).Stype() == Const.RBR ||
                        (arr.get(i).Stype() == Const.FUNC&& arr.get(i).Func.equals("!") )||
                        arr.get(i).Stype() == Const.NUM     )&&
                    (   arr.get(i + 1).Stype()  == Const.VAR ||
                        arr.get(i + 1).Stype() == Const.LBR ||
                        (arr.get(i + 1).Stype() == Const.FUNC&&
                                arr.get(i+1).Priority()==5&&
                                !arr.get(i+1).Func.equals("!")) ||
                        arr.get(i + 1).Stype() == Const.NUM)) {

                arr.add(i+1, new Token_d("*", 10, true));
                end++;
            }

        return end - end_;
    }


}

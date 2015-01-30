package com.dudkovlad.Calc3d.Parser;

import java.util.ArrayList;

/**
 * Created by vlad on 24.10.2014.
 */
public class Equation {


    public static String toString (Token [] arrlist)
    {
        String out = "";
        for (Token elem : arrlist)
            out += elem.toString();//todo num_system
        return out;
    }


    public static ArrayList<Token> toPolishNot(ArrayList<Token> arrayList)
    {
        ArrayList<Token> stack = new ArrayList<Token>(arrayList.size());
        ArrayList<Token> out = new ArrayList<Token>(arrayList.size());

        for (Token current :arrayList)
        {
            switch (current.Stype())
            {
                case Const.VAR:
                case Const.NUM: out.add(current);break;
                case Const.FUNC:
                    if (current.Func.getNumArguments()==1)
                        stack.add(current);
                    else
                    if (current.Func.getNumArguments()==2) {
                        for (int j = stack.size() - 1; j >= 0; j--) {
                            //if (stack.get(j).Stype() != Const.LBR)
                            if (current.Priority()==4 && current.Priority() >= stack.get(j).Priority())
                                j=-1;
                            else
                            if (current.Priority() > stack.get(j).Priority()&&current.Priority()!=5)
                                j=-1;
                            else
                                out.add(stack.remove(j));
                        }
                        stack.add(current);
                    }
                    break;
                case Const.COMMA:
                    for(int j = stack.size()-1;j>=0&&stack.get(j).Stype()!= Const.LBR;j--)
                    {
                        if (j==0)
                            throw new IllegalArgumentException("toPolishNot 1missed left parenthesis");
                        out.add(stack.remove(j));
                    }
                    break;
                case Const.LBR:stack.add(current);break;
                case Const.RBR:
                    for(int j = stack.size()-1;j>=0&&stack.get(j).Stype()!= Const.LBR;j--)
                    {
                        if(j==0)
                            throw new IllegalArgumentException("toPolishNot 2missed left parenthesis");
                        out.add(stack.remove(j));
                    }
                    stack.remove(stack.size()-1);
                    if (stack.size()>0&&(stack.get(stack.size()-1).Stype()==Const.FUNC&&
                                            stack.get(stack.size()-1).Priority()==5)) {
                        out.add(stack.remove(stack.size()-1));
                    }
                    break;
            }
        }
        for(int i = stack.size()-1; i>=0; i--)
            if(stack.get(i).Type()==Const.LBR)
                throw new IllegalArgumentException("toPolishNot 2missed left parenthesis");
            else
                out.add(stack.get(i));

        return out;

    }
/*todo make covert from polish to infix

    public static ArrayList<Element_of_equation> fromPolishNot(ArrayList<Element_of_equation> arrayList)
    {
        ArrayList<ArrayList<Element_of_equation>> stack = new ArrayList<ArrayList<Element_of_equation>>(arrayList.size());
        int stack_last = -1;
        int [] size = new int [arrayList.size()];
        byte [] priority = new byte [arrayList.size()];

        ArrayList<Element_of_equation> h1;
        ArrayList<Element_of_equation> h2;
        byte h1t = 0;
        byte h2t = 0;

        for (int i = 0; i< size.length; i++)
            size[i] = -1;
        ArrayList<Element_of_equation> out = new ArrayList<Element_of_equation>();

        for (int i = 0; i < arrayList.size();i++)
        {
            switch (arrayList.get(i).stype)
            {
                case Const.VAR:
                case Const.NUM:
                    new ArrayList<Element_of_equation>();
                    stack[++stack_last][++size[stack_last]] = arrayList.get(i);
                    priority[stack_last] = 0;
                    break;
                case Const.FUNC: break;
                //
                //h1 = pop(); // выталкиваем первый элемент
                //h2 = pop(); // выталкиваем второй элемент
                //// находим приоритет операции
                //if(c=='+') type = plus;
                //else if(c=='-') type = minus;
                //else if(c=='*') type = multiply;
                //else if(c=='/') type = devide;
                //else if(c=='^') type = power;
                //if(h2->type!=null && h2->type<type) { // если приоритет для 1-го элемента меньше
                //temp[0]='('; temp[1] = '\0'; // берем выражение в скобки
                //h2->val[strlen(h2->val)+2] = '\0';
                //h2->val[strlen(h2->val)+1] = c; // приписываем знак операции
                //h2->val[strlen(h2->val)] = ')';
                //} else {
                //h2->val[strlen(h2->val)+1] = '\0';
                //h2->val[strlen(h2->val)] = c;
                //}
                //strcat(temp, h2->val);
                //if(h1->type!=null && h1->type<type) {  // если приоритет для 2-го элемента меньше
                //strcat(temp, "(");
                //h1->val[strlen(h1->val)+1] = '\0';
                //h1->val[strlen(h1->val)] = ')'; // берем выражение в скобки
                //}
                //strcat(temp, h1->val);
                //strcpy(h2->val, temp); // что бы не выделять память под новый элемент, копируем полученное выражение во второй элемент
                //delete h1; // удаляем первый элемент
                //h2->type = type; // устанавливаем новый приоритет операции
                //push(h2); // добавляем новый элемент в стек
                //}
                //input++;
                //}
                //strcpy(output, (pop())->val); // копируем выражение из вершины стека в строку результата
                //}
                case Const.OPER:
                case Const.COMPARE:
                case Const.SET:
                    h1t = priority[stack_last];
                    h1 = stack[stack_last--];
                    h2t = priority[stack_last];
                    h2 = stack[stack_last--];
                    if(h2t!=0 && h2t<arrayList.get(i).priority) { // если приоритет для 1-го элемента меньше
                        temp[0]='('; temp[1] = '\0'; // берем выражение в скобки
                        h2->val[strlen(h2->val)+2] = '\0';
                        h2->val[strlen(h2->val)+1] = c; // приписываем знак операции
                        h2->val[strlen(h2->val)] = ')';
                    }
                    for(int j = stack.size()-1;j>=0&&stack.get(j).stype!= Const.LBRACK&&(
                            (arrayList.get(i).type== Const.POW&&arrayList.get(i).priority<stack.get(j).priority)||
                                    (arrayList.get(i).priority<=stack.get(j).priority));j--)
                    {
                        out.add(stack.remove(j));

                    }
                    stack.add(arrayList.get(i));
                    break;
            }
        }
        for(int i = stack.size()-1; i>=0; i--)
            out.add(stack.remove(i));

        return out;

    }
*/

    public static ArrayList<Token> Create (String in, int num_system, boolean radians)
    {
// todo make num_system works
        if (in.isEmpty())
            throw new IllegalArgumentException("create equation in is empty");
        String temp_s = in.charAt(0)+"";
        char temp_c, temp_cm, temp_cp;
        int in_length  = in.length();
        ArrayList<Token> equation = new ArrayList<Token>();
        int type=0;
        int num=0;
        int num2=0;
        if (in_length == 1) {
            equation.add(new Token_d(temp_s, num_system, radians));
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
                    else if (temp_cm=='<' || temp_cm == '>' || temp_cm == '³')                          //type = 0 is operator
                        type = 3;
                    else if (temp_cm==' ') {
                        if(temp_cp==0) {
                            equation.add(new Token_d(temp_c+"", num_system, radians));
                        }
                        continue;
                    }
                    else type = 0;
                }
                if (type==1&&(('0'<=temp_c&&temp_c<='9')||
                        (temp_c=='E'&&(temp_cp=='-'||'0'<=temp_cp&&temp_cp<='9'||temp_cp=='@'))||
                        (temp_cm=='@'&&'A'<=temp_c&&temp_c<='Z'||temp_c=='@')||
                        (temp_c=='.'&&(temp_cp=='@'||('0'<=temp_cp&&temp_cp<='9'))))) {
                    if (temp_c == '.') {
                        num++;
                        if (num > 1)
                            throw new IllegalArgumentException("create equation too many dots");
                    }
                    if (temp_c=='E'&& temp_cm != '@'){
                        num2++;
                        if (num2 > 1)
                            throw new IllegalArgumentException("create equation too many E");

                    }
                    temp_s = temp_s + temp_c;
                    if(temp_cp==0) {
                        equation.add(new Token_d(temp_s, num_system, radians));
                    }
                }
                else if (type == 3&&((temp_c=='='&&(temp_cm == '<'||temp_cm == '>'))||(temp_c == '√'&&temp_cm == '³')))
                {
                    temp_s = temp_s + temp_c;
                }
                else if (type == 2&&( 'a' <= temp_c && temp_c <= 'z' || 'A' <= temp_c && temp_c <= 'Z'//todo сделать распознавание 'е' и других констант такими чтобы функции начинались с маленьких букв
                        || '0' <= temp_c && temp_c <= '9' || temp_c == '.')) {
                    temp_s = temp_s + temp_c;
                    if(temp_cp==0) {
                        equation.add(new Token_d(temp_s, num_system, radians));
                    }
                }
                else {
                    equation.add(new Token_d(temp_s, num_system, radians));
                    type = 0;
                    num = 0;
                    if(temp_cp==0) {
                        equation.add(new Token_d(temp_c+"", num_system, radians));
                    }
                }
            }
        }

        return equation;
    }

}

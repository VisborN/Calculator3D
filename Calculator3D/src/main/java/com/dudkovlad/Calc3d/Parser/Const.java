package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 25.05.2014.
 */
public final class Const {
    public static final byte ERROR          = 0;


    public static final byte VAR            = 1;

    public static final byte NUM         = 2;
    public static final byte REAL           = 3;
    public static final byte COMPLEX        = 4;
    public static final byte END_NUM         = 20;




    public static final byte FUNC         = 21;
    public static final byte ROOT           = 22;
    public static final byte SIN            = 23;
    public static final byte COS            = 24;
    public static final byte TAN            = 25;
    public static final byte LOG            = 26;
    public static final byte LN             = 27;
    public static final byte ABS            = 28;
    public static final byte SQRT           = 29;
    public static final byte CBRT           = 30;
    public static final byte END_FUNC         = 40;




    public static final byte FUNCRE       = 41;
    public static final byte FACTORIAL      = 42;
    public static final byte END_FUNCRE       = 60;




    public static final byte OPER         = 61;
    public static final byte POW            = 62;
    public static final byte OPER_PRIOR1    = 63;
    public static final byte DIV            = 64;
    public static final byte MULT           = 65;
    public static final byte PRCNT          = 66;
    public static final byte OPER_PRIOR2    = 67;
    public static final byte PLUS           = 68;
    public static final byte MINUS          = 69;
    public static final byte END_OPER         = 80;




    public static final byte RBRACK        = 81;
    public static final byte RBR            = 82;
    public static final byte RPAR           = 83;
    public static final byte END_RBRACK        = 86;

    public static final byte LBRACK        = 87;
    public static final byte LBR            = 88;
    public static final byte LPAR           = 89;
    public static final byte END_LBRACK        = 99;

    public static final byte VLINE          = 100;



    public static final byte COMPARE      = 101;
    public static final byte EQUAL          = 102;
    public static final byte MORE           = 103;
    public static final byte MORE_OR_EQUAL  = 104;
    public static final byte LESS           = 105;
    public static final byte LESS_OR_EQUAL  = 106;
    public static final byte END_COMPARE      = 120;




    public static final byte SET          = -20;          //ввиду отсутствия соглашения
    public static final byte AND            = -19;          //о приоритете операций над множествами
    public static final byte XOR            = -17;          //приоритеты не расстывлены
    public static final byte OR             = -16;          //Priorities are not defined
    public static final byte DIFF           = -14;          //because of there isn't
    public static final byte END_SET          = -1;           //any general agreement

}

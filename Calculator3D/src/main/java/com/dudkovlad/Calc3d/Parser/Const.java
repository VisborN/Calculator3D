package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 25.05.2014.
 */
public final class Const {

/*

    public enum Const {
        ERROR,
        VAR,
        NUM,REAL,COMPLEX,END_NUM,
        FUNC,ROOT,SIN,COS,TAN,LOG,LN,ABS,SQRT,CBRT,END_FUNC,
        FUNCRE,FACTORIAL,END_FUNCRE,
        OPER,POW,OPER_PRIOR1,DIV,MULT,PRCNT,OPER_PRIOR2,PLUS,MINUS,END_OPER,
        RBR,LBR,ABSBR,
        COMPARE,EQUAL,MORE,LESS,END_COMPARE,
        SET,AND,XOR,OR,DIFF,END_SET
*/


    public static final byte ERROR          = 0;


    public static final byte RBR            = 82;
    public static final byte LBR            = 88;
    public static final byte COMMA          = 99;
    public static final byte ABSBR          = 100;


    public static final byte VAR            = 1;

    public static final byte X              = -6;
    public static final byte Y              = -5;
    public static final byte Z              = -4;
    public static final byte C              = -3;
    public static final byte T              = -2;


    public static final byte NUM            = 2;
    public static final byte REAL           = 3;
    public static final byte COMPLEX        = 4;
    public static final byte END_NUM        = 20;




    public static final byte FUNC           = 21;


}

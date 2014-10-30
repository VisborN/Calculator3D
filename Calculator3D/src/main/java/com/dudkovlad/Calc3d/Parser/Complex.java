package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 29.10.2014.
 */
public abstract class Complex {


    public abstract String toString();

    public abstract double Re();
    public abstract double Im();
    public abstract void Re(double in);
    public abstract void Im(double in);
    public abstract double abs();  // Math.sqrt(re*re + im*im)
    public abstract double phase(); // between -pi and pi

    public abstract Complex plus(Complex b);
    public abstract Complex minus(Complex b);

    public abstract Complex times(Complex b);// return a new Complex object whose value is (this * b)

    public abstract Complex times(double alpha);// return a new object whose value is (this * alpha)


    public abstract Complex conjugate();

    public abstract Complex reciprocal();

    // return a / b
    public abstract Complex divides(Complex b);

    // return 1 / b
    public abstract Complex divides1();

    // return a new Complex object whose value is the complex exponential of this
    public abstract Complex exp();
    // return a new Complex object whose value is the complex sine of this
    public abstract Complex sin();

    // return a new Complex object whose value is the complex cosine of this
    public abstract Complex cos();

    // return a new Complex object whose value is the complex tangent of this
    public abstract Complex tan();

    public abstract Complex log10();

    public abstract Complex log(double base);

    public abstract Complex ln();

    public abstract Complex pow( Complex power );

    public abstract Complex pow( double power );




}

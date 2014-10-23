package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 21.05.2014.
 */
public class Complex {
    private double re;   // the real part
    private double im;   // the imaginary part

    public Complex(double real) {
        re = real;
        im = 0;
    }

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public String toString() {
        if (im == 0) return MyFunc.Double_to_String(re);
        if (im != 1&& im != -1) {
            if (re == 0) return MyFunc.Double_to_String(im) + "i";
            if (im < 0)
                return MyFunc.Double_to_String(re) + MyFunc.Double_to_String(im) + "i";
            return MyFunc.Double_to_String(re) + "+" + MyFunc.Double_to_String(im) + "i";
        }else {
            if (re == 0) return "i";
            if (im < 0)
                return MyFunc.Double_to_String(re) + "-" + "i";
            return MyFunc.Double_to_String(re) + "+" + "i";
        }
    }

    public double Re(){return re;}
    public double Im(){return im;}
    public void Re(double in){ re = in;}
    public void Im(double in){ im = in;}
    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi

    public Complex plus(Complex b) {
        b.re = re + b.re;
        b.im = im + b.im;
        return b;
    }

    public Complex minus(Complex b) {
        b.re = re - b.re;
        b.im   = im - b.im;
        return b;
    }

    public Complex times(Complex b) {// return a new Complex object whose value is (this * b)
        double real = re * b.re - im * b.im;
        double imag = re * b.im + im * b.re;
        b.re = real;
        b.im = imag;
        return b;
    }

    public Complex times(double alpha) {// return a new object whose value is (this * alpha)
        return new Complex(alpha * re, alpha * im);
    }

    public Complex conjugate() {  return new Complex(re, -im); }

    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }


    // return a / b
    public Complex divides(Complex b) {
        return this.times(b.reciprocal());
    }

    // return 1 / b
    public static Complex divides1(Complex b) {
        double scale = b.re*b.re + b.im*b.im;
        b.re = b.re / scale;
        b.re = -b.im / scale;
        b.re = 1 * b.re;
        b.im = 1 * b.im;
        return b;
    }

    // return a new Complex object whose value is the complex exponential of this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public Complex tan() {
        return sin().divides(cos());
    }

    public Complex log10() {
        return this.log(10);
    }

    public Complex log(double base) {
        if(base == 1 || base <= 0) return new Complex(Double.NaN, Double.NaN); // even though base for complex logarithm, possibly, could be negative, I do not want to consider this case
        base = Math.log(base);
        return new Complex( Math.log(this.abs()) / base, this.phase() / base );
    }

    public Complex ln() {
        return new Complex(Math.log(this.abs()),this.phase());
    }

    public Complex pow( Complex power ) {
        return (this.ln().times(power)).exp();
    }

    public Complex pow( double power ) {
        return (this.ln().times(power)).exp();
    }

    public static Complex pow( Complex base, Complex power ) {
        return (base.ln().times(power)).exp();
    }




    // a static version of plus
    public static Complex plus(Complex a, Complex b) {
        a.re = a.re + b.re;
        a.im = a.im + b.im;
        return a;
    }






}

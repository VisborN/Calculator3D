package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 21.05.2014.
 */
public class Complex {
    double re;   // the real part
    double im;   // the imaginary part

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
        if (im != 1) {
            if (re == 0) return MyFunc.Double_to_String(im) + "i";
            return MyFunc.Double_to_String(re) + "+" + MyFunc.Double_to_String(im) + "i";
        }else {
            if (re == 0) return "i";
            return MyFunc.Double_to_String(re) + "+" + "i";
        }
    }

    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi

    public Complex plus(Complex b) {
        Complex a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    public Complex times(Complex b) {// return a new Complex object whose value is (this * b)
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
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
        double lg = Math.log(base);
        return new Complex( Math.log(this.abs()) / lg, this.phase() / lg );
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
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }






}

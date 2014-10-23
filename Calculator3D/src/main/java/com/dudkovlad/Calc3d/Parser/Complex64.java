package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 12.10.2014.
 */
public class Complex64 {
    private float re;   // the real part
    private float im;   // the imaginary part

    public Complex64(float real) {
        re = real;
        im = 0;
    }

    public Complex64(float real, float imag) {
        re = real;
        im = imag;
    }
    public Complex64(double real) {
        re = (float)real;
        im = 0;
    }

    public Complex64(double real, double imag) {
        re = (float)real;
        im = (float)imag;
    }

    public String toString() {
        if (im == 0) return MyFunc.Float_to_String(re);
        if (im != 1&& im != -1) {
            if (re == 0) return MyFunc.Float_to_String(im) + "i";
            if (im < 0)
                return MyFunc.Float_to_String(re) + MyFunc.Float_to_String(im) + "i";
            return MyFunc.Float_to_String(re) + "+" + MyFunc.Float_to_String(im) + "i";
        }else {
            if (re == 0) return "i";
            if (im < 0)
                return MyFunc.Float_to_String(re) + "-" + "i";
            return MyFunc.Float_to_String(re) + "+" + "i";
        }
    }


    public float Re(){return re;}
    public float Im(){return im;}
    public void Re(float in){ re = in;}
    public void Im(float in){ im = in;}
    public float abs()   { return (float)Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
    public float phase() { return (float)Math.atan2(im, re); }  // between -pi and pi

    public Complex64 plus(Complex64 b) {
        b.re = re + b.re;
        b.im = im + b.im;
        return b;
    }

    public Complex64 minus(Complex64 b) {
        b.re = re - b.re;
        b.im = im - b.im;
        return b;
    }

    public Complex64 times(Complex64 b) {// return a new Complex64 object whose value is (this * b)
        float real = re * b.re - im * b.im;
        float imag = re * b.im + im * b.re;
        b.re = real;
        b.im = imag;
        return b;
    }

    public Complex64 times(float alpha) {// return a new object whose value is (this * alpha)
        return new Complex64(alpha * re, alpha * im);
    }

    public Complex64 conjugate() {  return new Complex64(re, -im); }

    public Complex64 reciprocal() {
        float scale = re*re + im*im;
        return new Complex64(re / scale, -im / scale);
    }


    // return a / b
    public Complex64 divides(Complex64 b) {
        return this.times(b.reciprocal());
    }

    // return 1 / b
    public static Complex64 divides1(Complex64 b) {
        float scale = b.re*b.re + b.im*b.im;
        b.re = b.re / scale;
        b.re = -b.im / scale;
        b.re = 1 * b.re;
        b.im = 1 * b.im;
        return b;
    }

    // return a new Complex64 object whose value is the complex exponential of this
    public Complex64 exp() {
        return new Complex64(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex64 object whose value is the complex sine of this
    public Complex64 sin() {
        return new Complex64(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex64 object whose value is the complex cosine of this
    public Complex64 cos() {
        return new Complex64(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex64 object whose value is the complex tangent of this
    public Complex64 tan() {
        return sin().divides(cos());
    }

    public Complex64 log10() {
        return this.log(10);
    }

    public Complex64 log(float base) {
        if(base == 1 || base <= 0) return new Complex64(Float.NaN, Float.NaN); // even though base for complex logarithm, possibly, could be negative, I do not want to consider this case
        base = (float)Math.log(base);
        return new Complex64( Math.log(this.abs()) / base, this.phase() / base );
    }

    public Complex64 ln() {
        return new Complex64(Math.log(this.abs()),this.phase());
    }

    public Complex64 pow( Complex64 power ) {
        return (this.ln().times(power)).exp();
    }

    public Complex64 pow( float power ) {
        return (this.ln().times(power)).exp();
    }

    public static Complex64 pow( Complex64 base, Complex64 power ) {
        return (base.ln().times(power)).exp();
    }//TODO optimize when  you will not lazy




    // a static version of plus
    public static Complex64 plus(Complex64 a, Complex64 b) {
        a.re = a.re + b.re;
        a.im = a.im + b.im;

        return a;
    }




}

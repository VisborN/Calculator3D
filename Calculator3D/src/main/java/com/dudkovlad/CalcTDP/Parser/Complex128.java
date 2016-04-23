package com.dudkovlad.CalcTDP.Parser;

/**
 * Created by vlad on 21.05.2014.
 */
public class Complex128 extends Complex {
    private double re;   // the real part
    private double im;   // the imaginary part

    public Complex128(double real) {
        re = real;
        im = 0;
    }

    public Complex128(double real, double imag) {
        re = real;
        im = imag;
    }

    @Override
    public Complex64 toComplex64()
    {
        return new Complex64(re,im);
    }

    @Override
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

    @Override
    public double Re(){return re;}

    @Override
    public double Im(){return im;}

    @Override
    public void Re(double in){ re = in;}

    @Override
    public void Im(double in){ im = in;}

    @Override
    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)

    @Override
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi

    @Override
    public Complex128 plus(Complex b) {
        ((Complex128)b).re = re + ((Complex128)b).re;
        ((Complex128)b).im = im + ((Complex128)b).im;
        return ((Complex128)b);
    }

    @Override
    public Complex128 minus(Complex b) {
        ((Complex128)b).re = re - ((Complex128)b).re;
        ((Complex128)b).im   = im - ((Complex128)b).im;
        return ((Complex128)b);
    }


    @Override
    public Complex128 times(Complex b) {// return a new Complex object whose value is (this * b)
        double real = re * ((Complex128)b).re - im * ((Complex128)b).im;
        double imag = re * ((Complex128)b).im + im * ((Complex128)b).re;
        ((Complex128)b).re = real;
        ((Complex128)b).im = imag;
        return ((Complex128)b);
    }

    @Override
    public Complex128 times(double alpha) {// return a new object whose value is (this * alpha)
        return new Complex128(alpha * re, alpha * im);
    }

    @Override
    public Complex128 conjugate() {  return new Complex128(re, -im); }

    @Override
    public Complex128 reciprocal() {
        double scale = re*re + im*im;
        return new Complex128(re / scale, -im / scale);
    }


    // return a / b
    @Override
    public Complex128 divides(Complex b) {
        return this.times(b.reciprocal());
    }

    // return 1 / b
    @Override
    public Complex128 divides1() {
        double scale = re*re + im*im;
        re = re / scale;
        re = -im / scale;
        re = 1 * re;
        im = 1 * im;
        return this;
    }

    // return a new Complex object whose value is the complex exponential of this
    @Override
    public Complex128 exp() {
        return new Complex128(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    @Override
    public Complex128 sin() {
        return new Complex128(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    @Override
    public Complex128 cos() {
        return new Complex128(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    @Override
    public Complex128 tan() {
        return sin().divides(cos());
    }

    @Override
    public Complex arcsin()
    {
        return pow(2).times(new Complex128(-1)).plus(new Complex128(1)).pow(0.5)
                .plus(times(new Complex128(0, 1))).ln().times(new Complex128(0, -1));
    }

    @Override
    public Complex arccos()
    {
        return pow(2).times(new Complex128(-1)).plus(new Complex128(1)).pow(0.5)
                .plus(times(new Complex128(0f, 1))).ln().times(new Complex128(0, -1))
                .plus(new Complex128(Math.PI / 2));
    }

    @Override
    public Complex arctan()
    {
        return times(new Complex128(0, -1)).plus(new Complex128(1)).ln()
                .minus(times(new Complex128(0, 1)).plus(new Complex128(1)).ln())
                .times(new Complex128(0, 0.5));
    }

    @Override
    public Complex arccotan()
    {
        return minus(new Complex128(0, 1)).divides(this).ln()
                .minus(plus(new Complex128(0, 1)).divides(this).ln())
                .times(new Complex128(0, 0.5));
    }

    @Override
    public Complex cotan()
    {
        return cos().divides(sin());
    }

    @Override
    public Complex sinh()
    {
        return times(new Complex128(0, 1)).sin().times(new Complex128(0, -1));
    }

    @Override
    public Complex cosh()
    {
        return times(new Complex128(0, 1)).cos();
    }

    @Override
    public Complex tanh()
    {
        return times(new Complex128(0, 1)).tan().times(new Complex128(0, -1));
    }

    @Override
    public Complex arcsinh()
    {
        return times(new Complex128(0, -1)).arcsin().times(new Complex128(0, -1));
    }

    @Override
    public Complex arccosh()
    {
        return times(new Complex128(0, -1)).arccos();
    }

    @Override
    public Complex arctanh()
    {
        return times(new Complex128(0, -1)).tan().times(new Complex128(0, -1));
    }

    @Override
    public Complex128 log10() {
        return this.log(10);
    }

    @Override
    public Complex128 log(double base) {
        if(base == 1 || base <= 0) return new Complex128(Double.NaN, Double.NaN); // even though base for complex logarithm, possibly, could be negative, I do not want to consider this case
        base = Math.log(base);
        return new Complex128( Math.log(this.abs()) / base, this.phase() / base );
    }

    @Override
    public Complex128 ln() {
        return new Complex128(Math.log(this.abs()),this.phase());
    }

    @Override
    public Complex128 pow( Complex power ) {
        return (this.ln().times(power)).exp();
    }

    @Override
    public Complex128 pow( double power ) {
        return (this.ln().times(power)).exp();
    }



//todo complex classes need optimisation




}

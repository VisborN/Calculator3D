package com.dudkovlad.Calc3d.Parser;

/**
 * Created by vlad on 12.10.2014.
 */
public class Complex64 extends Complex{
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

    @Override
    public Complex64 toComplex64()
    {
        return new Complex64(re,im);
    }
    @Override
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


    @Override
    public double Re(){return re;}

    @Override
    public double Im(){return im;}

    @Override
    public void Re(double in){ re = (float)in;}

    @Override
    public void Im(double in){ im = (float)in;}

    @Override
    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)

    @Override
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi

    @Override
    public Complex64 plus(Complex b) {
        ((Complex64)b).re = re + ((Complex64)b).re;
        ((Complex64)b).im = im + ((Complex64)b).im;
        return ((Complex64)b);
    }

    @Override
    public Complex64 minus(Complex b) {
        ((Complex64)b).re = re - ((Complex64)b).re;
        ((Complex64)b).im = im - ((Complex64)b).im;
        return ((Complex64)b);
    }

    @Override
    public Complex64 times(Complex b) {// return a new Complex64 object whose value is (this * b)
        float real = re * ((Complex64)b).re - im * ((Complex64)b).im;
        float imag = re * ((Complex64)b).im + im * ((Complex64)b).re;
        ((Complex64)b).re = real;
        ((Complex64)b).im = imag;
        return ((Complex64)b);
    }

    @Override
    public Complex64 times(double alpha) {// return a new object whose value is (this * alpha)
        return new Complex64(alpha * re, alpha * im);
    }

    @Override
    public Complex64 conjugate() {  return new Complex64(re, -im); }

    @Override
    public Complex64 reciprocal() {
        float scale = re*re + im*im;
        return new Complex64(re / scale, -im / scale);
    }


    // return a / b
    @Override
    public Complex64 divides(Complex b) {
        return this.times(b.reciprocal());
    }

    // return 1 / a
    @Override
    public Complex64 divides1() {
        float scale = re*re + im*im;
        re = re / scale;
        re = -im / scale;
        re = 1 * re;
        im = 1 * im;
        return this;
    }

    // return a new Complex64 object whose value is the complex exponential of this
    @Override
    public Complex64 exp() {
        return new Complex64(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex64 object whose value is the complex sine of this
    @Override
    public Complex64 sin() {
        return new Complex64(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex64 object whose value is the complex cosine of this
    @Override
    public Complex64 cos() {
        return new Complex64(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex64 object whose value is the complex tangent of this
    @Override
    public Complex64 tan() {
        return sin().divides(cos());
    }


    @Override
    public Complex arcsin()
    {
        return pow(2).times(new Complex64(-1f)).plus(new Complex64(1f)).pow(0.5)
                .plus(times(new Complex64(0f, 1f))).ln().times(new Complex64(0f, -1f));
    }

    @Override
    public Complex arccos()
    {
        return pow(2).times(new Complex64(-1f)).plus(new Complex64(1f)).pow(0.5)
                .plus(times(new Complex64(0f, 1f))).ln().times(new Complex64(0f, -1f))
                .plus(new Complex64(Math.PI / 2));
    }

    @Override
    public Complex arctan()
    {
        return times(new Complex64(0f, -1f)).plus(new Complex64(1f)).ln()
                .minus(times(new Complex64(0f, 1f)).plus(new Complex64(1f)).ln())
                .times(new Complex64(0f, 0.5f));
    }

    @Override
    public Complex arccotan()
    {
        return minus(new Complex64(0f, 1f)).divides(this).ln()
                .minus(plus(new Complex64(0f, 1f)).divides(this).ln())
                .times(new Complex64(0f, 0.5f));
    }

    @Override
    public Complex cotan()
    {
        return cos().divides(sin());
    }

    @Override
    public Complex sinh()
    {
        return times(new Complex64(0f, 1f)).sin().times(new Complex64(0f, -1f));
    }

    @Override
    public Complex cosh()
    {
        return times(new Complex64(0f, 1f)).cos();
    }

    @Override
    public Complex tanh()
    {
        return times(new Complex64(0f, 1f)).tan().times(new Complex64(0f, -1f));
    }

    @Override
    public Complex arcsinh()
    {
        return times(new Complex64(0f, -1f)).arcsin().times(new Complex64(0f, -1f));
    }

    @Override
    public Complex arccosh()
    {
        return times(new Complex64(0f, -1f)).arccos();
    }

    @Override
    public Complex arctanh()
    {
        return times(new Complex64(0f, -1f)).tan().times(new Complex64(0f, -1f));
    }

    @Override
    public Complex64 log10() {
        return this.log(10);
    }

    @Override
    public Complex64 log(double base) {
        if(base == 1 || base <= 0) return new Complex64(Float.NaN, Float.NaN); // even though base for complex logarithm, possibly, could be negative, I do not want to consider this case
        base = (float)Math.log(base);
        return new Complex64( Math.log(this.abs()) / base, this.phase() / base );
    }

    @Override
    public Complex64 ln() {
        return new Complex64(Math.log(this.abs()),this.phase());
    }

    @Override
    public Complex64 pow( Complex power ) {
        return (this.ln().times(power)).exp();
    }

    @Override
    public Complex64 pow( double power ) {
        return (this.ln().times(power)).exp();
    }





}

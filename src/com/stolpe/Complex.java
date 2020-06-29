package com.stolpe;

import java.math.BigDecimal;
import java.util.Objects;

public class Complex {
    private final BigDecimal re;   // the real part
    private final BigDecimal im;   // the imaginary part

    // create a new object with the given real and imaginary parts
    public Complex(double real, double imag) {
        re = new BigDecimal(real);
        im = new BigDecimal(imag);
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        String result = "";
        if (im.equals(BigDecimal.ZERO)) result = re + "";
        if (im.compareTo(BigDecimal.ZERO)!=0 && re.equals(BigDecimal.ZERO)) result = (im) + "i";
        if (im.compareTo(BigDecimal.ZERO)!=0 && !re.equals(BigDecimal.ZERO)) result = re + " "+ im+ "i";
        return result;
    }

    // return abs/modulus/magnitude
    public double abs() {
        return Math.hypot(re.doubleValue(), im.doubleValue());
    }

    // return angle/phase/argument, normalized to be between -pi and pi
    public double phase() {
        return Math.atan2(im.doubleValue(), re.doubleValue());
    }

    // return a new Complex object whose value is (this + b)
    public Complex plus(Complex b) {
        Complex a = this;             // invoking object
        double real = a.re.doubleValue() + b.re.doubleValue();
        double imag = a.im.doubleValue() + b.im.doubleValue();
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re.doubleValue() - b.re.doubleValue();
        double imag = a.im.doubleValue() - b.im.doubleValue();
        return new Complex(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re.doubleValue() * b.re.doubleValue() - a.im.doubleValue() * b.im.doubleValue();
        double imag = a.re.doubleValue() * b.im.doubleValue() + a.im.doubleValue() * b.re.doubleValue();
        return new Complex(real, imag);
    }

    // return a new object whose value is (this * alpha)
    public Complex scale(double alpha) {
        return new Complex(alpha * re.doubleValue(), alpha * im.doubleValue());
    }

    // return a new Complex object whose value is the conjugate of this
    public Complex conjugate() {
        return new Complex(re.doubleValue(), -im.doubleValue());
    }

    // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = re.doubleValue()*re.doubleValue() + im.doubleValue()*im.doubleValue();
        return new Complex(re.doubleValue() / scale, -im.doubleValue() / scale);
    }

    // return the real or imaginary part
    public double re() { return re.doubleValue(); }
    public double im() { return im.doubleValue(); }

    // return a / b
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // return a new Complex object whose value is the complex exponential of this
    public Complex exp() {
        return new Complex(Math.exp(re.doubleValue()) * Math.cos(im.doubleValue()), Math.exp(re.doubleValue()) * Math.sin(im.doubleValue()));
    }

    // return a new Complex object whose value is the complex sine of this
    public Complex sin() {
        return new Complex(Math.sin(re.doubleValue()) * Math.cosh(im.doubleValue()), Math.cos(re.doubleValue()) * Math.sinh(im.doubleValue()));
    }

    // return a new Complex object whose value is the complex cosine of this
    public Complex cos() {
        return new Complex(Math.cos(re.doubleValue()) * Math.cosh(im.doubleValue()), -Math.sin(re.doubleValue()) * Math.sinh(im.doubleValue()));
    }

    // return a new Complex object whose value is the complex tangent of this
    public Complex tan() {
        return sin().divides(cos());
    }



    // a static version of plus
    public static Complex plus(Complex a, Complex b) {
        double real = a.re.doubleValue() + b.re.doubleValue();
        double imag = a.im.doubleValue() + b.im.doubleValue();
        Complex sum = new Complex(real, imag);
        return sum;
    }

    // See Section 3.3.
    public boolean equals(Object x) {
        boolean result = false;
        if (x != null && this.getClass() == x.getClass()) {
            Complex that = (Complex) x;
            result = (this.re == that.re) && (this.im == that.im);
        }
        return result;
    }

    // See Section 3.3.
    public int hashCode() {
        return Objects.hash(re, im);
    }

    // sample client for testing
    public static void main(String[] args) {
    }

}
/******************************************************************************
 *  Compilation:  javac Complex.java
 *  Execution:    java Complex
 *
 *  Data type for complex numbers.
 *
 *  The data type is "immutable" so once you create and initialize
 *  a Complex object, you cannot change it. The "final" keyword
 *  when declaring re and im enforces this rule, making it a
 *  compile-time error to change the .re or .im instance variables after
 *  they've been initialized.
 *
 *  % java Complex
 *  a            = 5.0 + 6.0i
 *  b            = -3.0 + 4.0i
 *  Re(a)        = 5.0
 *  Im(a)        = 6.0
 *  b + a        = 2.0 + 10.0i
 *  a - b        = 8.0 + 2.0i
 *  a * b        = -39.0 + 2.0i
 *  b * a        = -39.0 + 2.0i
 *  a / b        = 0.36 - 1.52i
 *  (a / b) * b  = 5.0 + 6.0i
 *  conj(a)      = 5.0 - 6.0i
 *  |a|          = 7.810249675906654
 *  tan(a)       = -6.685231390246571E-6 + 1.0000103108981198i
 *
 ******************************************************************************/

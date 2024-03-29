package com.stolpe;

import javagiac.context;
import javagiac.gen;
import javagiac.giac;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;


public class Complex {
    public static final String PLUS = "+";
    public static final String IMAGINARY = "i";
    public static final String MINUS = "-";
    public static final String TIMES = "*";
    public static final String DIVIDE = "/";
    private final BigDecimal re;   // the real part
    private final BigDecimal im;   // the imaginary part
    private MathContext mathContext = new MathContext(4, RoundingMode.HALF_EVEN);

    private int precisition = 3;

    public MathContext getMathContext() {
        return mathContext;
    }

    public void setMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    // create a new object with the given real and imaginary parts
    public Complex(double real, double imag) {
        re = BigDecimal.valueOf(real);
        im = BigDecimal.valueOf(imag);
    }

    private BigDecimal applySign(String complex, String value, BigDecimal operand){
        BigDecimal result = operand;
        int pos = complex.indexOf(value, 0);
        if (pos>0){
            String sign = complex.substring(pos-1, pos);
            if (sign.startsWith("-")){
                result = operand.negate();
            }
        }
        return result;
    }

    public Complex(String complexString){
        BigDecimal re1 = BigDecimal.ZERO;
        BigDecimal im1 = BigDecimal.ZERO;
        if (complexString==null || complexString.equals("")){
            throw new RuntimeException("No valid parameter.");
        }
        complexString = complexString.replaceAll("\\s",""); //strip all spaces
        complexString = complexString.replaceAll("\\*",""); //strip all "*"
        String[] valueComponents = complexString.split("[+-]");
        List componentList = new ArrayList(Arrays.asList(valueComponents));
        for (Iterator<String> iter = componentList.iterator(); iter.hasNext();){
            String component = iter.next();
            if (!component.equals("")){
                if (component.endsWith(IMAGINARY)){
                    im1 = new BigDecimal(component.substring(0,component.length()-1));
                    im1 = applySign(complexString, component, im1);
                } else {
                    re1 = new BigDecimal(component);
                    re1 = applySign(complexString, component, re1);
                }
            }
        }
        re = re1;
        im = im1;
    }

    private BigDecimal format(BigDecimal formatted){
        return formatted.round(getMathContext()).stripTrailingZeros();
    }
    // return a string representation of the invoking Complex object
    public String toString() {
        String result = "";
        if (im.equals(BigDecimal.ZERO))
            result = format(re) + "";
        if (im.compareTo(BigDecimal.ZERO)!=0 && re.equals(BigDecimal.ZERO))
            result = (format(im)) + "i";
        if (im.compareTo(BigDecimal.ZERO)!=0 && !re.equals(BigDecimal.ZERO))
            if (im.signum()==-1)
                result = format(re) + ""+ format(im)+ "i";
            else
                result = format(re) + PLUS+ format(im)+ "i";
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
        Complex result = binaryOperation(PLUS, this, b);
        return result;
    }

    public Complex binaryOperation(String operation, Complex a, Complex b){
        Complex result = new Complex(0,0);
        String aString = a.toString();
        String bString = b.toString();
        context ctx=new context();
        String expression = "("+aString+")"+operation+"("+bString+")";
        try {
            gen g=new gen(expression,ctx);
            g = giac._evalc(g,ctx);
            g = giac._simplify(g,ctx);
            g = giac._evalf(g,ctx);
            result = new Complex(g.print(ctx));
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // return a new Complex object whose value is (this - b)
    public Complex minus(Complex b) {
        Complex result = binaryOperation(MINUS, this, b);
        return result;
    }

    // return a new Complex object whose value is (this * b)
    public Complex times(Complex b) {
        Complex result = binaryOperation(TIMES, this, b);
        return result;
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
        Complex result = binaryOperation(DIVIDE, this, b);
        return result;
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
            result = (this.re.equals(that.re)) && (this.im.equals(that.im));
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

    public int getPrecisition() {
        return precisition;
    }

    public void setPrecisition(int precisition) {
        this.precisition = precisition;
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

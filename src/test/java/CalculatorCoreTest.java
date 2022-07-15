import com.stolpe.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorCoreTest {

    static {
        try {
            System.out.println("Loading giac java interface");
            //System.load("/usr/local/lib/libgiacjava.so");
            System.loadLibrary("javagiac");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" + e);
        }
    }

    private CalculatorCore calculatorCore;
    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        calculatorCore = new CalculatorCore();
        Complex a = new Complex(5.0, 6.0);
        calculatorCore.enter(a);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        Complex value = new Complex(-3.0, 4.0);
        calculatorCore.add(value);
        value = calculatorCore.getMantissa();
        Complex check = new Complex(2, 10);
        assertEquals(value, check);
    }


    @Test
    void subtract() {
        Complex value = new Complex(-3.0, 4.0);
        calculatorCore.subtract(value);
        value = calculatorCore.getMantissa();
        Complex check = new Complex(8, 2);
        assertEquals(value, check);
    }
    @Test
    void multiply() {
        Complex value = new Complex(-3.0, 4.0);
        calculatorCore.multiply(value);
        value = calculatorCore.getMantissa();
        Complex check = new Complex(-39, 2);
        assertEquals(value, check);
    }
    @Test
    void divide() {
        Complex value = new Complex(-3.0, 4.0);
        calculatorCore.divide(value);
        value = calculatorCore.getMantissa();
        Complex check = new Complex(9.0/25.0, -38.0/25.0);
        assertEquals(value, check);
    }

    @Test
    public void enterValueandGetMantissa() {
        Complex value = new Complex(5, 4);
        calculatorCore.enter(value);
        Complex result = calculatorCore.getMantissa();
        Assertions.assertEquals(value,result);
    }

    @Test
    public void createComplexAndCompareResults() {
        String[] values = {"0", "-4i", " - 5 - 4 i ", "-5", "5+4i", "4i", "5-4i", "5" ,"-4.345-5.335i"};
        for (String s : values) {
            Complex value = new Complex(s);
            calculatorCore.enter(value);
            Complex result = calculatorCore.getMantissa();
            String compareString = s.replaceAll("\\s", "");
            assertEquals(result.toString(), compareString);
        }
    }

}
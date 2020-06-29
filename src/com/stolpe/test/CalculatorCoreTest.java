package com.stolpe.test;

import com.stolpe.CalculatorCore;
import com.stolpe.Complex;
import org.junit.jupiter.api.Test;

class CalculatorCoreTest {
    private CalculatorCore calculatorCore;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        calculatorCore = new CalculatorCore();
        Complex a = new Complex(5.0, 6.0);
        Complex b = new Complex(-3.0, 4.0);
        calculatorCore.enter(a);
        calculatorCore.enter(b);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }
}
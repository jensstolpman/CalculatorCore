package com.stolpe;

import java.util.*;

public class CalculatorCore {
    private final Stack<Complex> stack;

    public CalculatorCore() {
        this.stack = new Stack<Complex>();
    }

    private FunctionArguments getFunctionArguments() {
        FunctionArguments result = new FunctionArguments();
        if (stack.size()>=2){
            result.setA(stack.pop());
            result.setB(stack.pop());
        } else throw new InsufficientArgumentsException("Function call needs two arguments.");
        return result;
    }

    /**
     * Press Enter key
     * @param number
     */
    public void enter(Complex number){
        stack.push(number);
    }

    /**
     * Adds the last two elements of the stack
     * and pushes it on the stack
     */
    public void add(){
        FunctionArguments arguments = getFunctionArguments();
        Complex result = arguments.getB().plus(arguments.getA());
        enter(result);
    }

    /**
     * Subtracts the last element of the stack from the second last
     * and pushes it on the stack
     */
    public void subtract(){
        FunctionArguments arguments = getFunctionArguments();
        Complex result = arguments.getB().minus(arguments.getA());
        enter(result);
    }
    /**
     * Multiplies the last two elements of the stack
     * and pushes it on the stack
     */
    public void multiply(){
        FunctionArguments arguments = getFunctionArguments();
        Complex result = arguments.getB().times(arguments.getA());
        enter(result);
    }

    /**
     * Divides the second last element of the stack from the last
     * and pushes it on the stack
     */
    public void divide(){
        FunctionArguments arguments = getFunctionArguments();
        Complex result = arguments.getB().divides(arguments.getA());
        enter(result);
    }
}

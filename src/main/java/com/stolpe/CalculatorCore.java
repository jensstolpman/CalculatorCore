package com.stolpe;

import java.util.Stack;

public class CalculatorCore {
    private final Stack<Complex> stack;

    public CalculatorCore() {
        this.stack = new Stack<>();
    }

    private com.stolpe.FunctionArguments getTwoFunctionArguments() {
        com.stolpe.FunctionArguments result = new com.stolpe.FunctionArguments();
        if (stack.size()<2)
            throw new com.stolpe.InsufficientArgumentsException("Function call needs two arguments.");
        result.setA(stack.pop());
        result.setB(stack.pop());
        return result;
    }

    /**
     * Pushes the number an the stack
     * @param number complex number
     */
    public void enter(Complex number){
        if (number!=null)
            stack.push(number);
    }

    /**
     * Pops the last two elements from the stack,
     * adds them and pushes the result on the stack.
     * @param number complex number
     */
    public void add(Complex number){
        enter(number);
        executeBinaryFunction( (Complex a, Complex b) -> b.plus(a) );
    }

    /**
     * Pops the last two elements from the stack,
     * subtracts the last element from the second last
     * and pushes the result on the stack.
     * @param number complex number
     */
    public void subtract(Complex number){
        enter(number);
        executeBinaryFunction( (Complex a, Complex b) -> b.minus(a) );
    }
    /**
     * Pops the last two elements from the stack,
     * multiplies them and pushes the result on the stack.
     * @param number complex number
     */
    public void multiply(Complex number){
        enter(number);
        executeBinaryFunction( (Complex a, Complex b) -> b.times(a) );
    }

    /**
     * Pops the last two elements from the stack,
     * divides the second last by the last
     * and pushes the result on the stack.
     * @param number complex number
     */
    public void divide(Complex number){
        enter(number);
        executeBinaryFunction( (Complex a, Complex b) -> b.divides(a) );
    }

    /**
     * Get the last element from the stack
     * @return complex number
     */
    public Complex getMantissa(){
        return stack.peek();
    }

    private void executeBinaryFunction(FunctionBinary command){
        com.stolpe.FunctionArguments arguments = getTwoFunctionArguments();
        Complex result = command.execute(arguments.getA(), arguments.getB());
        enter(result);
    }

    @FunctionalInterface
    interface FunctionBinary {
        Complex execute(Complex a, Complex b);
    }

    @FunctionalInterface
    interface FunctionUnary {
        Complex execute(Complex a);
    }

}

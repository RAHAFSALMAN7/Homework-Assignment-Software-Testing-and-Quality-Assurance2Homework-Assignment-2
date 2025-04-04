package main.najah.code;

public class Calculator {

    // إضافة دالة add
    public int add(int... numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    // إضافة دالة divide
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    // إضافة دالة multiply
    public int multiply(int a, int b) {
        return a * b;
    }

    // إضافة دالة factorial
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot calculate factorial of negative number");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}

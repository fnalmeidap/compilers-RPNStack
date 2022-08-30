package RPNStacker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class RPNStacker{
    File file;
    Scanner input;
    Stack<Integer> stack = new Stack<>();

    protected String _filePath = "";

    private int firstOperand = 0;
    private int secondOperand = 0;
    private int result = 0;
    private char operator;

    public RPNStacker(String filePath) throws FileNotFoundException, Exception {
        file = new File(filePath);
        _filePath = filePath;
    }

    public void run() {
        findFileOrThrow();
        readFileOrThrow();
        getResultOrThrow();
    }
    private void findFileOrThrow() {
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException error) {
            System.out.println("File not found.\n" + error);
        } catch (Exception exception){
            System.out.println("Exception found in findFile: " + exception);
        }
    }

    private void readFileOrThrow() {
        while (input.hasNext()) {
            if (input.hasNextInt()) {
                int number = input.nextInt();
                stack.push(number);
            } else {
                operator = input.next().charAt(0);

                getOperandsOrThrow();
                result = doOperationOrThrow();

                stack.push(result);
            }
        }
        input.close();
    }

    private void getOperandsOrThrow() {
    try {
        if (!stack.isEmpty()) {
            secondOperand = stack.pop();
        } else {
            throw new Exception("Error: Unexpected EMPTY STACK when trying to pop the second operand!");
        }

        if (!stack.isEmpty()) {
            firstOperand = stack.pop();
        } else {
            throw new Exception("Error: Unexpected EMPTY STACK when trying to pop the first operand!");
        }
    } catch (Exception except) {
        System.out.println("Error found in doOperation:" + except);
    }


}

    private int doOperationOrThrow(){
        try {
            switch (operator) {
                case '+':
                    return firstOperand + secondOperand;
                case '-':
                    return firstOperand - secondOperand;
                case '*':
                    return firstOperand * secondOperand;
                case '/':
                    return firstOperand / secondOperand;
                default:
                    throw new Exception("Unexpected Symbol " + operator);
            }
        } catch (Exception except){
            System.out.println("Exception found in doOperation:" + except);
        }
        return 1;
    }

    private void getResultOrThrow() {
        try {
            if (!stack.isEmpty()) {
                System.out.println("File read: " + _filePath);
                System.out.println("Result value: " + stack.pop());
            } else {
                throw new Exception("Unexpected EMPTY STACK when trying to pop the operation result!");
            }
        } catch (Exception except) {
            System.out.println("Exception found in getResult: " + except);
        }
    }
}
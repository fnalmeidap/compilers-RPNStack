package RPNStacker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class RPNStacker{
    File file;
    Scanner input;
    static Stack<Integer> stack = new Stack<>();

    protected String _filePath = "";

    private static int result = 0;
    private char operator;

    public RPNStacker(String filePath) throws FileNotFoundException, Exception {
        file = new File(filePath);
        _filePath = filePath;
    }

    public void run() {
        readFileOrThrow();
        getResultOrThrow();
    }

    public static void printTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    private void readFileOrThrow() {
        List<Token> tokens = null;

        try {
            tokens = getTokens(file);
            printTokens(tokens);
        } catch (FileNotFoundException e) {
            System.out.println("File " + _filePath + " not found!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (tokens != null) {
            doTokenCalculationOrThrow(tokens);
        } else {
            System.out.println("Stack empty.");
        }
    }

    public static List<Token> getTokens(File file) throws FileNotFoundException {
        List<Token> tokens = new ArrayList<>();

        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String lexeme = input.nextLine().trim();
            Token token;

            if (lexeme.matches("[0-9]+")) {
                token = new Token(TokenType.NUM, lexeme);
            } else if (lexeme.equals("+")) {
                token = new Token(TokenType.PLUS, lexeme);
            } else if (lexeme.equals("-")) {
                token = new Token(TokenType.MINUS, lexeme);
            } else if (lexeme.equals("*")) {
                token = new Token(TokenType.STAR, lexeme);
            } else if (lexeme.equals("/")) {
                token = new Token(TokenType.SLASH, lexeme);
            } else {
                input.close();
                throw new RuntimeException("Error: Unexpected character `" + lexeme + "`");
            }

            tokens.add(token);
        }

        input.close();

        return tokens;
    }

    private static void doTokenCalculationOrThrow(List<Token> tokens){
        for (Token token : tokens) {
            if (token.type == TokenType.NUM) {
                int number = Integer.parseInt(token.lexeme);
                stack.push(number);
            } else {
                try {
                    int firstOperand;
                    int secondOperand;

                    if (!stack.isEmpty()) {
                        secondOperand = stack.pop();
                    } else {
                        throw new Exception("Error: EMPTY STACK");
                    }

                    if (!stack.isEmpty()) {
                        firstOperand = stack.pop();
                    } else {
                        throw new Exception("Error: EMPTY STACK");
                    }

                    switch (token.type) {
                        case PLUS -> result = firstOperand + secondOperand;
                        case MINUS -> result = firstOperand - secondOperand;
                        case STAR -> result = firstOperand * secondOperand;
                        case SLASH -> result = firstOperand / secondOperand;
                        default -> {
                        }
                    }
                } catch (Exception except) {
                    System.out.println("Exception found: " + except);
                }

                stack.push(result);
            }
        }
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

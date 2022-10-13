package RPNStacker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LexicalScan {
    public static List<Token> getTokens(File file) throws FileNotFoundException {
        List<Token> tokens = new ArrayList<>();

        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String lexeme = input.nextLine().trim();
            Token token;

            if (Regex.isNum(lexeme)) {
                token = new Token(TokenType.NUM, lexeme);
            } else if (Regex.isPlus(lexeme)) {
                token = new Token(TokenType.PLUS, lexeme);
            } else if (Regex.isMinus(lexeme)) {
                token = new Token(TokenType.MINUS, lexeme);
            } else if (Regex.isStar(lexeme)) {
                token = new Token(TokenType.STAR, lexeme);
            } else if (Regex.isSlash(lexeme)) {
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

}

import RPNStacker.RPNStacker;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, Exception {
        RPNStacker stacker = new RPNStacker("expressions/Calc1.stk");
        stacker.run();
    }
}
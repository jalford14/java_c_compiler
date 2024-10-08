import compiler.Lexer;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: no input file");
            return;
        }

        List<String> tokens = Lexer.run(args[0]);
        System.out.println(tokens);
    }
}

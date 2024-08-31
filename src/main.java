import compiler.Lexer;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: no input file");
            return;
        }

        Lexer.run(args[0]);
    }
}

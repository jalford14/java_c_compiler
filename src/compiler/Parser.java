package compiler;

abstract class Exp {
    Exp(int value) { this.value = value; }
    abstract String to_string();
}

class Constant extends Exp {
    Constant(int _int) { super(_int); }
    public String to_string() {
        return String.format("Constant(%s)", _int);
    }
}

abstract class Statement {
    Statement(String exp) { this.exp = exp; }
    abstract String to_string();
}

class Return extends Statement {
    Return(String exp) { super(exp); }
    public String to_string() {
        return String.format("Return(%s)", exp);
    }
}

class Program {
    String value(String def) {
        return String.format("Program(%s)", def);
    }
}

public class Parser {
    public static pp(List<String> tokens) {
    }

    static String parse_statement(List<String> tokens) {
        expect("return", tokens);
        return_val = parse_exp(tokens);
        expect(";", tokens);
        return Return(return_val).to_string();
    }

    static String expect(String expected, List<String> tokens) {
        String actual = expected.remove(0);
        if (actual != expected) {
            throw new InvalidSyntaxException(
                String.format(
                    "Unexpected token. Expected: '%s'. Actual: '%s'.",
                    expected,
                    actual
                )
            );
        }
    }
}

abstract class Exp {
    Exp(value) { this.value = value; }
    abstract String to_string();
}

class Constant extends Exp {
    Constant(value) { super(_int); }
    public String to_string() {
        return String.format("Constant(%s)", _int);
    }
}

abstract class Statement {
    Statement(exp) { this.exp = exp; }
    abstract String to_string();
}

class Return extends Statement {
    Return(exp) { super(exp); }
    public String to_string() {
        return String.format("Return(%s)", exp);
    }
}

class Program {
    String value(def) {
        return String.format("Program(%s)", def);
    }
}

class parser {
    public static void parse() {
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class compiler {
    public static void main(String []args) {
        if (args.length == 0) {
            System.err.println("Error: no input file");
            return;
        }

        String filename = args[0];
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String substring = "";
                int min = 0;
                boolean inputFound = false;
                boolean validInput = false;

                for(int i = 1; i <= data.length(); i++) {
                    // System.out.println(data.charAt(i));
                    if (Character.isWhitespace(data.charAt(i - 1))) {
                        // ignoring whitespace
                        if (!inputFound) { min += 1; }
                        else {
                            substring = data.substring(min, i);
                            System.out.println("pattern no longer matches (whitespace) -- substring: " + substring);
                            identifyToken(substring);
                            min += substring.length() + 1;
                            inputFound = false;
                            validInput = false;
                        }
                    } else {
                        inputFound = true;
                        substring = data.substring(min, i);
                        // System.out.println("min, i: " + min + ", " + Integer.toString(i));
                        System.out.println(substring);
                        // when tokens are side by side
                        // i.e. int main(var1..
                        //              ^
                        if (identifyToken(substring) == 0 && validInput) {
                            substring = data.substring(min, i - 1);
                            System.out.println("pattern no longer matches -- substring: " + substring);
                            // System.out.println("min, i - 1: " + min + ", " + Integer.toString(i - 1));
                            identifyToken(substring);
                            min += substring.length();
                            i -=  1;
                            inputFound = false;
                            validInput = false;
                        } else if (identifyToken(substring) == 2) {
                            System.out.println("reserved word/symbol -- substring: " + substring);
                            identifyToken(substring);
                            min += substring.length();
                            i -= 1;
                            inputFound = false;
                            validInput = false;
                        } else if (!validInput) {
                            validInput = true;
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '${filename}' does not exist");
            e.printStackTrace();
        }
    }

    private static int identifyToken(String input) {
        // System.out.println("identifyToken -- " + input);
        switch(input) {
            case String s when isMatching(s, "[0-9]+\\b") ->
                System.out.println("identifyToken -- Constant");
            case String s when isMatching(s, "int\\b") -> {
                System.out.println("identifyToken -- int");
                return 2;
            }
            case String s when isMatching(s, "return\\b") -> {
                System.out.println("identifyToken -- return");
                return 2;
            }
            case String s when isMatching(s, "void\\b") -> {
                System.out.println("identifyToken -- void");
                return 2;
            }
            case String s when isMatching(s, "\\(") -> {
                System.out.println("identifyToken -- open parens");
                return 2;
            }
            case String s when isMatching(s, "\\)") -> {
                System.out.println("identifyToken -- close parens");
                return 2;
            }
            case String s when isMatching(s, "\\{") -> {
                System.out.println("identifyToken -- open brace");
                return 2;
            }
            case String s when isMatching(s, "\\}") -> {
                System.out.println("identifyToken -- close brace");
                return 2;
            }
            case String s when isMatching(s, ";") -> {
                System.out.println("identifyToken -- semicolon");
                return 2;
            }
            case String s when isMatching(s, "[a-zA-Z_]\\w*\\b") -> {
                return 1;
            }
            default -> {
                // System.out.println("Can't identify");
                return 0;
            }
        }
        return 1;
    }

    private static boolean matchToken(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private static boolean isMatching(String input, String regex) {
        // System.out.println("input: " + input);
        return Pattern.matches(regex, input);
    }
}

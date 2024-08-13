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

                for(int i = 0; i < data.length(); i++) {
                    System.out.println(data.charAt(i));
                    if (Character.isWhitespace(data.charAt(i))) {
                        // ignoring whitespace
                        if (!inputFound) { min += 1; }
                        else {
                            substring = data.substring(min, i - 1);
                            System.out.println(substring);
                            identifyToken(substring);
                            min += substring.length() + 1;
                            inputFound = false;
                        }
                    } else {
                        inputFound = true;
                        substring = data.substring(min, i);
                        // when tokens are side by side
                        // i.e. int main(var1..
                        //              ^
                        if (identifyToken(substring) == 0) {
                            substring = data.substring(min, i - 1);
                            System.out.println(substring);
                            identifyToken(substring);
                            min += substring.length() + 1;
                            inputFound = false;
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
        switch(input) {
            case String s when isMatching(s, "[a-zA-Z_]\\w*\\b") ->
                System.out.println("Identifier");
            case String s when isMatching(s, "[0-9]+\\b") ->
                System.out.println("Constant");
            case String s when isMatching(s, "int\\b") ->
                System.out.println("int");
            case String s when isMatching(s, "return\\b") ->
                System.out.println("void");
            case String s when isMatching(s, "\\(") ->
                System.out.println("return");
            case String s when isMatching(s, "\\)") ->
                System.out.println("open parens");
            case String s when isMatching(s, "\\{") ->
                System.out.println("close parens");
            case String s when isMatching(s, "\\}") ->
                System.out.println("open brace");
            case String s when isMatching(s, ";") ->
                System.out.println("closed brace");
            default -> {
                System.out.println("Can't identify");
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
        return Pattern.matches(regex, input);
    }
}

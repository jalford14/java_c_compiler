package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

public class Lexer {
    public static List<String> run(String filename) {
        List<String> tokens = new ArrayList<String>();
        
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
                    if (Character.isWhitespace(data.charAt(min))) {
                        // ignoring whitespace
                        if (!inputFound) { min += 1; }
                        else {
                            substring = data.substring(min, i);
                            if (identifyToken(substring) != 0) {
                                tokens.add(substring);
                            }
                            min += substring.length() + 1;
                            i = min + 1;
                            inputFound = false;
                            validInput = false;
                        }
                    } else {
                        inputFound = true;
                        substring = data.substring(min, i);
                        int tokenResult = identifyToken(substring);

                        // when pattern is now invalid but was valid at one point
                        // i.e. int main(..
                        //              ^
                        if (tokenResult == 0 && validInput) {
                            substring = data.substring(min, i - 1);
                            tokens.add(substring);
                            min += substring.length();
                            i = min; // this is really min + 1 because the loop will increase it
                            inputFound = false;
                            validInput = false;
                        } else if (tokenResult == 0 && !validInput) {
                            throw new RuntimeException("Error: Character not recognized: " +
                                    substring);
                        } else if (tokenResult == 2) {
                            min += substring.length();
                            tokens.add(substring);
                            i = min; // this is really min + 1 because the loop will increase it
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
            e.printStackTrace();
        }
        return tokens;
    }

    private static int identifyToken(String input) {
        switch(input) {
            case String s when isMatching(s, "[0-9]+\\b") -> {
                return 1;
            }
            case String s when isMatching(s, "(int\\b|return\\b|void\\b)") -> {
                return 2;
            }
            case String s when isMatching(s, "\\(") -> {
                return 2;
            }
            case String s when isMatching(s, "\\)") -> {
                return 2;
            }
            case String s when isMatching(s, "\\{") -> {
                return 2;
            }
            case String s when isMatching(s, "\\}") -> {
                return 2;
            }
            case String s when isMatching(s, ";") -> {
                return 2;
            }
            case String s when isMatching(s, "[a-zA-Z_]\\w*\\b") -> {
                return 1;
            }
            default -> {
                return 0;
            }
        }
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

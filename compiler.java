import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '${filename}' does not exist");
            e.printStackTrace();
        }
    }
}

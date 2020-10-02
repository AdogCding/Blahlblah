package DecoratorPattern;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        int c;
        try {
            InputStream inputStream =
                    new RandomLowCaseInput(new BufferedInputStream(new FileInputStream("test.txt")));
            while ((c = inputStream.read())>0)
                System.out.print((char)c);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

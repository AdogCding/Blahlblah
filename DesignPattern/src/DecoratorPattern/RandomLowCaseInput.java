package DecoratorPattern;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RandomLowCaseInput extends FilterInputStream {
    private final Random random = new Random();
    public RandomLowCaseInput(InputStream in) {
        super(in);
    }
    public int read() throws IOException {
        int c = in.read();
        int randomInt = random.nextInt(100);
        return (c<0? c: (randomInt>49?Character.toLowerCase(c):Character.toUpperCase(c)));
    }
    public int read(byte[] b, int offset, int len) throws IOException {
        int result = super.read(b, offset, len);
        for(int i = offset; i < offset + result; i++) {
            int randomInt = random.nextInt(100);
            b[i] = (byte) (randomInt > 49 ? Character.toLowerCase(b[i]):Character.toUpperCase(b[i]));
        }
        return result;
    }
}

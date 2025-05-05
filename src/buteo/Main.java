package buteo;

import Core.EventEmulator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var temp = new EventEmulator();

        var t1 = System.nanoTime();
        System.out.println(temp.Emulate(10, 10_000_000, 1));
        var t2 = System.nanoTime();

        System.out.println((double) (t2 - t1) / 1_000_000_000);
        System.out.println("No hello.");
    }
}

package buteo;

import Core.EventEmulator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var temp = new EventEmulator();

//        var t11 = System.nanoTime();
//        System.out.println(temp.Emulate(10, 10_000_000, 1));
//        var t21 = System.nanoTime();
//
//        System.out.println((double) (t21 - t11) / 1_000_000_000);

        var t12 = System.nanoTime();
        System.out.println(temp.Emulate(100, 100, 1));
        var t22 = System.nanoTime();

        System.out.println((double) (t22 - t12) / 1_000_000_000);

        System.out.println(temp.Emulate(100, 5, 2));

        System.out.println(temp.Emulate(95, 95, 2));

        System.out.println("No hello.");
    }
}

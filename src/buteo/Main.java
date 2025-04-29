package buteo;

import Core.EventEmulator;
import Core.EventGeneratorStreamed;
import Core.EventGeneratorListed;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws ExecutionControl.NotImplementedException, IOException {
        var temp = new EventEmulator();

        var t1 = System.nanoTime();
        System.out.println(temp.Emulate(10, 10_000_000, 1));
        var t2 = System.nanoTime();

        System.out.println((double) (t2 - t1) / 1_000_000_000);
    }

    public static void main3(String[] args) {
        long events = 10;
        long clients = 10_000;

        var temp1 = Stream.generate(new EventGeneratorStreamed(events, clients));

        var t11 = System.nanoTime();
        temp1//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t12 = System.nanoTime();

        events = 10;
        clients = 10_000;

        var temp2 = EventGeneratorListed.generate((int) events, (int) clients).stream();
        var t21 = System.nanoTime();
        temp2//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t22 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp3 = EventGeneratorListed.generate((int) events, (int) clients).stream();
        var t31 = System.nanoTime();
        temp3//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t32 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp4 = Stream.generate(new EventGeneratorStreamed(events, clients)).parallel();
        var t41 = System.nanoTime();
        temp4//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t42 = System.nanoTime();

        events = 10;
        clients = 10_000;

        var temp5 = EventGeneratorListed.generate((int) events, (int) clients);
        var t51 = System.nanoTime();
        temp5//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t52 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp6 = EventGeneratorListed.generate((int) events, (int) clients);
        var t61 = System.nanoTime();
        temp6//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t62 = System.nanoTime();

        System.out.println((double) (t12 - t11) / 1_000_000_000);
        System.out.println((double) (t22 - t21) / 1_000_000_000);
        System.out.println((double) (t32 - t31) / 1_000_000_000);
        System.out.println((double) (t42 - t41) / 1_000_000_000);
        System.out.println((double) (t52 - t51) / 1_000_000_000);
        System.out.println((double) (t62 - t61) / 1_000_000_000);
        System.out.println("No hello.");
    }

    public static void main2(String[] args) {
        long events = 10;
        long clients = 10_000;

        var temp1 = Stream.generate(new EventGeneratorStreamed(events, clients));

        var t11 = System.nanoTime();
        temp1//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t12 = System.nanoTime();

        events = 10;
        clients = 10_000;

        var temp2 = EventGeneratorListed.generate((int) events, (int) clients).stream();
        var t21 = System.nanoTime();
        temp2//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t22 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp3 = EventGeneratorListed.generate((int) events, (int) clients).stream();
        var t31 = System.nanoTime();
        temp3//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t32 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp4 = Stream.generate(new EventGeneratorStreamed(events, clients)).parallel();
        var t41 = System.nanoTime();
        temp4//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t42 = System.nanoTime();

        events = 10;
        clients = 10_000;

        var temp5 = EventGeneratorListed.generate((int) events, (int) clients).stream();
        var t51 = System.nanoTime();
        temp5//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t52 = System.nanoTime();

        events = 10;
        clients = 10_000_00;

        var temp6 = EventGeneratorListed.generate((int) events, (int) clients);
        var t61 = System.nanoTime();
        temp6//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                //.limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t62 = System.nanoTime();

        System.out.println((double) (t12 - t11) / 1_000_000_000);
        System.out.println((double) (t22 - t21) / 1_000_000_000);
        System.out.println((double) (t32 - t31) / 1_000_000_000);
        System.out.println((double) (t42 - t41) / 1_000_000_000);
        System.out.println((double) (t52 - t51) / 1_000_000_000);
        System.out.println((double) (t62 - t61) / 1_000_000_000);
        System.out.println("No hello.");
    }

    public static void main1(String[] a) {
        long events = 10;
        long clients = 10_000;

        var temp1 = Stream.generate(new EventGeneratorStreamed(events, clients));

        var t11 = System.nanoTime();
        temp1//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t12 = System.nanoTime();

        events = 10;
        clients = 10_000;

        var temp2 = Stream.generate(new EventGeneratorStreamed(events, clients)).parallel();
        var t21 = System.nanoTime();
        temp2//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t22 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp3 = Stream.generate(new EventGeneratorStreamed(events, clients));
        var t31 = System.nanoTime();
        temp3//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t32 = System.nanoTime();

        events = 10;
        clients = 10_000_0;

        var temp4 = Stream.generate(new EventGeneratorStreamed(events, clients)).parallel();
        var t41 = System.nanoTime();
        temp4//.parallel()
                //.filter(e -> e != null) //Objects::nonNull??
                .limit(events * clients)
                .forEach(e -> System.out.println(e.toString()));
        var t42 = System.nanoTime();

        System.out.println((double) (t12 - t11) / 1_000_000_000);
        System.out.println((double) (t22 - t21) / 1_000_000_000);
        System.out.println((double) (t32 - t31) / 1_000_000_000);
        System.out.println((double) (t42 - t41) / 1_000_000_000);
        System.out.println("No hello.");
    }
}

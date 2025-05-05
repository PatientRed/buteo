package Core;

import DataLayer.CSV.CSV;

import java.util.Scanner;

public class EventEmulator extends EventEmulatorBase<String> {
    @Override
    protected void askInputRedirect() {
        System.out.println("Input file?");
        storage.redirectInput(new Scanner(System.in).nextLine());

        return;
    }

    @Override
    protected void askOutputRedirect() {
        System.out.println("Output file?");
        storage.redirectOutput(new Scanner(System.in).nextLine());

        return;
    }

    public EventEmulator() {
        storage = new CSV("abc.csv");
    }
}

package Core;

import DataLayer.CSV.CSV;

public class EventEmulator extends EventEmulatorBase<String> {
    public EventEmulator() {
        storage = new CSV("abc.csv");
    }
}

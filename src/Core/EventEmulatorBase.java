package Core;

import DataLayer.EventsDataStorage;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.stream.Stream;

public class EventEmulatorBase<TStorage> {
    protected EventsDataStorage<TStorage> storage;

    //decompose, no switches on ints pls...
    public String Emulate(int eventCount, int clientCount, int executionPath) throws IOException, ExecutionControl.NotImplementedException {
        return switch (executionPath) {
            case EventEmulatorBase.executionPath.GENERATE -> generate(eventCount, clientCount);
            case EventEmulatorBase.executionPath.READ -> read(eventCount, clientCount);
            default -> throw new IllegalArgumentException("Incorrect executionPath provided.");
        };
    }

    private String generate(int events, int clients) throws IOException {
        //return storage.write(EventGeneratorListed.generate(events, clients));
        return storage.write(Stream.generate(new EventGeneratorStreamed(events, clients)).limit(events * clients));
    }

    private static String read(int events, int clients) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }

    public static class executionPath {
        public static final int GENERATE = 1;
        public static final int READ = 2;
    }
}

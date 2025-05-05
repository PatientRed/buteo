package Core;

import DataLayer.EventsDataStorage;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.stream.Stream;

public class EventEmulatorBase<TStorage> {
    protected EventsDataStorage<TStorage> storage;

    //decompose, no switches on ints pls...
    public String Emulate(int eventCount, int clientCount, int executionPath) throws IOException {
        return switch (executionPath) {
            case EventEmulatorBase.executionPath.GENERATE -> generate(eventCount, clientCount);
            case EventEmulatorBase.executionPath.TAKE -> process(eventCount, clientCount);
            default -> throw new IllegalArgumentException("Incorrect executionPath provided.");
        };
    }

    private String generate(int events, int clients) throws IOException {
        //return storage.write(EventGeneratorListed.generate(events, clients));
        return storage.write(Stream.generate(new EventGeneratorStreamed(events, clients)).limit(events * clients));

    private static Stream<Event> visitEvents(Stream<Event> original, Set<Event> visited) {
        return original.map(ev -> visited.contains(ev) ? visitEvent(ev) : ev);
    }

    //this is a part of Event. please move.
    private static Event visitEvent(Event ev) {
        return new Event(ev.eventId(), ev.clientId(), true);
    }

    public static class executionPath {
        public static final int GENERATE = 1;
        public static final int TAKE = 2;
    }
}

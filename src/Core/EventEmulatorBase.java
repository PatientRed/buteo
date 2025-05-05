package Core;

import DataLayer.EventsDataStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventEmulatorBase<TStorage> {
    protected EventsDataStorage<TStorage> storage;

    protected void askInputRedirect() {
        return;
    }

    protected void askOutputRedirect() {
        return;
    }

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
        return storage.writeAll(Stream.generate(new EventGeneratorStreamed(events, clients)).limit(events * clients));
    }

    private String process(int events, int clients) throws IOException {
        askInputRedirect();
        //thats not optimal but to prove we can guarantee the result and since the file cannot be modified in-place
        //we took all of its contents into memory [O(n) bytes]
        //straight alternative will be reading the file multiple times:
        //1. reading for cache map eventId-not visited clients counting
        //2. then read-write of selection saving written events in memory
        //3. and finally reading file third time to rewrite updated contents (using temp file?)
        var contents = storage.readAll().toArray(Event[]::new);

        //reuse required so additional array created. SEEMS NOT OPTIMAL?
        var selected = takeFiltered(events, clients, Arrays.stream(contents)).toArray(Event[]::new);

        storage.redirectOutput(newFile(storage.getInputPath()).toString());
        var result = storage.writeAll(Arrays.stream(selected));

        storage.redirectOutput(storage.getInputPath());
        storage.writeAll(visitEvents(Arrays.stream(contents), Arrays.stream(selected).collect(Collectors.toSet())));

        return result;
    }

    private static Path newFile(String old) {
        var lastDotIndex = old.lastIndexOf('.');

        if (lastDotIndex == -1)
            return Path.of(old + "_new");

        return Path.of(old.substring(0, lastDotIndex) + "_new" + old.substring(lastDotIndex));
    }

    private static Stream<Event> takeFiltered(int events, int clients, Stream<Event> eventStream) throws RuntimeException {
        var eventIds = eventStream.filter(ev -> !ev.visited())
                .collect(Collectors.groupingBy(ev -> ev.eventId()));

        if (eventIds.size() < events)
            throw new RuntimeException("Not enough events provided.");

        var selected = eventIds.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, kvEntry -> kvEntry.getValue().size()))
                .entrySet().stream()
                .filter(kvEntry -> kvEntry.getValue() >= clients)
                .limit(events)
                .mapToLong(kvEntry -> kvEntry.getKey())
                .toArray();

        if (selected.length < events)
            throw new RuntimeException("Not enough clients provided.");

        var result = new ArrayList<Event>(events * clients);

        for (int i = 0; i < clients; i++) {
            for (var evId : selected) {
                result.add(eventIds.get(evId).get(i));
            }
        }

        return result.stream();
    }

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

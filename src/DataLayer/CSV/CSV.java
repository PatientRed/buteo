package DataLayer.CSV;

import Core.Event;
import DataLayer.EventsDataStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CSV implements EventsDataStorage<String> {
    private final String HEADER = "event_id,client_id,visited";
    private Path INPUT_PATH;
    private Path OUTPUT_PATH;
    private final String DELIMITER;

    @Override
    public String writeAll(Stream<Event> stream) throws IOException {
        try (var bufferedFile = Files.newBufferedWriter(OUTPUT_PATH,
                new StandardOpenOption[]{StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE})) {
            bufferedFile.write(HEADER);
            bufferedFile.newLine();

            stream.forEach(ev -> {
                try {
                    bufferedFile.write(serializeEvent(ev));
                    bufferedFile.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return OUTPUT_PATH.toString();
    }

    @Override
    public String writeAll(List<Event> events) throws IOException {
        try (var bufferedFile = Files.newBufferedWriter(OUTPUT_PATH,
                new StandardOpenOption[]{StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE})) {
            bufferedFile.write(HEADER);
            bufferedFile.newLine();

            events.forEach(ev -> {
                try {
                    bufferedFile.write(serializeEvent(ev));
                    bufferedFile.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return OUTPUT_PATH.toString();
    }

    //if we don't consume stream before method exit it then cannot be consumed coz file is closed
    public Stream<Event> readAll(Path path) {
        try (var data = Files.lines(path)) {
            return Arrays.stream(data.skip(1).map(this::deSerializeEvent).toArray(Event[]::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<Event> readAll(String path) {
        return readAll(Path.of(path));
    }

    @Override
    public Stream<Event> readAll() {
        return readAll(INPUT_PATH);
    }

    @Override
    public String getInputPath() {
        return INPUT_PATH.toString();
    }

    @Override
    public String getOutputPath() {
        return OUTPUT_PATH.toString();
    }

    @Override
    public void redirectInput(String path) {
        INPUT_PATH = Path.of(path);
    }

    @Override
    public void redirectOutput(String path) {
        OUTPUT_PATH = Path.of(path);
    }

    @Override
    public Event deSerializeEvent(String line) throws NumberFormatException, SecurityException {
        var temp = line.split(DELIMITER);
        return new Event(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Boolean.parseBoolean(temp[2]));
    }

    @Override
    public String serializeEvent(Event input) {
        return Long.toString(input.eventId()) + DELIMITER + Long.toString(input.clientId())
                + DELIMITER + (input.visited() ? "true" : "false");
    }

    @Deprecated
    public String serializeNewEventRaw(int eventId, int clientId) {
        return Long.toString(eventId) + DELIMITER + Long.toString(clientId) + DELIMITER + "false";
    }

    public CSV(String path, char delimiter) {
        this.INPUT_PATH = Path.of(path);
        this.OUTPUT_PATH = this.INPUT_PATH;
        this.DELIMITER = Character.toString(delimiter);
    }

    public CSV(String path) {
        this(path, ',');
    }
}

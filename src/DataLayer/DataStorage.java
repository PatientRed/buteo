package DataLayer;

import Core.Event;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

//for now mixing what to store and how to do it, maybe split into separate storage and transfer entities
public interface DataStorage<TStorage, TEntity> {
    public TEntity deSerializeEvent(TStorage input);
    public TStorage serializeEvent(TEntity input);

    public String writeAll(Stream<Event> stream) throws IOException;
    public String writeAll(List<Event> events) throws IOException;

    public Stream<Event> readAll(String source);
    public Stream<Event> readAll();

    public String getInputPath();
    public String getOutputPath();

    public void redirectInput(String source);
    public void redirectOutput(String source);
}

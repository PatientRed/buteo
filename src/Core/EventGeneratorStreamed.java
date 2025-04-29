package Core;

import java.util.function.Supplier;

//seems to break using conventions? incorrect usage of infinite stream concept?
//check if some interface can be implemented to specify generation of finite sequences
public class EventGeneratorStreamed implements Supplier<Event> {
    private final long EVENTS;
    private final long CLIENTS;
    private long currEvent = 1;
    private long currClientGroup = 1;
    private boolean ended = false;

    //starts returning null if limits exceed
    @Override
    public Event get() {
        if (ended)
            return null;

        var result = new Event(currEvent, currClientGroup + ((currEvent - 1) * CLIENTS), false);

        if (currEvent != EVENTS)
            currEvent++;
        else if (currClientGroup != CLIENTS) {
            currClientGroup++;
            currEvent = 1;
        } else {
            currEvent = 1;
            currClientGroup = 1;
            ended = true;
        }

        return result;
    }

    public EventGeneratorStreamed(long events, long clients) {
        if (events <= 0 || clients <= 0)
            throw new IllegalArgumentException("Please use positive numbers of clients and events.");

        this.EVENTS = events;
        this.CLIENTS = clients;
    }
}

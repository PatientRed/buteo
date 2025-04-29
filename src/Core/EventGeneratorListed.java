package Core;

import java.util.ArrayList;
import java.util.List;

//No more than Integer.MAX_VALUE events*clients
public class EventGeneratorListed {
    public static List<Event> generate(int events, int clients) {
        var result = new ArrayList<Event>(events * clients); //possible overflow

        for (int i = 1; i <= clients; i++)
            for (int j = 1; j <= events; j++)
                result.add(new Event(j, i, false));

        return result;
    }
}

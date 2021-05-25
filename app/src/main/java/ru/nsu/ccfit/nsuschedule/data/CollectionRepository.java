package ru.nsu.ccfit.nsuschedule.data;

import java.util.Collection;

import ru.nsu.ccfit.nsuschedule.data.json_repository.EventList;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class CollectionRepository extends AbstractRepository {
    private EventList eventList;

    public CollectionRepository(Collection<Event> events) {
        eventList = new EventList();
        for (Event event : events) {
            eventList.addEvent(event);
        }
    }

    @Override
    protected void writeEventList(EventList eventList) {
        this.eventList = eventList;
    }

    @Override
    protected EventList readEventList() {
        return eventList;
    }
}

package ru.nsu.ccfit.nsuschedule.data;

import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import ru.nsu.ccfit.nsuschedule.data.json_repository.EventList;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public abstract class AbstractRepository implements Repository {
    @Override
    public void addEvents(Iterable<Event> events) throws RepositoryException {
        changeEventList(eventList -> {
                    for (Event event : events) eventList.addEvent(event);
                    return eventList;
                }
        );
    }

    @Override
    public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
        return readEventList().getEventList().stream()
                .filter(event ->
                        event.getDate().occursBetweenDates(startDate, endDate))
                .collect(Collectors.toList());
    }

    @Override
    public void addEvent(Event event) throws RepositoryException {
        changeEventList(eventList -> {
            eventList.addEvent(event);
            return eventList;
        });
    }

    @Override
    public void removeEvent(Event event) throws RepositoryException {
        changeEventList(eventList -> {
            eventList.remove(event);
            return eventList;
        });
    }

    private void changeEventList(UnaryOperator<EventList> function) throws RepositoryException {
        writeEventList(function.apply(readEventList()));
    }

    protected abstract void writeEventList(EventList eventList) throws RepositoryException;

    protected abstract EventList readEventList() throws RepositoryException;
}

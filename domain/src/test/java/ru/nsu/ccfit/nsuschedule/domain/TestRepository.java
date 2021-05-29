package ru.nsu.ccfit.nsuschedule.domain;

import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class TestRepository implements Repository {
    @Override
    public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
        return null;
    }

    @Override
    public void addEvent(Event event) throws RepositoryException {

    }

    @Override
    public void removeEvent(Event event) throws RepositoryException {

    }

    @Override
    public void updaterEvent(Event oldEvent, Event newEvent) throws RepositoryException {

    }

    @Override
    public void addEvents(Iterable<Event> events) throws RepositoryException {

    }

    @Override
    public Iterable<Event> getEvents() throws RepositoryException {
        return null;
    }
}

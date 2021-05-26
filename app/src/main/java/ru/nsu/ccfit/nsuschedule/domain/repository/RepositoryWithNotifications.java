package ru.nsu.ccfit.nsuschedule.domain.repository;

import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.usecases.SetupNextNotification;

public class RepositoryWithNotifications implements Repository {
    private final Repository repository;
    private final SetupNextNotification setupNextNotification;

    public RepositoryWithNotifications(Repository repository, SetupNextNotification setupNextNotification) {
        this.repository = repository;
        this.setupNextNotification = setupNextNotification;
    }

    @Override
    public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
        return repository.getEventsInRange(startDate, endDate);
    }

    @Override
    public void addEvent(Event event) throws RepositoryException {
        repository.addEvent(event);
        setupNextNotification.invoke();
    }

    @Override
    public void removeEvent(Event event) throws RepositoryException {
        repository.removeEvent(event);
        setupNextNotification.invoke();
    }

    @Override
    public void addEvents(Iterable<Event> events) throws RepositoryException {
        repository.addEvents(events);
        setupNextNotification.invoke();
    }

    @Override
    public Iterable<Event> getEvents() throws RepositoryException {
        return repository.getEvents();
    }
}

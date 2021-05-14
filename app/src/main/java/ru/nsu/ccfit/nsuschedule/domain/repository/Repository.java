package ru.nsu.ccfit.nsuschedule.domain.repository;

import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public interface Repository {
    List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException;
    void addEvent(Event event) throws RepositoryException;
    void removeEvent(Event event) throws RepositoryException;
}

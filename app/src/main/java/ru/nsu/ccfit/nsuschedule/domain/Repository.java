package ru.nsu.ccfit.nsuschedule.domain;

import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public interface Repository {
    List<Event> getEventsInRange(Date startDate, Date endDate);
    void addEvent(Event event);
}

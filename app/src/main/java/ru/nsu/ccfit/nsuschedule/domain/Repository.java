package ru.nsu.ccfit.nsuschedule.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public interface Repository {
    List<Event> getEventsInRange(Date startDate, Date endDate) throws IOException;
    void addEvent(Event event) throws IOException;
}

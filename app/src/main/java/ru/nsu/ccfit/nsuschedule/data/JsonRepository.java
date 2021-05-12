package ru.nsu.ccfit.nsuschedule.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.Repository;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class JsonRepository implements Repository {
    @Override
    public List<Event> getEventsInRange(Date startDate, Date endDate) {
        return new ArrayList<>();
    }

    @Override
    public void addEvent(Event event) {
    }
}

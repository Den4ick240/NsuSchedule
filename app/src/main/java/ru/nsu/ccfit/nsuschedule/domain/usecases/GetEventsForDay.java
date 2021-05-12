package ru.nsu.ccfit.nsuschedule.domain.usecases;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.Repository;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class GetEventsForDay {
    private final Repository repository;

    public GetEventsForDay(Repository repository) {
        this.repository = repository;
    }

    public List<Event> getEvents(Date day) throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE, 1);
        return repository.getEventsInRange(day, calendar.getTime());
    }
}

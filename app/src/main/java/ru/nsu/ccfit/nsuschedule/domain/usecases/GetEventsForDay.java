package ru.nsu.ccfit.nsuschedule.domain.usecases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class GetEventsForDay {
    private final Repository repository;

    public GetEventsForDay(Repository repository) {
        this.repository = repository;
    }

    public List<Event> getEvents(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE, 1);
        try {
            return repository.getEventsInRange(day, calendar.getTime());
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

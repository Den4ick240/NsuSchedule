package ru.nsu.ccfit.nsuschedule.domain.usecases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventDate;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventInfo;
import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;

public class GetEventsForDay {
    public List<Event> getEvents(Date day) {
        ArrayList<Event> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.HOUR, 1);
        events.add(new Event(new EventInfo("test description", "test summary", "test location"), new EventDate(Repeating.ONCE,
                day, calendar.getTime())));
        return events;
    }
}

package ru.nsu.ccfit.nsuschedule.domain.entities;


import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class ScheduleEvent implements Serializable {
    private final Event e;
    private final EventOccurrence eventOccurrence;
    private final DateFormat format;

    public ScheduleEvent(Event event, EventOccurrence eventOccurrence, DateFormat timeFormat) {
        e = event;
        this.eventOccurrence = eventOccurrence;
        this.format = timeFormat;
    }

    public String getSummary() {
        return e.getInfo().getSummary();
    }

    public String getDescription() {
        return e.getInfo().getDescription();
    }

    public String getLocation() {
        return e.getInfo().getLocation();
    }

    public String getTime() {
        return format.format(eventOccurrence.getStartDate()) + "-" + format.format(eventOccurrence.getEndDate());
    }

    public Date getStartTime() {
        return eventOccurrence.getStartDate();
    }

    public Event getEvent() {
        return e;
    }
}

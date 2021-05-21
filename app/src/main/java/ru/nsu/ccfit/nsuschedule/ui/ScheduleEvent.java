package ru.nsu.ccfit.nsuschedule.ui;


import java.text.DateFormat;
import java.util.Date;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventOccurrence;

public class ScheduleEvent {
    private final Event e;
    private final EventOccurrence eventOccurrence;
    private final DateFormat format;

    public ScheduleEvent(Event event, EventOccurrence eventOccurrence, DateFormat timeFormat) {
        e = event;
        this.eventOccurrence = eventOccurrence;
        this.format = timeFormat;
    }

    public CharSequence getSummary() {
        return e.getInfo().getSummary();
    }

    public CharSequence getDescription() {
        return e.getInfo().getDescription();
    }

    public CharSequence getLocation() {
        return e.getInfo().getLocation();
    }

    public CharSequence getTime() {
        return format.format(eventOccurrence.getStartDate()) + "-" + format.format(eventOccurrence.getEndDate());
    }

    public Date getStartTime() {
        return eventOccurrence.getStartDate();
    }

    public Event getEvent() {
        return e;
    }
}

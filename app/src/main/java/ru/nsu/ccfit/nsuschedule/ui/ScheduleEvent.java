package ru.nsu.ccfit.nsuschedule.ui;


import java.text.DateFormat;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventOccurrence;

public class ScheduleEvent {
    private final Event e;
    private final EventOccurrence eventOccurrence;

    public ScheduleEvent(Event event, EventOccurrence eventOccurrence) {
        e = event;
        this.eventOccurrence = eventOccurrence;
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
        DateFormat format = DateFormat.getDateTimeInstance();
        return format.format(eventOccurrence.getStartDate()) + "-" + format.format(eventOccurrence.getEndDate());
    }

    public Event getEvent() {
        return e;
    }
}

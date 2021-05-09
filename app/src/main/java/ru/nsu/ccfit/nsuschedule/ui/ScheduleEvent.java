package ru.nsu.ccfit.nsuschedule.ui;


import java.text.DateFormat;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class ScheduleEvent {
    private final Event e;

    public ScheduleEvent(Event event) {
        e = event;
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
        DateFormat format = DateFormat.getTimeInstance();
        return format.format(e.getDate().getStartDate()) + "-" + format.format(e.getDate().getEndDate());
    }
}

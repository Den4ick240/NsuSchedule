package ru.nsu.ccfit.nsuschedule.domain.entities;

public class Event {
    public Event(EventInfo eventInfo, EventDate eventDate) {
        info = eventInfo;
        date = eventDate;
    }

    private final EventInfo info;
    private final EventDate date;

    public EventInfo getInfo() {
        return info;
    }

    public EventDate getDate() {
        return date;
    }
}

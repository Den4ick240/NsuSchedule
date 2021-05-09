package ru.nsu.ccfit.nsuschedule.domain.entities;

public class Event {
    private final EventInfo info;
    private final EventDate date;

    public Event(EventInfo info, EventDate date) {
        this.info = info;
        this.date = date;
    }

    public EventInfo getInfo() {
        return info;
    }

    public EventDate getDate() {
        return date;
    }
}

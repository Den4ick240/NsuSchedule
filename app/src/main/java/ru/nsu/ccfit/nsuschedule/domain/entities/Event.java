package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Event implements Serializable {

    public Event(EventInfo eventInfo, EventDate eventDate) {
        info = eventInfo;
        date = eventDate;
    }

    public Event() {
        this.date = new EventDate();
        this.info = new EventInfo();
    }

    private final EventInfo info;
    private final EventDate date;

    public EventInfo getInfo() {
        return info;
    }

    public EventDate getDate() {
        return date;
    }

    @NotNull
    @Override
    public String toString() {
        return "Event {" + info + " " + date + "}";
    }
}

package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Event implements Serializable {

    public Event(EventInfo eventInfo, EventDate eventDate) {
        this(eventInfo, eventDate, true, false);
    }

    public Event(EventInfo eventInfo, EventDate eventDate, boolean notificationsOn, boolean alarmsOn) {
        info = eventInfo;
        date = eventDate;
        this.notificationsOn = notificationsOn;
        this.alarmsOn = alarmsOn;
    }

    public Event() {
        this.date = new EventDate();
        this.info = new EventInfo();
        notificationsOn = true;
        alarmsOn = false;
    }

    private final EventInfo info;
    private final EventDate date;
    private final boolean alarmsOn;
    private final boolean notificationsOn;

    public EventInfo getInfo() {
        return info;
    }

    public EventDate getDate() {
        return date;
    }

    public boolean isAlarmsOn() {
        return alarmsOn;
    }

    public boolean isNotificationsOn() {
        return notificationsOn;
    }

    @NotNull
    @Override
    public String toString() {
        return "Event {" + info + " " + date + "}";
    }
}

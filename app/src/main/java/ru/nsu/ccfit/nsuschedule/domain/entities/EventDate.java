package ru.nsu.ccfit.nsuschedule.domain.entities;

import java.util.Date;

public class EventDate {
    private final Repeating repeating;
    private final Date startDate;
    private final Date endDate;

    public EventDate(Repeating repeating, Date startDate, Date endDate) {
        this.repeating = repeating;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Repeating getRepeating() {
        return repeating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

package ru.nsu.ccfit.nsuschedule.domain.entities;

import java.util.Date;

public class EventDate {

    public EventDate(Date startDate, Date endDate, Repeating repeating) {

        this(startDate, endDate, repeating, endDate);
    }

    public EventDate(Date startDate, Date endDate, Repeating repeating, Date repeatUntilDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeating = repeating;
        this.repeatUntilDate = repeatUntilDate;
    }

    private final Repeating repeating;
    private final Date startDate;
    private final Date endDate;
    private final Date repeatUntilDate;

    public Repeating getRepeating() {
        return repeating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getRepeatUntilDate() {
        return repeatUntilDate;
    }
}

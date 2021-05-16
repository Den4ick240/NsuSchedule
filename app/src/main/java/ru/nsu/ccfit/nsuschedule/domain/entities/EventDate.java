package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class EventDate {

    public EventDate(Date startDate, Date endDate, Repeating repeating) {

        this(startDate, endDate, repeating, endDate);
    }

    public EventDate(Date startDate, Date endDate, Repeating repeating, Date repeatUntilDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeating = repeating;
        this.untilDate = repeatUntilDate;
    }

    public EventDate() {
    }

    private Repeating repeating;
    private Date startDate;
    private Date endDate;
    private Date untilDate;

    public Repeating getRepeating() {
        return repeating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setRepeating(Repeating repeating) {
        this.repeating = repeating;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(Date untilDate) {
        this.untilDate = untilDate;
    }

    @NotNull
    @Override
    public String toString() {
        return "st=" + startDate + " en=" + endDate + " un" + untilDate + "re=" + repeating;
    }
}

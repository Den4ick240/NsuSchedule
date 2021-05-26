package ru.nsu.ccfit.nsuschedule.domain.entities;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EventOccurrence implements Serializable {
    private final Calendar startDate = Calendar.getInstance();
    private final Calendar endDate = Calendar.getInstance();

    public EventOccurrence(EventOccurrence eventOccurrence) {
        startDate.setTime(eventOccurrence.startDate.getTime());
        endDate.setTime(eventOccurrence.endDate.getTime());
    }

    public EventOccurrence(Date startDate, Date endDate) {
        this.startDate.setTime(startDate);
        this.endDate.setTime(endDate);
    }

    public boolean startsBetween(Date startDate, Date endDate) {
        return !startsBefore(startDate) && startsBefore(endDate);
    }

    public boolean startsBefore(@NotNull Date when) {
        return startDate.getTime().before(when);
    }

    public boolean startsAfter(Date when) {
        return startDate.getTime().after(when);
    }

    public void add(int field, int amount) {
        startDate.add(field, amount);
        endDate.add(field, amount);
    }

    public void addInMillis(long millis) {
        startDate.setTimeInMillis(startDate.getTimeInMillis() + millis);
        endDate.setTimeInMillis(endDate.getTimeInMillis() + millis);
    }

    public void setStart(int field, int amount) {
        add(field, amount - getStart(field));
    }

    public int getStart(int field) {
        return startDate.get(field);
    }

    public Date getStartDate() {
        return startDate.getTime();
    }

    public Date getEndDate() {
        return endDate.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventOccurrence that = (EventOccurrence) o;
        return Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    public void moveToNext(Repeating repeating) {
        add(repeating.dateInterval, repeating.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return super.toString() + "{\n\t" + startDate.toString() + "\n\t" + endDate.toString() + "}";
    }
}

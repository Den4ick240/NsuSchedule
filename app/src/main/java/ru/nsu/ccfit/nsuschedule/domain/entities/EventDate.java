package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EventDate implements Serializable {

    public EventDate(Date startDate, Date endDate, Repeating repeating) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeating = repeating;
        repeatUntilDate = new Date(Long.MAX_VALUE);
    }

    public EventDate(Date startDate, Date endDate, Repeating repeating, Date repeatUntilDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeating = repeating;
        this.repeatUntilDate = repeatUntilDate;
    }

    private Repeating repeating;
    private Date startDate;
    private Date endDate;

    public void setRepeating(Repeating repeating) {
        this.repeating = repeating;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRepeatUntilDate(Date repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
    }

    private Date repeatUntilDate;

    public EventDate() {

    }

    public List<EventOccurrence> getOccurrencesBetweenDates(Date startDate, Date endDate) {
        List<EventOccurrence> occurrences = new ArrayList<>();
        Optional<EventOccurrence> optionalEventOccurrence = getFirstOccurrenceAfterDate(startDate);
        if (!optionalEventOccurrence.isPresent()) return occurrences;
        EventOccurrence eventOccurrence = optionalEventOccurrence.get();

        while (eventOccurrence.startsBefore(endDate) &&
                eventOccurrence.startsBefore(repeatUntilDate)) {
            occurrences.add(new EventOccurrence(eventOccurrence));
            eventOccurrence.moveToNext(repeating);
            if (repeating.equals(Repeating.ONCE)) break;
        }
        return occurrences;
    }

    public Optional<EventOccurrence> getFirstOccurrenceAfterDate(Date date) {
        EventOccurrence eventOccurrence = new EventOccurrence(this.startDate, this.endDate);
        if (!eventOccurrence.startsBefore(date)) return Optional.of(eventOccurrence);
        if (repeating.equals(Repeating.ONCE)) return Optional.empty();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (repeating.equals(Repeating.WEEK) || repeating.equals(Repeating.TWO_WEEK) || repeating.equals(Repeating.DAY)) {
            long diff = date.getTime() - startDate.getTime();
            long interval = repeating.getInterval();
            int intervalMultiplier = (int) Math.ceil((double) diff / interval);
            eventOccurrence.add(repeating.dateInterval, repeating.amount * intervalMultiplier);
        }

        if (repeating.equals(Repeating.MONTH) || repeating.equals(Repeating.YEAR)) {
            eventOccurrence.setStart(Calendar.YEAR, calendar.get(Calendar.YEAR));
            if (repeating.equals(Repeating.YEAR)) {
                if (eventOccurrence.startsBefore(date)) eventOccurrence.add(Calendar.YEAR, 1);
            } else {
                eventOccurrence.setStart(Calendar.MONTH, calendar.get(Calendar.MONTH));
                if (eventOccurrence.startsBefore(date)) eventOccurrence.add(Calendar.MONTH, 1);
            }
        }

        return Optional.of(eventOccurrence)
                .filter(e -> e.startsBefore(repeatUntilDate));
    }

    public boolean occursBetweenDates(Date startDate, Date endDate) {
        return getFirstOccurrenceAfterDate(startDate)
                .map(o -> o.startsBefore(endDate))
                .orElse(false);
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

    public Date getRepeatUntilDate() {
        return repeatUntilDate;
    }

    @NotNull
    @Override
    public String toString() {
        return "EventDate{" +
                "repeating=" + repeating +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", repeatUntilDate=" + repeatUntilDate +
                '}';
    }


}

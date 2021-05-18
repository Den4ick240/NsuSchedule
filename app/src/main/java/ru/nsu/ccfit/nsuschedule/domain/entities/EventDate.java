package ru.nsu.ccfit.nsuschedule.domain.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EventDate {

    public EventDate(Date startDate, Date endDate, Repeating repeating) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.MAX_VALUE);
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeating = repeating;
        repeatUntilDate = calendar.getTime();
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

    public List<EventOccurrence> getOccurrencesBetweenDates(Date startDate, Date endDate) {
        List<EventOccurrence> occurrences = new ArrayList<>();
        Optional<EventOccurrence> optionalEventOccurrence = getFirstOccurrenceAfterDate(startDate);
        if (!optionalEventOccurrence.isPresent()) return occurrences;
        EventOccurrence eventOccurrence = optionalEventOccurrence.get();

        while (eventOccurrence.startsBefore(endDate) && eventOccurrence.startsBefore(repeatUntilDate)) {
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
}

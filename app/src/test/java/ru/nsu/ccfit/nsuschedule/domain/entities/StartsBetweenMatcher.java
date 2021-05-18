package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.util.Date;

public class StartsBetweenMatcher extends TypeSafeMatcher<EventOccurrence> {
    private final Date startDate;
    private final Date repeatUntilDate;
    EventOccurrence item;

    private StartsBetweenMatcher(Date startDate, Date repeatUntilDate) {
        this.startDate = startDate;
        this.repeatUntilDate = repeatUntilDate;
    }

    static StartsBetweenMatcher startsBetween(Date startDate, Date endDate) {
        return new StartsBetweenMatcher(startDate, endDate);
    }

    @Override
    protected boolean matchesSafely(EventOccurrence item) {
        this.item = item;
        return item.startsBetween(startDate, repeatUntilDate);
    }

    @Override
    public void describeTo(Description description) {
        DateFormat f = DateFormat.getDateInstance();
        description.appendText(
                String.format("%s was expected to be between %s and %s",
                        f.format(item.getStartDate()), f.format(startDate), f.format(repeatUntilDate))
        );
    }
}
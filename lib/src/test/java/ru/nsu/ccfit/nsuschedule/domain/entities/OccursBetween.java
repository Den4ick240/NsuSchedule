package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class OccursBetween extends TypeSafeMatcher<EventDate> {
    private final Date startDate;
    private final Date endDate;

    public OccursBetween(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static OccursBetween occursBetween(Date startDate, Date endDate) {
        return new OccursBetween(startDate, endDate);
    }

    @Override
    protected boolean matchesSafely(EventDate item) {
        return item.occursBetweenDates(startDate, endDate);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format(
                "Event was expected to occur between %s and %s", startDate.toString(), endDate.toString()
        ));
    }
}

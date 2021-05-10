package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class WeekTabNamesProviderTest {
    private static final List<String> names = List.of("7", "1", "2", "3", "4", "5", "6");
    private static final List<String> expected = List.of(
      "1\n1", "2\n2", "3\n3", "4\n4", "5\n5", "6\n6", "7\n7"
    );

    @Test
    public void tabNameTest() {
        WeekTabNamesProvider provider = new WeekTabNamesProvider(names);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 6);
        assertThat(provider.getTabNames(calendar.getTime(), 7), is(equalTo(expected)));
    }

}
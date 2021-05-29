package ru.nsu.ccfit.nsuschedule.domain.entities;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class EventTest {
    Event event = new Event();

    @Test
    void isAlarmsOn() {
        assertThat(event.isAlarmsOn(), is(false));
    }

    @Test
    void isNotificationsOn() {
        assertThat(event.isNotificationsOn(), is(true));
    }
}
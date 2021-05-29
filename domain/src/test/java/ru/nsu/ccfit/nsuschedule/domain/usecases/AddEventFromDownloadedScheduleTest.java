package ru.nsu.ccfit.nsuschedule.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.TestRepository;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventDate;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventInfo;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class AddEventFromDownloadedScheduleTest {

    private AddEvent addEvent;
    private Repository repository;
    private Event testEvent;
    private boolean eventAdded;
    private boolean eventRemoved;

    @BeforeEach
    void setUp() {
        eventAdded = false;
        eventRemoved = false;
        testEvent = new Event(new EventInfo(), new EventDate(), false, true);
        addEvent = new AddEvent(new TestRepository() {
            @Override
            public void addEvent(Event event) throws RepositoryException {
                assertThat(event, equalTo(testEvent));
                eventAdded = true;
            }
        });
        repository = new TestRepository() {
            @Override
            public void removeEvent(Event event) throws RepositoryException {
                assertThat(event, equalTo(testEvent));
                eventRemoved = true;
            }
        };

    }


    @Test
    void addEvent() throws RepositoryException {
        new AddEventFromDownloadedSchedule(addEvent, repository).add(testEvent);
        assertThat(eventAdded, is(true));
        assertThat(eventRemoved, is(true));
    }
}
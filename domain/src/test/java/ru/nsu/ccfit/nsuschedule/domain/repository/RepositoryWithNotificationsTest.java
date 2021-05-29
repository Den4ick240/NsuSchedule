package ru.nsu.ccfit.nsuschedule.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventDate;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventInfo;
import ru.nsu.ccfit.nsuschedule.domain.usecases.NoParamUseCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class RepositoryWithNotificationsTest {
    Repository repository;
    NoParamUseCase useCase;
    boolean useCaseActed, repositoryActed;
    List<Event> eventList, rangeList;
    Date testStartDate, testEndDate;
    Event testEvent, testEvent2;

    RepositoryWithNotifications testSubject;

    @BeforeEach
    void setUp() {
        testEvent = new Event(new EventInfo("a", "b", "c"), new EventDate(), false, true);
        testEvent2 = new Event(new EventInfo(), new EventDate(), false, false);
        useCaseActed = false;
        repositoryActed = false;
        eventList = new ArrayList<>();
        eventList.add(new Event());
        rangeList = new ArrayList<>(eventList);
        rangeList.add(new Event());
        testStartDate = new Date(0);
        testEndDate = new Date(1);
        useCase = () -> useCaseActed = true;
        repository = new Repository() {
            @Override
            public List<Event> getEventsInRange(Date startDate, Date endDate) throws RepositoryException {
                assertThat(startDate, equalTo(testStartDate));
                assertThat(endDate, equalTo(testEndDate));
                repositoryActed = true;
                useCaseActed = true;
                return rangeList;
            }

            @Override
            public void addEvent(Event event) throws RepositoryException {
                assertThat(event, equalTo(testEvent));
                repositoryActed = true;
            }

            @Override
            public void removeEvent(Event event) throws RepositoryException {
                assertThat(event, equalTo(testEvent));
                repositoryActed = true;
            }

            @Override
            public void updaterEvent(Event oldEvent, Event newEvent) throws RepositoryException {
                assertThat(oldEvent, equalTo(testEvent));
                assertThat(newEvent, equalTo(testEvent2));
                repositoryActed = true;
            }

            @Override
            public void addEvents(Iterable<Event> events) throws RepositoryException {
                assertThat(events, equalTo(eventList));
                repositoryActed = true;
            }

            @Override
            public Iterable<Event> getEvents() throws RepositoryException {
                useCaseActed = true;
                repositoryActed = true;
                return eventList;
            }
        };
        testSubject = new RepositoryWithNotifications(repository, useCase);
    }

    @AfterEach
    void afterEach() {
        assertThat(repositoryActed, is(true));
        assertThat(useCaseActed, is(true));
    }

    @Test
    void getEventsInRange() throws RepositoryException {
        assertThat(testSubject.getEventsInRange(testStartDate, testEndDate), equalTo(rangeList));
    }

    @Test
    void addEvent() throws RepositoryException {
        testSubject.addEvent(testEvent);
    }

    @Test
    void removeEvent() throws RepositoryException {
        testSubject.removeEvent(testEvent);
    }

    @Test
    void updaterEvent() throws RepositoryException {
        testSubject.updaterEvent(testEvent, testEvent2);
    }

    @Test
    void addEvents() throws RepositoryException {
        testSubject.addEvents(eventList);
    }

    @Test
    void getEvents() throws RepositoryException {
        assertThat(testSubject.getEvents(), equalTo(eventList));
    }
}
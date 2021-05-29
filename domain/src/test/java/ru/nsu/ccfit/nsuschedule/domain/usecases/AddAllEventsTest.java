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

class AddAllEventsTest {
    Repository mainRepository, viewedRepository;
    List<Event> testList;
    boolean mainRepositoryActed, viewedRepositoryActed;
    AddAllEvents addAllEvents;

    @BeforeEach
    void setUp() {
        testList = new ArrayList<>();
        testList.add(new Event(new EventInfo(), new EventDate(), false, true));
        mainRepositoryActed = false;
        viewedRepositoryActed = false;
        viewedRepository = new TestRepository() {
            @Override
            public Iterable<Event> getEvents() throws RepositoryException {
                mainRepositoryActed = true;
                return testList;
            }
        };

        mainRepository = new TestRepository() {
            @Override
            public void addEvents(Iterable<Event> events) throws RepositoryException {
                viewedRepositoryActed = true;
                assertThat(events, equalTo(testList));
            }
        };
        addAllEvents = new AddAllEvents(mainRepository, viewedRepository);
    }

    @Test
    void addTest() throws RepositoryException {
        addAllEvents.add();
        assertThat(mainRepositoryActed, is(true));
        assertThat(viewedRepositoryActed, is(true));
    }

}
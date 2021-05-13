package ru.nsu.ccfit.nsuschedule.domain.usecases;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class AddEvent {
    private final Repository repository;

    public AddEvent(Repository repository) {
        this.repository = repository;
    }

    public void add(Event event) throws RepositoryException {
        repository.addEvent(event);
    }
}

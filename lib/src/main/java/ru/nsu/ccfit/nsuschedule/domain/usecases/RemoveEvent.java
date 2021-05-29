package ru.nsu.ccfit.nsuschedule.domain.usecases;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class RemoveEvent {
    private final Repository repository;

    public RemoveEvent(Repository repository) {
        this.repository = repository;
    }

    public void remove(Event event) throws RepositoryException {
        repository.removeEvent(event);
    }
}

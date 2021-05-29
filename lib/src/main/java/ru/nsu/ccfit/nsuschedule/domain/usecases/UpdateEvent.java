package ru.nsu.ccfit.nsuschedule.domain.usecases;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class UpdateEvent extends AddEvent {
    private final Event oldEvent;

    public UpdateEvent(Repository repository, Event oldEvent) {
        super(repository);
        this.oldEvent = oldEvent;
    }

    @Override
    public void add(Event event) throws RepositoryException {
        repository.updaterEvent(oldEvent, event);
    }
}

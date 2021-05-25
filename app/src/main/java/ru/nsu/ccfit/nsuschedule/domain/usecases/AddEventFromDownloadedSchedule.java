package ru.nsu.ccfit.nsuschedule.domain.usecases;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class AddEventFromDownloadedSchedule {
    private final AddEvent addEvent;
    private final Repository downloadedRepository;

    public AddEventFromDownloadedSchedule(AddEvent addEvent, Repository downloadedRepository) {
        this.addEvent = addEvent;
        this.downloadedRepository = downloadedRepository;
    }

    public void add(Event event) throws RepositoryException {
        addEvent.add(event);
        downloadedRepository.removeEvent(event);
    }
}

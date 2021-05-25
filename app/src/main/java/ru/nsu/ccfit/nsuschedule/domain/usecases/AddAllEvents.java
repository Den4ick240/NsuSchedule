package ru.nsu.ccfit.nsuschedule.domain.usecases;

import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public class AddAllEvents {
    private final Repository mainRepository;
    private final Repository viewedRepository;

    public AddAllEvents(Repository mainRepository, Repository viewedRepository) {
        this.mainRepository = mainRepository;
        this.viewedRepository = viewedRepository;
    }

    public void add() throws RepositoryException {
        mainRepository.addEvents(viewedRepository.getEvents());
    }
}

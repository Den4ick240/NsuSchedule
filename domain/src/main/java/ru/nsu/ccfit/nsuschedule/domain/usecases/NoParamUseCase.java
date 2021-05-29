package ru.nsu.ccfit.nsuschedule.domain.usecases;

import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;

public interface NoParamUseCase {
    void invoke() throws RepositoryException;
}

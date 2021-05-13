package ru.nsu.ccfit.nsuschedule;

import androidx.lifecycle.ViewModelProvider;

import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;

public class AppContainer {
    public final GetEventsForDay getEventsForDay;
    public final ViewModelProvider.Factory scheduleDayViewModelFactory;
    public final ViewModelProvider.Factory createEventViewModelFactory;

    public AppContainer(GetEventsForDay getEventsForDay, ViewModelProvider.Factory scheduleDayViewModelFactory, ViewModelProvider.Factory createEventViewModelFactory) {
        this.getEventsForDay = getEventsForDay;
        this.scheduleDayViewModelFactory = scheduleDayViewModelFactory;
        this.createEventViewModelFactory = createEventViewModelFactory;
    }

}

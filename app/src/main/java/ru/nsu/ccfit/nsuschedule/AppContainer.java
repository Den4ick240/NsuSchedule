package ru.nsu.ccfit.nsuschedule;

import androidx.lifecycle.ViewModelProvider;

import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;

public class AppContainer {
    public final GetEventsForDay getEventsForDay;
    public final ViewModelProvider.Factory scheduleDayViewModelFactory;

    public AppContainer(GetEventsForDay getEventsForDay, ViewModelProvider.Factory scheduleDayViewModelFactory) {
        this.getEventsForDay = getEventsForDay;
        this.scheduleDayViewModelFactory = scheduleDayViewModelFactory;
    }

}

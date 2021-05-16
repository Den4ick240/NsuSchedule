package ru.nsu.ccfit.nsuschedule;

import androidx.lifecycle.ViewModelProvider;

public class AppContainer {
    public final ViewModelProvider.Factory scheduleDayViewModelFactory;
    public final ViewModelProvider.Factory createEventViewModelFactory;

    public AppContainer(ViewModelProvider.Factory scheduleDayViewModelFactory,
                        ViewModelProvider.Factory createEventViewModelFactory) {
        this.scheduleDayViewModelFactory = scheduleDayViewModelFactory;
        this.createEventViewModelFactory = createEventViewModelFactory;
    }

}

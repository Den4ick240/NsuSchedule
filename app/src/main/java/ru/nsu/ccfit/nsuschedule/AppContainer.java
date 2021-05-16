package ru.nsu.ccfit.nsuschedule;

import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.Map;

import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;

public class AppContainer {
    public final Map<Repeating, String> repeatingEnumTranslationMap;

    public final ViewModelProvider.Factory scheduleDayViewModelFactory;
    public final ViewModelProvider.Factory createEventViewModelFactory;

    public AppContainer(Map<Repeating, String> repeatingEnumTranslationMap,
                        ViewModelProvider.Factory scheduleDayViewModelFactory,
                        ViewModelProvider.Factory createEventViewModelFactory) {
        this.repeatingEnumTranslationMap = Collections.unmodifiableMap(repeatingEnumTranslationMap);
        this.scheduleDayViewModelFactory = scheduleDayViewModelFactory;
        this.createEventViewModelFactory = createEventViewModelFactory;
    }

}

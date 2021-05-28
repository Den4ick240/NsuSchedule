package ru.nsu.ccfit.nsuschedule;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ru.nsu.ccfit.nsuschedule.data.SharedPreferencesSettingsRepository;
import ru.nsu.ccfit.nsuschedule.data.json_repository.JsonRepository;
import ru.nsu.ccfit.nsuschedule.domain.ScheduleNotificationManager;
import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;
import ru.nsu.ccfit.nsuschedule.domain.repository.Repository;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryWithNotifications;
import ru.nsu.ccfit.nsuschedule.domain.repository.SettingsRepository;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddAllEvents;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddEvent;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddEventFromDownloadedSchedule;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddScheduleFromUrl;
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;
import ru.nsu.ccfit.nsuschedule.domain.usecases.RemoveEvent;
import ru.nsu.ccfit.nsuschedule.domain.usecases.SetupNextNotification;
import ru.nsu.ccfit.nsuschedule.notifications.AndroidScheduleNotificationManager;
import ru.nsu.ccfit.nsuschedule.ui.create_event.CreateEventViewModel;
import ru.nsu.ccfit.nsuschedule.ui.downloaded_schedule.DownloadedScheduleViewModel;
import ru.nsu.ccfit.nsuschedule.ui.import_schedule.ImportScheduleViewModel;
import ru.nsu.ccfit.nsuschedule.ui.schedule_day.ScheduleDayViewModel;

public class AppContainer {
    private final ViewModelProvider.Factory scheduleDayViewModelFactory;
    public final ViewModelProvider.Factory createEventViewModelFactory;
    public final ViewModelProvider.Factory importScheduleViewModelFactory;
    public final SettingsRepository settingsRepository;
    public final ScheduleNotificationManager scheduleNotificationManager;
    public final SetupNextNotification setupNextNotification;
    public static final String LOCAL_SCHEDULE_FLOW = "LOCAL_SCHEDULE_FLOW";
    public static final String DOWNLOADED_SCHEDULE_FLOW = "DOWNLOADED_SCHEDULE_FLOW";

    private ViewModelProvider.Factory downloadedScheduleViewModelFactory;
    private ViewModelProvider.Factory downloadedScheduleDayViewModelFactory;
    @SuppressLint("SimpleDateFormat")
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private final Context context;
    private final Repository repository;

    public ViewModelProvider.Factory getScheduleDayViewModelFactory(String flowName) {
        if (flowName.equals(LOCAL_SCHEDULE_FLOW)) return scheduleDayViewModelFactory;
        if (flowName.equals(DOWNLOADED_SCHEDULE_FLOW)) return downloadedScheduleDayViewModelFactory;
        return null;
    }

    @SuppressWarnings({"squid:S3740", "unchecked", "rawtypes"})
    private ViewModelProvider.Factory createFactory(Function<Class, ViewModel> factory) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @NotNull
            @Override
            public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
                return (T) factory.apply(modelClass);
            }
        };
    }

    public ViewModelProvider.Factory getDownloadedScheduleViewModelFactory() {
        return downloadedScheduleViewModelFactory;
    }

    public AppContainer(Context context) throws IOException {
        this.context = context;
        String nsuLinkForGroup = context.getResources().getString(R.string.nsu_link_for_group);
        String filePath = context.getFilesDir().getPath() + "/downloadedFile.ics";
        Repository baseRepository = new JsonRepository(context);
        scheduleNotificationManager = new AndroidScheduleNotificationManager(context);
        settingsRepository = new SharedPreferencesSettingsRepository(context);
        setupNextNotification = new SetupNextNotification(15, scheduleNotificationManager,
                baseRepository, timeFormat, settingsRepository);
        repository = new RepositoryWithNotifications(baseRepository, setupNextNotification);
//        repository = new JsonRepository(context);
        scheduleDayViewModelFactory = createFactory(unused ->
                new ScheduleDayViewModel(new GetEventsForDay(repository), null,
                        new RemoveEvent(repository), timeFormat, R.menu.event_context_menu));
        createEventViewModelFactory = createFactory(unused ->
                new CreateEventViewModel(getRepeatingEnumTranslationMap(), new AddEvent(repository), timeFormat));
        importScheduleViewModelFactory = createFactory(unused ->
                new ImportScheduleViewModel(new AddScheduleFromUrl(filePath, this), nsuLinkForGroup));
        downloadedScheduleDayViewModelFactory = null;
    }

    private Map<String, Repeating> getRepeatingEnumTranslationMap() {
        String[] enumValues = context.getResources().getStringArray(R.array.repeating_enum_values);
        String[] keys = context.getResources().getStringArray(R.array.repeating_enum_translations);
        Map<String, Repeating> map = new HashMap<>();
        for (int i = 0; i < Math.min(enumValues.length, keys.length); i++) {
            map.put(keys[i], Repeating.valueOf(enumValues[i]));
        }
        return Collections.unmodifiableMap(map);
    }

    public void initDownloadedScheduleViewModelFactory(Repository downloadedRepository) {
        downloadedScheduleDayViewModelFactory = createFactory(unused ->
            new ScheduleDayViewModel(new GetEventsForDay(downloadedRepository),
                    new AddEventFromDownloadedSchedule(new AddEvent(repository), downloadedRepository), null,
                    timeFormat, R.menu.downloaded_schedule_context_menu));
        downloadedScheduleViewModelFactory = createFactory(unused ->
                new DownloadedScheduleViewModel(new AddAllEvents(repository, downloadedRepository)));
    }
}

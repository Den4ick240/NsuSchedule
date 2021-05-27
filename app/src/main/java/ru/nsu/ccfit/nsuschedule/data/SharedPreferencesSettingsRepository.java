package ru.nsu.ccfit.nsuschedule.data;

import android.content.Context;
import android.content.SharedPreferences;

import ru.nsu.ccfit.nsuschedule.domain.repository.SettingsRepository;

public class SharedPreferencesSettingsRepository implements SettingsRepository {
    public static final String NOTIFICATIONS_KEY = "NOTIFICATIONS_KEY";
    public static final String ALARMS_KEY = "ALARMS_KEY";
    private static final String SHARED_PREFERENCES_NAME = "ru.nsu.ccfit.nsuschedule.settingspreferences";

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesSettingsRepository(Context sharedPreferences) {
        this.sharedPreferences = sharedPreferences.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean notificationsEnabled() {
        return sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true);
    }

    @Override
    public boolean alarmsEnabled() {
        return sharedPreferences.getBoolean(ALARMS_KEY, false);
    }

    @Override
    public void setAlarmsEnabled(boolean alarmsEnabled) {
        setSharedBoolean(ALARMS_KEY, alarmsEnabled);
    }

    @Override
    public void setNotificationsEnabled(boolean notificationsEnabled) {
        setSharedBoolean(NOTIFICATIONS_KEY, notificationsEnabled);
    }

    private void setSharedBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
}

package ru.nsu.ccfit.nsuschedule.domain.repository;

public interface SettingsRepository {
    boolean notificationsEnabled();
    boolean alarmsEnabled();
    void setAlarmsEnabled(boolean alarmsEnabled);
    void setNotificationsEnabled(boolean notificationsEnabled);
}

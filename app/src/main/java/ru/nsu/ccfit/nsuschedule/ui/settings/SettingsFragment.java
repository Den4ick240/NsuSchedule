package ru.nsu.ccfit.nsuschedule.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;
import ru.nsu.ccfit.nsuschedule.domain.repository.SettingsRepository;

public class SettingsFragment extends Fragment {
    private SettingsRepository settingsRepository;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingsRepository = ((ApplicationWithAppContainer) requireActivity().getApplication())
                .getAppContainer().settingsRepository;
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        SwitchMaterial notificationsSwitch = view.findViewById(R.id.notifications_switch);
        notificationsSwitch.setChecked(settingsRepository.notificationsEnabled());
        notificationsSwitch.setOnCheckedChangeListener(this::onNotificationsCheckedChanged);
        SwitchMaterial alarmsSwitch = view.findViewById(R.id.alarms_switch);
        alarmsSwitch.setChecked(settingsRepository.alarmsEnabled());
        alarmsSwitch.setOnCheckedChangeListener(this::onAlarmsCheckedChanged);
        return view;
    }

    private void onNotificationsCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        settingsRepository.setNotificationsEnabled(isChecked);
    }

    private void onAlarmsCheckedChanged(CompoundButton compoundButton, boolean b) {
        settingsRepository.setAlarmsEnabled(b);
    }
}
package ru.nsu.ccfit.nsuschedule.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ru.nsu.ccfit.nsuschedule.R;

public class SettingsFragment extends Fragment {
    public static final String NOTIFICATIONS_KEY = "NOTIFICATIONS_KEY";
    public static final String ALARMS_KEY = "ALARMS_KEY";


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ((SwitchMaterial) view.findViewById(R.id.notifications_switch))
                .setOnCheckedChangeListener(this::onNotificationsCheckedChanged);
        ((SwitchMaterial) view.findViewById(R.id.alarms_switch))
                .setOnCheckedChangeListener(this::onAlarmsCheckedChanged);
        return view;
    }

    private void onNotificationsCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setSharedBoolean(NOTIFICATIONS_KEY, isChecked);
    }

    private void onAlarmsCheckedChanged(CompoundButton compoundButton, boolean b) {
        setSharedBoolean(ALARMS_KEY, b);
    }

    private void setSharedBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
}
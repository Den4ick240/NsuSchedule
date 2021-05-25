package ru.nsu.ccfit.nsuschedule.ui.downloaded_schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ru.nsu.ccfit.nsuschedule.R;
import ru.nsu.ccfit.nsuschedule.ui.week_schedule.WeekScheduleFragment;
import ru.nsu.ccfit.nsuschedule.ui.week_schedule.WeekScheduleViewModel;

public class DownloadedScheduleFragment extends WeekScheduleFragment {
    public static DownloadedScheduleFragment newInstance() {
        return new DownloadedScheduleFragment();
    }

    @Override
    protected WeekScheduleViewModel getViewModel() {
        return new ViewModelProvider(this).get(DownloadedScheduleViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.add_all_button).setOnClickListener(v -> {
            if (v.getId() == R.id.add_all_button)
                ((DownloadedScheduleViewModel) getViewModel()).addAll();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloaded_schedule, container, false);
    }
}
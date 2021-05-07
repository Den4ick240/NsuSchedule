package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import ru.nsu.ccfit.nsuschedule.R;

public class WeekScheduleFragment extends Fragment {

    public static WeekScheduleFragment newInstance() {
        return new WeekScheduleFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WeekScheduleViewModel model = new ViewModelProvider(this).get(WeekScheduleViewModel.class);

        FragmentStateAdapter adapter = new WeekSchedulePagerAdapter(getParentFragmentManager(), getLifecycle(), new Date());
        ViewPager2 viewPager = view.findViewById(R.id.days_view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(model.getStartPosition());

        ViewPager2 weekTabsViewPager = view.findViewById(R.id.tab_view_pager);
        weekTabsViewPager.setAdapter(model.getTabsAdapter(getParentFragmentManager(), getLifecycle()));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                model.onPositionSelected(position);
            }
        });
        model.getSelectedDayPositionLiveData().observe(getViewLifecycleOwner(), viewPager::setCurrentItem);
        model.getSelectedWeekPositionLiveData().observe(getViewLifecycleOwner(), weekTabsViewPager::setCurrentItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_schedule, container, false);
    }
}
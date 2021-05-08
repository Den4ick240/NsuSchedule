package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import ru.nsu.ccfit.nsuschedule.R;

public class WeekScheduleFragment extends Fragment {

    public static WeekScheduleFragment newInstance() {
        return new WeekScheduleFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WeekScheduleViewModel model = new ViewModelProvider(this).get(WeekScheduleViewModel.class);

        ViewPager2 viewPager = view.findViewById(R.id.days_view_pager);
        FragmentStateAdapter adapter = new WeekSchedulePagerAdapter(getParentFragmentManager(), getLifecycle(), model);
        viewPager.setAdapter(adapter);

        ViewPager2 weekTabsViewPager = view.findViewById(R.id.tab_view_pager);
        FragmentStateAdapter tabsAdapter = new WeekTabsPagerAdapter(getParentFragmentManager(), getLifecycle(), model);
        weekTabsViewPager.setAdapter(tabsAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                model.onPositionSelected(position);
            }
        });
        model.getSelectedDayPositionLiveData().observe(getViewLifecycleOwner(), viewPager::setCurrentItem);
        model.getSelectedWeekPositionLiveData().observe(getViewLifecycleOwner(), weekTabsViewPager::setCurrentItem);

        TextView textView = view.findViewById(R.id.current_date_text_view);
        model.getCurrentDateString().observe(getViewLifecycleOwner(), textView::setText);

        viewPager.setCurrentItem(model.getStartPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_schedule, container, false);
    }
}
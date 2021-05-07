package ru.nsu.ccfit.nsuschedule.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import ru.nsu.ccfit.nsuschedule.R;

public class WeekScheduleFragment extends Fragment {

    private WeekScheduleViewModel model;

    public static WeekScheduleFragment newInstance() {
        return new WeekScheduleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_schedule, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(WeekScheduleViewModel.class);
        PagerAdapter pagerAdapter = model.getPagerAdapter(getContext(), getParentFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.week_days_view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.selectTab(tabs.getTabAt(model.getStartingPosition()));

        TextView selectedDateTextView = view.findViewById(R.id.selected_date);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedDateTextView.setText(model.getDateFormatted(tab.getPosition()));
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                //unused
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //unused
            }
        });
    }

}
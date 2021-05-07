package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;

import ru.nsu.ccfit.nsuschedule.R;


public class WeekTabsFragment extends Fragment {
    public void selectTab(int i) {
        tabs.selectTab(tabs.getTabAt(i));
    }

    interface OnPositionSelectedListener {
        void selected(int position);
    }
    private static final String FIRST_DAY_ARG = "first_day";
    private static final int[] DAYS_OF_WEEK_TITLES = new int[]{
            R.string.sundayTab,
            R.string.mondayTab,
            R.string.tuesdayTab,
            R.string.wednesdayTab,
            R.string.thursdayTab,
            R.string.fridayTab,
            R.string.saturdayTab
    };

    public static WeekTabsFragment newInstance(Date firstDate) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FIRST_DAY_ARG, firstDate);
        WeekTabsFragment weekTabsFragment = new WeekTabsFragment();
        weekTabsFragment.setArguments(bundle);
        return weekTabsFragment;

    }

    private Date firstDayDate;
    private TabLayout tabs;
    private OnPositionSelectedListener onPositionSelectedListener;

    public void setOnPositionSelectedListener(OnPositionSelectedListener onPositionSelectedListener) {
        this.onPositionSelectedListener = onPositionSelectedListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstDayDate = (Date) getArguments().getSerializable(FIRST_DAY_ARG);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabs = view.findViewById(R.id.week_day_tabs);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayDate);
        for (int i = 0; i < DAYS_OF_WEEK_TITLES.length; i++) {
            TabLayout.Tab tab = tabs.newTab();
            tab.setText(getTabContentDescription(calendar));
            tabs.addTab(tab, i);
            calendar.add(Calendar.DATE, 1);
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onPositionSelectedListener.selected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //unused
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //unused
            }
        });
    }

    public TabLayout getTabs() {
        return tabs;
    }

    private String getTabContentDescription(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = getContext().getResources().getString(DAYS_OF_WEEK_TITLES[dayOfWeekNumber - 1]);
        return date + "\n" + dayOfWeek;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_tab, container, false);
    }
}
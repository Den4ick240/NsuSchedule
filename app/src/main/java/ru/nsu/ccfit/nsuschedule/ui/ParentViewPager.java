package ru.nsu.ccfit.nsuschedule.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ParentViewPager extends ViewPager {

    public ParentViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if(v != this && v instanceof ViewPager) {
//            int currentItem = ((ViewPager) v).getCurrentItem();
//            int countItem = ((ViewPager) v).getAdapter().getCount();
//            if((currentItem==(countItem-1) && dx<0) || (currentItem==0 && dx>0)){
//                return false;
//            }
//            return true;
//        }
//        return super.canScroll(v, checkV, dx, x, y);
        return false;
    }
}

package com.example.hihihahahehe.portablept.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private Calendar calendar;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/M");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        ArrayList<String> dayOfWeek = new ArrayList<>();
        dayOfWeek.add(sdf.format(today));
        for(int i = 1; i <= 4; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date newDate = calendar.getTime();
            dayOfWeek.add(sdf.format(newDate));
        }

        for(int i = 0; i < dayOfWeek.size(); i++){
            Utils.addTab(tabLayout,dayOfWeek.get(i));
        }
    }
}

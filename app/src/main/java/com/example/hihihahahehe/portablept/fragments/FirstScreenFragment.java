package com.example.hihihahahehe.portablept.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.adapters.MainPagerAdapter;
import com.example.hihihahahehe.portablept.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstScreenFragment extends Fragment {
    private static final String TAG = FirstScreenFragment.class.toString();
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public FirstScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_screen, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        ButterKnife.bind(this, view);
        Realm.init(getContext());

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        int[] iconUser = {
                R.drawable.ic_home,
                R.drawable.ic_format_list_bulleted,
                R.drawable.ic_calendar,
                R.drawable.ic_notifications_none,
                R.drawable.ic_person
        };
        
        Utils.setIconBottomBar(tabLayout, iconUser);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

//        final View touchView = view.findViewById(R.id.view_pager);
////        touchView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
    }
}

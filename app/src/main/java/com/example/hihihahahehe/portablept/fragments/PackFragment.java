package com.example.hihihahahehe.portablept.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.FirstTypeFragment;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.FourtTypeFragment;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.SecondTypeFragment;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.ThirdTypeFragment;
import com.example.hihihahahehe.portablept.utils.ScreenManager;
import com.example.hihihahahehe.portablept.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackFragment extends Fragment {
    public static final String TAG = PackFragment.class.toString();
    private String tabName;
    String[] listPack;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public PackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pack, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        setTablayoutItem();

        ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(),
                new FirstTypeFragment(), R.id.layout_container_pack, false);
    }

    private void setTablayoutItem() {
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        listPack = new String[]{
                "Fitness",
                "Zumba",
                "Kickfit",
                "Boxing"
        };

        for (int i = 0; i < listPack.length; i++) {
            Utils.addTab(tabLayout, listPack[i]);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                switch (tab.getPosition()) {
                    case 0:
                        ScreenManager.replaceFragment(fm, new FirstTypeFragment(), R.id.layout_container_pack, false);
                        break;
                    case 1:
                        ScreenManager.replaceFragment(fm, new SecondTypeFragment(), R.id.layout_container_pack, false);
                        break;
                    case 2:
                        ScreenManager.replaceFragment(fm, new ThirdTypeFragment(), R.id.layout_container_pack, false);
                        break;
                    case 3:
                        ScreenManager.replaceFragment(fm, new FourtTypeFragment(), R.id.layout_container_pack, false);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

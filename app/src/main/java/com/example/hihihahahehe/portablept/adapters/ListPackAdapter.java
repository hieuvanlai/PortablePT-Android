package com.example.hihihahahehe.portablept.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hihihahahehe.portablept.fragments.typeofpacks.FirstTypeFragment;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.FourtTypeFragment;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.SecondTypeFragment;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.ThirdTypeFragment;

/**
 * Created by hihihahahehe on 8/25/17.
 */

public class ListPackAdapter extends FragmentStatePagerAdapter{

    public ListPackAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FirstTypeFragment firstType = new FirstTypeFragment();
        SecondTypeFragment secondType = new SecondTypeFragment();
        ThirdTypeFragment thirdType = new ThirdTypeFragment();
        FourtTypeFragment fourtType = new FourtTypeFragment();
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = firstType;
                break;
            case 1:
                fragment = secondType;
                break;
            case 2:
                fragment = thirdType;
                break;
            case 3:
                fragment = fourtType;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

package ru.alex.sibrivertest.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.alex.sibrivertest.R;
import ru.alex.sibrivertest.fragments.TestList;
import ru.alex.sibrivertest.fragments.TestMap;

public class VPAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    TestList testList;
    TestMap testMap;

    public VPAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        fragments.add(TestList.getInstance(this.testList));
        fragments.add(TestMap.getInstance(this.testMap));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";
        switch (position){
            case 0:
                pageTitle = "Список";
                break;
            case 1:
                pageTitle = "Карта";
                break;
        }
        return pageTitle;
    }
}

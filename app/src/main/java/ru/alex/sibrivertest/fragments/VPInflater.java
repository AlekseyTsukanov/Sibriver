package ru.alex.sibrivertest.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alex.sibrivertest.R;
import ru.alex.sibrivertest.adapter.VPAdapter;

public class VPInflater extends Fragment {

    private View rootView;
    private ViewPager viewPager;
    private VPAdapter vpAdapter;
    private PagerTabStrip pagerTabStrip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.vpfragment, container, false);

        pagerTabStrip = (PagerTabStrip)rootView.findViewById(R.id.pagerTitleStrip);
        pagerTabStrip.setTabIndicatorColor(Color.YELLOW);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
        vpAdapter = new VPAdapter(getFragmentManager());
        viewPager.setAdapter(vpAdapter);

        return rootView;
    }
}

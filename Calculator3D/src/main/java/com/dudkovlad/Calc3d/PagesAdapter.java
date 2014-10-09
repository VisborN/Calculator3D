package com.dudkovlad.Calc3d;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by vlad on 17.02.14.
 */
//*
public class PagesAdapter   extends FragmentPagerAdapter
                           // implements ViewPager.OnPageChangeListener
{
    private final Context mContext;
    PagesFragment mPagesFragment;


    public PagesAdapter (Context activity, FragmentManager fm) {
        super(fm);
        mContext = activity;
        mPagesFragment = new PagesFragment();
    }

    @Override
    public int getCount() {
        return Data.pages_count;
    }

    @Override
    public Fragment getItem(int position) {
        return PagesFragment.newInstance(position, mContext);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale loc = Locale.getDefault();
        switch (position) {
            case 0:
                return "Section 1".toUpperCase(loc);
            case 1:
                return "Section 2".toUpperCase(loc);
            case 2:
                return "Section 3".toUpperCase(loc);
        }
        return null;
    }

    /*
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
*/


}
//*/

package com.dudkovlad.CalcTDP;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by vlad on 17.02.14.
 */
//*
public class PagesAdapter   extends FragmentPagerAdapter
                           // implements ViewPager.OnPageChangeListener
{
    Context mContext;
    PagesFragment mPagesFragment;


    public PagesAdapter (Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
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
        //Locale loc = Locale.getDefault();
        switch (position) {
            case 0:
                return "Section 1";//.toUpperCase(loc);
            case 1:
                return "Section 2";//.toUpperCase(loc);
            case 2:
                return "Section 3";//.toUpperCase(loc);
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

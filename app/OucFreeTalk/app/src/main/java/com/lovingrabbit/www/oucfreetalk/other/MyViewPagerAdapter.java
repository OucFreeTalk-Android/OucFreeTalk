package com.lovingrabbit.www.oucfreetalk.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by 17922 on 2017/4/25.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public MyViewPagerAdapter(FragmentManager fm,int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TalkFragment.newInstance();
            default:
                return TalkFragment.newInstance();
        }

    }

    @Override
    public int getCount() {
        return size;
    }
}

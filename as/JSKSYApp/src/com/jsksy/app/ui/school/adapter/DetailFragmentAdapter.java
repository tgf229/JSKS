package com.jsksy.app.ui.school.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.jsksy.app.ui.school.SchoolDetailActivity;
import com.jsksy.app.ui.school.fragment.SchoolDetailFragmentOne;
import com.jsksy.app.ui.school.fragment.SchoolDetailFragmentThree;
import com.jsksy.app.ui.school.fragment.SchoolDetailFragmentTwo;

/**
 * Created by 涂高峰 on 17/4/19.
 */
public class DetailFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = "DetailFragmentAdapter";
    public final int COUNT = 3;
    private Context context;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public DetailFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position,fragment);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (0 == position) {
            fragment = new SchoolDetailFragmentOne();
        } else if (1 == position) {
            fragment = new SchoolDetailFragmentTwo();
        } else if (2 == position) {
            Log.e(TAG, "position=2 ");
            fragment = new SchoolDetailFragmentThree();
            SchoolDetailActivity act = (SchoolDetailActivity) this.context;
            Bundle bundle = new Bundle();
            bundle.putParcelable("detail",act.mDetail);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        Log.i(TAG, "destroyItem: "+position);
//        registeredFragments.remove(position);
//        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "院校介绍";
            case 1:
                return "录取分数线";
            case 2:
                return "招生计划";
        }
        return null;
    }
}

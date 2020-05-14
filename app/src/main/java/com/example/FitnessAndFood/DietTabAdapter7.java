//for diet page tab view
package com.example.FitnessAndFood;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DietTabAdapter7 extends FragmentStatePagerAdapter {
    int numOfTabs;
    public DietTabAdapter7(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0: return new DietStandard7();
            case 1: return new DietVegan7();
            default:return null;
        }

    }

    @Override
    public int getCount() {

        return numOfTabs;
    }
}

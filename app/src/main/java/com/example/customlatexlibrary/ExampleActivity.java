package com.example.customlatexlibrary;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.scilab.forge.jlatexmath.core.AjLatexMath;

;


public class ExampleActivity extends FragmentActivity implements
        ViewPager.OnPageChangeListener, OnClickListener {

    private ViewPager mPager;
    private PagesAdapter mAdapter;
    private String[] mExamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		AjLatexMath.init(this);
        mExamples = ExampleFormula.getFormulaArray();
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new PagesAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(this);
        setTitle(getString(R.string.app_name) + ": About");

    }

    private class PagesAdapter extends FragmentPagerAdapter {

        public PagesAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return ExampleFragment
                    .newInstance(mExamples[position], position);
        }

        @Override
        public int getCount() {
            return mExamples.length;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0)
            setTitle(getString(R.string.app_name) + ": About");
        else
            setTitle(getString(R.string.app_name) + ": Example" + (position));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapCacheUtil.getInstance().clearCache();
    }

    @Override
    public void onClick(View v) {
        mAdapter.notifyDataSetChanged();
    }

}

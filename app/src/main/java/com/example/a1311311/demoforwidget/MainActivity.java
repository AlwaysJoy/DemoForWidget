package com.example.a1311311.demoforwidget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a1311311.demoforwidget.fragments.PlanetsFragment;
import com.example.a1311311.demoforwidget.fragments.TurnplateFragment;
import com.example.a1311311.demoforwidget.fragments.VatFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PlanetsFragment mPlanetsFragment;
    private VatFragment mVatFragment;
    private ArrayList<Fragment> mFragmentList;
    private Fragment mCurrentFragment;
    private Fragment mTurnplateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        findViewById(R.id.btn_stars).setOnClickListener(this);
        findViewById(R.id.btn_vat).setOnClickListener(this);
        findViewById(R.id.btn_turnplate).setOnClickListener(this);
    }

    private void init() {
        mFragmentList = new ArrayList<>();
        mPlanetsFragment = new PlanetsFragment();
        mVatFragment = new VatFragment();
        mTurnplateFragment = new TurnplateFragment();

    }

    @OnClick({R.id.btn_stars,R.id.btn_vat})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stars:
                changePage(mPlanetsFragment);
                break;
            case R.id.btn_vat:
                changePage(mVatFragment);
                break;
            case R.id.btn_turnplate:
                changePage(mTurnplateFragment);
                break;
        }
    }

    private void changePage(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment oldFragment = mCurrentFragment;
        if (mCurrentFragment != null) {
            fragmentTransaction.detach(mCurrentFragment);
            if (mCurrentFragment == newFragment) {
                mCurrentFragment = null;
            }
        }
        if (oldFragment != newFragment) {
            if (mFragmentList.contains(newFragment)) {
                fragmentTransaction.attach(newFragment);
            } else {
                fragmentTransaction.add(R.id.fl_show_window, newFragment);
                mFragmentList.add(newFragment);
            }
            mCurrentFragment = newFragment;
        }
        fragmentTransaction.commit();
    }
}

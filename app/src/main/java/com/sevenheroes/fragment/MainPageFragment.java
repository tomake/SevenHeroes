package com.sevenheroes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sevenheroes.CommonActivity;
import com.sevenheroes.R;

public class MainPageFragment extends Fragment implements View.OnClickListener {

    private static final String INTENT_ACTION = "com.sevenheroes.CommonActivity";
    private static final String FRAGMENT_TAG = "fragment";
    private static Intent mFunctionIntent;
    private NavigationDrawerFragment mDrawerFragment;
    private ImageButton mDrawerToggle;
    private ImageButton mIbGift;
    private ImageButton mIbFloor;
    private ImageButton mIbTraining;
    private ImageButton mIbPay;

    public MainPageFragment() {
    }

    public MainPageFragment(NavigationDrawerFragment drawerLayout) {
        mDrawerFragment = drawerLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFunctionIntent = new Intent(INTENT_ACTION);
        View rootView = inflater.inflate(R.layout.fragment_game_page, container, false);
        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        mDrawerToggle = (ImageButton) rootView.findViewById(R.id.ibDrawerToggle);
        mDrawerToggle.setOnClickListener(this);
        mIbPay = (ImageButton) rootView.findViewById(R.id.ibPay);
        mIbPay.setOnClickListener(this);
        mIbGift = (ImageButton) rootView.findViewById(R.id.ibGift);
        mIbGift.setOnClickListener(this);
        mIbFloor = (ImageButton) rootView.findViewById(R.id.ibFloor);
        mIbFloor.setOnClickListener(this);
        mIbTraining = (ImageButton) rootView.findViewById(R.id.ibTraining);
        mIbTraining.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibDrawerToggle:
                if (mDrawerFragment.isDrawerOpen()) {
                    mDrawerFragment.openDrawer(false);
                } else {
                    mDrawerFragment.openDrawer(true);
                }
                break;
            case R.id.ibPay:
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.pay));
                startActivity(mFunctionIntent);
                break;
            case R.id.ibGift:
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.gift));
                startActivity(mFunctionIntent);
                break;
            case R.id.ibFloor:
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.floor));
                startActivity(mFunctionIntent);
                break;
            case R.id.ibTraining:
                mFunctionIntent.putExtra(FRAGMENT_TAG , getString(R.string.training_home));
                startActivity(mFunctionIntent);
                break;
            default:
                break;
        }
    }


}

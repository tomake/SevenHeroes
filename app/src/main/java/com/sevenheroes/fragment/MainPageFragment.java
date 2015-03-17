package com.sevenheroes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sevenheroes.R;
import com.sevenheroes.util.DataUtil;
import com.sevenheroes.util.ViewUtil;

import java.util.HashMap;

public class MainPageFragment extends Fragment implements View.OnClickListener {

    private static final String ACTION_COMMON_ACTIVITY = "com.sevenheroes.CommonActivity";
    private static final String ACTION_FIGHTING_ACTIVITY = "com.sevenheroes.FloorFightingActivity";

    private static final String FRAGMENT_TAG = "fragment";
    private static Intent mFunctionIntent;
    private NavigationDrawerFragment mDrawerFragment;
    private ImageButton mDrawerToggle;
    private ImageButton mIbGift;
    private ImageButton mIbFloor;
    private ImageButton mIbTraining;
    private ImageButton mIbPay;
    private TextView mTvExperience ;

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
        mFunctionIntent = new Intent(ACTION_COMMON_ACTIVITY);
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

        mTvExperience = (TextView) rootView.findViewById(R.id.tvExperience);
        String text = getString(R.string.experience , "126/1000");
        mTvExperience.setText(ViewUtil.getSpannableString(text ,R.color.yellow ,0 ,3));
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
                enterFloor();
                break;
            case R.id.ibTraining:
                mFunctionIntent.putExtra(FRAGMENT_TAG , getString(R.string.training_home));
                startActivity(mFunctionIntent);
                break;
            default:
                break;
        }
    }

    private void enterFloor() {
        String recordTag = getString(R.string.fighting_record) ;
        HashMap<String,String> fightingRecords = DataUtil.getSharedPreferencesData(recordTag);
        if(fightingRecords == null || fightingRecords.size()==0){ // never fighting before
            // record fighting state first
            DataUtil.recordSharedPreferencesData( recordTag  ,
                   getString(R.string.fighting_state) , getString(R.string.fighting_state_false));
            // enter floor selecting activity
            mFunctionIntent.setAction(ACTION_COMMON_ACTIVITY);
            mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.fight_to_floor));
            startActivity(mFunctionIntent);
        }else{      // ever fighting before
            String fightingState = fightingRecords.get(getString(R.string.fighting_state));
            if(getString(R.string.fighting_state_false).equals(fightingState)){ //already exited challenging
                // enter floor selecting activity
                mFunctionIntent.setAction(ACTION_COMMON_ACTIVITY);
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.fight_to_floor));
                startActivity(mFunctionIntent);
            }else{  // still challenging
                // enter the floor fighting activity
                String floorTitle = fightingRecords.get(getString(R.string.fighting_floor_name));
                mFunctionIntent.setAction(ACTION_FIGHTING_ACTIVITY);
                mFunctionIntent.putExtra("title" , floorTitle);
                startActivity(mFunctionIntent);
            }
        }
        // restore intent defaults action
        mFunctionIntent.setAction(ACTION_COMMON_ACTIVITY);
    }


}

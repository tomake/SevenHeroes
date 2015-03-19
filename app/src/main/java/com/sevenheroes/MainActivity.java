package com.sevenheroes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sevenheroes.fragment.MenuFragment;
import com.sevenheroes.util.DataUtil;
import com.sevenheroes.util.InjectView;
import com.sevenheroes.util.Injector;
import com.sevenheroes.util.ViewUtil;

import java.util.HashMap;


public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener , MenuFragment.OnMenuSelectListener {

    private static final String ACTION_COMMON_ACTIVITY = "com.sevenheroes.CommonActivity";
    private static final String ACTION_FIGHTING_ACTIVITY = "com.sevenheroes.FloorFightingActivity";

    private static final String FRAGMENT_TAG = "fragment";
    private static Intent mFunctionIntent;

    @InjectView(R.id.ibDrawerToggle)
    private ImageButton mDrawerToggle;
    @InjectView(R.id.ibGift)
    private ImageButton mIbGift;
    @InjectView(R.id.ibFloor)
    private ImageButton mIbFloor;
    @InjectView(R.id.ibTraining)
    private ImageButton mIbTraining;
    @InjectView(R.id.ibPay)
    private ImageButton mIbPay;
    @InjectView(R.id.tvExperience)
    private TextView mTvExperience;
    @InjectView(R.id.btnUpgrade)
    private Button mBtnUpgrade;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragment_game_page);
        setBehindContentView(R.layout.menu_frame);
        initViews();
    }


    private void initViews() {
        Injector.get(this).inject();
        mDrawerToggle.setOnClickListener(this);
        mIbPay.setOnClickListener(this);
        mIbGift.setOnClickListener(this);
        mIbFloor.setOnClickListener(this);
        mIbTraining.setOnClickListener(this);
        mBtnUpgrade.setOnClickListener(this);
        String text = getString(R.string.experience, "126/1000");
        mTvExperience.setText(ViewUtil.getSpannableString(text, R.color.yellow, 0, 3));

        getSupportActionBar().hide();

        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setOnMenuSelectListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.menu, menuFragment);
        ft.commit();

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setShadowWidth(20);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindWidth(200);
        slidingMenu.setFadeDegree(0.7f);
        slidingMenu.setTouchModeAbove(SlidingMenu.LEFT);

        mFunctionIntent = new Intent(ACTION_COMMON_ACTIVITY);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibDrawerToggle:
                toggle();
                break;
            case R.id.ibPay:
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.pay));
                startActivity(mFunctionIntent);
                break;
            case R.id.btnUpgrade:
                ViewUtil.toastInCenter(getString(R.string.resource_not_enough));
                break;
            case R.id.ibGift:
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.gift));
                startActivity(mFunctionIntent);
                break;
            case R.id.ibFloor:
                enterFloor();
                break;
            case R.id.ibTraining:
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.training_home));
                startActivity(mFunctionIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuSelect() {
        toggle();
    }

    private void enterFloor() {
        String recordTag = getString(R.string.fighting_record);
        HashMap<String, String> fightingRecords = DataUtil.getSharedPreferencesData(recordTag);
        if (fightingRecords == null || fightingRecords.size() == 0) { // never fighting before
            // record fighting state first
            DataUtil.recordSharedPreferencesData(recordTag,
                    getString(R.string.fighting_state), getString(R.string.fighting_state_false));
            // enter floor selecting activity
            mFunctionIntent.setAction(ACTION_COMMON_ACTIVITY);
            mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.fight_to_floor));
            startActivity(mFunctionIntent);
        } else {      // ever fighting before
            String fightingState = fightingRecords.get(getString(R.string.fighting_state));
            if (getString(R.string.fighting_state_false).equals(fightingState)) { //already exited challenging
                // enter floor selecting activity
                mFunctionIntent.setAction(ACTION_COMMON_ACTIVITY);
                mFunctionIntent.putExtra(FRAGMENT_TAG, getString(R.string.fight_to_floor));
                startActivity(mFunctionIntent);
            } else {  // still challenging
                // enter the floor fighting activity
                String floorTitle = fightingRecords.get(getString(R.string.fighting_floor_name));
                mFunctionIntent.setAction(ACTION_FIGHTING_ACTIVITY);
                mFunctionIntent.putExtra("title", floorTitle);
                startActivity(mFunctionIntent);
            }
        }
        // restore intent defaults action
        mFunctionIntent.setAction(ACTION_COMMON_ACTIVITY);
    }


}
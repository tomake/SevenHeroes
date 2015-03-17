package com.sevenheroes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sevenheroes.util.DataUtil;
import com.sevenheroes.util.InjectView;
import com.sevenheroes.util.Injector;
import com.sevenheroes.util.ViewUtil;
import com.sevenheroes.view.AddRecoveryDialog;
import com.sevenheroes.view.AutoChallengeDialog;


public class FloorFightingActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.ivBack)
    private ImageView mIvBack;
    @InjectView(R.id.ivBackLogo)
    private ImageView mIvBackLogo;
    @InjectView(R.id.ivFightKnife)
    private ImageView mIvFightKnife;
    @InjectView(R.id.btnAddRecovery)
    private Button mBtnAddRecovery;
    @InjectView(R.id.btnAddExperience)
    private Button mBtnAddExperience;
    @InjectView(R.id.btnTreatment)
    private Button mBtnTreatment;
    @InjectView(R.id.btnChangeBoss)
    private Button mBtnChangeBoss;
    @InjectView(R.id.btnAutoSoldier)
    private Button mBtnAutoSoldier;
    @InjectView(R.id.btnChallenge)
    private Button mBtnChallenge;
    @InjectView(R.id.btnAutoChallenge)
    private Button mBtnAutoChallenge;
    @InjectView(R.id.btnExit)
    private Button mBtnExit;
    @InjectView(R.id.ratingBarChanllengeTimes)
    private RatingBar mRatingBatTimes;
    @InjectView(R.id.tvLeftTime)
    private TextView mTvLeftTime;
    @InjectView(R.id.tvGainBossExperience)
    private TextView mTvGainBossExperience;
    @InjectView(R.id.tvGainRewards)
    private TextView mTvGainRewards;
    @InjectView(R.id.tvLevelChallengeResult)
    private TextView mTvLevelChallengeResult;
    @InjectView(R.id.tvTitle)
    private TextView mTvTitle;

    private boolean mIsChallenging = false;
    private AsyncTask<Integer, Integer, Void> mTimingTask;
    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_floor_fighting);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimingTask != null) {
            mTimingTask.cancel(true);
            mTimingTask = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showFightingBoss(data.getIntExtra(getString(R.string.choose_result_tag), -1));
            }
        }
    }

    private void initViews() {
        Injector.get(this).inject();
        mIvBack.setOnClickListener(this);
        mIvBackLogo.setOnClickListener(this);
        mBtnAddExperience.setOnClickListener(this);
        mBtnAddRecovery.setOnClickListener(this);
        mBtnAutoChallenge.setOnClickListener(this);
        mBtnAutoSoldier.setOnClickListener(this);
        mBtnChallenge.setOnClickListener(this);
        mBtnChangeBoss.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
        mBtnTreatment.setOnClickListener(this);

        mTvTitle.setText(getIntent().getStringExtra("title"));

        String experience = getString(R.string.boss_gain_experience);
        SpannableString spannableString = ViewUtil.getSpannableString(experience, R.color.yellow, 7, experience.length());
        mTvGainBossExperience.setText(spannableString);

        String rewards = getString(R.string.gain_rewards);
        spannableString = ViewUtil.getSpannableString(rewards, R.color.yellow, 5, rewards.length());
        mTvGainRewards.setText(spannableString);
    }

    private void showFightingBoss(int fightingCount) {
        if (fightingCount <= 0) return;
        LinearLayout bossesLayout = (LinearLayout) findViewById(R.id.llBosses);
        int totalCount = bossesLayout.getChildCount();
        for (int i = 0; i < totalCount; i++) {
            if (i < fightingCount) {
                bossesLayout.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                bossesLayout.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddExperience:
                alert(getString(R.string.tips), getString(R.string.add_experience_tips)
                        , getString(R.string.add_experience));
                break;
            case R.id.btnAddRecovery:
                showRecoveryDialog();
                break;
            case R.id.btnTreatment:
                ViewUtil.toastInCenter(getString(R.string.operate_successful));
                break;
            case R.id.btnChangeBoss:
                Intent intent = new Intent(FloorFightingActivity.this, ChooseLeaderActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btnAutoSoldier:
                ViewUtil.toastInCenter(getString(R.string.operate_successful));
                break;
            case R.id.btnChallenge:
                startChallenge();
                break;
            case R.id.btnAutoChallenge:
                showAutoChallengeDialog();
                break;
            case R.id.btnExit:
                alert(getString(R.string.tips), getString(R.string.exit_tips)
                        , getString(R.string.exit));
                break;
            case R.id.ivBack:
            case R.id.ivBackLogo:
                finish();
                break;
            default:
                break;
        }
    }

    private void showAutoChallengeDialog() {
        AutoChallengeDialog dialog = new AutoChallengeDialog(this);
        dialog.setOnAutoFightingResultListener(new AutoChallengeDialog.OnAutoFightingResultListener() {
            @Override
            public void onStartClick() {
                startChallenge();
            }

            @Override
            public void onExitClick() {

            }
        });
        dialog.show();
    }

    private void showRecoveryDialog() {
        AddRecoveryDialog dialog = new AddRecoveryDialog(this);
        dialog.show();
    }

    private void startChallenge() {
        if (!mIsChallenging) {
            ViewUtil.toastInCenter(getString(R.string.challenge_begin));
            startTiming(63);
        } else {
            ViewUtil.toastInCenter(getString(R.string.battle_not_end));
        }
    }

    private void startTiming(int seconds) {
        mIsChallenging = true;
        mIvFightKnife.setVisibility(View.VISIBLE);
        mTvLeftTime.setVisibility(View.VISIBLE);
        mTimingTask = new AsyncTask<Integer, Integer, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {
                long oldTime = System.currentTimeMillis();
                while (params[0] > 0) {
                    if (System.currentTimeMillis() - oldTime == 1000) {
                        oldTime = System.currentTimeMillis();
                        publishProgress(--params[0]);
                    }
                }
                return null;  // time out
            }


            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                int minutes = values[0] / 60;
                String minuteText = minutes >= 10 ? (minutes + "") : ("0" + minutes);
                int seconds = values[0] % 60;
                String secondText = seconds >= 10 ? (seconds + "") : ("0" + seconds);
                String text = getResources().getString(R.string.left_time, minuteText, secondText);

                // the prefix of text is 剩余时间：**时**秒，so the colored text start from index 5
                SpannableString spannableString = ViewUtil.getSpannableString(text, R.color.yellow, 5, text.length());
                mTvLeftTime.setText(spannableString);
                if (values[0] == 0) {
                    String resultText = getString(R.string.floor_level) + getString(R.string.fighting_result);
                    mTvLevelChallengeResult.setText(ViewUtil.getSpannableString(resultText, R.color.yellow,
                            resultText.length() - 3, resultText.length()));
                    mTvLeftTime.setText("");
                    mTvLeftTime.setVisibility(View.GONE);
                    mIvFightKnife.setVisibility(View.GONE);
                    mIsChallenging = false;
                }

            }
        };
        mTimingTask.execute(seconds);
    }

    /**
     * tips dialog to show
     *
     * @param title  dialog title
     * @param msg    tips message
     * @param action button action
     */
    public void alert(String title, String msg, final String action) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getString(R.string.add_experience).equals(action)) {
                            ViewUtil.toastInCenter(getString(R.string.got_experience));
                        } else if (getString(R.string.exit).equals(action)) {
                            // record fighting state
                            DataUtil.recordSharedPreferencesData(getString(R.string.fighting_record),
                                    getString(R.string.fighting_state), getString(R.string.fighting_state_false));

                            Intent intent = new Intent("com.sevenheroes.CommonActivity");
                            intent.putExtra(getString(R.string.fragment), getString(R.string.fight_to_floor));
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
        alertDialog.show();
    }
}

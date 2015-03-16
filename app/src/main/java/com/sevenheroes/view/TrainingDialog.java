package com.sevenheroes.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sevenheroes.R;
import com.sevenheroes.util.InjectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by forest on 2015/3/16.
 */
public class TrainingDialog extends Dialog implements View.OnClickListener, TextWatcher {

    @InjectView(R.id.tvConsumeTitle)
    private TextView mTvConsumeTitle;
    @InjectView(R.id.etAmount)
    private EditText mEtAmount;
    @InjectView(R.id.lvDialogTraining)
    private ListView mLvTraining;
    @InjectView(R.id.ibPlus)
    private ImageButton mIbPlus;
    @InjectView(R.id.ibReduce)
    private ImageButton mIbReduces;
    @InjectView(R.id.btnTrainingSure)
    private Button mBtnSure;
    @InjectView(R.id.btnTrainingCancel)
    private Button mBtnCancel;


    private OnTrainingSelectedListener mOnTrainingSelectedListener;
    private SimpleAdapter mSimpleAdapter;
    private List<HashMap<String, Object>> mDatas;
    private int mSingleExperience;
    private int mSingleMoney;

    public TrainingDialog(Context context) {
        super(context, R.style.DialogStyle);
        setContentView(R.layout.dialog_training);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        initViews();

    }


    public TrainingDialog(Context context, int theme) {
        super(context, theme);
    }

    protected TrainingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initViews() {
        mSingleExperience = 372;
        mSingleMoney = 732;

        mTvConsumeTitle = (TextView) findViewById(R.id.tvConsumeTitle);
        mTvConsumeTitle.setText("消费元宝");

        mEtAmount = (EditText) findViewById(R.id.etAmount);
        mEtAmount.addTextChangedListener(this);
        mLvTraining = (ListView) findViewById(R.id.lvDialogTraining);
        mIbPlus = (ImageButton) findViewById(R.id.ibPlus);
        mIbReduces = (ImageButton) findViewById(R.id.ibReduce);
        mBtnSure = (Button) findViewById(R.id.btnTrainingSure);
        mBtnCancel = (Button) findViewById(R.id.btnTrainingCancel);

        mIbPlus.setOnClickListener(this);
        mIbReduces.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mDatas = changeTrainingData(new ArrayList<HashMap<String, Object>>(), 1);
        mSimpleAdapter = new SimpleAdapter(getContext(), mDatas, R.layout.item_dialog_training,
                new String[]{"name", "experience", "money"},
                new int[]{R.id.tvName, R.id.tvExperienceAmount, R.id.tvConsumeMoney});
        mLvTraining.setAdapter(mSimpleAdapter);
    }


    public void setTrainingTypeAdvanced(boolean isAdvanced) {
        if (isAdvanced) {
            mSingleExperience = 1024;
            mSingleMoney = 2;
            mTvConsumeTitle.setText("消费元宝");
        } else {
            mSingleExperience = 372;
            mSingleMoney = 732;
            mTvConsumeTitle.setText("消费铜币");
        }
        if (mSimpleAdapter != null){
            changeTrainingData(mDatas , 1);
        }

    }

    private void increaseTrainingAmount(boolean isAdded) {
        String amountString = mEtAmount.getText().toString();
        int result = getAmountResult(amountString, isAdded);
        mEtAmount.setText(result + "");
        changeTrainingData(mDatas, result);
    }

    private int getAmountResult(String value, boolean isIncrease) {
        if (value != null && value.length() > 0) { // training amount input has value
            try {
                int amount = Integer.parseInt(value);
                if (isIncrease) {
                    return (amount + 1);
                } else {
                    if (amount > 1)
                        return (amount - 1);
                    else return 1;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 1;  // return default value while user input was not a number
            }
        } else { // if  training amount input was invalid , return default value
            return 1;
        }
    }

    private int getInputAmount() {
        String value = mEtAmount.getText().toString();
        if (value != null && value.length() > 0) { // training amount input has value
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;  // return default value while user input was not a number
            }
        } else { // if  training amount input was invalid , return default value
            return 0;
        }
    }

    public List<HashMap<String, Object>> changeTrainingData(List<HashMap<String, Object>> maps, int count) {
        maps.clear();
        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("name", "孙晓武");
            item.put("experience", mSingleExperience * count);
            item.put("money", mSingleMoney * count);
            maps.add(item);
        }
        if (mSimpleAdapter != null)
            mSimpleAdapter.notifyDataSetChanged();
        return maps;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibPlus:
                increaseTrainingAmount(true);
                break;
            case R.id.ibReduce:
                increaseTrainingAmount(false);
                break;
            case R.id.btnTrainingSure:
                if(mOnTrainingSelectedListener == null){
                    throw  new IllegalArgumentException("You have to call setOnTrainingSelectedListener first !");
                }else {
                    mOnTrainingSelectedListener.onSureClick(getInputAmount());
                }
                dismiss();
                break;
            case R.id.btnTrainingCancel:
                if(mOnTrainingSelectedListener == null){
                    throw  new IllegalArgumentException("You have to call setOnTrainingSelectedListener first !");
                }else {
                    mOnTrainingSelectedListener.onCancelClick();
                }
                dismiss();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int count = getInputAmount();
        changeTrainingData(mDatas, count);

    }

    public interface OnTrainingSelectedListener {
        void onSureClick(int result);
        void onCancelClick();
    }

    public void setOnTrainingSelectedListener(OnTrainingSelectedListener onTrainingSelectedListener) {
        mOnTrainingSelectedListener = onTrainingSelectedListener;
    }

}

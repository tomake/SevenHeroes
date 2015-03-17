package com.sevenheroes.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sevenheroes.R;
import com.sevenheroes.util.ViewUtil;

/**
 * Created by forest on 2015/3/17.
 */
public class AutoChallengeDialog extends Dialog implements View.OnClickListener{
    private OnAutoFightingResultListener mOnAutoFightingResultListener;
    private EditText mEtAutoFightingLevel ;
    private Button mBtnStart;
    private Button mBtnExit;

    public AutoChallengeDialog(Context context) {
        super(context , R.style.DialogStyle);
        setContentView(R.layout.dialog_auto_challenge);
        initViews();
    }

    public AutoChallengeDialog(Context context, int theme) {
        super(context, theme);
    }

    protected AutoChallengeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initViews() {
        mBtnExit =(Button) findViewById(R.id.btnExit) ;
        mBtnExit.setOnClickListener(this);
        mBtnStart = (Button) findViewById(R.id.btnStart) ;
        mBtnStart.setOnClickListener(this);
        mEtAutoFightingLevel = (EditText) findViewById(R.id.etAutoFightingLevel) ;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                start();
                break;
            case  R.id.btnExit:
                mOnAutoFightingResultListener.onExitClick();
                dismiss();
                break;
            default:
                break;

        }
    }

    private void start(){
        String result = mEtAutoFightingLevel.getText().toString();
        try {
            int level = Integer.valueOf(result);
            if(level>0){
                mOnAutoFightingResultListener.onStartClick();
                dismiss();
            }else{
                ViewUtil.toastInCenter(getContext().getResources().getString(R.string.floor_input_amount_tips));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ViewUtil.toastInCenter(getContext().getResources().getString(R.string.floor_input_amount_tips));
        }
    }

    public void setOnAutoFightingResultListener(OnAutoFightingResultListener onAutoFightingResultListener) {
        mOnAutoFightingResultListener = onAutoFightingResultListener;
    }

    public interface OnAutoFightingResultListener{
        public void onStartClick();
        public void onExitClick();
    }

}

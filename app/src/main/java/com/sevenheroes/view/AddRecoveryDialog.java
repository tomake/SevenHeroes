package com.sevenheroes.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sevenheroes.R;
import com.sevenheroes.util.ViewUtil;

/**
 * Created by forest on 2015/3/17.
 */
public class AddRecoveryDialog extends Dialog implements View.OnClickListener{

    private Button mBtnSure;
    private Button mBtnCancel;
    private TextView mTvHelpBox;
    private TextView mTvHelpPackage;
    public AddRecoveryDialog(Context context) {
        super(context , R.style.DialogStyle);
        setContentView(R.layout.dialog_help_add);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT , WindowManager.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    public AddRecoveryDialog(Context context, int theme) {
        super(context, theme);
    }

    protected AddRecoveryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initViews() {
        mTvHelpBox = (TextView) findViewById(R.id.tvHelpBox);
        mTvHelpBox.setOnClickListener(this);
        mTvHelpPackage = (TextView) findViewById(R.id.tvHelpPackage);
        mTvHelpPackage.setOnClickListener(this);
        mBtnCancel =(Button) findViewById(R.id.btnCancel) ;
        mBtnCancel.setOnClickListener(this);
        mBtnSure = (Button) findViewById(R.id.btnSure) ;
        mBtnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSure:
                ViewUtil.toastInCenter(getContext().getResources().getString(R.string.operate_successful));
                dismiss();
                break;
            case  R.id.btnCancel:
                dismiss();
                break;
            case R.id.tvHelpBox:
                mTvHelpBox.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                mTvHelpPackage.setBackgroundColor(Color.TRANSPARENT);
                break;
            case R.id.tvHelpPackage:
                mTvHelpPackage.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                mTvHelpBox.setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                break;

        }
    }
}

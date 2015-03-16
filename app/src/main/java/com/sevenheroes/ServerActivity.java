package com.sevenheroes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sevenheroes.util.DataUtil;
import com.sevenheroes.util.InjectView;
import com.sevenheroes.util.Injector;

import java.util.ArrayList;
import java.util.HashMap;


public class ServerActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.btnEnterGame)
    private Button mBtnEnterGame;
    @InjectView(R.id.btnArea)
    private Button mBtnArea;
    @InjectView(R.id.btnServer)
    private Button mBtnServer;
    @InjectView(R.id.tvExchangeAccount)
    private TextView mTvExchangeAccount;
    @InjectView(R.id.tvServerHistory1)
    private TextView mTvServerHistory1;
    @InjectView(R.id.tvServerHistory2)
    private TextView mTvServerHistory2;
    @InjectView(R.id.tvServerHistory3)
    private TextView mTvServerHistory3;
    @InjectView(R.id.tvServerHistory4)
    private TextView mTvServerHistory4;

    private final String[] mAreas = new String[]{"神州区(双线)", "华夏区(电信)", "华南区(电信)", "华北区(电信)", "华东区(电信)",
            "西部区(电信)", "华中区(电信)", "东北区(网通)"};
    private final String[] mServers = new String[]{"广东一区", "湖南一区", "河北一区", "黑龙江一区", "广西一区", "青海一区", "江西一区",
            "浙江一区", "北京一区", "辽宁一区", "内蒙古一区", "四川一区", "重庆一区", "西藏一区", "新疆一区",
            "河南一区", "湖北一区", "甘肃一区", "宁夏一区", "深圳一区", "广州一区", "珠海一区", "福建一区",};

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_server);
        initViews();
    }

    private void initViews() {
        Injector.get(this).inject();    // init views' id
        mTvExchangeAccount.setOnClickListener(this);
        mBtnEnterGame.setOnClickListener(this);
        mBtnArea.setOnClickListener(this);
        mBtnServer.setOnClickListener(this);
        mTvServerHistory1.setOnClickListener(this);
        mTvServerHistory2.setOnClickListener(this);
        mTvServerHistory3.setOnClickListener(this);
        mTvServerHistory4.setOnClickListener(this);
        String username = getIntent().getStringExtra(getString(R.string.username));
        mTvExchangeAccount.setText(getString(R.string.exchange_account, username));

        // show history logon servers
        HashMap<String, String> serverHistory = DataUtil.getSharedPreferencesData(getString(R.string.server_history));
        if (serverHistory != null && serverHistory.size() > 0) {
            ArrayList<String> keys = new ArrayList<>();
            for (String key : serverHistory.keySet()) {
                keys.add(key);
            }
            if (keys.size() >= 1) {
                String area = keys.get(0);
                String server = serverHistory.get(keys.get(0));
                mTvServerHistory1.setVisibility(View.VISIBLE);
                mTvServerHistory1.setOnClickListener(this);
                mTvServerHistory1.setText(area + server);
                mTvServerHistory2.setVisibility(View.INVISIBLE); // history1、history2 layout the view equally in horizontal direction
                mBtnArea.setText(area);
                mBtnServer.setText(server);
            }
            if (keys.size() >= 2) {
                mTvServerHistory2.setVisibility(View.VISIBLE);
                mTvServerHistory2.setOnClickListener(this);
                mTvServerHistory2.setText(keys.get(1) + serverHistory.get(keys.get(1)));
            }
            if (keys.size() >= 3) {
                mTvServerHistory3.setVisibility(View.VISIBLE);
                mTvServerHistory3.setOnClickListener(this);
                mTvServerHistory3.setText(keys.get(2) + serverHistory.get(keys.get(2)));
                mTvServerHistory4.setVisibility(View.INVISIBLE); // history3、history4 layout the view equally in horizontal direction
            }
            if (keys.size() >= 4) {
                mTvServerHistory4.setVisibility(View.VISIBLE);
                mTvServerHistory4.setOnClickListener(this);
                mTvServerHistory4.setText(keys.get(3) + serverHistory.get(keys.get(3)));
            }
        } else {
            mBtnArea.setText(mAreas[0]);
            mBtnServer.setText(mServers[0]);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tvExchangeAccount:
                intent = new Intent(ServerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnEnterGame:
            case R.id.tvServerHistory1:
            case R.id.tvServerHistory2:
            case R.id.tvServerHistory3:
            case R.id.tvServerHistory4:
                String area = mBtnArea.getText().toString();
                String server = mBtnServer.getText().toString();
                DataUtil.recordSharedPreferencesData(getString(R.string.server_history), area, server);
                intent = new Intent(ServerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnServer:
                selectServer(getString(R.string.select_server), mServers);
                break;
            case R.id.btnArea:
                selectServer(getString(R.string.select_area), mAreas);
                break;
            default:
                break;
        }

    }

    /**
     * select server
     *
     * @param title
     * @param items selected items
     */
    public void selectServer(final String title, String[] items) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title.equals(getString(R.string.select_server)))
                            mBtnServer.setText(mServers[which]);
                        else
                            mBtnArea.setText(mAreas[which]);
                    }
                })
                .create()
                .show();
    }

}

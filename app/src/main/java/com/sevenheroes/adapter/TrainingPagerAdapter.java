package com.sevenheroes.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.sevenheroes.R;
import com.sevenheroes.bean.Role;
import com.sevenheroes.util.ViewUtil;
import com.sevenheroes.view.TrainingDialog;

import java.util.ArrayList;

/**
 * Created by forest on 2015/3/15.
 */
public class TrainingPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private ArrayList<View> mViews;
    private String[] mTitles;
    private Context mContext;
    private ArrayList<Role> mRoles;
    private Button mBtnSelectAll;
    private Button mBtnCancelAll;
    private Button mBtnNormalTraining;
    private Button mBtnAdvanceTraining;
    private Button mBtnStopTraining;

    private TrainingAdapter mTrainingAdapter;
    private TrainingAdapter mWujiangAdapter;
    private TrainingAdapter mLingwuAdapter;

    public TrainingPagerAdapter(Context context, ArrayList<View> views, ArrayList<Role> roles) {
        this(context, views, roles, null);
    }

    public TrainingPagerAdapter(Context context, ArrayList<View> views, ArrayList<Role> roles, String[] titles) {
        mContext = context;
        mTitles = titles;
        mViews = views;
        mRoles = roles;

        mWujiangAdapter = new TrainingAdapter();
        mLingwuAdapter = new TrainingAdapter();
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        View v = mViews.get(position);

        mBtnSelectAll = (Button) v.findViewById(R.id.btnSelectAll);
        mBtnSelectAll.setOnClickListener(this);
        mBtnCancelAll = (Button) v.findViewById(R.id.btnCancelAll);
        mBtnCancelAll.setOnClickListener(this);
        mBtnNormalTraining = (Button) v.findViewById(R.id.btnNormalTraining);
        mBtnNormalTraining.setOnClickListener(this);
        mBtnAdvanceTraining = (Button) v.findViewById(R.id.btnAdvanceTraining);
        mBtnAdvanceTraining.setOnClickListener(this);
        mBtnStopTraining = (Button) v.findViewById(R.id.btnStopTraining);
        mBtnStopTraining.setOnClickListener(this);

        ListView lvTraining = (ListView) v.findViewById(R.id.lvTraining);
        if (position == 0) {
            mTrainingAdapter = mWujiangAdapter;
            lvTraining.setAdapter(mWujiangAdapter);
        } else if (position == 1) {
            mTrainingAdapter = mLingwuAdapter;
            lvTraining.setAdapter(mLingwuAdapter);

        }

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelectAll:
                selectAllRoles(true);
                break;
            case R.id.btnCancelAll:
                selectAllRoles(false);
                break;
            case R.id.btnNormalTraining:
                openAdvancedTrainingDialog(false); //showTrainingDialog(false);
                break;
            case R.id.btnAdvanceTraining:
                openAdvancedTrainingDialog(true);  //showTrainingDialog(true);
                break;
            case R.id.btnStopTraining:
                selectAllRoles(false);
                break;

        }
    }

    private void openAdvancedTrainingDialog(boolean isAdvanced){
        TrainingDialog trainingDialog = new TrainingDialog(mContext);
        trainingDialog.setTrainingTypeAdvanced(isAdvanced);
        trainingDialog.setOnTrainingSelectedListener(new TrainingDialog.OnTrainingSelectedListener() {
            @Override
            public void onSureClick(int result) {
                ViewUtil.toast(result+"");
            }

            @Override
            public void onCancelClick() {

            }
        });
        trainingDialog.show();
    }

    private void selectAllRoles(boolean b) {
        if (mTrainingAdapter != null) {
            mTrainingAdapter.selectAllItem(b);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        CheckBox cbTraining;
        TextView tvRoleName;
        TextView tvRoleState;
        TextView tvExperience;
        TextView tvLeftTime;
        TextView tvLevel;
        View vItem;
    }

    public class TrainingAdapter extends BaseAdapter {

        private boolean isRoleSelect = false;

        public TrainingAdapter() {
        }

        @Override
        public int getCount() {
            return mRoles.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_training, null);
                viewHolder = new ViewHolder();
                viewHolder.cbTraining = (CheckBox) convertView.findViewById(R.id.cbTraining);
                viewHolder.tvExperience = (TextView) convertView.findViewById(R.id.tvGottenExperience);
                viewHolder.tvLeftTime = (TextView) convertView.findViewById(R.id.tvLeftTime);
                viewHolder.tvRoleName = (TextView) convertView.findViewById(R.id.tvRoleName);
                viewHolder.tvRoleState = (TextView) convertView.findViewById(R.id.tvRoleState);
                viewHolder.tvLevel = (TextView) convertView.findViewById(R.id.tvBossLevel);
                viewHolder.vItem = convertView.findViewById(R.id.rlRoleTraning);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            Role role = mRoles.get(position);
            viewHolder.cbTraining.setChecked(isRoleSelect);
            viewHolder.tvExperience.setText(role.getExperience());
            viewHolder.tvLeftTime.setText("剩余时间：00分00秒");
            viewHolder.tvRoleState.setText(role.getState());
            viewHolder.tvRoleName.setText(role.getName());
            viewHolder.tvLevel.setText(role.getLevel());
            viewHolder.vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = !viewHolder.cbTraining.isChecked();
                    viewHolder.cbTraining.setChecked(checked);
                }
            });
            return convertView;
        }

        public void selectAllItem(boolean b) {
            isRoleSelect = b;
            notifyDataSetChanged();
        }
    }
}

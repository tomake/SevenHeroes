package com.sevenheroes.adapter;

import android.content.Context;
import android.os.AsyncTask;
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
    private Button mBtnLingwu;
    private Button mBtnStop;
    private Button mBtnTrainingAll;
    private Button mBtnCancelAllTraining;

    private TrainingAdapter mTrainingAdapter;
    private TrainingAdapter mLingwuAdapter;

    public TrainingPagerAdapter(Context context, ArrayList<View> views, ArrayList<Role> roles) {
        this(context, views, roles, null);
    }

    public TrainingPagerAdapter(Context context, ArrayList<View> views, ArrayList<Role> roles, String[] titles) {
        mContext = context;
        mTitles = titles;
        mViews = views;
        mRoles = roles;

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
        if (position == 0) {
            mBtnCancelAllTraining = (Button) v.findViewById(R.id.btnCancelAllTraining);
            mBtnCancelAllTraining.setOnClickListener(this);
            mBtnTrainingAll = (Button) v.findViewById(R.id.btnTrainingAll);
            mBtnTrainingAll.setOnClickListener(this);
            mBtnNormalTraining = (Button) v.findViewById(R.id.btnNormalTraining);
            mBtnNormalTraining.setOnClickListener(this);
            mBtnAdvanceTraining = (Button) v.findViewById(R.id.btnAdvancedTraining);
            mBtnAdvanceTraining.setOnClickListener(this);
            mBtnStopTraining = (Button) v.findViewById(R.id.btnStopTraining);
            mBtnStopTraining.setOnClickListener(this);
            ListView lvTraining = (ListView) v.findViewById(R.id.lvTraining);
            mTrainingAdapter = new TrainingAdapter();
            lvTraining.setAdapter(mTrainingAdapter);
        } else if (position == 1) {
            mBtnSelectAll = (Button) v.findViewById(R.id.btnSelectAll);
            mBtnSelectAll.setOnClickListener(this);
            mBtnCancelAll = (Button) v.findViewById(R.id.btnCancelAll);
            mBtnCancelAll.setOnClickListener(this);
            mBtnLingwu = (Button) v.findViewById(R.id.btnLingWu);
            mBtnLingwu.setOnClickListener(this);
            mBtnStop = (Button) v.findViewById(R.id.btnStopLingWu);
            mBtnStop.setOnClickListener(this);
            ListView lvTraining = (ListView) v.findViewById(R.id.lvLingWu);
            mLingwuAdapter = new TrainingAdapter();
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
                selectAllRoles(mLingwuAdapter ,true);
                break;
            case R.id.btnCancelAll:
                selectAllRoles(mLingwuAdapter ,false);
                break;
            case R.id.btnLingWu:
                mLingwuAdapter.startTraining();
                ViewUtil.toastInCenter(mContext.getString(R.string.operate_successful));
                break;
            case R.id.btnStopLingWu:
                selectAllRoles(mLingwuAdapter ,false);
                mLingwuAdapter.stopTraining();
                ViewUtil.toastInCenter(mContext.getString(R.string.operate_successful));
                break;

            case R.id.btnTrainingAll:
                selectAllRoles(mTrainingAdapter ,true);
                break;
            case R.id.btnCancelAllTraining:
                selectAllRoles(mTrainingAdapter ,false);
                break;
            case R.id.btnAdvancedTraining:
                openAdvancedTrainingDialog(true);
                break;
            case R.id.btnNormalTraining:
                openAdvancedTrainingDialog(false);
                break;

            case R.id.btnStopTraining:
                selectAllRoles(mTrainingAdapter,false);
                mTrainingAdapter.stopTraining();
                ViewUtil.toastInCenter(mContext.getString(R.string.operate_successful));
                break;

        }
    }

    private void openAdvancedTrainingDialog(boolean isAdvanced) {
        TrainingDialog trainingDialog = new TrainingDialog(mContext);
        trainingDialog.setTrainingTypeAdvanced(isAdvanced);
        trainingDialog.setOnTrainingSelectedListener(new TrainingDialog.OnTrainingSelectedListener() {
            @Override
            public void onSureClick(int result) {
                mTrainingAdapter.startTraining();
            }

            @Override
            public void onCancelClick() {

            }
        });
        trainingDialog.show();
    }

    private void selectAllRoles(TrainingAdapter adapter, boolean b) {
        if (adapter != null) {
            adapter.selectAllItem(b);
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
        private boolean isTimingOn = false ;

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

            viewHolder.tvExperience.setText(role.getExperience());
            viewHolder.tvLeftTime.setText(mContext.getString(R.string.left_time, "00", "00"));
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
            if(isTimingOn){
                if(viewHolder.cbTraining.isChecked())
                    startTiming(viewHolder.tvLeftTime , 30*60);
            }
                viewHolder.cbTraining.setChecked(isRoleSelect);
            return convertView;
        }

        public void selectAllItem(boolean b) {
            isRoleSelect = b;
            notifyDataSetChanged();
        }

        public void startTraining(){
            isTimingOn = true ;
            notifyDataSetChanged();
        }

        public void stopTraining(){
            isTimingOn = false ;
        }

        private void startTiming(final TextView view , int seconds) {
            final AsyncTask<Integer, Integer, Void> task = new AsyncTask<Integer, Integer, Void>() {

                @Override
                protected Void doInBackground(Integer... params) {
                    long oldTime = System.currentTimeMillis();
                    while (params[0] > 0) {
                        if(!isTimingOn){  // stop timing
                            publishProgress(0);
                            return null;
                        }
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
                    String text = mContext.getResources().getString(R.string.left_time, minuteText, secondText);
                    view.setText(text);
                }
            };
            task.execute(seconds);
        }


    }
}

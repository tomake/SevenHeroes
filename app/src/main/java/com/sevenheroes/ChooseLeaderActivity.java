package com.sevenheroes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sevenheroes.bean.Role;
import com.sevenheroes.util.InjectView;
import com.sevenheroes.util.Injector;
import com.sevenheroes.util.ViewUtil;

import java.util.ArrayList;


public class ChooseLeaderActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.tvTitle)
    private TextView mTvTitle;
    @InjectView(R.id.ivBack)
    private ImageView mIvBack;
    @InjectView(R.id.ivBackLogo)
    private ImageView mIvBackLogo;
    @InjectView(R.id.btnAutoSelect)
    private Button mBtnAutoSelect;
    @InjectView(R.id.btnCancelAll)
    private Button mBtnCancelAll;
    @InjectView(R.id.btnSure)
    private Button mBtnSure;
    @InjectView(R.id.btnCancel)
    private Button mBtnCancel;
    @InjectView(R.id.lvChooseLeader)
    private ListView mListView;

    private ChooseLeaderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_leader);
        initViews();
    }

    private void initViews() {
        Injector.get(this).inject();
        mTvTitle.setText(getString(R.string.select_leader));
        mBtnAutoSelect.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnCancelAll.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mIvBackLogo.setOnClickListener(this);

        ArrayList<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setState("空闲");
        role1.setLevel("11");
        role1.setHealthAmount(100);
        role1.setTakeSoldierAmount(100);
        role1.setTakeSoldierType("一阶步兵");
        role1.setExperience("50");
        role1.setName("孙小武");
        role1.setJob("步将");
        role1.setBeingSelected(false);
        roles.add(role1);

        Role role2 = new Role();
        role2.setName("钟玄奇");
        role2.setJob("弓将");
        role2.setTakeSoldierType("一阶弓兵");
        role2.setState("空闲");
        role2.setLevel("11");
        role2.setHealthAmount(100);
        role2.setTakeSoldierAmount(100);
        role2.setExperience("50");
        role2.setBeingSelected(false);
        roles.add(role2);

        mAdapter = new ChooseLeaderAdapter(roles);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAutoSelect:
                if (mAdapter != null)
                    mAdapter.setAllItemSelect(true);
                break;
            case R.id.btnCancelAll:
                if (mAdapter != null)
                    mAdapter.setAllItemSelect(false);
                break;
            case R.id.btnSure:
                Intent intent = new Intent(ChooseLeaderActivity.this , FloorFightingActivity.class);
                intent.putExtra(getString(R.string.choose_result_tag), mAdapter.getSelectRolesAmount());
                setResult(RESULT_OK , intent);
                finish();
                break;
            case R.id.btnCancel:
            case R.id.ivBack:
            case R.id.ivBackLogo:
                finish();
                break;
            default:
                break;

        }
    }

    static class ViewHolder {
        CheckBox cbChooseLeader;
        TextView tvOrderNum;
        TextView tvLeaderName;
        TextView tvHealthAmount;
        TextView tvJob;
        TextView tvBossLevel;
        TextView tvSoldierType;
        TextView tvTakeSoldierAmount;
        TextView tvLeaderState;
    }

    class ChooseLeaderAdapter extends BaseAdapter {
        private ArrayList<Role> mRoles;
        private boolean mIsSelectAll = false;

        ChooseLeaderAdapter(ArrayList<Role> roles) {
            mRoles = roles;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_choose_leader, null);
                viewHolder = new ViewHolder();
                viewHolder.cbChooseLeader = (CheckBox) convertView.findViewById(R.id.cbChooseLeader);
                viewHolder.tvBossLevel = (TextView) convertView.findViewById(R.id.tvBossLevel);
                viewHolder.tvHealthAmount = (TextView) convertView.findViewById(R.id.tvHealthAmount);
                viewHolder.tvJob = (TextView) convertView.findViewById(R.id.tvJob);
                viewHolder.tvLeaderName = (TextView) convertView.findViewById(R.id.tvLeaderName);
                viewHolder.tvLeaderState = (TextView) convertView.findViewById(R.id.tvLeaderState);
                viewHolder.tvOrderNum = (TextView) convertView.findViewById(R.id.tvOrderNum);
                viewHolder.tvSoldierType = (TextView) convertView.findViewById(R.id.tvSoldierType);
                viewHolder.tvTakeSoldierAmount = (TextView) convertView.findViewById(R.id.tvTakeSoldierAmount);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Role role = mRoles.get(position);
            viewHolder.cbChooseLeader.setChecked(mIsSelectAll);
            viewHolder.tvTakeSoldierAmount.setText(role.getTakeSoldierAmount()+"");
            viewHolder.tvSoldierType.setText(role.getTakeSoldierType());
            viewHolder.tvOrderNum.setText(position+"");
            viewHolder.tvLeaderState.setText(role.getState());
            viewHolder.tvBossLevel.setText(role.getLevel());
            viewHolder.tvHealthAmount.setText(role.getHealthAmount()+"");
            viewHolder.tvJob.setText(role.getJob());
            viewHolder.tvLeaderName.setText(role.getName());
            viewHolder.cbChooseLeader.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mRoles.get(position).setBeingSelected(isChecked);
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean state = !viewHolder.cbChooseLeader.isChecked();
                    viewHolder.cbChooseLeader.setChecked(state);
                }
            });
            return convertView;
        }

        public void setAllItemSelect(boolean b) {
            mIsSelectAll = b;
            notifyDataSetChanged();
        }

        public int getSelectRolesAmount(){
            int account =0 ;
            for (Role role : mRoles){
                if (role.isBeingSelected())
                    account++;
            }
            return  account;
        }
    }
}

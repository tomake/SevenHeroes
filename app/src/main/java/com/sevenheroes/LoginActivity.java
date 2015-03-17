package com.sevenheroes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sevenheroes.util.DataUtil;
import com.sevenheroes.util.InjectView;
import com.sevenheroes.util.Injector;

import java.util.ArrayList;
import java.util.Set;


public class LoginActivity extends Activity implements View.OnClickListener , TextWatcher ,AdapterView.OnItemClickListener{
    @InjectView(R.id.btnLogin)
    private Button mBtnLogin;
    @InjectView(R.id.tvAutoCompleteAccount)
    private AutoCompleteTextView mTvAccount;
    @InjectView(R.id.etPassword)
    private EditText mEtPassword;
    @InjectView(R.id.cbAccount)
    private CheckBox mCbAccount;
    @InjectView(R.id.loginLayout)
    private RelativeLayout mRootView;

    final ArrayList<String> mOriginAccounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        Injector.get(this).inject();
        mRootView.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mCbAccount.setOnClickListener(this);
        Set<String> names = DataUtil.getSharedPreferencesData(getString(R.string.userdata)).keySet();
        for(String key : names){
            mOriginAccounts.add(key);
        }
        AccountAdapter adapter = new AccountAdapter(this , mOriginAccounts);
        mTvAccount.setDropDownBackgroundResource(R.drawable.dropdown_list_bg);
        mTvAccount.setThreshold(1);
        mTvAccount.setAdapter(adapter);
        mTvAccount.setDropDownVerticalOffset(10);
        mTvAccount.setOnItemClickListener(this);
        mTvAccount.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()!=R.id.cbAccount)
            mCbAccount.setChecked(false);
        switch (v.getId()){
            case R.id.btnLogin:
                login();
            break;
            case R.id.cbAccount:
                if(mCbAccount.isChecked()){
                    mTvAccount.showDropDown();
                }else{
                    mTvAccount.dismissDropDown();
                }
                break;
            default:break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String pwd = DataUtil.getSharedPreferencesData(getString(R.string.userdata))
                .get(mOriginAccounts.get(position));
        mEtPassword.setText(pwd);
        mCbAccount.setChecked(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!mTvAccount.isPopupShowing()){
            mCbAccount.setChecked(false);
        }else{
            mCbAccount.setChecked(true);
        }
        String userName = mTvAccount.getText().toString();
        if(!mOriginAccounts.contains(userName)){    // no account history matched
            mEtPassword.setText("");
        }
        else{   // account history matched
            String pwd = DataUtil.getSharedPreferencesData(getString(R.string.userdata))
                    .get(userName);
            mEtPassword.setText(pwd);
            mCbAccount.setChecked(false);
            mTvAccount.dismissDropDown();
        }
    }

    /**
     * user login
     */
    public void login(){
        String userName = mTvAccount.getText().toString();
        String pwd = mEtPassword.getText().toString();
        if(userName == null || userName.length() == 0){     // username not input
            alert(getString(R.string.login),getString(R.string.name_invalid) , getString(R.string.sure));
            return ;
        }
        if(pwd == null || pwd.length() == 0){   // password not input
            alert(getString(R.string.login),getString(R.string.pwd_invalid) , getString(R.string.sure));
           return ;
        }
        DataUtil.recordSharedPreferencesData(getString(R.string.userdata) , userName , pwd);
        Intent intent = new Intent(LoginActivity.this , ServerActivity.class);
        intent.putExtra(getString(R.string.username) , userName);
        startActivity(intent);
        finish();
    }

    /**
     * tips dialog to show
     * @param title dialog title
     * @param msg tips message
     * @param buttonString button text
     */
    public void alert(String title ,String msg , String buttonString){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(buttonString , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }


    static class ViewHolder{
        TextView tvAccountItem;
        ImageView ivDelete;
    }

    /**
     * AutoCompleteTextView Adapter
     */
    class AccountAdapter extends BaseAdapter implements Filterable{
        private LayoutInflater mInflater ;
        private ArrayList<String> mAccounts ;
        private ArrayFilter mFilter ;
        private Context mContext ;
        private Object mLock = new Object();
        public AccountAdapter(Context context ,ArrayList<String> accounts ){
            mContext = context ;
            mInflater = LayoutInflater.from(context);
            mAccounts = accounts ;
        }

        @Override
        public int getCount() {
            return mAccounts.size();
        }

        @Override
        public Object getItem(int position) {
            return mAccounts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.item_account, null);
                viewHolder = new ViewHolder();
                viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
                viewHolder.tvAccountItem = (TextView) convertView.findViewById(R.id.tvAccountItem);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvAccountItem.setText(mAccounts.get(position));
            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAccountInfo(position);
                }
            });
            return convertView;
        }

        private void removeAccountInfo(final int position) {
            new AlertDialog.Builder(mContext)
                    .setTitle(getString(R.string.remove_account))
                    .setMessage(getString(R.string.remove_account_confirm))
                    .setPositiveButton(getString(R.string.sure) , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataUtil.removeUserData(getString(R.string.userdata), mAccounts.get(position));
                            mAccounts.remove(position);
                            notifyDataSetChanged();
                        }
                    })
                    .create()
                    .show();
        }

        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new ArrayFilter();
            }
            return mFilter;
        }

        /**
         * AutoCompleteTextView Filter
         */
        private class ArrayFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();

                if(mAccounts == null || mAccounts.size()==0)
                    mAccounts = new ArrayList<>(mOriginAccounts);

                if (prefix == null || prefix.length() == 0) {
                    ArrayList<String> list;
                    synchronized (mLock) {
                        list = new ArrayList<>(mAccounts);
                    }
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = prefix.toString().toLowerCase();

                    ArrayList<String> values;
                    synchronized (mLock) {
                        values = new ArrayList<String>(mAccounts);
                    }

                    final int count = values.size();
                    final ArrayList<String> newValues = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        final String value = values.get(i);
                        final String valueText = value.toString().toLowerCase();

                        // First match against the whole, non-splitted value
                        if (valueText.startsWith(prefixString)) {
                            newValues.add(value);
                        } else {
                            final String[] words = valueText.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                            for (int k = 0; k < wordCount; k++) {
                                if (words[k].startsWith(prefixString)) {
                                    newValues.add(value);
                                    break;
                                }
                            }
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //noinspection unchecked
                mAccounts = (ArrayList<String>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }
}

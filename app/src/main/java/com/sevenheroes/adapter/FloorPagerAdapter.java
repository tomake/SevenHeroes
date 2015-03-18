package com.sevenheroes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sevenheroes.R;
import com.sevenheroes.util.DataUtil;
import com.sevenheroes.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by forest on 2015/3/18.
 */
public class FloorPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private ListView mLvRanking;
    private LinearLayout mLlRanking;
    private Button mBtnEnter;
    private Context mContext;

    public FloorPagerAdapter(Context context, List<View> views) {
        mContext = context;
        mViews = views;
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
    public View instantiateItem(ViewGroup container, int position) {
        View rootView = mViews.get(position);
        initView(rootView, position);
        container.addView(rootView);
        return rootView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @SuppressWarnings("deprecation")
    private void initView(View rootView, final int position) {
        int[] resId = new int[]{R.drawable.chonglou_home_top, R.drawable.chonglou_yun, R.drawable.chonglou_di};
        mLlRanking = (LinearLayout) rootView.findViewById(R.id.llFloorRanking);
        mLlRanking.setBackgroundDrawable(mContext.getResources().getDrawable(resId[position]));

        TextView tvRewardsGoods = (TextView) rootView.findViewById(R.id.tvRewardsGoods);
        String goods = mContext.getString(R.string.floor_rewards);
        tvRewardsGoods.setText(ViewUtil.getSpannableString(goods, R.color.yellow, 3, goods.length()));

        mBtnEnter = (Button) rootView.findViewById(R.id.btnEnter);
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] floorType = new String[]{"千重楼" ,"云中重楼" ,"地底宫阙"};

                // record fighting state
                DataUtil.recordSharedPreferencesData(mContext.getString(R.string.fighting_record),
                        mContext.getString(R.string.fighting_state), mContext.getString(R.string.fighting_state_true));
                // record fighting floor type
                DataUtil.recordSharedPreferencesData(mContext.getString(R.string.fighting_record),
                        mContext.getString(R.string.fighting_floor_name), floorType[position]);

                // enter fighting activity
                Intent intent = new Intent("com.sevenheroes.FloorFightingActivity");
                intent.putExtra("title" ,floorType[position]);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });

        mLvRanking = (ListView) rootView.findViewById(R.id.lvFloorRanking);
        List<HashMap<String , String>> rankingItems = new ArrayList<>();
        HashMap<String , String> item1 = new HashMap<>();
        item1.put("order" ,"第一名");
        item1.put("player" ,"鲁智深");
        item1.put("floor" ,"地宫28层");

        HashMap<String , String> item2 = new HashMap<>();
        item2.put("order" ,"第二名");
        item2.put("player" ,"刘备");
        item2.put("floor" ,"地宫19层");

        HashMap<String , String> item3 = new HashMap<>();
        item3.put("order" ,"第三名");
        item3.put("player" ,"曹操");
        item3.put("floor" ,"地宫17层");

        HashMap<String , String> item4 = new HashMap<>();
        item4.put("order" ,"第四名");
        item4.put("player" ,"张瑜");
        item4.put("floor" ,"地宫15层");

        HashMap<String , String> item5 = new HashMap<>();
        item5.put("order" ,"第五名");
        item5.put("player" ,"诸葛亮");
        item5.put("floor" ,"地宫14层");

        rankingItems.add(item1);
        rankingItems.add(item2);
        rankingItems.add(item3);
        rankingItems.add(item4);
        rankingItems.add(item5);

        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext ,rankingItems ,
                R.layout.item_floor_ranking , new String[]{"order","player","floor"} ,
                new int[]{R.id.tvRankingOrder ,R.id.tvPlayerName ,R.id.tvFloorAndLevel});
        mLvRanking.setAdapter(simpleAdapter);

    }
}

package com.sevenheroes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.sevenheroes.R;
import com.sevenheroes.adapter.FloorPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FloorFragment extends Fragment implements ViewPager.OnPageChangeListener ,View.OnClickListener{
    private RadioButton mRbThousandFloor;
    private RadioButton mRbCloudFloor;
    private RadioButton mRbUndergroundFloor;
    private ViewPager mVpFloor;

    public static FloorFragment newInstance() {
        return new FloorFragment();
    }

    public FloorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_floor, container, false);
        mRbThousandFloor = (RadioButton) rootView.findViewById(R.id.rbThousandFloor);
        mRbThousandFloor.setOnClickListener(this);
        mRbThousandFloor.setChecked(true);
        mRbCloudFloor = (RadioButton) rootView.findViewById(R.id.rbCloudFloor);
        mRbCloudFloor.setOnClickListener(this);
        mRbUndergroundFloor = (RadioButton) rootView.findViewById(R.id.rbUndergroundFloor);
        mRbUndergroundFloor.setOnClickListener(this);

        List<View> views = new ArrayList<>();
        View v1 = inflater.inflate(R.layout.layout_floor_select , null);
        View v2 = inflater.inflate(R.layout.layout_floor_select , null);
        View v3 = inflater.inflate(R.layout.layout_floor_select , null);
        views.add(v1);
        views.add(v2);
        views.add(v3);
        mVpFloor = (ViewPager) rootView.findViewById(R.id.vpFloor);
        mVpFloor.setOnPageChangeListener(this);
        mVpFloor.setAdapter(new FloorPagerAdapter(getActivity() , views));

        return rootView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position ==0){
            mRbThousandFloor.setChecked(true);
        }else if(position ==1 ){
            mRbCloudFloor.setChecked(true);
        }else if(position == 2 ){
            mRbUndergroundFloor.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rbThousandFloor:
                mVpFloor.setCurrentItem(0);
                break;
            case R.id.rbCloudFloor:
                mVpFloor.setCurrentItem(1);
                break;
            case R.id.rbUndergroundFloor:
                mVpFloor.setCurrentItem(2);
                break;
            default:break;
        }
    }


    /*class FloorPagerAdapter extends PagerAdapter {
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
            String goods = getString(R.string.floor_rewards);
            tvRewardsGoods.setText(ViewUtil.getSpannableString(goods , R.color.yellow , 3 ,goods.length()));

            mBtnEnter = (Button) rootView.findViewById(R.id.btnEnter);
            mBtnEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] floorType = new String[]{"千重楼" ,"云中重楼" ,"地底宫阙"};

                    // record fighting state
                    DataUtil.recordSharedPreferencesData(getString(R.string.fighting_record),
                            getString(R.string.fighting_state), getString(R.string.fighting_state_true));
                    // record fighting floor type
                    DataUtil.recordSharedPreferencesData(getString(R.string.fighting_record),
                            getString(R.string.fighting_floor_name), floorType[position]);

                    // enter fighting activity
                    Intent intent = new Intent("com.sevenheroes.FloorFightingActivity");
                    intent.putExtra("title" ,floorType[position]);
                    startActivity(intent);
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
*/

}

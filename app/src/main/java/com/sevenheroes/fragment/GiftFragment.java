package com.sevenheroes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sevenheroes.R;


public class GiftFragment extends Fragment {

    private ListView mLvGift ;
    public static GiftFragment newInstance() {
        return new GiftFragment();
    }

    public GiftFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gift, container, false);
        mLvGift = (ListView) rootView.findViewById(R.id.lvGift);
        mLvGift.setAdapter(new GiftAdapter());
        return rootView;
    }

    static class ViewHolder{
        TextView tvRewards ;
        TextView tvGetGift;
        ImageView ivGift;
    }
    class GiftAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 3;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder ;
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_gift,null);
                viewHolder = new ViewHolder();
                viewHolder.ivGift = (ImageView) convertView.findViewById(R.id.ivGift);
                viewHolder.tvGetGift = (TextView) convertView.findViewById(R.id.tvGetGift);
                viewHolder.tvRewards = (TextView) convertView.findViewById(R.id.tvRewards);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvGetGift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.tvGetGift.setTextColor(getResources().getColor(R.color.content_bg));
                    viewHolder.tvGetGift.setText(getString(R.string.got_rewards));
                    viewHolder.tvGetGift.setEnabled(false);
                }
            });
            return convertView;
        }
    }

}

package com.sevenheroes.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by forest on 2015/3/12.
 */
public class ViewUtil {
    private static Context mContext;

    /**
     * to init the Application Context when launcher
     * @param context application context suggest !
     */
    public static void init(Context context){
        mContext = context ;
    }

    /**
     * show toast
     * @param msg message to show
     */
    public static void toast(String msg){
        Toast.makeText(mContext , msg , Toast.LENGTH_SHORT).show();
    }
}

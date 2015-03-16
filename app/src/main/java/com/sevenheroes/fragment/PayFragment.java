package com.sevenheroes.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sevenheroes.R;

public class PayFragment extends Fragment {

    private WebView mWvPay ;
    private ProgressBar mPbPayLoading;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment PayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragment newInstance() {
        return new PayFragment();
    }

    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pay, container, false) ;
        mPbPayLoading = (ProgressBar) rootView.findViewById(R.id.pbPayLoading);
        mWvPay = (WebView) rootView.findViewById(R.id.wvPay);
        mWvPay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPbPayLoading.setVisibility(View.GONE);
            }
        });
        WebSettings webSettings = mWvPay.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWvPay.loadUrl(getString(R.string.pay_address));
        return rootView;
    }


}

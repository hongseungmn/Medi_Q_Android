package com.kosmo.kosmo.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.kosmo.MainActivity;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.magazine.MagazineFragment;

public class MainWebViewFragment extends Fragment {
    private WebView webView = null;
    private String magazinePage;
    public static MainWebViewFragment newInstance() {
        return new MainWebViewFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_magazine_webview, container, false);
        webView = (WebView) rootView.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        if (getArguments() != null)
        {
            magazinePage = getArguments().getString("magazinePage"); // 프래그먼트1에서 받아온 값 넣기
        }
        webView.loadUrl(magazinePage);

        return rootView;
    }


    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // WebView에서 뒤로가기 불가능하면 기본 뒤로가기 동작 실행
            ((MainActivity)getActivity()).replaceFragment(MagazineFragment.newInstance(),null);
        }
    }
}

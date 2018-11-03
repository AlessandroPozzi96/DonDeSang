package com.henallux.dondesang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ConditionsDonSangFragment extends Fragment implements IOnBackPressed{
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conditions_don_de_sang_accueil_fragment, container, false);

        //Mise en place d'une webview pour voir les conditions du don du sang
        webView = view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://donneurdesang.be/fr/le-don-de-sang/qui-peut-donner-du-sang/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);


        return view;
    }

    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        else
        {
            return false;
        }
    }
}

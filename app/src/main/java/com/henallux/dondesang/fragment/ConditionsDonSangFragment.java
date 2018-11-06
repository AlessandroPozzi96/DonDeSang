package com.henallux.dondesang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.henallux.dondesang.IOnBackPressed;
import com.henallux.dondesang.R;
import com.henallux.dondesang.activity.MainActivity;

public class ConditionsDonSangFragment extends Fragment implements IOnBackPressed {
    private WebView webView;
    private Button button;
    private View view;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conditions_don_de_sang_accueil_fragment, container, false);

        //Mise en place d'une webview pour voir les conditions du don du sang
        webView = view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://donneurdesang.be/fr/le-don-de-sang/qui-peut-donner-du-sang/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);

        button = view.findViewById(R.id.butAccueil);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


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

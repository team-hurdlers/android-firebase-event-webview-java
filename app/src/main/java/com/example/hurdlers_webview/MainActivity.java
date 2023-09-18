package com.example.hurdlers_webview;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        WebView.setWebContentsDebuggingEnabled(true);
        webView.addJavascriptInterface(new AnalyticsWebInterface(this), AnalyticsWebInterface.TAG);

        // 웹뷰에서 새 페이지가 열릴 때, 다른 브라우저를 사용하지 않도록 설정
        webView.setWebViewClient(new WebViewClient());

        // 원하는 URL 로드
        webView.loadUrl("http://118.67.142.80/");
    }

    // 뒤로가기 버튼 누를 때 WebView의 뒤로가기가 동작하도록 설정
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

package com.feyzakahyaoglu.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {
    private WebView webView;
    private final String HOME = "https://feyzakahyaoglu.com/";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setSupportMultipleWindows(false);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setBuiltInZoomControls(false);
        s.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri u = request.getUrl();
                String host = u.getHost() == null ? "" : u.getHost();
                if (host.contains("feyzakahyaoglu.com") || host.contains("google.com") ||
                    host.contains("google.com.tr") || host.contains("share.google") ||
                    host.contains("maps.google")) {
                    view.loadUrl(u.toString());
                    return true;
                }
                if (u.getScheme().equals("whatsapp") || u.getScheme().equals("tel") ||
                    u.getScheme().equals("mailto")) {
                    try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch(Exception ignored) {}
                    return true;
                }
                view.loadUrl(u.toString());
                return true;
            }
        });
        webView.loadUrl(HOME);
    }

    @Override public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}

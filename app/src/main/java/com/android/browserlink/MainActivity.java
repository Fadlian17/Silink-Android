package com.android.browserlink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView webv;
    private EditText txturl;
    private Button btncari;
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi
        webv = (WebView) findViewById(R.id.webv);
        txturl = (EditText) findViewById(R.id.txturl);
        btncari = (Button) findViewById(R.id.btncari);

        //penyiapan url
        final String url = "https://www.google.co.id/";
        //inisialisasi kebutuhan browser

        webv.getSettings().setJavaScriptEnabled(true); //mendukung Javascipt
        webv.getSettings().setDisplayZoomControls(true);//kontrol zoom pada browser
        webv.getSettings().setLoadWithOverviewMode(true); //otomatis loading zoom
        webv.getSettings().setUseWideViewPort(true); //memberi tahu browser untuk mengaktifkan Wide Viewport
        webv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//otomatis menampilkan javascript window
        webv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webv.getSettings().setAllowFileAccessFromFileURLs(true);
        webv.getSettings().setAllowUniversalAccessFromFileURLs(true);

       pg=(ProgressBar)findViewById(R.id.progressBar4);
       webv.setWebChromeClient(new WebChromeClient(){
           @Override
           public void onProgressChanged(WebView view, int newProgress){
               pg.setVisibility(View.VISIBLE);
               pg.setProgress(newProgress);
               if (newProgress ==100){
                   pg.setVisibility(View.GONE);
               }
           }
       });

        webv.loadUrl(url);
        webv.setWebViewClient(new MyWebLaunch());

        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aktifkan Javascript
                webv.getSettings().setJavaScriptEnabled(true);
                webv.getSettings().setDisplayZoomControls(true);
                pg = (ProgressBar) findViewById(R.id.progressBar4);
                webv.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        pg.setVisibility(View.VISIBLE);
                        pg.setProgress(newProgress);
                        if (newProgress == 100) {
                            pg.setVisibility(View.GONE);
                        }
                    }

                });
                webv.loadUrl(url);
                webv.setWebViewClient(new MyWebLaunch());
            }
        });

    }

    private class MyWebLaunch extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view,url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
       if (event.getAction()== KeyEvent.ACTION_DOWN){
           switch (keyCode){
               case KeyEvent.KEYCODE_BACK:
                   if (webv.canGoBack()){
                       webv.goBack();
                   }else {
                       finish();
                   }
                   return true;
           }
       }
       return super.onKeyDown(keyCode,event);
    }
}

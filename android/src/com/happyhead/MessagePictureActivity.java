package com.happyhead;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MessagePictureActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppConfig appConfig = new AppConfig(this);
        String messageAppUrl = appConfig.getConfig("url");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WebView messageView = (WebView) findViewById(R.id.messagesWebView);

        messageView.addJavascriptInterface(new JSBridge(this), "platform");

        messageView.loadUrl(messageAppUrl);


    }
}

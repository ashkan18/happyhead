package com.happyhead;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.happyhead.photo.CameraPreview;
import com.happyhead.photo.PhotoHandler;

/**
 * This class is the main activity of the app, it will populate different views of the app.
 */
public class MessagePictureActivity extends Activity {
    public final static String DEBUG_TAG = MessagePictureActivity.class.getSimpleName();
    private Camera mCamera;
    private CameraPreview mPreview;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Create a new AppConfig, we need to read the app url from the config
        AppConfig appConfig = new AppConfig(this);
        String messageAppUrl = appConfig.getConfig("url");


        // now handle creating the webview, the webview is used to show the messages
        WebView messageView = (WebView) findViewById(R.id.my_messages);
        WebSettings webSettings = messageView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // inject our js bridge into the webview, so webview can male call to native wrapper
        messageView.addJavascriptInterface(new JSBridge(this), "Platform");
        messageView.loadUrl(messageAppUrl);

        messageView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void showMessage(int message_id) {
        // go to message activity and pass the message id
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("id", message_id);
        startActivity(intent);
    }

}

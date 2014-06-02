package com.happyhead;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * User: Ashkan
 * Date: 6/2/14
 * Time: 7:04 AM
 * This class defines a JSBrdige which is passed to webview and is used from webview's javascript
 */
public class JSBridge {
    private static String TAG = JSBridge.class.getSimpleName();
    Context mContext;

    /** Instantiate the interface and set the context */
    JSBridge(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void showMessageAndTakePhoto() {
        Toast.makeText(mContext, "Hello yaroo", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Received show message and take photo call from webview");
        ((MessagePictureActivity)this.mContext).takePicture();
    }

}

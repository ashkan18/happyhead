package com.happyhead;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * User: Ashkan
 * Date: 6/2/14
 * Time: 7:04 AM
 * This class defines a JSBrdige which is passed to webview and is used from webview's javascript
 */
public class JSBridge {
    Context mContext;

    /** Instantiate the interface and set the context */
    JSBridge(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void showMessageAndTakePhoto(final String user, final String pass, final String rm) {
        ((MessagePictureActivity)this.mContext).takePicture();
    }

}

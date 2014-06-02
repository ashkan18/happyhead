package com.happyhead;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.happyhead.photo.CameraPreview;
import com.happyhead.photo.PhotoHandler;

public class MessagePictureActivity extends Activity {
    public final static String DEBUG_TAG = MessagePictureActivity.class.getSimpleName();
    private Camera mCamera;
    private CameraPreview mPreview;
    private int cameraId = 0;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppConfig appConfig = new AppConfig(this);
        String messageAppUrl = appConfig.getConfig("url");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (checkCameraHardware()) {
            int fronCameraId = findFrontFacingCamera();
            // Create an instance of Camera
            mCamera = getCameraInstance(fronCameraId);
        } else {
            Log.e(DEBUG_TAG, "No access to the camera");
        }

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        WebView messageView = (WebView) findViewById(R.id.messages_webview);
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


    /** A safe way to get an instance of the Camera object. */
    public Camera getCameraInstance(int cameraId){
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.e(DEBUG_TAG, "Can't open camera", e);
        }
        return c; // returns null if camera is unavailable
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    public void takePicture() {
        mCamera.takePicture(null, null,
                new PhotoHandler(getApplicationContext()));

    }



    private void releaseCameraAndPreview() {
        mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        this.releaseCameraAndPreview();
    }


}

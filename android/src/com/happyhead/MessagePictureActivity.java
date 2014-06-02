package com.happyhead;

import android.app.Activity;
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

        // check if device has camera or not
        if (checkCameraHardware()) {
            int fronCameraId = findFrontFacingCamera();
            // Create an instance of Camera
            mCamera = getCameraInstance(fronCameraId);
        } else {
            Toast.makeText(this, R.string.missing_camera_toast, 5);
            Log.e(DEBUG_TAG, "No access to the camera");
        }

        // Create our Preview view and set it as the content of our activity.
        // this view will show what we see from the camera
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        // now handle creating the webview, the webview is used to show the messages
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

    /**
     * This method will find the camera Id of the front camera
     * @return int id of the front camera
     */
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


}

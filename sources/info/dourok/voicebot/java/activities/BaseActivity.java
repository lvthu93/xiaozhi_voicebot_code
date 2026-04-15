package info.dourok.voicebot.java.activities;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int PERMISSION_REQUEST_CODE = 1001;
    protected static final String[] REQUIRED_PERMISSIONS = {"android.permission.RECORD_AUDIO", "android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
    protected static final String TAG = "BaseActivity";
    protected h1 dependencies;

    public void checkAndRequestPermissions() {
        if (!hasAllPermissions()) {
            Log.d(TAG, "Requesting permissions...");
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, 1001);
            return;
        }
        Log.d(TAG, "All permissions already granted");
        onPermissionsGranted();
    }

    public boolean hasAllPermissions() {
        for (String checkSelfPermission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public void logMemoryUsage(String str) {
        if (this.dependencies != null) {
            StringBuilder o = y2.o(str, " - ");
            this.dependencies.getClass();
            o.append(h1.f());
            Log.d(TAG, o.toString());
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "BaseActivity onCreate");
        this.dependencies = h1.d(this);
        checkAndRequestPermissions();
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "Activity paused");
    }

    public void onPermissionsDenied() {
    }

    public void onPermissionsGranted() {
        Log.d(TAG, "Permissions granted - ready to proceed");
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1001) {
            boolean z = true;
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (iArr[i2] != 0) {
                    String str = strArr[i2];
                    z = false;
                }
            }
            if (z) {
                Log.d(TAG, "All permissions granted");
                onPermissionsGranted();
                return;
            }
            onPermissionsDenied();
        }
    }

    public void onResume() {
        super.onResume();
        StringBuilder sb = new StringBuilder("Activity resumed. ");
        this.dependencies.getClass();
        sb.append(h1.f());
        Log.d(TAG, sb.toString());
    }

    public void showError(String str) {
    }
}

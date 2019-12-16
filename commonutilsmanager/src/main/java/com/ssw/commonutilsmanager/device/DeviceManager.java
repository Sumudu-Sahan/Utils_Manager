package com.ssw.commonutilsmanager.device;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.fingerprint.FingerprintManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;
import static com.ssw.commonutilsmanager.common.ConstantList.EMPTY_STRING;

public class DeviceManager {
    private static final String TAG = "DeviceManager";

    private static DeviceManager deviceManager;

    public static synchronized DeviceManager getInstance() {
        if (deviceManager == null) {
            deviceManager = new DeviceManager();
        }
        return deviceManager;
    }

    public String getBrand() {
        String brand = "B";
        try {
            brand = Build.MANUFACTURER;
            if (brand == null || brand.isEmpty()) {
                brand = "B";
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
        return brand;
    }

    public String getModel() {
        String model = "M";
        try {
            model = Build.MODEL;
            if (model == null || model.isEmpty()) {
                model = "M";
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public void showSoftKeyboard(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
    }

    public void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
    }

    public void hideSoftKeyboard(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
    }

    public Point getScreenDimensions(Context context) {
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size;
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public double getInches(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        double widthDpi = metrics.xdpi;
        double heightDpi = metrics.ydpi;

        double widthInches = widthPixels / widthDpi;
        double heightInches = heightPixels / heightDpi;

        return Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
    }

    public void copyToClipboard(Context context, String label, String text) {
        try {
            ClipboardManager cManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText(label, text);
            cManager.setPrimaryClip(cd);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
    }

    public String getTextFromClipboard(Context context) {
        try {
            ClipboardManager cManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cManager.getPrimaryClip().getItemCount() > 0) {
                return cManager.getPrimaryClip().getItemAt(0).getText().toString();
            } else {
                return EMPTY_STRING;
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return EMPTY_STRING;
        }
    }

    public String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public boolean isFingerPrintAvailable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

                return fingerprintManager != null && fingerprintManager.isHardwareDetected();
            } catch (Exception e) {
                if (DEV_MODE) {
                    e.printStackTrace();
                }
                return false;
            }
        } else {
            try {
                FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
                return fingerprintManagerCompat.isHardwareDetected();
            } catch (Exception e) {
                if (DEV_MODE) {
                    e.printStackTrace();
                }
                return false;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean hasEnrolledFingerprints(Context context) {
        try {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
                return fingerprintManager.hasEnrolledFingerprints();
            }
        } catch (Exception ex) {
            if (DEV_MODE) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean isGPSEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public Integer getApiLevel() {
        return Build.VERSION.SDK_INT;
    }
}

package com.ssw.commonutilsmanager.toasts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.google.android.material.snackbar.Snackbar;

public class ToastManager {
    private static final String TAG = "ToastManager";

    public static final int TOAST_LENGTH_SHORT = 0;
    public static final int TOAST_LENGTH_LONG = 1;

    public static final int TOP_DURATION_LONG = 0;
    public static final int TOP_DURATION_INDEFINITE = -2;
    public static final int TOP_DURATION_SHORT = -1;

    private static ToastManager toastManager;

    public static synchronized ToastManager getInstance() {
        if (toastManager == null) {
            toastManager = new ToastManager();
        }
        return toastManager;
    }

    public void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public void showTopToast(Activity activity, String message, int duration, String actionBarTextColor, String backgroundColor, String textColor, int padding) {
        TSnackbar snackBar = TSnackbar.make(activity.findViewById(android.R.id.content), message, duration);
        snackBar.setActionTextColor(Color.parseColor(actionBarTextColor));

        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(Color.parseColor(backgroundColor));
        snackBarView.setPadding(0, padding, 0, 0);

        TextView textView = snackBarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor(textColor));
        textView.setPadding(padding, padding, padding, padding);
        snackBar.show();
    }

    public void showTopToast(Activity activity, String message, int duration) {
        TSnackbar snackBar = TSnackbar.make(activity.findViewById(android.R.id.content), message, duration);
        snackBar.show();
    }

    public void showSnackBar(View view, String message, String buttonText, final SnackBarButtonAction snackBarButtonAction) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
                .setAction(buttonText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackBarButtonAction.buttonClick();
                    }
                });

        snackbar.show();
    }

    public void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public interface SnackBarButtonAction {
        void buttonClick();
    }
}

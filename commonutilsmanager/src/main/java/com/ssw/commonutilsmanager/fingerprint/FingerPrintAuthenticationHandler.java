package com.ssw.commonutilsmanager.fingerprint;


import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.ssw.commonutilsmanager.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.Context.VIBRATOR_SERVICE;

public class FingerPrintAuthenticationHandler {
    private static final String TAG = "FingerPrintAuthenticationHandler";

    private FragmentActivity fragmentActivity;
    private FingerPrintAuthenticationHandlerEvents fingerPrintAuthenticationHandlerEvents;

    //Old Android Versions
    private FingerprintHandler fingerprintHandler;
    private Dialog alertDialog;
    private Vibrator vibrator;

    //Android 9.0+
    private Executor newExecutor;
    private BiometricPrompt myBiometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public FingerPrintAuthenticationHandler(FragmentActivity fragmentActivity, FingerPrintAuthenticationHandlerEvents fingerPrintAuthenticationHandlerEvents) {
        this.fragmentActivity = fragmentActivity;
        this.fingerPrintAuthenticationHandlerEvents = fingerPrintAuthenticationHandlerEvents;
    }

    public void startAuthentication(String title, String subtitle, String description, String negativeButtonText, int logo) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            authenticateWithNewAPI(title, subtitle, description, negativeButtonText);
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            authenticateWithOldAPI(description, negativeButtonText, logo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void authenticateWithOldAPI(String description, String negativeButtonText, int logo) {
        openDialog(description, negativeButtonText, logo);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openDialog(String description, String negativeButtonText, int logo) {
        alertDialog = new Dialog(fragmentActivity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.fp_dialog_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);

        TextView tvDescription = alertDialog.findViewById(R.id.tvDescription);
        tvDescription.setText(description);

        TextView tvCancel = alertDialog.findViewById(R.id.tvCancel);
        tvCancel.setText(negativeButtonText);

        CardView mcvCancel = alertDialog.findViewById(R.id.mcvCancel);
        mcvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fingerPrintAuthenticationHandlerEvents.onAuthenticationCancelled();
                alertDialog.dismiss();
            }
        });

        vibrator = (Vibrator) fragmentActivity.getSystemService(VIBRATOR_SERVICE);

        final AppCompatImageView ivLogo = alertDialog.findViewById(R.id.ivLogo);
        ivLogo.setImageResource(logo);

        final AppCompatImageView ivFingerPrint = alertDialog.findViewById(R.id.ivFingerPrint);
        ivFingerPrint.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        fingerprintHandler = new FingerprintHandler(fragmentActivity, new FingerprintHandler.FingerprintHandlerEvents() {
            @Override
            public void onAuthSuccess() {
                ivFingerPrint.setColorFilter(ContextCompat.getColor(fragmentActivity, R.color.authentication_success), android.graphics.PorterDuff.Mode.MULTIPLY);
                vibrator.vibrate(100);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fingerPrintAuthenticationHandlerEvents.onAuthenticationSuccess();
                        alertDialog.dismiss();
                    }
                }, 500);
            }

            @Override
            public void onAuthFailed() {
                ivFingerPrint.setColorFilter(ContextCompat.getColor(fragmentActivity, R.color.authentication_failed), android.graphics.PorterDuff.Mode.MULTIPLY);
                vibrator.vibrate(100);
                fingerPrintAuthenticationHandlerEvents.onAuthenticationFailed();
            }

            @Override
            public void onAuthCancelled() {

            }
        });
        fingerprintHandler.fingerPrintInit();
        alertDialog.show();
    }

    private void authenticateWithNewAPI(String title, String subtitle, String description, String negativeButtonText) {
        newExecutor = Executors.newSingleThreadExecutor();
        myBiometricPrompt = new BiometricPrompt(fragmentActivity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    fingerPrintAuthenticationHandlerEvents.onAuthenticationFailed();
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                fragmentActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        fingerPrintAuthenticationHandlerEvents.onAuthenticationSuccess();
                    }
                });
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                fingerPrintAuthenticationHandlerEvents.onAuthenticationFailed();
            }
        });
        myBiometricPrompt.authenticate(getBiometricPrompt(title, subtitle, description, negativeButtonText));
    }

    private BiometricPrompt.PromptInfo getBiometricPrompt(String title, String subtitle, String description, String negativeButtonText) {
        try {
            if (promptInfo == null) {
                promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle(title)
                        .setSubtitle(subtitle)
                        .setDescription(description)
                        .setNegativeButtonText(negativeButtonText)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subtitle)
                    .setDescription(description)
                    .setNegativeButtonText(negativeButtonText)
                    .build();
        }

        return promptInfo;
    }


    public interface FingerPrintAuthenticationHandlerEvents {
        void onAuthenticationSuccess();

        void onAuthenticationFailed();

        void onAuthenticationCancelled();
    }
}

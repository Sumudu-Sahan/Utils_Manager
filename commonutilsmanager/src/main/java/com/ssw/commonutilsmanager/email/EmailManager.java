package com.ssw.commonutilsmanager.email;

import android.util.Patterns;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class EmailManager {
    private static final String TAG = "EmailManager";

    private static EmailManager emailManager;

    public static synchronized EmailManager getInstance() {
        if (emailManager == null) {
            emailManager = new EmailManager();
        }

        return emailManager;
    }

    public boolean isValidEmailAddress(String emailAddress) {
        try {
            if (emailAddress.trim().isEmpty()) {
                return false;
            } else return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }
}

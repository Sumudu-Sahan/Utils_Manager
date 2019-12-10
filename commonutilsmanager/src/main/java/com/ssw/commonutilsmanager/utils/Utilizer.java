package com.ssw.commonutilsmanager.utils;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;
import static com.ssw.commonutilsmanager.common.ConstantList.EMPTY_STRING;
import static com.ssw.commonutilsmanager.common.ConstantList.STRING_ZERO;

public class Utilizer {
    private static final String TAG = "Utilizer";

    private static Utilizer utilizer;

    public static synchronized Utilizer getInstance() {
        if (utilizer == null) {
            utilizer = new Utilizer();
        }
        return utilizer;
    }

    public String zeroPad(String message, int length) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < (length - message.length()); i++) {
                stringBuilder.append(STRING_ZERO);
            }

            return (stringBuilder + message);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return EMPTY_STRING;
        }
    }

    public boolean isStringEmpty(String input) {
        try {
            return input.trim().isEmpty();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return true;
        }
    }
}

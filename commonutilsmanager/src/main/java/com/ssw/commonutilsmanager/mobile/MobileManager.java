package com.ssw.commonutilsmanager.mobile;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class MobileManager {
    private static final String TAG = "MobileManager";

    private static final String PREFIX_DIALOG1 = "077";
    private static final String PREFIX_DIALOG2 = "076";
    private static final String PREFIX_MOBITEL1 = "071";
    private static final String PREFIX_MOBITEL2 = "070";
    private static final String PREFIX_ETISALAT = "072";
    private static final String PREFIX_HUTCH = "078";
    private static final String PREFIX_AIRTEL = "075";

    public static final int CAREER_DIALOG = 1;
    public static final int CAREER_MOBITEL = 2;
    public static final int CAREER_HUTCH = 3;
    public static final int CAREER_AIRTEL = 4;
    public static final int CAREER_INVALID = 0;

    private static MobileManager mobileManager;

    public static synchronized MobileManager getInstance() {
        if (mobileManager == null) {
            mobileManager = new MobileManager();
        }
        return mobileManager;
    }

    public int getCareerFromMobileNumber(String mobileNumber) {
        if (isValidMobileNumber(mobileNumber.trim(), true)) {
            String formattedNumber;
            if (mobileNumber.startsWith("+94")) {
                formattedNumber = mobileNumber.replace("+94", "0");
            } else if (mobileNumber.startsWith("0094")) {
                formattedNumber = mobileNumber.replace("0094", "0");
            } else {
                formattedNumber = mobileNumber;
            }
            if (formattedNumber.startsWith(PREFIX_DIALOG1) || formattedNumber.startsWith(PREFIX_DIALOG2)) {
                return CAREER_DIALOG;
            } else if (formattedNumber.startsWith(PREFIX_MOBITEL1) || formattedNumber.startsWith(PREFIX_MOBITEL2)) {
                return CAREER_MOBITEL;
            } else if (formattedNumber.startsWith(PREFIX_HUTCH) || formattedNumber.startsWith(PREFIX_ETISALAT)) {
                return CAREER_HUTCH;
            } else if (formattedNumber.startsWith(PREFIX_AIRTEL)) {
                return CAREER_AIRTEL;
            } else {
                return CAREER_INVALID;
            }
        } else return CAREER_INVALID;
    }

    public boolean isValidMobileNumber(String mobileNumber, boolean enableCareerValidation) {
        try {
            if (mobileNumber.trim().isEmpty()) {
                return false;
            } else {
                if (mobileNumber.startsWith("+94")) {
                    String s = mobileNumber.replace("+94", "0");
                    if (s.length() != 10) {
                        return false;
                    } else if (enableCareerValidation) {
                        return (s.trim().startsWith(PREFIX_DIALOG1)
                                || s.trim().startsWith(PREFIX_DIALOG2)
                                || s.trim().startsWith(PREFIX_MOBITEL1)
                                || s.trim().startsWith(PREFIX_MOBITEL2)
                                || s.trim().startsWith(PREFIX_ETISALAT)
                                || s.trim().startsWith(PREFIX_HUTCH)
                                || s.trim().startsWith(PREFIX_AIRTEL));
                    } else {
                        return true;
                    }
                } else if (mobileNumber.startsWith("0094")) {
                    String s = mobileNumber.replace("0094", "0");
                    if (s.length() != 10) {
                        return false;
                    } else if (enableCareerValidation) {
                        return (s.trim().startsWith(PREFIX_DIALOG1)
                                || s.trim().startsWith(PREFIX_DIALOG2)
                                || s.trim().startsWith(PREFIX_MOBITEL1)
                                || s.trim().startsWith(PREFIX_MOBITEL2)
                                || s.trim().startsWith(PREFIX_ETISALAT)
                                || s.trim().startsWith(PREFIX_HUTCH)
                                || s.trim().startsWith(PREFIX_AIRTEL));
                    } else {
                        return true;
                    }
                } else {
                    if (mobileNumber.length() != 10) {
                        return false;
                    } else if (enableCareerValidation) {
                        return (mobileNumber.trim().startsWith(PREFIX_DIALOG1)
                                || mobileNumber.trim().startsWith(PREFIX_DIALOG2)
                                || mobileNumber.trim().startsWith(PREFIX_MOBITEL1)
                                || mobileNumber.trim().startsWith(PREFIX_MOBITEL2)
                                || mobileNumber.trim().startsWith(PREFIX_ETISALAT)
                                || mobileNumber.trim().startsWith(PREFIX_HUTCH)
                                || mobileNumber.trim().startsWith(PREFIX_AIRTEL));
                    } else {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }
}

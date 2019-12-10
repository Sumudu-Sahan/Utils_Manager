package com.ssw.commonutilsmanager.nic;

import com.ssw.commonutilsmanager.utils.Utilizer;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class NICManager {
    private static final String TAG = "NICManager";

    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 0;

    private static final int NEW_NIC_LENGTH = 12;
    private static final int GENDER_CUTOFF_VALIDATOR = 500;
    private static final int[] MONTH_LIST = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static NICManager nicManager;

    public static synchronized NICManager getInstance() {
        if (nicManager == null) {
            nicManager = new NICManager();
        }

        return nicManager;
    }

    public boolean isValidNICNumber(String nicNumber) {
        if (nicNumber.trim().length() == NEW_NIC_LENGTH) {
            return true;
        } else {
            if (nicNumber.trim().length() == 10) {
                return nicNumber.trim().toUpperCase().endsWith("V") || nicNumber.trim().toUpperCase().endsWith("X");
            } else return false;
        }
    }

    public int getBirthYearFromNIC(String nicNumber) {
        if (nicNumber.trim().length() == NEW_NIC_LENGTH) {
            return (Integer.parseInt(nicNumber.trim().substring(0, 4)));
        }
        return (1900 + Integer.parseInt(nicNumber.trim().substring(0, 2)));
    }

    private int getNumberOfDaysFromNIC(String nicNumber) {
        int d = 0;
        try {
            if (nicNumber.trim().length() == NEW_NIC_LENGTH) {
                d = Integer.parseInt(nicNumber.trim().substring(4, 7));
            } else {
                d = Integer.parseInt(nicNumber.trim().substring(2, 5));
            }

            if (d > GENDER_CUTOFF_VALIDATOR) {
                return (d - GENDER_CUTOFF_VALIDATOR);
            } else {
                return d;
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return d;
        }
    }

    public String getBirthdayFromNIC(String nicNumber) {
        String dob;
        int mo = 0, da = 0;
        int days = getNumberOfDaysFromNIC(nicNumber);

        for (int i = 0; i < MONTH_LIST.length; i++) {
            if (days <= MONTH_LIST[i]) {
                mo = i + 1;
                da = days;
                break;
            } else {
                days = days - MONTH_LIST[i];
            }
        }

        dob = Utilizer.getInstance().zeroPad(String.valueOf(da), 2) + "/"
                + Utilizer.getInstance().zeroPad(String.valueOf(mo), 2) + "/"
                + getBirthYearFromNIC(nicNumber);
        return dob;
    }

    public int getGenderFromNIC(String nicNumber) {
        try {
            int date = Integer.parseInt(nicNumber.trim().substring(2, 5));
            if (nicNumber.trim().length() == NEW_NIC_LENGTH) {
                date = Integer.parseInt(nicNumber.trim().substring(4, 7));
            }
            if (date > 500) {
                return GENDER_FEMALE;
            } else {
                return GENDER_MALE;
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}

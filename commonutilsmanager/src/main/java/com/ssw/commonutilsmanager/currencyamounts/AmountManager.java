package com.ssw.commonutilsmanager.currencyamounts;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.ssw.commonutilsmanager.common.ConstantList.AMOUNT_FORMAT;
import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class AmountManager {
    private static final String TAG = "AmountManager";

    private static AmountManager amountManager;

    public static synchronized AmountManager getInstance() {
        if (amountManager == null) {
            amountManager = new AmountManager();
        }
        return amountManager;
    }

    public String getFormattedAmount(double amount) {
        DecimalFormat formatter = new DecimalFormat(AMOUNT_FORMAT);
        return formatter.format(amount);
    }

    public double getDoubleValueOfAmount(String formattedAmount) {
        String replaceable = String.format("[%s,]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
        String cleanString = formattedAmount.replaceAll(replaceable, "").replaceAll(" ", "");

        try {
            return Double.parseDouble(cleanString.trim());
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return 0.00;
        }
    }
}

package com.ssw.commonutilsmanager.credit_cards;

import java.util.regex.Pattern;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;
import static com.ssw.commonutilsmanager.common.ConstantList.SPACE_STRING;

public class CardNumberValidations {
    private static final String TAG = "CardNumberValidations";

    private static final String PATTERN_VISA = "^4[0-9]{12}(?:[0-9]{3}){0,2}$";
    private static final String PATTERN_MASTERCARD = "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$";
    private static final String PATTERN_AMERICAN_EXPRESS = "^3[47][0-9]{13}$";
    private static final String PATTERN_DINERS_CLUB = "^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$";
    private static final String PATTERN_DISCOVER = "^6(?:011|[45][0-9]{2})[0-9]{12}$";
    private static final String PATTERN_JCB = "^(?:2131|1800|35\\d{3})\\d{11}$";
    private static final String PATTERN_CHINA_UNION_PAY = "^62[0-9]{14,17}$";

    public static final int CARD_TYPE_VISA = 1;
    public static final int CARD_TYPE_MASTERCARD = 2;
    public static final int CARD_TYPE_AMERICAN_EXPRESS = 3;
    public static final int CARD_TYPE_DINERS_CLUB = 4;
    public static final int CARD_TYPE_DISCOVER = 5;
    public static final int CARD_TYPE_JCB = 6;
    public static final int CARD_TYPE_CHINA_UNION_PAY = 7;
    public static final int CARD_TYPE_UNKNOWN = 0;

    private static CardNumberValidations cardNumberValidations;

    public static synchronized CardNumberValidations getInstance() {
        if (cardNumberValidations == null) {
            cardNumberValidations = new CardNumberValidations();
        }
        return cardNumberValidations;
    }

    public String getFormattedCardNumber(String plainCardNumber, int interval) {
        char[] chars = plainCardNumber.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int count = 0; count < chars.length; count++) {
            stringBuilder.append(chars[count]);
            if ((count + 1) % interval == 0) {
                stringBuilder.append(SPACE_STRING);
            }
        }

        return stringBuilder.toString();
    }

    public String getFormattedCardNumber(String plainCardNumber, int interval, String intervalCharacter) {
        char[] chars = plainCardNumber.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int count = 0; count < chars.length; count++) {
            stringBuilder.append(chars[count]);
            if ((count + 1) % interval == 0) {
                stringBuilder.append(intervalCharacter);
            }
        }

        return stringBuilder.toString();
    }

    public int getCardTypeFromCardNumber(String cardNumber) {
        if (validateCreditCardNumber(cardNumber)) {
            if (Pattern.matches(PATTERN_VISA, cardNumber)) {
                return CARD_TYPE_VISA;
            } else if (Pattern.matches(PATTERN_MASTERCARD, cardNumber)) {
                return CARD_TYPE_MASTERCARD;
            } else if (Pattern.matches(PATTERN_AMERICAN_EXPRESS, cardNumber)) {
                return CARD_TYPE_AMERICAN_EXPRESS;
            } else if (Pattern.matches(PATTERN_DINERS_CLUB, cardNumber)) {
                return CARD_TYPE_DINERS_CLUB;
            } else if (Pattern.matches(PATTERN_DISCOVER, cardNumber)) {
                return CARD_TYPE_DISCOVER;
            } else if (Pattern.matches(PATTERN_JCB, cardNumber)) {
                return CARD_TYPE_JCB;
            } else if (Pattern.matches(PATTERN_CHINA_UNION_PAY, cardNumber)) {
                return CARD_TYPE_CHINA_UNION_PAY;
            } else return CARD_TYPE_UNKNOWN;
        } else {
            return CARD_TYPE_UNKNOWN;
        }
    }

    public boolean validateCreditCardNumber(String cardNumberString) {
        try {
            if (cardNumberString.trim().length() == 0) {
                return false;
            } else {
                int[] ints = new int[cardNumberString.trim().length()];
                for (int i = 0; i < cardNumberString.trim().length(); i++) {
                    ints[i] = Integer.parseInt(cardNumberString.trim().substring(i, i + 1));
                }
                for (int i = ints.length - 2; i >= 0; i = i - 2) {
                    int j = ints[i];
                    j = j * 2;
                    if (j > 9) {
                        j = j % 10 + 1;
                    }
                    ints[i] = j;
                }
                int sum = 0;
                for (int anInt : ints) {
                    sum += anInt;
                }
                return sum % 10 == 0;
            }
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }
}

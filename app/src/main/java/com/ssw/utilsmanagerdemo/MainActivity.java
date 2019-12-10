package com.ssw.utilsmanagerdemo;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ssw.commonutilsmanager.app.AppManager;
import com.ssw.commonutilsmanager.calendar.CalendarUtilsManager;
import com.ssw.commonutilsmanager.credit_cards.CardNumberValidations;
import com.ssw.commonutilsmanager.currencyamounts.AmountManager;
import com.ssw.commonutilsmanager.email.EmailManager;
import com.ssw.commonutilsmanager.fingerprint.BiometricAuthenticationHandler;
import com.ssw.commonutilsmanager.mobile.MobileManager;
import com.ssw.commonutilsmanager.network.NetworkManager;
import com.ssw.commonutilsmanager.nic.NICManager;
import com.ssw.commonutilsmanager.toasts.ToastManager;
import com.ssw.commonutilsmanager.utils.Utilizer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        textView = findViewById(R.id.textView);

        getAppVersion();
    }

    // <editor-fold defaultstate="collapsed" desc="App Manager">
    private void checkAppRunning() {
        System.out.println(AppManager.getInstance().isAppRunning(this, getPackageName()));
    }

    private void getAppVersion() {
        System.out.println(AppManager.getInstance().getAppVersion(this));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Finger Print and Face ID Handler">
    private void verifyBiometrics() {
        BiometricAuthenticationHandler biometricAuthenticationHandler = new BiometricAuthenticationHandler(this, new BiometricAuthenticationHandler.BiometricAuthenticationHandlerEvents() {
            @Override
            public void onAuthenticationSuccess() {
                ToastManager.getInstance().showTopToast(MainActivity.this, "Authentication Success", ToastManager.TOP_DURATION_LONG);
            }

            @Override
            public void onAuthenticationFailed() {
                ToastManager.getInstance().showTopToast(MainActivity.this, "Authentication Failed", ToastManager.TOP_DURATION_LONG);
            }

            @Override
            public void onAuthenticationCancelled() {
                ToastManager.getInstance().showTopToast(MainActivity.this, "Authentication Cancelled", ToastManager.TOP_DURATION_LONG);
            }
        });

        biometricAuthenticationHandler.startAuthentication("Title", "SubTitle", "Description", "BTNTEXT", R.mipmap.ic_launcher);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Network Checker">
    private void checkInternetOn() {
        System.out.println(NetworkManager.getInstance().isInternetOn(this));
    }

    private void checkInternetAvailable() {
        NetworkManager.getInstance().isInternetAvailable(new NetworkManager.NetworkCheckListener() {
            @Override
            public void onInternetAvailable() {
                System.out.println("onInternetAvailable");
            }

            @Override
            public void onError() {
                System.out.println("onError");
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String Validations">
    private void checkEmptyString() {
        System.out.println(Utilizer.getInstance().isStringEmpty(""));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Card Number Validations, Card Types, Card Number Formatting">
    private void validateCardNumber() {
        System.out.println(CardNumberValidations.getInstance().validateCreditCardNumber("123456789123456789"));
    }

    private void getFormattedCardNumber() {
        System.out.println(CardNumberValidations.getInstance().getFormattedCardNumber("1234567898765432", 4));
    }

    private void getCardTypeFromNumber() {
        if (CardNumberValidations.getInstance().getCardTypeFromCardNumber("123456789123456789") == CardNumberValidations.CARD_TYPE_VISA) {

        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Calendar, Date Time ago">
    private void getCurrentDateInCustomFormat() {
        System.out.println(CalendarUtilsManager.getInstance().getCurrentDateCustomFormat("yyyy-MMM-dd HH:mm:ss"));
    }

    private void getPreviousDate() {
        System.out.println(CalendarUtilsManager.getInstance().getPreviousDate(3, "yyyy-MMM-dd HH:mm:ss"));
    }

    private void convertDateStrings() {
        System.out.println(CalendarUtilsManager.getInstance().convertDateString(CalendarUtilsManager.getInstance().getCurrentDateCustomFormat("yyyy-MMM-dd HH:mm:ss"), "dd-MM-yyyy HH:mm:ss", "yyyy-MMM-dd HH:mm:ss", "yyyy-MMM-dd HH:mm:ss"));
    }

    private void getTimeAgo() {
        System.out.println(CalendarUtilsManager.getInstance().getDuration(System.currentTimeMillis() - CalendarUtilsManager.getInstance().getPreviousDate(3).getTime()));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mobile Numbers, Careers">
    private void isValidMobileNumber() {
        //If career validation is true, number will validate with all the mobile number careers in Sri Lanka
        //Mobile number formats - +94773606094, 0094773606094, 0773606094
        MobileManager.getInstance().isValidMobileNumber("+94773606094", true);
    }

    private void getCareerFromMobileNumber() {
        //Mobile number formats - +94773606094, 0094773606094, 0773606094
        if (MobileManager.getInstance().getCareerFromMobileNumber("+94773606094") == MobileManager.CAREER_DIALOG) {

        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Toast Messages, SnackBards, Top Toasts">
    private void showBasicSnackBar() {
        ToastManager.getInstance().showSnackBar(this.textView, "Test Message");
    }

    private void showSnackBarWithAction() {
        ToastManager.getInstance().showSnackBar(this.textView, "Test Message", "OK", new ToastManager.SnackBarButtonAction() {
            @Override
            public void buttonClick() {
                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showToast() {
        ToastManager.getInstance().showToast(this, "Test Message", ToastManager.TOAST_LENGTH_LONG);
    }

    private void showTopToastShort() {
        ToastManager.getInstance().showTopToast(this, "Test Message", ToastManager.TOP_DURATION_SHORT);
    }

    private void showTopToastLong() {
        ToastManager.getInstance().showTopToast(this, "Test Message", ToastManager.TOP_DURATION_LONG);
    }

    private void showTopToastIndefinite() {
        ToastManager.getInstance().showTopToast(this, "Test Message", ToastManager.TOP_DURATION_INDEFINITE);
    }

    private void showTopToastWithStyles() {
        ToastManager.getInstance().showTopToast(this, "Test Message", ToastManager.TOP_DURATION_LONG, "#880000", "#880000", "#FFFFFF", 10);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="NIC Numbers, Birthdays, Gender">
    private void isValidNIC() {
        System.out.println(NICManager.getInstance().isValidNICNumber("199410900877")); //New NIC
        System.out.println(NICManager.getInstance().isValidNICNumber("942490259V")); // Old NIC
    }

    private void getBirthYearFromNIC() {
        System.out.println(NICManager.getInstance().getBirthYearFromNIC("199410900877")); //New NIC
        System.out.println(NICManager.getInstance().getBirthYearFromNIC("942490259V")); // Old NIC
    }

    private void getGenderFromNIC() {
        //New NIC
        if (NICManager.getInstance().getGenderFromNIC("199410900877") == NICManager.GENDER_FEMALE) {

        }

        // Old NIC
        else if (NICManager.getInstance().getGenderFromNIC("942490259V") == NICManager.GENDER_MALE) {

        }
    }

    private void getBirthdayFromNIC() {
        System.out.println(NICManager.getInstance().getBirthdayFromNIC("199410900877")); //New NIC
        System.out.println(NICManager.getInstance().getBirthdayFromNIC("942490259V")); //Old NIC
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Email Validations">
    private void isValidEmailAddress() {
        System.out.println(EmailManager.getInstance().isValidEmailAddress("sumudusahanmax@gmail.com"));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Amounts">
    private void getFormattedAmount() {
        System.out.println(AmountManager.getInstance().getFormattedAmount(15548.25));
    }

    private void getCleanAmount() {
        System.out.println(AmountManager.getInstance().getDoubleValueOfAmount("15,753.14"));
    }
    // </editor-fold>
}

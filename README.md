# Util_Manager
Android Library with a collection of Util functions and Biometric Authentications

## What is Util_Manager
Util_Manager is an android library which can be used to implement biometric authentications, validate data in any android application.

## Main Classes
```Java
AppManager - include functions which related to application level (app running status, get app version).

CalendarUtilsManager - include functions which related to Date time validations and conversions.

CardNumberValidations - include functions which related to validate credit, debit card numbers, get card types with luhn algorithm.

AmountManager - collection of functions to format amounts and get clean numbers from formatted amounts.

DeviceManager - collection of functions to get device brand, model, device related functions.

EmailManager - collection of functions to validate email addresses.

BiometricAuthenticationHandler - to implement biometric validations with Face ID and Fingerprint(FaceID will support with BiometricPromts)

MobileManager - collection of functions to validate mobile numbers with careers, get career of the number.

NetworkManager - include functions which related to check internet connection in both device side and ISP side.(Both cellular data and WIFI)

NICManager - include set of functions to validate NIC numbers, get gender, get birthday etc...(supports for old(length=10 with V,X) and new(length=12) types)

SharedPreferenceManager - collection of functions to manage SharedPreference (Add, update, check, delete)

RecyclerViewLayoutManager - include functions to set item flow orientations for the recyclerview.

ObjectSerializer - collection of functions to serialize, deserialize custom bean objects.

ToastManager - collection of function for show toasts (Default toasts, Top bar toasts, SnackBars).

Utilizer - collection of functions to validate Strings and add zeropads.
```


## Implementation

### Step : 1 - Add the JitPack repository to your project root build.gradle file
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### Step : 2- Add the dependency
```gradle
dependencies {
      implementation 'com.github.Sumudu-Sahan:Utils_Manager:1.00.01'
}
```
#### This library packs with SDP, SSP libraries, androidx biometric library and GPS, network,vibrator, biometric related permissions. Therefore no need to add below mentioned permissions again and libraries
##### Below permissions are already included inside the library
```XML
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.USE_FINGERPRINT" />
<uses-permission android:name="com.samsung.android.providers.context.permission.WRITE_USE_APP_FEATURE_SURVEY" />
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.VIBRATE" />
```
##### below dependencies are already packed with the library
```Gradle
implementation 'androidx.biometric:biometric:1.0.0'
implementation 'com.intuit.ssp:ssp-android:1.0.6'
implementation 'com.intuit.sdp:sdp-android:1.0.6'
implementation 'com.androidadvance:topsnackbar:1.1.1'
```
#### Then sync the project.

## Few Functions

### Biometric Authentication
```Java
BiometricAuthenticationHandler biometricAuthenticationHandler = new BiometricAuthenticationHandler(fragmentActivity, new BiometricAuthenticationHandler.BiometricAuthenticationHandlerEvents() {
            @Override
            public void onAuthenticationSuccess() {
                ToastManager.getInstance().showTopToast(activity, "Authentication Success", ToastManager.TOP_DURATION_LONG);
            }

            @Override
            public void onAuthenticationFailed() {
                ToastManager.getInstance().showTopToast(activity, "Authentication Failed", ToastManager.TOP_DURATION_LONG);
            }

            @Override
            public void onAuthenticationCancelled() {
                ToastManager.getInstance().showTopToast(activity, "Authentication Cancelled", ToastManager.TOP_DURATION_LONG);
            }
        });

        biometricAuthenticationHandler.startAuthentication("Title", "SubTitle", "Description", "BTNTEXT", resID); //resID - popup dialog image icon resource
```


### Card Number Validation
```Java
CardNumberValidations.getInstance().validateCreditCardNumber("Card Number")
```
### Card Number Format
```Java
CardNumberValidations.getInstance().getFormattedCardNumber("Card Number", 4) //4 - Interval
```
### Get Card Type
```Java
CardNumberValidations.getInstance().getCardTypeFromCardNumber("Card Number")
```
##### Card Types
```Java
CardNumberValidations.CARD_TYPE_VISA - VISA type
CardNumberValidations.CARD_TYPE_MASTERCARD - Master Card Type
CardNumberValidations.CARD_TYPE_AMERICAN_EXPRESS - Amex
CardNumberValidations.CARD_TYPE_DINERS_CLUB - Diners Club
CardNumberValidations.CARD_TYPE_DISCOVER - Discover
CardNumberValidations.CARD_TYPE_JCB - JCB
CardNumberValidations.CARD_TYPE_CHINA_UNION_PAY - China Union Pay
CardNumberValidations.CARD_TYPE_UNKNOWN - Invalid Card Type
```


### Show Top Bar Toast
```Java
ToastManager.getInstance().showTopToast(activity, "Message", ToastManager.TOP_DURATION_LONG);
```
##### Available durations for TopBarToasts
```Java
ToastManager.TOP_DURATION_LONG
ToastManager.TOP_DURATION_INDEFINITE
ToastManager.TOP_DURATION_SHORT
```
### Show Snackbar
```Java
ToastManager.getInstance().showSnackBar(view, "Test Message", "OK", new ToastManager.SnackBarButtonAction() {
            @Override
            public void buttonClick() {
                //Do Something Here
            }
        });
```


### Mobile Number Validations
```Java
MobileManager.getInstance().isValidMobileNumber("+94773606094", true); //if career validation is enabled, mobile number check with current available mobile IPS codes in Sri Lanka
```
### Get Career from Mobile Number
```Java
MobileManager.getInstance().getCareerFromMobileNumber("+94773606094");
```
##### Available Mobile Number Careers
```Java
MobileManager.CAREER_DIALOG
MobileManager.CAREER_MOBITEL
MobileManager.CAREER_HUTCH
MobileManager.CAREER_AIRTEL
MobileManager.CAREER_INVALID
```


### Validate NIC Number
```Java
NICManager.getInstance().isValidNICNumber("942490259V"); // This method supports with both old and new formats
```
### Get Birthday from NIC
```Java
NICManager.getInstance().getBirthdayFromNIC("199410900877"); // This method supports with both old and new formats
```
### Get Birth Year from NIC
```Java
NICManager.getInstance().getBirthYearFromNIC("942490259V") // This method supports with both old and new formats
```
### Get Gender from NIC
```Java
NICManager.getInstance().getGenderFromNIC("942490259V"); // This method supports with both old and new formats
```
##### Returning gender types
```Java
NICManager.GENDER_MALE
NICManager.GENDER_FEMALE
```


### Format Amounts
```Java
AmountManager.getInstance().getFormattedAmount(15548.25);
```
### Get Clean Double Value from Formatted Amount
```Java
AmountManager.getInstance().getDoubleValueOfAmount("15,753.14");
```


### Validate Email Address
```Java
EmailManager.getInstance().isValidEmailAddress("sumudusahanmax@gmail.com");
```


### Get Current Date in Custom Format
```Java
CalendarUtilsManager.getInstance().getCurrentDateCustomFormat("yyyy-MMM-dd HH:mm:ss");
```
### Convert Date String to custom date formats
```Java
CalendarUtilsManager.getInstance().convertDateString("dateString", "newFormat", "parsingFormat", "AlternetParsingFormat")
```
### Get Time Gaps
```Java
CalendarUtilsManager.getInstance().getDuration(System.currentTimeMillis() - CalendarUtilsManager.getInstance().getPreviousDate(3).getTime());
```


### Check the availability of the Internet in Device Side (This checks both cellular data and WIFI)
```Java
NetworkManager.getInstance().isInternetOn(context);
```
### Check the availability of the Internet in ISP Side 
```Java
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
```


### Get App Version
```Java
AppManager.getInstance().getAppVersion(context);
```
### Check App Running Status
```Java
AppManager.getInstance().isAppRunning(context, getPackageName())
```


### Get Boolean Preference
```Java
SharedPreferenceManager.getInstance().getBooleanPref(context, "PreferenceName", "Key", true);
SharedPreferenceManager.getInstance().getBooleanPref(context, "Key", true);
```
### Get String Preference
```Java
SharedPreferenceManager.getInstance().getStringPreference(context, "PreferenceName", "Key", "defaultValue");
SharedPreferenceManager.getInstance().getStringPreference(context, "Key", "defaultValue");
```
### Get Int Preference
```Java
SharedPreferenceManager.getInstance().getIntPreference(context, "PreferenceName", "Key", 0);
SharedPreferenceManager.getInstance().getIntPreference(context, "Key", 0);
```
### Put String Value
```Java
SharedPreferenceManager.getInstance().putStringPreference(context, "PreferenceName", "Key", "value");
SharedPreferenceManager.getInstance().putStringPreference(context, "Key", "value");
```
### Check Preference Keys
```Java
SharedPreferenceManager.getInstance().hasPreference(context, "preferenceName", "key");
SharedPreferenceManager.getInstance().hasPreference(context, "key");
```
##### You can get, add values to the Shared Preference by creating a custom preference by parsing the preference name or get default preference.


### Set Recyclerview Item Orientations
```Java
RecyclerViewLayoutManager.getInstance().setLayoutManager(context, recyclerview, RecyclerViewLayoutManager.ORIENTATION_VERTICAL);
```


That's it. 
Happy Coding :smiley: :smiley: :smiley:

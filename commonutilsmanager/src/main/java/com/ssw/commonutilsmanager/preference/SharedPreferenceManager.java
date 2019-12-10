package com.ssw.commonutilsmanager.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class SharedPreferenceManager {
    private static final String TAG = "SharedPreferenceManager";

    private static SharedPreferenceManager sharedPreferenceManager;

    public static synchronized SharedPreferenceManager getInstance() {
        if (sharedPreferenceManager == null) {
            sharedPreferenceManager = new SharedPreferenceManager();
        }
        return sharedPreferenceManager;
    }

    public void removePrefefence(Context context, String preferenceName, String key) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.remove(key);
            editor.apply();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
    }

    public void removePrefefence(Context context, String key) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.remove(key);
            editor.apply();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasPreference(Context context, String preferenceName, String key) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            return sharedPrefs.contains(key);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean hasPreference(Context context, String key) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPrefs.contains(key);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean putStringPreference(Context context, String preferenceName, String key, String value) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(key, value);
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean putStringPreference(Context context, String key, String value) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(key, value);
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean putIntPreference(Context context, String preferenceName, String key, int value) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt(key, value);
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean putIntPreference(Context context, String key, int value) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt(key, value);
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean removeAllPreference(Context context, String preferenceName) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.clear();
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean removeAllPreference(Context context) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.clear();
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean putBooleanPreference(Context context, String preferenceName, String key, boolean value) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean putBooleanPreference(Context context, String key, boolean value) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public String getStringPreference(Context context, String preferenceName, String key, String defaultValue) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPrefs.getString(key, defaultValue);
    }

    public String getStringPreference(Context context, String key, String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(key, defaultValue);
    }

    public int getIntPreference(Context context, String preferenceName, String key, int defaultValue) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return sharedPrefs.getInt(key, defaultValue);
    }

    public int getIntPreference(Context context, String key, int defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getInt(key, defaultValue);
    }

    public boolean getBooleanPref(Context context, String preferenceName, String key, boolean defaultValue) {
        try {
            SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
            return sharedPrefs.getBoolean(key, defaultValue);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public boolean getBooleanPref(Context context, String key, boolean defaultValue) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPrefs.getBoolean(key, defaultValue);
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }
}

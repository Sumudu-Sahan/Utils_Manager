package com.ssw.commonutilsmanager.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.ssw.commonutilsmanager.common.ConstantList;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class NetworkManager {
    private static final String TAG = "NetworkManager";

    private static NetworkManager networkManager;

    public static synchronized NetworkManager getInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    public boolean isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void isInternetAvailable(NetworkCheckListener networkCheckListener) {
        new NetworkChecker(networkCheckListener).execute();
    }


    private class NetworkChecker extends AsyncTask<String, Void, Boolean> {
        private NetworkCheckListener networkCheckListener;

        public NetworkChecker(NetworkCheckListener networkCheckListener) {
            this.networkCheckListener = networkCheckListener;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                URL url = new URL("https://www.google.com/");
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestProperty("User-Agent", "test");
                httpsURLConnection.setRequestProperty("Connection", "close");
                httpsURLConnection.setConnectTimeout(ConstantList.DEFAULT_CONNECTION_TIMEOUT);
                httpsURLConnection.connect();

                return (httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
            } catch (Exception e) {
                if(DEV_MODE){
                    e.printStackTrace();
                }
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                networkCheckListener.onInternetAvailable();
            } else {
                networkCheckListener.onError();
            }
        }
    }

    public interface NetworkCheckListener {
        void onInternetAvailable();
        void onError();
    }
}

package demo.example.com.tvc_erp.ui.activity;

import android.app.Application;

import java.net.CookiePolicy;

import demo.example.com.tvc_erp.utils.ConnectivityReceiver;

/**
 * Created by Manh on 2/29/2016.
 */
public class TCVApplication extends Application{

    private static TCVApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static java.net.CookieManager msCookieManager = new java.net.CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER);

    public static synchronized TCVApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}

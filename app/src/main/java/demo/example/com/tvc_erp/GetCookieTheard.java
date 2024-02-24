package demo.example.com.tvc_erp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import demo.example.com.tvc_erp.ui.activity.TCVApplication;

/**
 * Created by Manh on 9/9/2016.
 */
public class GetCookieTheard implements Runnable {

    private Context context;
    private TCVApplication application;
    private static final String HOST = "http://tbi.tavicosoft.com";

    public GetCookieTheard(Context context) {
        this.context = context;
        this.application = (TCVApplication)context.getApplicationContext();
    }

    @Override
    public void run() {
        CookieHandler.setDefault(application.msCookieManager);
        HttpURLConnection connection = null;
        try {
            URL myUrl = new URL(HOST);
            connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.getResponseCode();
            String fCookie = TextUtils.join(";", application.msCookieManager.getCookieStore().getCookies());

            Log.i("LOG_FRESPONSE", fCookie);
        }catch (MalformedURLException e){
            final String errMsg = e.toString();
            Log.i("ERROR:", errMsg);
        }catch (IOException e){
            final String errMsg = e.toString();
            Log.i("ERROR:", errMsg);
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

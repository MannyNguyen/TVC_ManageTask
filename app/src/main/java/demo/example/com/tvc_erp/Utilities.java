package demo.example.com.tvc_erp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Manh on 9/9/2016.
 */
public class Utilities {
    public static Bitmap getBitmap(String strURL) throws IOException {
        Bitmap bitmap = null;
        InputStream iStream = null;
        HttpURLConnection urlConection = null;
        try {
            URL url = new URL(strURL);
            urlConection = (HttpURLConnection) url.openConnection();
            urlConection.setRequestProperty("Content-Type", "application/json");
            urlConection.connect();
            iStream = urlConection.getInputStream();
            bitmap = BitmapFactory.decodeStream(iStream);
        } catch (Exception ex) {
            Log.e("Error download url", ex.toString());
        }
        return bitmap;
    }

    public static HttpURLConnection applyHeaderWithPost(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Chrome/44.0.2403.130");
        connection.setRequestProperty("Connection", "keep-alive");

        return connection;
    }

    public static HttpURLConnection applyHeaderWithGet(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Chrome/44.0.2403.130");
        connection.setRequestProperty("Connection", "keep-alive");

        return connection;
    }

    public static JSONObject createRequestParam(String action, String method, JSONObject data) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("action", action);
        result.put("method", method);
        result.put("type", "rpc");
        result.put("tid", 7);
        JSONArray arr = new JSONArray();
        arr.put(data);
        result.put("data", arr);

        return result;
    }

    public static String getStringFromInputStream(InputStream is){
        BufferedReader br=null;
        StringBuilder sb=new StringBuilder();
        String line;
        try {
            br=new BufferedReader(new InputStreamReader(is));
            while((line=br.readLine())!=null){
                sb.append(line);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}

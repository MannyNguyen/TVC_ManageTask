package demo.example.com.tvc_erp.asynctask;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import demo.example.com.tvc_erp.Utilities;
import demo.example.com.tvc_erp.ui.activity.SplashScreenActivity;
import demo.example.com.tvc_erp.utils.SystemUtils;

/**
 * Created by prosoft on 9/14/16.
 */
public class GetListTaskAsynctask extends AsyncTask<String, String, String> {

    private Context context;
    private Dialog dLog;
    private String oldSessionId;
    static final String HOST = "http://tbi.tavicosoft.com";
    static final String SEC_IMG_LINK = "/captcha/getimage";
    static final String POST_URL = "http://tbi.tavicosoft.com/DirectRouter/Index";
    private SplashScreenActivity splashScreenActivity;

    public GetListTaskAsynctask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String fResponse) {
        super.onPostExecute(fResponse);
        if (fResponse == "fail") {
            Toast.makeText(context, "Try again, Session failed", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream iStream = null;
        OutputStream wr = null;
        HttpURLConnection urlConnection = null;
        String errMsg = "";
        String fResponse = "";
        try {
            URL myUrl = new URL(params[0]);
            urlConnection = (HttpURLConnection) myUrl.openConnection();
            urlConnection = Utilities.applyHeaderWithPost(urlConnection);

            JSONObject data = new JSONObject();
            data.put("category", "all");
            data.put("searchString", "");
            data.put("read", "all");
            data.put("page", 1);
            data.put("limit", 7);
            JSONArray sort = new JSONArray();
            JSONObject haoidhas = new JSONObject();
            haoidhas.put("property","lastcommentdate");
            haoidhas.put("direction","DESC");
            sort.put(haoidhas);
            data.put("sort", sort);

            JSONObject jsonParam = Utilities.createRequestParam("FrmCcWork", "get", data);
            String str = jsonParam.toString();
            byte[] outputInBytes = str.getBytes("UTF-8");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(outputInBytes.length));

            wr = urlConnection.getOutputStream();
            wr.write(outputInBytes);
            wr.flush();
            wr.close();

            /** Reading data from url */
            iStream = urlConnection.getInputStream();

            fResponse = Utilities.getStringFromInputStream(iStream);
            Log.i("LOG_FRESPONSE", fResponse);

            JSONObject json = new JSONObject(fResponse);
            JSONObject result = json.getJSONObject("result");
            boolean success = result.optBoolean("success");
            String sessionId = result.optString("sessionId");
            SystemUtils.saveSessionId(sessionId, context);
            JSONObject dataRes = result.getJSONObject("data");
            boolean login = dataRes.optBoolean("login");
            if (success && login) {
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception ex) {
            errMsg += "\n" + ex.toString();
            return "fail";
        } finally {
            try {
                if (iStream != null) iStream.close();
            } catch (IOException e) {
                Log.i("IO Exception", e.toString());
            }
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }
}

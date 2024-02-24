package demo.example.com.tvc_erp.api;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import demo.example.com.tvc_erp.Utilities;
import demo.example.com.tvc_erp.api.object_request_api.ActionRequest;
import demo.example.com.tvc_erp.utils.FileHelper;

/**
 * Created by prosoft on 12/24/15.
 */
public class JsonParser {

    private InputStream is = null;
    private JSONObject jObj = null;
    private String json = "";

    // constructor
    public JsonParser() {

    }

    // function get json from url
    // by making HTTP POST or GET meod

    public JSONObject makeHttpRequest(String url, String method, Object param, ActionRequest actionRequest) {

        // Making HTTP request
        // check for request method
        if (method.equals("POST")) {
            InputStream iStream = null;
            OutputStream wr = null;
            HttpURLConnection urlConnection = null;
            String errMsg = "";
            String fResponse = "";

            try {
                URL myUrl = new URL(url);
                urlConnection = (HttpURLConnection) myUrl.openConnection();
                urlConnection = Utilities.applyHeaderWithPost(urlConnection);
                Gson gson = new Gson();
                JSONObject data = new JSONObject(gson.toJson(param));
//                    JSONObject data = new JSONObject(param.toString());
                JSONObject jsonParam = Utilities.createRequestParam(actionRequest.getAction(), actionRequest.getMethod(), data);
                String str = jsonParam.toString();
                Log.v("Create request", "POST : " + str);
                byte[] outputInBytes = str.getBytes("UTF-8");
                urlConnection.setRequestProperty("Content-Length", Integer.toString(outputInBytes.length));

                Log.i("1111111", "aaaaaaa");
                wr = urlConnection.getOutputStream();
                wr.write(outputInBytes);
                wr.flush();
                wr.close();
                Log.i("1111111", "bbbbbbbb");
                iStream = urlConnection.getInputStream();
                Log.i("1111111", "cccccccc");
                fResponse = Utilities.getStringFromInputStream(iStream);

                Log.v("TestResponse", fResponse);

                try {
                    jObj = new JSONObject(fResponse);
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                return jObj;

            } catch (Exception ex) {
                errMsg += "\n" + ex.toString();
                Log.i("Error Exception", errMsg);
                return jObj;
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

        return jObj;

    }

    public JSONObject getHttpRequest(String url, String method) {
        HttpURLConnection urlConnection = null;
        String fResponse = "";
        String errMsg = "";
        InputStream iStream = null;

        try {
            URL myUrl = new URL(url);
            urlConnection = (HttpURLConnection) myUrl.openConnection();
            urlConnection = Utilities.applyHeaderWithGet(urlConnection);
            iStream = urlConnection.getInputStream();

            fResponse = Utilities.getStringFromInputStream(iStream);

            Log.v("TestResponse", fResponse);

            try {
                jObj = new JSONObject(fResponse);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return jObj;


        } catch (Exception ex) {
            errMsg += "\n" + ex.toString();
            Log.i("Error Exception", errMsg);
            return jObj;
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

    public byte[] downloadImage(String imgName, String url) {
        HttpURLConnection urlConnection = null;
        String errMsg = "";
        Log.i("000000","0000000");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Log.i("111111","1111111");
        try {
            URL myUrl = new URL(url);
            urlConnection = (HttpURLConnection) myUrl.openConnection();
            Log.i("222222","2222222");
            urlConnection = Utilities.applyHeaderWithGet(urlConnection);
            System.out.println("URL [" + url + "] - Name [" + imgName + "]");
            Log.i("3333333","3333333");

            final String filename = getUniqueImageFilename();
            File realImageFile = FileHelper.getOutputMediaFilePath(filename);
            FileOutputStream f = new FileOutputStream(realImageFile);

            Log.i("444444444","44444444");
            byte[] buffer = new byte[1024];
            InputStream is = urlConnection.getInputStream();
            Log.i("55555555","55555555" + is);
            int len1 ;
            while ( (len1 = is.read(buffer)) > 0 ) {
                f.write(buffer,0, len1);
                baos.write(buffer, 0, len1);
            }
            Log.i("66666666","666666666");
            f.close();
            urlConnection.disconnect();


        } catch (Exception ex) {
            errMsg += "\n" + ex.toString();
            Log.i("Error Exception", errMsg);
        }

        return baos.toByteArray();
    }

    private String getUniqueImageFilename() {
        return "img_" + System.currentTimeMillis() + ".jpg";
    }
//    public JSONObject putHttpRequest(String url, String method, Object param) {
//
//        // Making HTTP request
//        try {
//            // check for request method
//            if (method.equals("PUT")) {
//                Log.v("Create Response", "PUT");
//                // request method is PUT
//                HttpParams httpParameters = new BasicHttpParams();
//                int timeoutConnection = 3000;
//                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
//                int timeoutSocket = 5000;
//                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//
//                Log.v("Create Response", "POST");
//                // request method is POST
//                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
//                HttpPut httpPost = new HttpPut(url);
//
//                Gson gson = new Gson();
//                StringEntity se = new StringEntity(gson.toJson(param));
//                Log.v("testlog", "param---" + gson.toJson(param));
//
//                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                httpPost.setEntity(se);
//                // Log.v("testlog", "param" + param.toString());
//                HttpResponse httpResponse = createHttpClient().execute(httpPost);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                is = httpEntity.getContent();
//
//            } else if (method == "GET") {
//                // request method is GET
//                /**
//                 DefaultHttpClient httpClient = new DefaultHttpClient();
//                 String paramString = URLEncodedUtils.format(params, "utf-8");
//                 url += "?" + paramString;
//                 HttpGet httpGet = new HttpGet(url);
//
//                 HttpResponse httpResponse = httpClient.execute(httpGet);
//                 HttpEntity httpEntity = httpResponse.getEntity();
//                 is = httpEntity.getContent();**/
//            }
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            is = null;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (SocketTimeoutException e) {
//            e.printStackTrace();
//            is = null;
//        } catch (ConnectTimeoutException e) {
//            e.printStackTrace();
//            is = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    is, "utf-8"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            is.close();
//            json = sb.toString();
//        } catch (Exception e) {
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//        }
//        Log.v("Testlog", json);
//        // try parse the string to a JSON object
//        try {
//            jObj = new JSONObject(json);
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        // return JSON String
//        return jObj;
//
//    }
//
//    public JSONObject makeHttpRequest(String url) {
//
//        // Making HTTP request
//        // JSONArray jsonArray =null;
//        try {
//
//            // check for request method
//            /**   if(method == "POST"){
//             // request method is POST
//             // defaultHttpClient
//
//             DefaultHttpClient httpClient = new DefaultHttpClient();
//             HttpPost httpPost = new HttpPost(url);
//
//             httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
//
//
//             if(Constants.cookieStore==null)
//             {
//             Constants.cookieStore = new BasicCookieStore();
//             }
//             HttpContext localContext = new BasicHttpContext();
//             localContext.setAttribute(ClientContext.COOKIE_STORE, Constants.cookieStore);
//             HttpResponse httpResponse = httpClient.execute(httpPost,localContext);
//             HttpEntity httpEntity = httpResponse.getEntity();
//             is = httpEntity.getContent();
//
//             }else if(method == "GET"){**/
//
//            HttpParams httpParameters = new BasicHttpParams();
//            int timeoutConnection = 3000;
//            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
//            int timeoutSocket = 5000;
//            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//            // request method is GET
//            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
//            //   String paramString = URLEncodedUtils.format(params, "utf-8");
//            // url += "?" + paramString;
//            //   Log.v("paramString", paramString);
//            HttpGet httpGet = new HttpGet(url);
//            // CookieStore cookieStore = new BasicCookieStore();
//            //  Cookie cookie = new BasicClientCookie("loginToken", "22222");
//
//            // cookieStore.addCookie(cookie);
//            /**   if(Constants.cookieStore==null)
//             {
//             Constants.cookieStore = new BasicCookieStore();
//             }**/
//            HttpContext localContext = new BasicHttpContext();
//            // localContext.setAttribute(ClientContext.COOKIE_STORE, Constants.cookieStore);
//            HttpResponse httpResponse = httpClient.execute(httpGet, localContext);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            is = httpEntity.getContent();
//            //  }
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//            is = null;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (SocketTimeoutException e) {
//            e.printStackTrace();
//            is = null;
//        } catch (ConnectTimeoutException e) {
//            e.printStackTrace();
//            is = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    is, "utf-8"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            //sb.append("{\"result\":[");
//            while ((line = reader.readLine()) != null) {
//                // Log.v("Lineee" , line);
//                sb.append(line + "\n");
//            }
//            //sb.append("]}");
//            is.close();
//            json = sb.toString();
//
//        } catch (Exception e) {
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//        }
//
//        // try parse the string to a JSON object
//        try {
//            jObj = new JSONObject(json);
//
//            // Log.v("JSONJSONJSONJSON", jObj.toString().substring(0,100));
//            //  jsonArray = new JSONArray(json);
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//
//        // return JSON String
//        //  Log.v("resultttttt1222" ,jObj.toString());
//        return jObj;
//
//    }
//
//

//
//    private static HttpClient createHttpClient() {
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//
////            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
//            SSLSocketFactory sf = new SSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//            HttpParams params = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(params, 15000);
//            HttpConnectionParams.setSoTimeout(params, 15000);
//
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//
//            return new DefaultHttpClient(ccm, params);
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//    }
}

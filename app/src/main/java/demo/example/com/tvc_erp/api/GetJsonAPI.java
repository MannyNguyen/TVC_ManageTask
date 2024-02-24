package demo.example.com.tvc_erp.api;


import java.util.ArrayList;
import java.util.List;

import demo.example.com.tvc_erp.ui.enums.API_Method;
import demo.example.com.tvc_erp.api.object_request_api.ActionRequest;

/**
 * Created by Tung on 24/01/2015.
 */
public class GetJsonAPI {

     public static void getQueries(String url, API_Method method, Object jsonObjectToCallAPI, CallBackAPI callBackDone, ActionRequest actionRequest)
     {
         List<Object> listParams = new ArrayList<Object>();
         listParams.add(url);
         listParams.add(method);
         listParams.add(jsonObjectToCallAPI);
         listParams.add(actionRequest);

         callBackDone.execute(listParams);
     }


    public static void getQueries(String url, API_Method method, CallBackDownloadAPI callBackDone, String fileName)
    {
        List<Object> listParams = new ArrayList<Object>();
        listParams.add(url);
        listParams.add(method);
        listParams.add(fileName);
        callBackDone.execute(listParams);
    }

}

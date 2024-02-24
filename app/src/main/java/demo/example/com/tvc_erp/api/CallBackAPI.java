package demo.example.com.tvc_erp.api;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.List;

import demo.example.com.tvc_erp.ui.enums.API_Method;
import demo.example.com.tvc_erp.api.object_request_api.ActionRequest;
import demo.example.com.tvc_erp.ui.objects.ModelError;
import demo.example.com.tvc_erp.ui.objects.ModelObject;
import demo.example.com.tvc_erp.utils.TaxiLoyDebug;

/**
 * Created by prosoft on 12/24/15.
 */
public class CallBackAPI extends AsyncTask<List<Object>, String, JSONObject> {

    private OnTaskComplete onTaskComplete;
    private API_Method method;

    public interface OnTaskComplete {
        public void setMyTaskComplete(JSONObject result);

        public void onStart();

        public void onSuccess(ModelObject modelObject);

        public void onFail(ModelError error);

        public void onComplete();
    }

    public void setMyTaskCompleteListener(OnTaskComplete onTaskComplete) {
        this.onTaskComplete = onTaskComplete;
    }

    public CallBackAPI() {

    }

    public CallBackAPI(Context context) {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(List<Object>... params) {
        JSONObject result = null;
        //Generating random number between:
        //params[0] is url
        //params[1] is method
        JsonParser json = new JsonParser();

        String url = params[0].get(0).toString();
        API_Method method =(API_Method)params[0].get(1);
        Object param = (Object) params[0].get(2);
        ActionRequest actionRequest = (ActionRequest)  params[0].get(3);

        this.method = method;
        if (this.method == API_Method.POST) {
            result = json.makeHttpRequest(url, method.getValue(), param, actionRequest);
        } else if (this.method == API_Method.GET) {
            result = json.getHttpRequest(url, method.getValue());
        }
//        else {
//            result = json.putHttpRequest(url, method.getValue(), param);
//        }
        //message
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            if (result == null || result.has("error") || result.has("code") || result.has("message")) {
                //onTaskComplete.setMyTaskComplete(result);
                onTaskComplete.onFail(processErrorResponse(result));
            } else {
                boolean isBoolean = result.getJSONObject("result").optBoolean("success", false);
                if(isBoolean){
                    onTaskComplete.setMyTaskComplete(result);
                } else {
                    onTaskComplete.onFail(processErrorResponse(result));
                }
                /**
                 Object modelJson = result.get(modelObj.getKeyPath());

                 modelObj.setValuesWithJSON(modelJson);

                 boolean clean = modelObj.isClean();

                 if (clean) {
                 modelObject = modelObj;
                 }
                 onTaskComplete.onSuccess(modelObject);**/


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Class dataClassForPostResponse() {
        TaxiLoyDebug.d(this.getClass().toString() + " Need to implement dataClassForPostResponse");
        return null;
    }

    public Class dataClassForGetResponse() {
        TaxiLoyDebug.d(this.getClass().toString() + " Need to implement dataClassForGetResponse");
        return null;
    }


    private ModelError processErrorResponse(Object modelJSON) {
        if (modelJSON == null) {
            ModelError error = new ModelError(ModelError.UNSPECIFIED_ERROR, "");
            return error;
        }

        ModelError error = new ModelError();
        error.setValuesWithJSON(modelJSON);

        if (!error.isClean()) {
            error = new ModelError(ModelError.UNSPECIFIED_ERROR, "");
        }
        return error;
        //processErrorResponse(error);
    }
}
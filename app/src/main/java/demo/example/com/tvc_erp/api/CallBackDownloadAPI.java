package demo.example.com.tvc_erp.api;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.List;

import demo.example.com.tvc_erp.api.object_request_api.ActionRequest;
import demo.example.com.tvc_erp.ui.enums.API_Method;
import demo.example.com.tvc_erp.ui.objects.ModelError;
import demo.example.com.tvc_erp.ui.objects.ModelObject;

/**
 * Created by prosoft on 11/1/16.
 */

public class CallBackDownloadAPI extends AsyncTask<List<Object>, Void, byte[]> {

    private OnTaskComplete onTaskComplete;
    private API_Method method;

    public interface OnTaskComplete {
        public void setMyTaskComplete(byte[] result);

        public void onStart();

        public void onSuccess(ModelObject modelObject);

        public void onFail(ModelError error);

        public void onComplete();
    }

    public void setMyTaskCompleteListener(CallBackDownloadAPI.OnTaskComplete onTaskComplete) {
        this.onTaskComplete = onTaskComplete;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected byte[] doInBackground(List<Object>... params) {

        byte[] result = null;
        //Generating random number between:
        //params[0] is url
        //params[1] is method
        JsonParser json = new JsonParser();

        String url = params[0].get(0).toString();
        API_Method method =(API_Method)params[0].get(1);
        String fileName =  params[0].get(2).toString();
        this.method = method;
        if (this.method == API_Method.DOWNLOAD) {
            result = json.downloadImage(fileName, url);
        }

        return result;
    }

    @Override
    protected void onPostExecute(byte[] result) {
        try {
            if (result == null) {
                //onTaskComplete.setMyTaskComplete(result);
                onTaskComplete.onFail(processErrorResponse(result));
            } else {
                /**
                 Object modelJson = result.get(modelObj.getKeyPath());

                 modelObj.setValuesWithJSON(modelJson);

                 boolean clean = modelObj.isClean();

                 if (clean) {
                 modelObject = modelObj;
                 }
                 onTaskComplete.onSuccess(modelObject);**/

                onTaskComplete.setMyTaskComplete(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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

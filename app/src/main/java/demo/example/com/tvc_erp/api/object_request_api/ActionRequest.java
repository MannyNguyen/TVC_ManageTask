package demo.example.com.tvc_erp.api.object_request_api;

/**
 * Created by prosoft on 9/20/16.
 */
public class ActionRequest {
    private String action;
    private String method;

    public ActionRequest(String action, String method){
        this.action = action;
        this.method = method;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

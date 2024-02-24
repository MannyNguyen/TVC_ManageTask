package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 10/31/16.
 */

public class GetActionTaskResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetActionTaskResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public String title;
        public String message;
    }

}

package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;

/**
 * Created by prosoft on 11/3/16.
 */

public class GetDeleteCommentResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetDeleteCommentResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public String title;
        public String message;
    }
}

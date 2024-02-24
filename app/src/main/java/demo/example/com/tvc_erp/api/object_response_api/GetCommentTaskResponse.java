package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 11/9/16.
 */

public class GetCommentTaskResponse  implements Serializable {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetCommentTaskResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetCommentTaskResponse.Data> data;
    }

    public class Data implements Serializable{
        public int linenum;
        public String comments;
        public String operatorid;
        public String title;
        public int attachCount;
        public String employee;
    }
}

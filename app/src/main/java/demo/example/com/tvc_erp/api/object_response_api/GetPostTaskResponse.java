package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 11/4/16.
 */

public class GetPostTaskResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetPostTaskResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetPostTaskResponse.Data> data;
        public String title;
        public String message;
    }

    public class Data implements Serializable{
        public String recordid;

    }

}

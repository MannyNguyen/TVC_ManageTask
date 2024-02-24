package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 10/20/16.
 */

public class GetJobTYpeResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetJobTYpeResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetJobTYpeResponse.Data> data;
        public String message;
        public int totalCount;
    }

    public class Data implements Serializable{
        public String category;
        public String code;
        public String name;

    }
}

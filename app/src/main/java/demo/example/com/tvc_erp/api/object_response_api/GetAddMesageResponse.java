package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 11/1/16.
 */

public class GetAddMesageResponse implements Serializable{

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetAddMesageResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public GetAddMesageResponse.Data data;
        public String title;
        public String message;
    }

    public class Data implements Serializable{
        public String title;
        public int linenum;
        public String operatorid;
        public String commentdate;


    }
}

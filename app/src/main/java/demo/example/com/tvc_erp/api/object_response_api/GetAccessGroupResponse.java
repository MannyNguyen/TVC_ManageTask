package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 10/26/16.
 */

public class GetAccessGroupResponse implements Serializable{

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetAccessGroupResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetAccessGroupResponse.Data> data;
    }

    public class Data implements Serializable{
        public String dagid;
        public String ei;
        public String name;


    }
}

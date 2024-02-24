package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 10/18/16.
 */

public class GetListUsersResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetListUsersResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetListUsersResponse.Data> data;
        public String message;
        public int totalCount;
    }

    public class Data implements Serializable{
        public String employee;
        public String name;

    }

}

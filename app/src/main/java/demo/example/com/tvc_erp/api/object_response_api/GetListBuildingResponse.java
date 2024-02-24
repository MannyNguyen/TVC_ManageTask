package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 12/14/16.
 */

public class GetListBuildingResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetListBuildingResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetListBuildingResponse.Data> data;
    }

    public class Data implements Serializable{
        public String block;
        public String name;

    }
}

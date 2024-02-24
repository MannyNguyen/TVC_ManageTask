package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 12/14/16.
 */

public class GetListApartmentRespone {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetListApartmentRespone.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetListApartmentRespone.Data> data;
    }

    public class Data implements Serializable{
        public String property;
        public String description;

    }
}

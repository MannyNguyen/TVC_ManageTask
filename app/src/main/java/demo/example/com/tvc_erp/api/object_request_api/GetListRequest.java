package demo.example.com.tvc_erp.api.object_request_api;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by prosoft on 9/20/16.
 */
public class GetListRequest {

    public String category;
    public String searchString;
    public String read;
    public int start;
    public int limit;
    public ArrayList<Sort> sort = new ArrayList<>();

    public GetListRequest(String property, String direction) {
        super();
        sort.add(new Sort(property, direction));
    }

    public class Sort{
        public String property;
        public String direction;

        public Sort(String property, String direction){
            this.property = property;
            this.direction = direction;
        }
    }
}

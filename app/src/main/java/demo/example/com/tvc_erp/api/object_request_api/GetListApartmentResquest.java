package demo.example.com.tvc_erp.api.object_request_api;

import android.util.Base64;

import java.util.ArrayList;

/**
 * Created by prosoft on 12/14/16.
 */

public class GetListApartmentResquest {

    public String isShortData;
    public ArrayList<GetListApartmentResquest.Filter> filters = new ArrayList<>();

    public class Filter {
        public String field = "block";
        public String op = "eq";
        public String value;
        public String type = "string";

        public Filter(String value) {
            this.value = value;
        }
    }
}

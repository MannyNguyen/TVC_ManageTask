package demo.example.com.tvc_erp.api.object_response_api;

/**
 * Created by prosoft on 9/22/16.
 */

public class LoginResponse {

    public String type;
    public int tid;
    public String action;
    public Result result;

    public class Result{
        public boolean success;
        public String sessionId;
        public Data data;
    }

    public class Data {
        public boolean login;
        public String ledger;
        public String company;
        public String operatorid;
        public String employee;
        public String warehouse;
        public String roleid;
        public String operatorname;
        public String homepage;
        public String dateformat;
        public String language;
        public String email;
        public String operations;
        public boolean debug;
        public String title;
        public String tabname;
        public int langVersion;
    }
}

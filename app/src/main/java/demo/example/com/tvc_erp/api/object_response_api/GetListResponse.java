package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 9/20/16.
 */
public class GetListResponse implements Serializable{

    public String type;
    public int tid;
    public String action;
    public String method;
    public Result result;

    public class Result implements Serializable{
        public boolean success ;
        public ArrayList<Data> data;
        public String message;
        public int totalCount;
    }

    public class Data implements Serializable{
        public String recordid;
        public String recordtype;
        public String title;
        public String status;
        public String taskmaster;
        public String pic;
        public String dagid;
        public String duedate;
        public String startdate;
        public String enddate;
        public String cc;
        public String jobtype;
        public String priority;
        public String description;
        public String lastcommentdate;
        public int maxline;
        public String createddate;
        public String author;
        public String read;
        public int attachCount;
        public ArrayList<Comment> cmts;
    }

    public class Comment implements Serializable {
        public int linenum;
        public String comments;
        public String employee;
        public String operatorid;
        public String title;
        public int attachCount;
    }
}

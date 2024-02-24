package demo.example.com.tvc_erp.api.object_request_api;

import android.util.Base64;

import java.util.ArrayList;

/**
 * Created by prosoft on 11/2/16.
 */

public class PostUpdateTaskRequest {

    public String startdate;
    public String taskmaster;
    public String recordtype ;
    public String status;
    public String title ;
    public String duedate;
    public String enddate;
    public String jobtype;
    public String priority;
    public String description;
    public String email;
    public String completionrate;
    public String comments = "";
    public String duration;
    public String anal_wlt0 = "";
    public String anal_wlt1 = "";
    public String cc;
    public String dagid;
    public String pic;
    public String recordid ;
    public ArrayList<PostUpdateTaskRequest.Attachment> attachments = new ArrayList<>();


    public class Attachment {
        public String filename;
        public String data;

        public Attachment( String filename, byte[] data){
            this.filename = filename;
            this.data =  Base64.encodeToString(data, Base64.DEFAULT);

        }
    }

}

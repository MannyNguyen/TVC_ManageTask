package demo.example.com.tvc_erp.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 10/11/16.
 */

public class GetDetailAttachmentResponse implements Serializable {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetDetailAttachmentResponse.Result result;

    public class Result implements Serializable{
        public boolean success ;
        public ArrayList<GetDetailAttachmentResponse.Data> data;
    }

    public class Data implements Serializable{
        public String author;
        public String recordtype;
        public String autonum;
        public String category;
        public String comments;
        public String createddate;
        public String documentcode;
        public String filename;
        public String isdefault;
        public String linkto;
        public String referencekey1;
        public String referencekey2;
        public String referencekey3;
        public String referencekey4;
        public String referencekey5;
        public String subject;

    }
}

package demo.example.com.tvc_erp.api.object_request_api;

import android.util.Base64;

import java.util.ArrayList;

/**
 * Created by prosoft on 11/9/16.
 */

public class UpdateCommentRequest {

    public String recordid;
    public int linenum;
    public String comments;
    public ArrayList<AddCommentRequest.Attachment> attachments = new ArrayList<>();

    public class Attachment {
        public String filename;
        public String data;

        public Attachment( String filename, byte[] data){
            this.filename = filename;
            this.data =  Base64.encodeToString(data, Base64.DEFAULT);

        }
    }

}

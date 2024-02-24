package demo.example.com.tvc_erp.api.object_request_api;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by prosoft on 10/6/16.
 */

public class AddCommentRequest {

    public String recordid;
    public String comments;
    public ArrayList<Attachment> attachments = new ArrayList<>();

    public class Attachment {
        public String filename;
        public String data;

        public Attachment( String filename, byte[] data){
            this.filename = filename;
            this.data =  Base64.encodeToString(data, Base64.DEFAULT);

        }
    }
}

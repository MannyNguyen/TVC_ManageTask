package demo.example.com.tvc_erp.ui.objects;

import java.io.Serializable;

/**
 * Created by prosoft on 11/4/16.
 */

public class Comment implements Serializable {

    public int linenum;
    public String comments;
    public String operatorid;
    public String title;
    public int attachCount;

    public int getLinenum() {
        return linenum;
    }

    public void setLinenum(int linenum) {
        this.linenum = linenum;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAttachCount() {
        return attachCount;
    }

    public void setAttachCount(int attachCount) {
        this.attachCount = attachCount;
    }
}

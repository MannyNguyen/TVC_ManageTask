package demo.example.com.tvc_erp.ui.objects;

/**
 * Created by Manh on 9/15/2016.
 */
public class WorkTask {
    private String title;
    private String taskName;
    private String masterName;
    private String date;
    private String numCmt;
    private String description;
    private byte[] imgAvatar;

    public WorkTask(String title, String taskName, String masterName, String date, String numCmt, String description, byte[] imgAvatar) {
        this.title = title;
        this.taskName = taskName;
        this.masterName = masterName;
        this.date = date;
        this.numCmt = numCmt;
        this.description = description;
        this.imgAvatar = imgAvatar;
    }

    public WorkTask() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumCmt() {
        return numCmt;
    }

    public void setNumCmt(String numCmt) {
        this.numCmt = numCmt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(byte[] imgAvatar) {
        this.imgAvatar = imgAvatar;
    }
}

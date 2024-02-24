package demo.example.com.tvc_erp.ui.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import demo.example.com.tvc_erp.api.object_response_api.GetListResponse;

/**
 * Created by prosoft on 9/20/16.
 */
public class Task implements Serializable {

    private String title;
    private String type;
    private String author;
    private String createDate;
    private int numCmt;
    private String detail;
    private String taskMaster;
    private String pic;
    private List<String> cc = new ArrayList<>();
    private boolean isLoad;
    private String recordID ;
    private String status;
    private int attachCount;
    private String startDate;
    private String endDate;
    private String dueDate;
    private String jobType;
    private String priorityl;

    private List<GetListResponse.Comment> commentList;

    public Task(boolean isLoad){
        this.isLoad = isLoad;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getNumCmt() {
        return numCmt;
    }

    public void setNumCmt(int numCmt) {
        this.numCmt = numCmt;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTaskMaster() {
        return taskMaster;
    }

    public void setTaskMaster(String taskMaster) {
        this.taskMaster = taskMaster;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<GetListResponse.Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<GetListResponse.Comment> commentList) {
        this.commentList = commentList;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAttachCount() {
        return attachCount;
    }

    public void setAttachCount(int attachCount) {
        this.attachCount = attachCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPriorityl() {
        return priorityl;
    }

    public void setPriorityl(String priorityl) {
        this.priorityl = priorityl;
    }
}

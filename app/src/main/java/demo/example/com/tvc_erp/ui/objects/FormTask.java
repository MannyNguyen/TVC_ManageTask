package demo.example.com.tvc_erp.ui.objects;

import java.util.ArrayList;

import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.utils.FileHelper;

/**
 * Created by prosoft on 10/20/16.
 */

public class FormTask {

    private String title;
    private String taskMaster;
    private String personCharge;
    private String cc ;
    private String startDate;
    private String dueDate;
    private String endDate;
    private String jobType;
    private String jobPrioriy;
    private String status;
    private String description;
    private String email;
    private String dagid ;
    private String parentId;
    private String anal_wlt0;
    private String anal_wlt1;
    private ArrayList imageByteArray;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskMaster() {
        return taskMaster;
    }

    public void setTaskMaster(String taskMaster) {
        this.taskMaster = taskMaster;
    }

    public String getPersonCharge() {
        return personCharge;
    }

    public void setPersonCharge(String personCharge) {
        this.personCharge = personCharge;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobPrioriy() {
        return jobPrioriy;
    }

    public void setJobPrioriy(String jobPrioriy) {
        this.jobPrioriy = jobPrioriy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(ArrayList imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public String getDagid() {
        return dagid;
    }

    public void setDagid(String dagid) {
        this.dagid = dagid;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAnal_wlt0() {
        return anal_wlt0;
    }

    public void setAnal_wlt0(String anal_wlt0) {
        this.anal_wlt0 = anal_wlt0;
    }

    public String getAnal_wlt1() {
        return anal_wlt1;
    }

    public void setAnal_wlt1(String anal_wlt1) {
        this.anal_wlt1 = anal_wlt1;
    }
}

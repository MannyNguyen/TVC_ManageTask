package demo.example.com.tvc_erp.ui.objects;

import java.io.Serializable;

/**
 * Created by prosoft on 11/3/16.
 */

public class CurrentUserInfo implements Serializable {

    private String userName;
    private String operatorid;
    private String company;
    private String tabname;
    private String employee;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
}

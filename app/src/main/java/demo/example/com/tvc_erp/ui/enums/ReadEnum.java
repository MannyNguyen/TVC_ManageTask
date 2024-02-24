package demo.example.com.tvc_erp.ui.enums;

/**
 * Created by prosoft on 10/12/16.
 */

public enum ReadEnum {

    TYPE1("all"),
    TYPE2("read"),
    TYPE3("unread");

    private String mType;

    ReadEnum(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }
}

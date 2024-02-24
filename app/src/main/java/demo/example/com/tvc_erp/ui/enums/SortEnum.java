package demo.example.com.tvc_erp.ui.enums;

/**
 * Created by prosoft on 10/12/16.
 */

public enum SortEnum {

    TYPE1("lastcommentdate"),
    TYPE2("priority");

    private String mType;

    SortEnum(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }
}

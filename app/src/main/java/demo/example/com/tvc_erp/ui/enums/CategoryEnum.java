package demo.example.com.tvc_erp.ui.enums;

/**
 * Created by prosoft on 10/12/16.
 */

public enum CategoryEnum {

    TYPE1("all"),
    TYPE2("news"),
    TYPE3("opt"),
    TYPE4("onc"),
    TYPE5("oc"),
    TYPE6("jnc"),
    TYPE7("jc"),
    TYPE8("rnc"),
    TYPE9("rc"),
    TYPE10("onh");

    private String mType;

    CategoryEnum(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

}

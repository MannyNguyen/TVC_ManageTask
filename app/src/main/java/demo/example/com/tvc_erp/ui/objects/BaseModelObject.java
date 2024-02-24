package demo.example.com.tvc_erp.ui.objects;

import org.json.JSONObject;

import demo.example.com.tvc_erp.utils.TaxiLoyDebug;


public class BaseModelObject extends InstantObservable implements ModelObject {
    protected boolean clean;

    @Override
    public boolean isClean() {
        return clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    @Override
    public void setValuesWithJSON(Object jsonObject) {
        TaxiLoyDebug.d(this.getClass().toString() + " Needs to implement setValuesWithJSON.");
    }

    @Override
    public JSONObject getJSONRepresentation() {
        TaxiLoyDebug.d(this.getClass().toString() + " Needs to implement getJSONRepresentation.");
        return null;
    }

    @Override
    public String getKeyPath() {
        TaxiLoyDebug.d(this.getClass().toString() + " Needs to implement Key Path.");
        return null;
    }

}

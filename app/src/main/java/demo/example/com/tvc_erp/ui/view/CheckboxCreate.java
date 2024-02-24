package demo.example.com.tvc_erp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import demo.example.com.tvc_erp.R;

/**
 * Created by prosoft on 10/20/16.
 */

public class CheckboxCreate extends Button {

    Context context;
    boolean isCheck;
    public CheckboxCreate(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
    }

    public CheckboxCreate(Context context) {
        super(context);
    }


    public CheckboxCreate(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isCheck(){
        return isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
        changeBackground();
    }

    private void changeBackground() {
        setBackgroundResource(isCheck ? R.drawable.icon_send_email : R.drawable.icon_unckeck_mail);
    }
}

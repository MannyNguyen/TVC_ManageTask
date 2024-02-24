package demo.example.com.tvc_erp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import demo.example.com.tvc_erp.R;

/**
 * Created by prosoft on 9/15/16.
 */
public class CheckboxLogin extends Button{

    Context context;
    boolean isCheck;
    public CheckboxLogin(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
    }

    public CheckboxLogin(Context context) {
        super(context);
    }


    public CheckboxLogin(Context context, AttributeSet attrs, int defStyle) {
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
        setBackgroundResource(isCheck ? R.drawable.icon_remember_check : R.drawable.icon_remember);
    }
}

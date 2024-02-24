package demo.example.com.tvc_erp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.activity.BaseActivity;

/**
 * Created by prosoft on 10/11/16.
 */

public class FilterView extends FrameLayout implements View.OnClickListener{

    private BaseActivity context;

    public FilterView(Context context) {
        super(context);
        if (!isInEditMode()) {
            this.context = (BaseActivity) context;
            //loadViews();
        }
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            this.context = (BaseActivity) context;
            LayoutInflater inflater = LayoutInflater.from(context);
            inflater.inflate(R.layout.view_filter_task, this);
            //loadViews();
        }

    }

    @Override
    public void onClick(View view) {

    }
}

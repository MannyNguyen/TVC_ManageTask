package demo.example.com.tvc_erp.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import demo.example.com.tvc_erp.R;

/**
 * Created by mgod on 5/27/15.
 * <p>
 * Simple custom view example to show how to get selected events from the token
 * view. See ContactsCompletionView and contact_token.xml for usage
 */
public class TokenTextView extends TextView {
    private Context context;

    public TokenTextView(Context context) {
        super(context);
        this.context = context;
    }

    public TokenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        Drawable img;
        Resources res = getResources();
        img = ContextCompat.getDrawable(context, R.drawable.icon_remove);
        //You need to setBounds before setCompoundDrawables , or it couldn't display
        img.setBounds(0, 0, 100, 100);
        setCompoundDrawables(null, null, selected ? img : null, null);
    }
}

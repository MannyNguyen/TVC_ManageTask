package demo.example.com.tvc_erp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tokenautocomplete.TokenCompleteTextView;

import demo.example.com.tvc_erp.R;

/**
 * Created by prosoft on 10/19/16.
 */

public class ContactsCompletionView extends TokenCompleteTextView<String> {

    public ContactsCompletionView(Context context) {
        super(context);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(String name) {
        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TokenTextView token = (TokenTextView) l.inflate(R.layout.contact_token, (ViewGroup) getParent(), false);
        token.setText(name);
        return token;
    }

    @Override
    protected String defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return "";
        } else {
            return completionText;
        }
    }

    @Override
    protected void removeListeners() {
        super.removeListeners();
    }
}

package demo.example.com.tvc_erp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.util.regex.Pattern;

import demo.example.com.tvc_erp.GetCookieTheard;
import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.asynctask.GetListTaskAsynctask;
import demo.example.com.tvc_erp.asynctask.LoginAsynctask;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.objects.UserInfo;
import demo.example.com.tvc_erp.ui.view.CheckboxLogin;
import demo.example.com.tvc_erp.utils.SystemUtils;

/**
 * Created by prosoft on 12/16/15.
 */
public class SplashScreenActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private LinearLayout field_name, field_pass, field_code, tv_signup, field_remember;
    private ImageView img_logo, img_code;
    private EditText edt_email, edt_password, edt_code;
    private Button btn_login, btn_signup;
    private boolean isFirstClick = true;
    private CheckboxLogin chk_remember;
    private UserInfo userInfo;
    private TCVApplication application;
    private LoginAsynctask loginAsyncTask;

    static final String POST_URL = "http://tbi.tavicosoft.com/DirectRouter/Index";
    static final String HOST = "http://tbi.tavicosoft.com";
    static final String SEC_IMG_LINK = "/captcha/getimage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        application = (TCVApplication) getApplicationContext();
        CookieHandler.setDefault(application.msCookieManager);
        new Thread(new GetCookieTheard(this)).start();

//        layout = (LinearLayout)findViewById(R.id.ll_bottom_selection);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        field_name = (LinearLayout) findViewById(R.id.field_name);
        field_pass = (LinearLayout) findViewById(R.id.field_pass);
        field_code = (LinearLayout) findViewById(R.id.field_code);
        tv_signup = (LinearLayout) findViewById(R.id.tv_signup);
        field_remember = (LinearLayout) findViewById(R.id.field_remember);
        img_code = (ImageView) findViewById(R.id.img_code);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_code = (EditText) findViewById(R.id.edt_code);
        chk_remember = (CheckboxLogin) findViewById(R.id.chk_remember);

        if (SystemUtils.getRememberMe(this)) {
            chk_remember.setIsCheck(true);
            userInfo = SystemUtils.getUserInfo(this);
            if (userInfo != null) {
                edt_email.setText(userInfo.getEmail().toString());
                edt_password.setText(userInfo.getPassword().toString());
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //set animation
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_login);
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_login);

                if (btn_login.getVisibility() == View.INVISIBLE) {
                    btn_login.startAnimation(slideUp);
                    btn_login.setVisibility(View.VISIBLE);

                }

                if (btn_signup.getVisibility() == View.INVISIBLE) {
                    btn_signup.startAnimation(slideUp);
                    btn_signup.setVisibility(View.VISIBLE);
                }


            }
        }, SPLASH_DISPLAY_LENGTH);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstClick) {
                    LoadAnimation();
                } else {
                    LoadAsynctask();
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chk_remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_remember.isCheck()) {
                    chk_remember.setIsCheck(false);
                } else {
                    chk_remember.setIsCheck(true);
                }
            }
        });


    }

    private void LoadAsynctask() {
        if (!isFirstClick) {
            String oldSessionId = SystemUtils.getSessionId(this);
            String email = edt_email.getText().toString();
            String password = edt_password.getText().toString();
            String code = edt_code.getText().toString();
            userInfo = new UserInfo(email, password);
            UserInfo userTemp = SystemUtils.getUserInfo(this);

            String USERNAME_PATTERN = "^[a-z0-9@._+-]{6,30}$";
            Pattern pattern = Pattern.compile(USERNAME_PATTERN);

            if (email.isEmpty()) {
                edt_email.setError(getResources().getString(R.string.error_user_name_require));
                return;
            }
//            else if (!pattern.matcher(email).matches()) {
//                edt_email.setError(getResources().getString(R.string.error_user_name_validate));
//                return;
//            }
            else {
                edt_email.setError(null);
            }


            if (password.isEmpty()) {
                edt_password.setError(getResources().getString(R.string.error_password_require));
                return;
            } else {
                edt_password.setError(null);
            }

            if (code.isEmpty()) {
                edt_code.setError(getResources().getString(R.string.error_code_require));
                return;
            } else {
                edt_code.setError(null);
            }

            getBusyIndicator(R.string.login).show();
            if (oldSessionId == null || !email.equalsIgnoreCase((userTemp.getEmail().toString()))) {
                Model.getInstance().callAPILoginPasserger(userInfo, code, null);
            } else if (oldSessionId != null) {
                Model.getInstance().callAPILoginPasserger(userInfo, code, oldSessionId);
            }
        }
    }

    private void LoadAnimation() {
        if (isFirstClick) {
            Animation zoom_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_logo);
            Animation slide_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_login);
            Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_login);
            img_logo.startAnimation(zoom_out);
            field_name.startAnimation(slide_right);
            field_name.setVisibility(View.VISIBLE);
            field_pass.startAnimation(slide_right);
            field_pass.setVisibility(View.VISIBLE);
            field_code.startAnimation(slide_right);
            field_code.setVisibility(View.VISIBLE);
            tv_signup.startAnimation(slide_right);
            tv_signup.setVisibility(View.VISIBLE);
            btn_signup.startAnimation(slide_left);
            field_remember.setVisibility(View.VISIBLE);
            btn_signup.setVisibility(View.INVISIBLE);
            field_remember.startAnimation(slide_right);

            Picasso.with(SplashScreenActivity.this).load(HOST + SEC_IMG_LINK).memoryPolicy(MemoryPolicy.NO_CACHE).into(img_code);
            isFirstClick = false;
        }
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        getBusyIndicator(R.string.login).dismiss();
        if (evt == ModelEvent.LOGIN_FAIL) {
            showErrorMessage(R.string.login_fail, R.string.message_login_fail, new Runnable() {
                @Override
                public void run() {
                }
            }).show();
        } else if (evt == ModelEvent.LOGIN_SUCCESS) {
            if (chk_remember.isCheck()) {
                SystemUtils.saveRememberMe(true, this);
            }
            SystemUtils.saveUserInfo(userInfo, this);
            SystemUtils.saveSessionId(Model.getInstance().getLoginResponse().result.sessionId, this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (evt == ModelEvent.LOGIN_ALREADY) {
            showErrorMessage(R.string.login_fail, R.string.message_login_already, new Runnable() {
                @Override
                public void run() {
                }
            }).show();
        }
    }

}


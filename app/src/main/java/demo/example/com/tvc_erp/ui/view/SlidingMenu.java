package demo.example.com.tvc_erp.ui.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.activity.BaseActivity;
import demo.example.com.tvc_erp.ui.objects.CurrentUserInfo;
import demo.example.com.tvc_erp.utils.Config;

/**
 * Created by prosoft on 12/24/15.
 */
public class SlidingMenu extends FrameLayout {

    RelativeLayout rl_list_task;
    RelativeLayout rl_language;
    RelativeLayout rl_account_field;
    TextView txt_name;
    CirleImageView img_avatar;
    BaseActivity context;
    DrawerLayout drawerLayout;

    public SlidingMenu(Context context) {
        super(context);
        if (!isInEditMode()) {
            this.context = (BaseActivity) context;
            loadViews();
        }
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            this.context = (BaseActivity) context;
            LayoutInflater inflater = LayoutInflater.from(context);
            inflater.inflate(R.layout.slidingmenu_left_list, this);
            loadViews();
        }

    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    private void loadViews() {
        rl_account_field = (RelativeLayout) findViewById(R.id.rl_account_field);
        rl_language = (RelativeLayout) findViewById(R.id.rl_language);
        rl_list_task = (RelativeLayout) findViewById(R.id.rl_list_task);
        img_avatar = (CirleImageView) findViewById(R.id.img_avatar);

        txt_name = (TextView) findViewById(R.id.txt_name);

        rl_account_field.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_language.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_list_task.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void showInfo() {
        CurrentUserInfo currentUserInfo = Model.getInstance().getCurrentUserInfo();
        txt_name.setText(currentUserInfo.getUserName());

        String url_avatar = Config.BASEURL + String.format(Config.API_GET_IMAGE_AVATAR, String.valueOf(currentUserInfo.getEmployee()));

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(currentUserInfo.getUserName());
        String firstLetter = String.valueOf(currentUserInfo.getUserName().charAt(0));
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .width(80)
                .height(80)
                .withBorder(4)
                .toUpperCase()
                .endConfig()
                .round();

        img_avatar.setBorderColor(color);
        TextDrawable drawable = builder.build(firstLetter, color);

        Glide.with(context)
                .load(url_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .placeholder(drawable)
                .into(img_avatar);

    }
}

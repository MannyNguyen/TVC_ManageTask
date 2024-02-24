package demo.example.com.tvc_erp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.URI;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.api.object_response_api.GetCommentTaskResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetListResponse;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.adapter.DetailAdapter;
import demo.example.com.tvc_erp.ui.adapter.WorkTaskAdapter;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.objects.Task;
import demo.example.com.tvc_erp.ui.view.CirleImageView;
import demo.example.com.tvc_erp.utils.Config;
import demo.example.com.tvc_erp.utils.FileHelper;
import demo.example.com.tvc_erp.utils.GlobalConfig;
import demo.example.com.tvc_erp.utils.ImagePickerHelper;
import demo.example.com.tvc_erp.utils.SystemUtils;

/**
 * Created by prosoft on 10/4/16.
 */

public class DetailActivity extends BaseActivity {
    private DetailAdapter detailAdapter;
    private Task task;
    private RecyclerView recyTask;

    private ImagePickerHelper pickerHelper;
    private final int PICK_IMAGE_LOGO = 111;

    private Button btn_attachment, btn_send;
    private ImageView img_attachment;
    private RelativeLayout rl_attachment, rl_box_comment;
    private Button btn_del_attachment;
    private EditText edt_comment;
    private int photoSize;
    private Uri currentURI;
    private RelativeLayout rl_edit;
    private EditText edt_edit;
    private CirleImageView img_avatar;
    private ImageView img_edit_attachment;
    private Button btn_update, btn_cancel;
    private boolean isEditView = false;
    private GetCommentTaskResponse.Data comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Window window = getWindow();
        //Regulations android version
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditView) {
                    hideFilterView();
                } else {
                    finish();
                }
            }
        });
        task = (Task) getIntent().getSerializableExtra("task");
        Model.getInstance().getCommentOfTask(task.getRecordID());
        getBusyIndicator(R.string.please_wait).show();
        addControl();
    }


    private void addControl() {

        detailAdapter = new DetailAdapter(this, task, Model.getInstance().getCommentTasks());

        recyTask = (RecyclerView) findViewById(R.id.re_view_detail);
        btn_attachment = (Button) findViewById(R.id.btn_attachment);
        btn_send = (Button) findViewById(R.id.btn_send);
        img_attachment = (ImageView) findViewById(R.id.img_attachment);
        btn_del_attachment = (Button) findViewById(R.id.btn_del_attachment);
        rl_attachment = (RelativeLayout) findViewById(R.id.rl_attachment);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);
        edt_edit = (EditText) findViewById(R.id.edt_edit);
        img_avatar = (CirleImageView) findViewById(R.id.img_avatar);
        img_edit_attachment = (ImageView) findViewById(R.id.img_edit_attachment);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        rl_box_comment = (RelativeLayout) findViewById(R.id.rl_box_comment);
        recyTask.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyTask.setLayoutManager(mLayoutManager);
        recyTask.setItemAnimator(new DefaultItemAnimator());

        recyTask.setAdapter(detailAdapter);

        btn_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerHelper = new ImagePickerHelper(DetailActivity.this);
                showPickImage(PICK_IMAGE_LOGO);
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = edt_comment.getText().toString();
                edt_comment.setText("");
                if (mess.isEmpty()) {
                    return;
                }
                getBusyIndicator(R.string.please_wait).show();
                if (currentURI != null) {
                    byte[] data = FileHelper.getByteDataFromUri(DetailActivity.this, currentURI, 1000, 1000);
                    Model.getInstance().callAddComment(mess, task.getRecordID(), data);
                } else {
                    byte[] emptyArray = new byte[0];
                    Model.getInstance().callAddComment(mess, task.getRecordID(), emptyArray);
                }
            }
        });

        btn_del_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_attachment.setVisibility(View.GONE);
                currentURI = null;
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateComment();
                rl_edit.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rl_edit.getWindowToken(), 0);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFilterView();
            }
        });
    }


    private void showPickImage(int id) {
        Intent pickerIntent = pickerHelper.getPickIntent();
        startActivityForResult(pickerIntent, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_LOGO && resultCode == RESULT_OK) {
            Uri[] uris = pickerHelper.getUrisImageAfterPick(data);
            for (Uri uri : uris) {
                photoSize += FileHelper.getSize(DetailActivity.this, uri);
                if (photoSize > GlobalConfig.MAX_FILE_SIZE) {
                    break;
                } else if (!FileHelper.checkImageType(DetailActivity.this, uri)) {
                    break;
                } else {

                }

            }

            if (uris != null && uris.length > 0) {
                currentURI = FileHelper.getUriAuthority(DetailActivity.this, uris[0]);

                rl_attachment.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(uris[0])
                        .centerCrop()
                        .placeholder(R.color.gray_light_main)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_attachment);

            }

        }
    }

    @Override
    public void onBackPressed() {

        if (isEditView) {
            hideFilterView();
        } else {
            finish();
        }

    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GET_DETAIL_ATTACHMENT_SUCCESS) {
            String url = Config.BASEURL + String.format(Config.API_GET_URL_ATTACHMENT, Model.getInstance().getAttachments().get(0).getDocumentcode());
            Model.getInstance().downloadImageURL(url, Model.getInstance().getAttachments().get(0).getFilename());
        } else if (evt == ModelEvent.DOWNLOAD_IMAGE_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            Bitmap img = BitmapFactory.decodeByteArray(Model.getInstance().getImage(), 0, Model.getInstance().getImage().length);
            if (!isEditView) {
                showImageDialog(img);
            } else {
                img_edit_attachment.setImageBitmap(img);
            }
        } else if (evt == ModelEvent.ADD_MESSAGE_SUCCESS) {
//            getBusyIndicator(R.string.please_wait).dismiss();
            Toast.makeText(this, "Send message success", Toast.LENGTH_SHORT).show();
            Model.getInstance().getCommentOfTask(task.getRecordID());
        } else if (evt == ModelEvent.DELETE_MESSAGE_SUCCESS) {
//            getBusyIndicator(R.string.please_wait).dismiss();
            Toast.makeText(this, "Delete message success", Toast.LENGTH_SHORT).show();
            Model.getInstance().getCommentOfTask(task.getRecordID());
        } else if (evt == ModelEvent.GET_COMMENT_TASK_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            detailAdapter.notifyDataSetChanged();
        } else if (evt == ModelEvent.UPDATE_MESSAGE_SUCCESS) {
            Toast.makeText(this, "Update message success", Toast.LENGTH_SHORT).show();
            Model.getInstance().getCommentOfTask(task.getRecordID());
        }
    }

    public void showEditView(GetCommentTaskResponse.Data comment) {
        isEditView = true;
        this.comment = comment;
        String url_avatar = Config.BASEURL + String.format(Config.API_GET_IMAGE_AVATAR, String.valueOf(comment.employee));
        if (comment.attachCount > 0) {
            Model.getInstance().getDetailAttachment(task.getRecordID(), String.valueOf(comment.linenum));
        }
        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.bottom_up);
        rl_edit.startAnimation(bottomUp);
        rl_edit.setVisibility(View.VISIBLE);
        edt_edit.setText(comment.comments);

        String firstLetter = "A";
        if (!comment.title.equalsIgnoreCase("") && comment.title != null) {
            firstLetter = String.valueOf(comment.title.charAt(0));
        }
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(comment.title);

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
        Glide.with(this)
                .load(url_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .placeholder(drawable)
                .into(img_avatar);

    }

    private void hideFilterView() {
        isEditView = false;
        Animation bottomDown = AnimationUtils.loadAnimation(this,
                R.anim.bottom_down);
        rl_edit.startAnimation(bottomDown);
        rl_edit.setVisibility(View.GONE);
    }

    private void updateComment() {
        String comment = edt_edit.getText().toString();
        if (comment.isEmpty()) {
            return;
        }
        getBusyIndicator(R.string.please_wait).show();
        Model.getInstance().updateCommentOfTask(task.getRecordID(), String.valueOf(this.comment.linenum), comment);
    }
}

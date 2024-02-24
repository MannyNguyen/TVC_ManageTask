package demo.example.com.tvc_erp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.Arrays;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.adapter.ImagesAdapter;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.objects.FormTask;
import demo.example.com.tvc_erp.ui.objects.Task;
import demo.example.com.tvc_erp.utils.FileHelper;
import demo.example.com.tvc_erp.utils.GlobalConfig;
import demo.example.com.tvc_erp.utils.ImagePickerHelper;

public class CreateNewsActivity extends BaseActivity {

    private EditText edt_news_title, edt_news_taskmaster, edt_news_description;
    private LinearLayout ll_attach;
    private ImagePickerHelper pickerHelper;
    private final int PICK_IMAGE_LOGO = 111;
    private int photoSize;
    private ArrayList<Uri> photos = new ArrayList<>();
    private ImagesAdapter imageAdapter;
    private RecyclerView rw_images;
    private Button btn_post, btn_cancel;
    private String[] accessGroups;
    private ArrayAdapter<String> adapter;
    private String dagid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        addControl();
        addEvent();
    }

    private void addControl() {

        edt_news_title = (EditText) findViewById(R.id.edt_news_title);
        edt_news_taskmaster = (EditText) findViewById(R.id.edt_news_taskmaster);
        edt_news_taskmaster.setInputType(InputType.TYPE_NULL);
        edt_news_description = (EditText) findViewById(R.id.edt_news_description);
        ll_attach = (LinearLayout) findViewById(R.id.ll_attach);
        rw_images = (RecyclerView) findViewById(R.id.rw_images);
        btn_post = (Button) findViewById(R.id.btn_news_post);
        btn_cancel = (Button) findViewById(R.id.btn_news_cancel);
    }

    private void addEvent() {
        getBusyIndicator(R.string.please_wait).show();
        Model.getInstance().getAccessGroup();

        ll_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Caution ImagePickerHelper to attachment
                pickerHelper = new ImagePickerHelper(CreateNewsActivity.this);
                showPickImage(PICK_IMAGE_LOGO);
            }
        });

        edt_news_taskmaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListUserDialog(accessGroups, "Select a group", new Runnable() {
                    @Override
                    public void run() {

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(accessGroups).get(which);

                        edt_news_taskmaster.setText(selectedText);
                        dagid = Model.getInstance().getAccessGroups().get(which).dagid;
                    }
                }).show();
            }
        });


        edt_news_taskmaster.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showListUserDialog(accessGroups, "Select a group", new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedText = Arrays.asList(accessGroups).get(which);

                            edt_news_taskmaster.setText(selectedText);
                            dagid = Model.getInstance().getAccessGroups().get(which).dagid;
                        }
                    }).show();
                }
            }
        });

        imageAdapter = new ImagesAdapter(this, photos, new ImagesAdapter.IPhotoAction() {
            @Override
            public void didDeletePicture(final int index) {

                deletePropertyImage(index);

            }

            @Override
            public void didSelectAPicture(int index) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNews();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            rw_images.setHasFixedSize(false);
//        rvImages.addItemDecoration(new SuggestedPropertiesAdapter.SpacesItemDecoration(20));

        rw_images.setAdapter(imageAdapter);
        rw_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rw_images.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GET_ACCESS_GROUP_SUCCESS) {
            accessGroups = new String[Model.getInstance().getAccessGroups().size()];
            for (int m = 0; m < Model.getInstance().getAccessGroups().size(); m++) {
                accessGroups[m] = Model.getInstance().getAccessGroups().get(m).name;
            }
            edt_news_taskmaster.setText(Model.getInstance().getAccessGroups().get(0).name);
            dagid = Model.getInstance().getAccessGroups().get(0).dagid;
            getBusyIndicator(R.string.please_wait).dismiss();
        } else if (evt == ModelEvent.POST_TASK_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            Toast.makeText(this, "Create news is successful", Toast.LENGTH_SHORT).show();
        }
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
                photoSize += FileHelper.getSize(CreateNewsActivity.this, uri);
                if (photoSize > GlobalConfig.MAX_FILE_SIZE) {
                    showErrorMessage(R.string.error_limit_size_multi_photo, R.string.ok, new Runnable() {
                        @Override
                        public void run() {
                        }
                    }).show();
                    break;
                } else if (!FileHelper.checkImageType(CreateNewsActivity.this, uri)) {
                    showErrorMessage(R.string.error_limit_type_photo, R.string.ok, new Runnable() {
                        @Override
                        public void run() {
                        }
                    }).show();
                    break;
                } else {
                    photos.add(uri);
                }

            }

            imageAdapter.selectedPosition = photos.size() - 1;
            imageAdapter.notifyDataSetChanged();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    rw_images.smoothScrollToPosition(photos.size());
                }
            }, 1000);

        }
    }

    private void postNews() {
        FormTask formTask = new FormTask();
        String title = edt_news_title.getText().toString();
        if (title.isEmpty()) {
            edt_news_title.setError(getResources().getString(R.string.title_require));
            return;
        } else {
            edt_news_title.setError(null);
        }

        String taskMaster = edt_news_taskmaster.getText().toString();

        String personCharge = "";
        String cc = "";
        String startDate = "";
        String dueDate = "";
        String endDate = "";
        String jobType = "";
        String jobPrioriy = "";
        String status = "O";
        String description = edt_news_description.getText().toString();
        if (description.isEmpty()) {
            edt_news_description.setError(getResources().getString(R.string.description_require));
            return;
        }

        String email = "N";
        ArrayList imageByteArray = new ArrayList();
        for (int i = 0; i < photos.size(); i++) {
            byte[] data = FileHelper.getByteDataFromUri(this, photos.get(i), 1000, 1000);
            imageByteArray.add(data);
        }

        formTask.setTitle(title);
        formTask.setTaskMaster(taskMaster);
        formTask.setPersonCharge(personCharge);
        formTask.setCc(cc);
        formTask.setStartDate(startDate);
        formTask.setDueDate(dueDate);
        formTask.setEndDate(endDate);
        formTask.setJobType(jobType);
        formTask.setJobPrioriy(jobPrioriy);
        formTask.setStatus(status);
        formTask.setDescription(description);
        formTask.setEmail(email);
        formTask.setImageByteArray(imageByteArray);
        formTask.setDagid(dagid);
        formTask.setAnal_wlt0("");
        formTask.setAnal_wlt1("");

        Model.getInstance().postCreateTask(formTask, "N");
        getBusyIndicator(R.string.please_wait).show();
    }

    private void deletePropertyImage(final int index) {

        Uri uri = photos.get(index);
        photoSize -= FileHelper.getSize(CreateNewsActivity.this, uri);
        imageAdapter.photos.remove(index);
        imageAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        finish();

    }
}

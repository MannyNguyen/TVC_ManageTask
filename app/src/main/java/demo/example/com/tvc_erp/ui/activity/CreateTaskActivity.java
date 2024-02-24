package demo.example.com.tvc_erp.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tokenautocomplete.TokenCompleteTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import com.jaredrummler.materialspinner.MaterialSpinner;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.adapter.ImagesAdapter;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.objects.FormTask;
import demo.example.com.tvc_erp.ui.objects.Task;
import demo.example.com.tvc_erp.ui.view.CheckboxCreate;
import demo.example.com.tvc_erp.ui.view.ContactsCompletionView;
import demo.example.com.tvc_erp.utils.FileHelper;
import demo.example.com.tvc_erp.utils.GlobalConfig;
import demo.example.com.tvc_erp.utils.ImagePickerHelper;

public class CreateTaskActivity extends BaseActivity implements TokenCompleteTextView.TokenListener<String> {

    private ContactsCompletionView completionView;
    ArrayAdapter<String> adapter;
    private ImagesAdapter imageAdapter;

    private ArrayList<String> seletedItems = new ArrayList<>();
    TabHost thCreateTask;
    private String[] types, priorities, users, buildings, apartments;
    private ArrayList<String> listUsers = new ArrayList<>();
    private String userCC;
    private ArrayList<String> listUserToRemove = new ArrayList<>();
    private EditText edt_taskmaster, edt_person_in_charge, edt_start_date, edt_due_date, edt_end_date, edt_status, edt_description, edt_title;
    private Button btn_add_cc;
    private Calendar dobCalendar = Calendar.getInstance();
    private MaterialSpinner spinner_job_style, spinner_job_priority, spinner_apartment, spinner_building;
    private ImagePickerHelper pickerHelper;
    private LinearLayout ll_attach;
    private final int PICK_IMAGE_LOGO = 111;
    private RecyclerView rw_images;
    private ArrayList<Uri> photos = new ArrayList<>();
    private int selectedPhotoIndex = 0;
    private int photoSize;
    private CheckboxCreate chk_send_email;
    private Button btn_post, btn_cancel;
    private Task task;
    private boolean isEdit;
    private boolean isSubtask;
    private int numGetData = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        addControl();
        addEvent();
    }

    private void addEvent() {
        getBusyIndicator(R.string.please_wait).show();
        Model.getInstance().getListUsers();
        Model.getInstance().getJobType();
        Model.getInstance().getJobPriority();
        Model.getInstance().getListBuiding();

        task = (Task) getIntent().getSerializableExtra("task");
        isSubtask = getIntent().getBooleanExtra("isSubtask", false);
        if (task != null) {
            isEdit = true;
            if (task.getStatus().equalsIgnoreCase("O")) {
                edt_status.setText("O-Open");
            } else if (task.getStatus().equalsIgnoreCase("P")) {
                edt_status.setText("P-Accept");
            } else if (task.getStatus().equalsIgnoreCase("D")) {
                edt_status.setText("D-Done");
            }

            edt_title.setText(task.getTitle());
            edt_taskmaster.setText(task.getTaskMaster());
            edt_person_in_charge.setText(task.getPic());

            edt_start_date.setText(task.getStartDate());
            edt_due_date.setText(task.getDueDate());
            edt_end_date.setText(task.getEndDate());

            for (int i = 0; i < Model.getInstance().getTypes().size(); i++) {
                if (Model.getInstance().getTypes().get(i).code.equalsIgnoreCase(task.getJobType())) {
                    spinner_job_style.setSelectedIndex(i);
                }
            }

            for (int i = 0; i < Model.getInstance().getPriorities().size(); i++) {
                if (Model.getInstance().getPriorities().get(i).code.equalsIgnoreCase(task.getPriorityl())) {
                    spinner_job_priority.setSelectedIndex(i);
                }
            }
            edt_description.setText(task.getDetail());
        } else {
            edt_status.setText("O-Open");
            edt_taskmaster.setText(Model.getInstance().getLoginResponse().result.data.employee);
        }

        edt_person_in_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> listTemp = new ArrayList<String>();
                for (int m = 0; m < listUsers.size(); m++) {
                    boolean isChosed = false;
                    for (int n = 0; n < listUserToRemove.size(); n++) {
                        if (listUserToRemove.get(n).equalsIgnoreCase(listUsers.get(m))) {
                            isChosed = true;
                            break;
                        }

                    }
                    if (!isChosed) {
                        listTemp.add(listUsers.get(m));
                    }

                }
                users = new String[listTemp.size()];
                for (int m = 0; m < listTemp.size(); m++) {
                    users[m] = listTemp.get(m);
                }

                showListUserDialog(users, "Select a user", new Runnable() {
                    @Override
                    public void run() {

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (userCC != null) {
                            listUserToRemove.remove(userCC);
                        }
                        userCC = Arrays.asList(users).get(which);
                        listUserToRemove.add(userCC);
                        edt_person_in_charge.setText(userCC);
                    }
                }).show();
            }
        });

        edt_person_in_charge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ArrayList<String> listTemp = new ArrayList<String>();
                    for (int m = 0; m < listUsers.size(); m++) {
                        boolean isChosed = false;
                        for (int n = 0; n < listUserToRemove.size(); n++) {
                            if (listUserToRemove.get(n).equalsIgnoreCase(listUsers.get(m))) {
                                isChosed = true;
                                break;
                            }

                        }
                        if (!isChosed) {
                            listTemp.add(listUsers.get(m));
                        }

                    }
                    users = new String[listTemp.size()];
                    for (int m = 0; m < listTemp.size(); m++) {
                        users[m] = listTemp.get(m);
                    }

                    showListUserDialog(users, "Select a user", new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (userCC != null) {
                                listUserToRemove.remove(userCC);
                            }
                            userCC = Arrays.asList(users).get(which);
                            listUserToRemove.add(userCC);
                            edt_person_in_charge.setText(userCC);
                        }
                    }).show();
                }
            }
        });

        btn_add_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listTemp = new ArrayList<>();
                for (int m = 0; m < listUsers.size(); m++) {
                    boolean isChosed = false;
                    for (int n = 0; n < listUserToRemove.size(); n++) {
                        if (listUserToRemove.get(n).equalsIgnoreCase(listUsers.get(m))) {
                            isChosed = true;
                            break;
                        }

                    }

                    if (!isChosed) {
                        listTemp.add(listUsers.get(m));
                    }
                }
                users = new String[listTemp.size()];
                for (int m = 0; m < listTemp.size(); m++) {
                    users[m] = listTemp.get(m);
                }
                showListUserDialogMultiChoice(users, "Select a user", new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < seletedItems.size(); i++) {
                            String user = seletedItems.get(i);
                            boolean isAddAlready = false;
                            for (int j = 0; j < listUserToRemove.size(); j++) {
                                if (listUserToRemove.get(j).equalsIgnoreCase(user)) {
                                    isAddAlready = true;
                                    break;
                                }
                            }
                            if (!isAddAlready) {
                                completionView.addObject(user);
                                listUserToRemove.add(user);
                            }
                        }

                    }
                }, new Runnable() {
                    @Override
                    public void run() {

                    }
                }, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            if (!seletedItems.contains(users[indexSelected])) {
                                seletedItems.add(users[indexSelected]);
                            }

                        } else if (seletedItems.contains(users[indexSelected])) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(users[indexSelected]);
                        }
                    }
                }).show();

            }
        });

        edt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        updateDob(year, monthOfYear, dayOfMonth, edt_start_date);
                    }
                }, dobCalendar
                        .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                        dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edt_start_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(CreateTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            updateDob(year, monthOfYear, dayOfMonth, edt_start_date);
                        }
                    }, dobCalendar
                            .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                            dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        edt_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        updateDob(year, monthOfYear, dayOfMonth, edt_due_date);
                    }
                }, dobCalendar
                        .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                        dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edt_due_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(CreateTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            updateDob(year, monthOfYear, dayOfMonth, edt_due_date);
                        }
                    }, dobCalendar
                            .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                            dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        edt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        updateDob(year, monthOfYear, dayOfMonth, edt_end_date);
                    }
                }, dobCalendar
                        .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                        dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edt_end_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(CreateTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            updateDob(year, monthOfYear, dayOfMonth, edt_end_date);
                        }
                    }, dobCalendar
                            .get(Calendar.YEAR), dobCalendar.get(Calendar.MONTH),
                            dobCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        ll_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerHelper = new ImagePickerHelper(CreateTaskActivity.this);
                showPickImage(PICK_IMAGE_LOGO);
            }
        });

        chk_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_send_email.isCheck()) {
                    chk_send_email.setIsCheck(false);
                } else {
                    chk_send_email.setIsCheck(true);
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
        spinner_building.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                String block = Model.getInstance().getBuildings().get(position).block;
                Model.getInstance().getListAparment(block);
                getBusyIndicator(R.string.please_wait).show();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isEdit) {
                    postTask();
                } else {
                    updateTask();
                }
            }
        });

        rw_images.setHasFixedSize(false);
//        rvImages.addItemDecoration(new SuggestedPropertiesAdapter.SpacesItemDecoration(20));

        rw_images.setAdapter(imageAdapter);
        rw_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rw_images.setItemAnimator(new DefaultItemAnimator());


    }

    private void addControl() {
        thCreateTask = (TabHost) findViewById(R.id.thCreateTask);
        completionView = (ContactsCompletionView) findViewById(R.id.searchView);
        completionView.setKeyListener(null);

        edt_person_in_charge = (EditText) findViewById(R.id.edt_person_in_charge);
        edt_person_in_charge.setInputType(InputType.TYPE_NULL);
        edt_taskmaster = (EditText) findViewById(R.id.edt_taskmaster);
        edt_taskmaster.setInputType(InputType.TYPE_NULL);
        edt_start_date = (EditText) findViewById(R.id.edt_start_date);
        edt_start_date.setInputType(InputType.TYPE_NULL);
        edt_due_date = (EditText) findViewById(R.id.edt_due_date);
        edt_due_date.setInputType(InputType.TYPE_NULL);
        edt_end_date = (EditText) findViewById(R.id.edt_end_date);
        edt_end_date.setInputType(InputType.TYPE_NULL);
        edt_status = (EditText) findViewById(R.id.edt_status);
        edt_status.setInputType(InputType.TYPE_NULL);
        edt_description = (EditText) findViewById(R.id.edt_description);
        edt_title = (EditText) findViewById(R.id.edt_title);

        btn_add_cc = (Button) findViewById(R.id.btn_add_cc);
        spinner_job_style = (MaterialSpinner) findViewById(R.id.spinner_job_style);
        spinner_job_priority = (MaterialSpinner) findViewById(R.id.spinner_job_priority);
        spinner_apartment = (MaterialSpinner) findViewById(R.id.spinner_apartment);
        spinner_building = (MaterialSpinner) findViewById(R.id.spinner_building);
        ll_attach = (LinearLayout) findViewById(R.id.ll_attach);
        rw_images = (RecyclerView) findViewById(R.id.rw_images);
        chk_send_email = (CheckboxCreate) findViewById(R.id.chk_send_email);
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        setupTab(new TextView(this), getString(R.string.information));
        setupRightTab(new TextView(this), getString(R.string.conclusion));


    }

    private void setupTab(final View view, final String tag) {

        thCreateTask.setup();

        View tabview = createTabView(thCreateTask.getContext(), tag);
        TabHost.TabSpec setContent = thCreateTask.newTabSpec(tag).setIndicator(tabview)
                .setContent(new TabHost.TabContentFactory() {
                    public View createTabContent(String tag) {
                        return view;
                    }
                });
        setContent.setContent(R.id.llnInfo);
        thCreateTask.addTab(setContent);

    }

    private void setupRightTab(final View view, final String tag) {
        thCreateTask.setup();

        View tabRightView = createRightTabView(thCreateTask.getContext(), tag);
        TabHost.TabSpec setRightContent = thCreateTask.newTabSpec(tag).setIndicator(tabRightView)
                .setContent(new TabHost.TabContentFactory() {
                    @Override
                    public View createTabContent(String tag) {
                        return view;
                    }
                });
        setRightContent.setContent(R.id.llConclusion);
        thCreateTask.addTab(setRightContent);
    }

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }

    private static View createRightTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_right_bg, null);
        TextView trv = (TextView) view.findViewById(R.id.tabsRightText);
        trv.setText(text);
        return view;
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GET_LIST_USERS_SUCCESS) {

            for (int i = 0; i < Model.getInstance().getUsers().size(); i++) {
                if (!Model.getInstance().getUsers().get(i).employee.equalsIgnoreCase(Model.getInstance().getLoginResponse().result.data.employee)) {
                    listUsers.add(Model.getInstance().getUsers().get(i).employee);
                }
            }

            users = new String[listUsers.size()];
            for (int m = 0; m < listUsers.size(); m++) {
                users[m] = listUsers.get(m);

            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
            completionView.setAdapter(adapter);
            completionView.setTokenListener(this);
            completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
            numGetData++;

            if (task != null && task.getCc().size() > 0 && isEdit) {
                for (int i = 0; i < task.getCc().size(); i++) {
                    String user = task.getCc().get(i);
                    completionView.addObject(user);
                }
            }
        } else if (evt == ModelEvent.GET_LIST_JOB_TYPE_SUCCESS) {
            types = new String[Model.getInstance().getTypes().size()];
            for (int m = 0; m < Model.getInstance().getTypes().size(); m++) {
                types[m] = Model.getInstance().getTypes().get(m).name;
                spinner_job_style.setItems(types);
            }
            numGetData++;
        } else if (evt == ModelEvent.GET_LIST_JOB_PRIORITY_SUCCESS) {
            priorities = new String[Model.getInstance().getPriorities().size()];
            for (int m = 0; m < Model.getInstance().getPriorities().size(); m++) {
                priorities[m] = Model.getInstance().getPriorities().get(m).name;
            }
            spinner_job_priority.setItems(priorities);
            numGetData++;
        } else if (evt == ModelEvent.POST_TASK_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            Toast.makeText(this, "Add task is successful", Toast.LENGTH_SHORT).show();
        } else if (evt == ModelEvent.GET_BUILDINGS_SUCCESS) {
            buildings = new String[Model.getInstance().getBuildings().size()];
            for (int m = 0; m < Model.getInstance().getBuildings().size(); m++) {
                buildings[m] = Model.getInstance().getBuildings().get(m).name;
            }
            spinner_building.setSelectedIndex(0);
            spinner_building.setItems(buildings);
            String block = Model.getInstance().getBuildings().get(0).block;
            Model.getInstance().getListAparment(block);
           numGetData++;
        } else if (evt == ModelEvent.GET_APARTMENTS_SUCCESS) {
            apartments = new String[Model.getInstance().getApartments().size()];
            for (int m = 0; m < Model.getInstance().getApartments().size(); m++) {
                apartments[m] = Model.getInstance().getApartments().get(m).property;
            }
            spinner_apartment.setSelectedIndex(0);
            spinner_apartment.setItems(apartments);
            numGetData++;
        }

        if (numGetData >= 5) {
            getBusyIndicator(R.string.please_wait).dismiss();
        }
    }


    private void updateDob(int year, int monthOfYear, int dayOfMonth, final EditText editText) {
        dobCalendar.set(Calendar.YEAR, year);
        dobCalendar.set(Calendar.MONTH, monthOfYear);
        dobCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        dobCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dobCalendar.set(Calendar.MINUTE, minute);
                        String myFormat = "yyyy-MM-dd'T'HH:mm:ss";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        editText.setText(sdf.format(dobCalendar.getTime()));

                    }
                }, dobCalendar
                .get(Calendar.HOUR_OF_DAY), dobCalendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    private void showPickImage(int id) {
        Intent pickerIntent = pickerHelper.getPickIntent();
        startActivityForResult(pickerIntent, id);
    }

    @Override
    public void onTokenAdded(String token) {

    }

    @Override
    public void onTokenRemoved(String token) {
        listUserToRemove.remove(token);
        seletedItems.remove(token);
    }

    private void deletePropertyImage(final int index) {

        Uri uri = photos.get(index);
        photoSize -= FileHelper.getSize(CreateTaskActivity.this, uri);
        imageAdapter.photos.remove(index);
        imageAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_LOGO && resultCode == RESULT_OK) {
            Uri[] uris = pickerHelper.getUrisImageAfterPick(data);
            for (Uri uri : uris) {
                photoSize += FileHelper.getSize(CreateTaskActivity.this, uri);
                if (photoSize > GlobalConfig.MAX_FILE_SIZE) {
                    showErrorMessage(R.string.error_limit_size_multi_photo, R.string.ok, new Runnable() {
                        @Override
                        public void run() {
                        }
                    }).show();
                    break;
                } else if (!FileHelper.checkImageType(CreateTaskActivity.this, uri)) {
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

    private void postTask() {
        FormTask formTask = new FormTask();
        String title = edt_title.getText().toString();
        if (title.isEmpty()) {
            edt_title.setError(getResources().getString(R.string.title_require));
            return;
        } else {
            edt_title.setError(null);
        }
        String taskMaster = edt_taskmaster.getText().toString();
        if (taskMaster.isEmpty()) {
            edt_taskmaster.setError(getResources().getString(R.string.task_master_require));
            return;
        } else {
            edt_taskmaster.setError(null);
        }
        String personCharge = edt_person_in_charge.getText().toString();
        if (personCharge.isEmpty()) {
            edt_person_in_charge.setError(getResources().getString(R.string.pic_require));
            return;
        } else {
            edt_person_in_charge.setError(null);
        }

        String cc = getCCUsers();
        String startDate = edt_start_date.getText().toString();
        String dueDate = edt_due_date.getText().toString();
        String endDate = edt_end_date.getText().toString();
        String jobType = Model.getInstance().getTypes().get(spinner_job_style.getSelectedIndex()).code;
        String jobPrioriy = Model.getInstance().getPriorities().get(spinner_job_priority.getSelectedIndex()).code;
        String status = "O";
        String description = edt_description.getText().toString();
        if (description.isEmpty()) {
            edt_description.setError(getResources().getString(R.string.description_require));
            return;
        }

        String email = "N";
        String dagid = "";
        String parentId = "";
        if (isSubtask && task != null) {
            parentId = task.getRecordID();
        }
        if (chk_send_email.isCheck()) {
            email = "Y";
        }
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
        formTask.setParentId(parentId);
        String block = Model.getInstance().getBuildings().get(spinner_building.getSelectedIndex()).block;
        String apartment = Model.getInstance().getApartments().get(spinner_apartment.getSelectedIndex()).property;

        formTask.setAnal_wlt0(apartment);
        formTask.setAnal_wlt1(block);

        Model.getInstance().postCreateTask(formTask, "T");
        getBusyIndicator(R.string.please_wait).show();

        finish();
    }

    private void updateTask() {

        FormTask formTask = new FormTask();
        String title = edt_title.getText().toString();
        if (title.isEmpty()) {
            edt_title.setError(getResources().getString(R.string.title_require));
            return;
        } else {
            edt_title.setError(null);
        }
        String taskMaster = edt_taskmaster.getText().toString();
        if (taskMaster.isEmpty()) {
            edt_taskmaster.setError(getResources().getString(R.string.task_master_require));
            return;
        } else {
            edt_taskmaster.setError(null);
        }
        String personCharge = edt_person_in_charge.getText().toString();
        if (personCharge.isEmpty()) {
            edt_person_in_charge.setError(getResources().getString(R.string.pic_require));
            return;
        } else {
            edt_person_in_charge.setError(null);
        }

        String cc = getCCUsers();
        String startDate = edt_start_date.getText().toString();
        String dueDate = edt_due_date.getText().toString();
        String endDate = edt_end_date.getText().toString();
        String jobType = Model.getInstance().getTypes().get(spinner_job_style.getSelectedIndex()).code;
        String jobPrioriy = Model.getInstance().getPriorities().get(spinner_job_priority.getSelectedIndex()).code;
        String status = "O";
        String description = edt_description.getText().toString();
        if (description.isEmpty()) {
            edt_description.setError(getResources().getString(R.string.description_require));
            return;
        }
        String email = "N";
        String dagid = "";

        if (chk_send_email.isCheck()) {
            email = "Y";
        }
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

        String block = Model.getInstance().getBuildings().get(spinner_building.getSelectedIndex()).block;
        String apartment = Model.getInstance().getApartments().get(spinner_apartment.getSelectedIndex()).property;

        formTask.setAnal_wlt0(apartment);
        formTask.setAnal_wlt1(block);

        Model.getInstance().postUpdateTask(formTask, "I", task.getRecordID());
        getBusyIndicator(R.string.please_wait).show();
    }

    private String getCCUsers() {
        String cc = "";
        for (int i = 0; i < seletedItems.size(); i++) {
//            int postition = (Integer) seletedItems.get(i);
            if (i > 0) {
                cc += ";" + seletedItems.get(i);
            } else {
                cc = seletedItems.get(i);
            }

        }
        return cc;
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}



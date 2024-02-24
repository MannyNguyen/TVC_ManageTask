package demo.example.com.tvc_erp.ui.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.adapter.SpinnerAdapter;
import demo.example.com.tvc_erp.ui.adapter.WorkTaskAdapter;
import demo.example.com.tvc_erp.ui.enums.CategoryEnum;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.enums.ReadEnum;
import demo.example.com.tvc_erp.ui.enums.SortEnum;
import demo.example.com.tvc_erp.ui.objects.Task;
import demo.example.com.tvc_erp.ui.view.FilterView;
import demo.example.com.tvc_erp.ui.view.FloatingActionButton;
import demo.example.com.tvc_erp.ui.view.FloatingActionMenu;
import demo.example.com.tvc_erp.ui.view.LVNews;
import demo.example.com.tvc_erp.ui.view.SlidingMenu;
import demo.example.com.tvc_erp.ui.view.SubActionButton;
import demo.example.com.tvc_erp.utils.Config;
import demo.example.com.tvc_erp.utils.SystemUtils;
import me.leolin.shortcutbadger.ShortcutBadger;


public class MainActivity extends BaseActivity {

    private SlidingMenu slidingMenu;
    private Drawer result = null;
    private RecyclerView recyTask;
    private WorkTaskAdapter workTaskAdapter;
    private View background_menu;
    private FloatingActionMenu rightLowerMenu;
    private FloatingActionButton rightLowerButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView fabIconNew;
    private WindowManager.LayoutParams params;
    private ActionBarDrawerToggle toggle;
    private boolean isDragging = false;
    private int page = 0;
    private String[] summary;
    private String[] read;
    private String[] sort;
    private SpinnerAdapter adapter;
    private Spinner spin_summary, spin_read, spin_update;
    private AppBarLayout appBarLayout;
    private FilterView filter_view;
    private Button btn_filter;
    private Toolbar toolbar;
    private EditText edt_filter;
    private String categoryString = "all";
    private String readString = "all";
    private String sortString = "lastcommentdate";
    private String filterString = "";
    private boolean isFirstInit = true;
    private LVNews lv_news;
    private int mValueLVNews = 0;
    public Timer mTimerLVNews = new Timer();
    public boolean isFilterShowed = false;
    private DrawerLayout drawer;
    private boolean isHidingFilter = false;
    private String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();
        Model.getInstance().callAPIToken(token, "");
        Log.i("Token_Log", token);

        Window window = getWindow();
        //Regulations android version
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        addControl();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset == 0) {

                    if (isFirstInit && isDragging) {
                        isDragging = false;
                        init();
                        return;
                    }

                    if (rightLowerButton != null && isDragging) {
                        isDragging = false;
                        rightLowerButton.attach(params);
                    }

                    if (rightLowerMenu != null) {
                        rightLowerMenu.attachOverlayContainer();
                    }

                } else if (slideOffset != 0 && !isDragging) {
                    isDragging = true;
                    if (rightLowerMenu.isOpen()) {
                        rightLowerMenu.close(true);
                    }

                    if (rightLowerMenu != null) {
                        rightLowerMenu.detachOverlayContainer();
                    }

                    if (rightLowerButton != null) {
                        rightLowerButton.detach();
                    }
                    background_menu.setVisibility(View.GONE);
                }

            }
        }

        ;
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.icon_hambagur);

        init();
        startLVNewsAnim();

    }

    private void init() {
        slidingMenu.showInfo();
        fabIconNew = new ImageView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_task, this.getTheme()));
        } else {
            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_task));
        }
        params = FloatingActionButton.Builder.getDefaultSystemWindowParams(this);

        operation = Model.getInstance().getLoginResponse().result.data.operations;
        rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .setSystemOverlay(true)
                .setLayoutParams(params)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_work, this.getTheme()));
            rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_chance, this.getTheme()));
            rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_new, this.getTheme()));
            rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_problem, this.getTheme()));
        } else {
            rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_work));
            rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_chance));
            rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_new));
            rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_problem));
        }

        final SubActionButton rlSub1 = rLSubBuilder.setContentView(rlIcon1).build();
        final SubActionButton rlSub2 = rLSubBuilder.setContentView(rlIcon2).build();
        final SubActionButton rlSub3 = rLSubBuilder.setContentView(rlIcon3).build();
        final SubActionButton rlSub4 = rLSubBuilder.setContentView(rlIcon4).build();
        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons

        rightLowerButton.post(new Runnable() {

            @Override
            public void run() {
                rightLowerMenu = new FloatingActionMenu.Builder(MainActivity.this, true)
                        .addSubActionView(rlSub1, rlSub1.getLayoutParams().width, rlSub1.getLayoutParams().height)
                        .addSubActionView(rlSub2, rlSub2.getLayoutParams().width, rlSub2.getLayoutParams().height)
                        .addSubActionView(rlSub3, rlSub3.getLayoutParams().width, rlSub3.getLayoutParams().height)
                        .addSubActionView(rlSub4, rlSub4.getLayoutParams().width, rlSub4.getLayoutParams().height)
                        .attachTo(rightLowerButton)
                        .setStartAngle(180)
                        .setEndAngle(270)
                        .build();

                rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu menu) {

                        int startRadius = 0;
                        int endRadius = Math.max(background_menu.getWidth(), background_menu.getHeight());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Animator anim = ViewAnimationUtils.createCircularReveal(background_menu, background_menu.getRight(), background_menu.getBottom(), startRadius, endRadius);

                            anim.setDuration(400);
                            anim.start();
                            background_menu.setVisibility(View.VISIBLE);
                            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.icon_menu_task, MainActivity.this.getTheme()));
                        } else {
                            background_menu.setVisibility(View.VISIBLE);
                            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.icon_menu_task));
                        }
                        if (isFirstInit) {
                            isFirstInit = false;
                        }
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu menu) {

                        background_menu.setVisibility(View.GONE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_task, MainActivity.this.getTheme()));
                        } else {
                            fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.icon_add_task));
                        }
                    }
                });

            }

        });

        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation != null && operation.charAt(1063) == 'Y') {
                    Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Ouh ! Sorry, you can't create this.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rlIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation != null && operation.charAt(1064) == 'Y') {
                    Intent intent = new Intent(MainActivity.this, CreateNewsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Ouh ! Sorry, you can't create this.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        rlIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation != null && operation.charAt(1065) == 'Y') {
                    Intent intent = new Intent(MainActivity.this, CreateOpportunityActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Ouh ! Sorry, you can't create this.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        rlIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation != null && operation.charAt(1062) == 'Y') {
                    Intent intent = new Intent(MainActivity.this, CreateIssueActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Ouh ! Sorry, you can't create this.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listen menu open and close events to animate the button content view

        background_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightLowerMenu.close(true);
            }
        });

    }

    private void addControl() {
        slidingMenu = (SlidingMenu) findViewById(R.id.nav_view);
        initSpinner();
        btn_filter = (Button) findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFilterView(false);
            }
        });

        filter_view = (FilterView) findViewById(R.id.filter_view);
        filter_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFilterView(false);
            }
        });

        background_menu = findViewById(R.id.background_menu);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.issue,
                R.color.task,
                R.color.opportunity,
                R.color.news);

        workTaskAdapter = new WorkTaskAdapter(Model.getInstance().getTaskArrayList(), this);
        recyTask = (RecyclerView) findViewById(R.id.recyTask);
        recyTask.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyTask.setLayoutManager(mLayoutManager);
        recyTask.setItemAnimator(new DefaultItemAnimator());

        workTaskAdapter.setLoadMoreListener(new WorkTaskAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyTask.post(new Runnable() {
                    @Override
                    public void run() {

                        if (Model.getInstance().getTotalCount() != 0 && Model.getInstance().getTotalCount() > Model.getInstance().getTaskArrayList().size()) {
                            int index = Model.getInstance().getTaskArrayList().size() - 1;
                            Model.getInstance().getTaskArrayList().add(new Task(true));
                            workTaskAdapter.notifyItemInserted(index);
                            getListTask();
                        }
                    }
                });
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        lv_news = (LVNews) findViewById(R.id.lv_news);
        recyTask.setAdapter(workTaskAdapter);


    }

    private void initSpinner() {
        edt_filter = (EditText) findViewById(R.id.edt_filter);
        spin_summary = (Spinner) findViewById(R.id.spin_summary);
        spin_read = (Spinner) findViewById(R.id.spin_read);
        spin_update = (Spinner) findViewById(R.id.spin_update);
        summary = getResources().getStringArray(R.array.summary);
        read = getResources().getStringArray(R.array.read);
        sort = getResources().getStringArray(R.array.sort);
        ArrayList<String> optionArrayStairsToBuilding = new ArrayList<>();
        ArrayList<String> optionArrayStairsToBuilding1 = new ArrayList<>();
        ArrayList<String> optionArrayStairsToBuilding2 = new ArrayList<>();
        Collections.addAll(optionArrayStairsToBuilding, summary);
        Collections.addAll(optionArrayStairsToBuilding1, read);
        Collections.addAll(optionArrayStairsToBuilding2, sort);
        adapter = new SpinnerAdapter(this, R.layout.cell_spinner,
                optionArrayStairsToBuilding, getResources());
        spin_summary.setAdapter(adapter);

        adapter = new SpinnerAdapter(this, R.layout.cell_spinner,
                optionArrayStairsToBuilding1, getResources());
        spin_read.setAdapter(adapter);

        adapter = new SpinnerAdapter(this, R.layout.cell_spinner,
                optionArrayStairsToBuilding2, getResources());
        spin_update.setAdapter(adapter);

        edt_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    filterString = edt_filter.getText().toString();
                    page = 0;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            hideFilterView(true);
                        }
                    }, 150);
                    getBusyIndicator(R.string.please_wait).show();

                    return true;
                }
                return false;
            }
        });

        spin_read.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initializedAdapter != parent.getAdapter()) {
                    initializedAdapter = parent.getAdapter();
                    return;
                }
                readString = ReadEnum.values()[position].getType();
                page = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        hideFilterView(true);
                    }
                }, 150);

                getBusyIndicator(R.string.please_wait).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initializedAdapter != parent.getAdapter()) {
                    initializedAdapter = parent.getAdapter();
                    return;
                }
                sortString = SortEnum.values()[position].getType();
                page = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        hideFilterView(true);
                    }
                }, 150);
                getBusyIndicator(R.string.please_wait).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_summary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (initializedAdapter != parent.getAdapter()) {
                    initializedAdapter = parent.getAdapter();
                    return;
                }
                categoryString = CategoryEnum.values()[position].getType();
                page = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        hideFilterView(true);
                    }
                }, 150);
                getBusyIndicator(R.string.please_wait).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getListTask() {
        Model.getInstance().callAPIGetListTask(filterString, page++, 10, categoryString, readString, sortString);
    }

    private void refreshItems() {
        page = 0;
        workTaskAdapter.setLastPosition(-1);
        Model.getInstance().callAPIGetListTask(filterString, page, 20, categoryString, readString, sortString);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isFilterShowed) {
            hideFilterView(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onModelUpdated(ModelEvent evt) {
        super.onModelUpdated(evt);

        if (evt == ModelEvent.GET_TASK_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            stopLVNewsAnim();
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            lv_news.setVisibility(View.GONE);
            workTaskAdapter.notifyDataChanged();
            swipeRefreshLayout.setRefreshing(false);
        } else if (evt == ModelEvent.ERRORUNSPECIFIED_ERROR) {
            getBusyIndicator(R.string.please_wait).dismiss();
            stopLVNewsAnim();
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            lv_news.setVisibility(View.GONE);
            workTaskAdapter.notifyDataChanged();
            swipeRefreshLayout.setRefreshing(false);
        } else if (evt == ModelEvent.GET_DETAIL_ATTACHMENT_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            showPopupMenu(workTaskAdapter.getViewFocused(), Model.getInstance().getAttachments());
        } else if (evt == ModelEvent.DOWNLOAD_IMAGE_SUCCESS) {
            getBusyIndicator(R.string.please_wait).dismiss();
            Bitmap img = BitmapFactory.decodeByteArray(Model.getInstance().getImage(), 0, Model.getInstance().getImage().length);
            showImageDialog(img);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rightLowerMenu.isOpen()) {
            rightLowerMenu.close(true);
        }

        if (rightLowerMenu != null) {
            rightLowerMenu.detachOverlayContainer();
        }

        if (rightLowerButton != null) {
            rightLowerButton.detach();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (rightLowerMenu.isOpen()) {
            rightLowerMenu.close(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshItems();
        SystemUtils.saveNumsOfNotify(0, this);
        ShortcutBadger.removeCount(this);
    }

    @Override
    protected void onRestart() {
        super.onStop();

        if (rightLowerButton != null) {
            rightLowerButton.attach(params);
        }

        if (rightLowerMenu != null) {
            rightLowerMenu.attachOverlayContainer();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_filter) {
            showFilterView();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFilterView() {
        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.bottom_up);
        filter_view.startAnimation(bottomUp);
        filter_view.setVisibility(View.VISIBLE);
        appBarLayout.setExpanded(false, true);

        if (rightLowerMenu.isOpen()) {
            rightLowerMenu.close(true);
        }

        if (rightLowerMenu != null) {
            rightLowerMenu.detachOverlayContainer();
        }

        if (rightLowerButton != null) {
            rightLowerButton.detach();
        }
        background_menu.setVisibility(View.GONE);
        isFilterShowed = true;
        setDrawerState(false);
    }

    private void hideFilterView(final boolean isGetList) {
        if (isHidingFilter) {
            return;
        }
        filter_view.setVisibility(View.GONE);
        isHidingFilter = true;
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        appBarLayout.setExpanded(true, true);
        Animation bottomDown = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.bottom_down);
        bottomDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                setDrawerState(true);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                isHidingFilter = false;
                if (rightLowerButton != null) {
                    isDragging = false;
                    rightLowerButton.attach(params);
                }

                if (rightLowerMenu != null) {
                    rightLowerMenu.attachOverlayContainer();
                }

                SystemUtils.hideSoftKeyboard(MainActivity.this);

                if (isGetList) {
                    getListTask();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        filter_view.startAnimation(bottomDown);
        isFilterShowed = false;

    }

    private Handler mHandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                lv_news.setValue(msg.arg1);
            }
        }
    };

    private void startLVNewsAnim() {
        mValueLVNews = 0;
        if (mTimerLVNews != null) {

            mTimerLVNews.cancel();
        }
        mTimerLVNews = new Timer();
        timerTaskLVNews();
    }

    public void timerTaskLVNews() {
        mTimerLVNews.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mValueLVNews < 100) {
                    mValueLVNews++;
                    Message msg = mHandle.obtainMessage(1);
                    msg.arg1 = mValueLVNews;
                    mHandle.sendMessage(msg);
                } else {
                    mTimerLVNews.cancel();
                }
            }
        }, 0, 10);
    }

    private void stopLVNewsAnim() {
        lv_news.stopAnim();
        if (mTimerLVNews != null) {
            mTimerLVNews.cancel();
        }
    }

    private void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.syncState();
            toggle.setDrawerIndicatorEnabled(true);
            drawer.addDrawerListener(toggle);
            toolbar.setNavigationIcon(R.drawable.icon_hambagur);

        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }
}

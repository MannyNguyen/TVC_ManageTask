package demo.example.com.tvc_erp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.dialogs.SimpleAlertDialog;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.ui.objects.Attachment;
import demo.example.com.tvc_erp.utils.ConnectivityReceiver;

/**
 * Created by prosoft on 12/16/15.
 */
public class BaseActivity extends AppCompatActivity implements Observer, View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private boolean isVisible;
    static final int DATE_PICKER_ID = 1111;

    public static boolean isInApp;
    private EditText editTextDate;

    protected void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    protected boolean isVisible() {
        return isVisible;
    }

    protected ProgressDialog busyIndicator;
    protected AlertDialog alertDialog;
    protected ReceiverFromPushNotification receiver;
    protected BroadcastReceiver mReceiver;
    protected int idString;
    protected boolean doubleBackToExitPressedOnce = false;

    // UI components
//    protected ProgressDialog getBusyIndicator() {
//        if (busyIndicator == null) {
//            busyIndicator = SimpleAlertDialog.createBusyIndicator(this, getString(R.string.logging_in_message));
//        }
//        return busyIndicator;
//    }
//
    public ProgressDialog getBusyIndicator(int resString) {
        if (busyIndicator == null) {
            busyIndicator = SimpleAlertDialog.createBusyIndicator(this, getString(resString));
        }
        return busyIndicator;
    }

    //
//    public ProgressDialog getBusyIndicator(String string) {
//        if (busyIndicator == null) {
//            busyIndicator = SimpleAlertDialog.createBusyIndicator(this, string);
//        }
//        return busyIndicator;
//    }
//
//    protected AlertDialog showMessageWithEditText(int string, int cancel, int accept, Runnable onCancel, Runnable onAccept) {
//        if (alertDialog == null) {
//            alertDialog = SimpleAlertDialog.showMessageWithCancelAndAcceptButtons(this, null, getString(string), getString(cancel).toUpperCase(), getString(accept).toUpperCase(), onCancel, onAccept).create();
//        }
//        return alertDialog;
//    }
//
    protected AlertDialog showErrorMessage(int string, int cancel, int accept, Runnable onCancel, Runnable onAccept) {
        if (alertDialog == null) {
            alertDialog = SimpleAlertDialog.showMessageWithCancelAndAcceptButtons(this, null, getString(string), getString(cancel).toUpperCase(), getString(accept).toUpperCase(), onCancel, onAccept).create();
        }
        return alertDialog;
    }

    protected AlertDialog showErrorMessage(String title, String message, Runnable onAccept) {
        alertDialog = SimpleAlertDialog.showMessageWithOkButton(this, title, message, onAccept).create();
        return alertDialog;
    }

    protected AlertDialog showErrorMessage(int string, int accept, Runnable onAccept) {
        if (alertDialog == null || idString != accept) {
            idString = accept;
            alertDialog = SimpleAlertDialog.showMessageWithOkButton(this, getString(string), getString(accept), onAccept).create();
        }
        return alertDialog;
    }

    protected AlertDialog showListUserDialog(final String[] users, String title, Runnable onAccept, DialogInterface.OnClickListener onclick) {
        alertDialog = SimpleAlertDialog.showListText(this, title, onAccept, users, onclick).create();
        return alertDialog;
    }

    protected AlertDialog showListUserDialogMultiChoice(final String[] users, String title, Runnable onAccept, Runnable onCancel, DialogInterface.OnMultiChoiceClickListener onclick) {
        alertDialog = SimpleAlertDialog.showListTextMultiChoice(this, title, onAccept, onCancel, users, onclick).create();
        return alertDialog;
    }

    protected AlertDialog showImageDialog(Bitmap bitmap) {
        alertDialog = SimpleAlertDialog.showImageLoader(this, bitmap);
        return alertDialog;
    }

    protected void showPopupMenu(View view, ArrayList<Attachment> attachmentArrayList) {
        SimpleAlertDialog.createPopupMenu(this, view, attachmentArrayList);
    }

    @Override
    public void onResume() {
        super.onResume();

        setIsVisible(true);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        Model.getInstance().addObserver(this);
        TCVApplication.getInstance().setConnectivityListener(this);
    }

    protected BroadcastReceiver getBroacastReceiver(final ReceiverFromPushNotification receiver) {
        if (receiver == null) {
            this.receiver = receiver;
        }

        if (mReceiver == null) {
            return mReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    receiver.setWorkTodo(context, intent);
                }
            };
        }
        return mReceiver;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isInApp = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isInApp = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        setIsVisible(false);
        Model.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable == Model.getInstance() && isVisible()) {
            onModelUpdated((ModelEvent) object);
        }
    }

    protected void onModelUpdated(ModelEvent evt) {
        // Nothing to do, yet.
        if (evt == ModelEvent.ERRORUNSPECIFIED_ERROR) {
            if(alertDialog != null){
                alertDialog.dismiss();
            }

            if(busyIndicator != null){
                busyIndicator.dismiss();
            }
            showErrorMessage(Model.getInstance().getTitle(), Model.getInstance().getMessageError(), new Runnable() {
                @Override
                public void run() {
                }
            }).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    public void onClick(View v) {

    }

    public void setDateText(EditText editText) {
        editTextDate = editText;
    }

    public EditText getDateText() {
        if (editTextDate != null) {
            return editTextDate;
        }
        return null;
    }

//    public static void showToastMessage(Context context, String toastContent) {
//        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast_report,
//                (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_report));
//        TextView txtToast = (TextView) layout.findViewById(R.id.txt_report_toast);
//        txtToast.setText(toastContent);
//        Toast toast = new Toast(context);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.setView(layout);
//        toast.show();
//    }

    @Deprecated
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                Calendar c = Calendar.getInstance();
                return new DatePickerDialog(this, pickerListener
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            // Show selected date
            getDateText().setText(new StringBuilder().append(selectedMonth + 1)
                    .append("/").append(selectedDay).append("/").append(selectedYear)
                    .append(" "));

        }
    };

    protected interface ReceiverFromPushNotification {
        void setWorkTodo(Context context, Intent intent);
    }

    protected void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//            showErrorMessage(R.string.closed_connection, R.string.connect_closed, new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                    startActivity(intent);
//                }
//            }).dismiss();
        } else {
//            showErrorMessage(R.string.closed_connection, R.string.connect_closed, new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                    startActivity(intent);
//                }
//            }).show();
        }

    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

}
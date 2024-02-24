package demo.example.com.tvc_erp.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mikepenz.iconics.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import demo.example.com.tvc_erp.R;
import demo.example.com.tvc_erp.ui.Model;
import demo.example.com.tvc_erp.ui.activity.BaseActivity;
import demo.example.com.tvc_erp.ui.objects.Attachment;
import demo.example.com.tvc_erp.ui.objects.ModelError;
import demo.example.com.tvc_erp.utils.Config;
import demo.example.com.tvc_erp.utils.SystemUtils;


public class SimpleAlertDialog {

    public static AlertDialog.Builder showMessageWithCancelAndAcceptButtons(Context context, String title, String message,
                                                                            String cancelText, String acceptText,
                                                                            final Runnable onCancel, final Runnable onAccept) {
        return new AlertDialog.Builder(context)
                .setTitle((title != null) ? title : "")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(acceptText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (onAccept != null) {
                            onAccept.run();
                        }
                    }
                })
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (onCancel != null) {
                            onCancel.run();
                        }
                    }
                });

    }

    public static void showMessageWithEditText(Context context, String title, String message,
                                               String cancelText, String acceptText,
                                               final EditText editText,
                                               final Runnable onCancel, final Runnable onAccept) {
        new AlertDialog.Builder(context)
                .setTitle((title != null) ? title : "")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(acceptText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SystemUtils.hideSoftKeyboardForEditText(editText);
                        dialog.dismiss();
                        if (onAccept != null) {
                            onAccept.run();
                        }
                    }
                })
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SystemUtils.hideSoftKeyboardForEditText(editText);
                        dialog.cancel();
                        if (onCancel != null) {
                            onCancel.run();
                        }
                    }
                })
                .setView(editText)
                .show();
    }


    public static  AlertDialog.Builder showMessageWithOkButton(Context context, String title, String message, final Runnable callback) {
        return new AlertDialog.Builder(context)
                .setTitle((title != null) ? title : context.getString(R.string.message))
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (callback != null) {
                            callback.run();
                        }
                    }
                });
    }

    public static AlertDialog.Builder showListText(Context context, String title, final Runnable callback, String [] users, DialogInterface.OnClickListener onclick){
        return new AlertDialog.Builder(context).setTitle(title).setItems(users, onclick).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (callback != null) {
                    callback.run();
                }
            }
        });
    }

    public static AlertDialog.Builder showListTextMultiChoice(Context context, String title, final Runnable onAccept, final Runnable onCancel, String [] users, DialogInterface.OnMultiChoiceClickListener onclick){
        return new AlertDialog.Builder(context).setTitle(title).setMultiChoiceItems(users, null, onclick).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (onCancel != null) {
                    onCancel.run();
                }
            }
        }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (onAccept != null) {
                    onAccept.run();
                }
            }
        });
    }

    public static AlertDialog showImageLoader(final Activity context, final Bitmap bitmap){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_image, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final ImageView image = (ImageView) dialogLayout.findViewById(R.id.goProDialogImage);



        float imageWidthInPX = (float)bitmap.getWidth();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                Math.round(imageWidthInPX * (float)bitmap.getHeight() / (float)bitmap.getWidth()));
        image.setLayoutParams(layoutParams);
        image.setImageBitmap(bitmap);

        dialog.show();
        return dialog;

    }
//    public static AlertDialog.Builder showErrorWithOkButton(Context context, ModelError error, final Runnable callback) {
//        error.setHandled(true);
//
//        return new AlertDialog.Builder(context)
//                .setTitle(context.getString(R.string.error))
//                .setMessage(error.getReadableCode())
//                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                        if (callback != null) {
//                            callback.run();
//                        }
//                    }
//                });
//    }
//
    public static ProgressDialog createBusyIndicator(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage((message != null) ? message : context.getString(R.string.please_wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void createPopupMenu(final BaseActivity context, View view, final ArrayList<Attachment> attachmentArrayList){
        int popupWidth = 150;
        int popupHeight = 150;
        Point p;

        int[] location = new int[2];

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        view.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];


        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_menu, viewGroup);

        ListView lv_item = (ListView) layout.findViewById(R.id.lv_item);
        String[] values = new String[attachmentArrayList.size()];
        int m = 1;
        for(int i = 0; i< attachmentArrayList.size(); i++){
//            values[i] = attachmentArrayList.get(i).getFilename();
            String name = "Image " + m++;
            values[i] = name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lv_item.setAdapter(adapter);
        lv_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                context.getBusyIndicator(R.string.please_wait).show();
                String url = Config.BASEURL + String.format(Config.API_GET_URL_ATTACHMENT, attachmentArrayList.get(position).getDocumentcode());
                Model.getInstance().downloadImageURL(url, Model.getInstance().getAttachments().get(0).getFilename());
            }
        });

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(Utils.convertDpToPx(context, popupWidth));
//        popup.setHeight(lv_item.getHeight());
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
//        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.

    }
}

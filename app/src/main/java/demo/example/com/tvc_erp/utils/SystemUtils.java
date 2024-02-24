package demo.example.com.tvc_erp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

import demo.example.com.tvc_erp.ui.objects.UserInfo;

/**
 * Created by Manh on 9/9/2016.
 */
public class SystemUtils {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    public static void hideKeyboard(View view, Context ctx) {
        InputMethodManager keyboard = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(view, 0);
    }

    public static void hideSoftKeyboardForEditText(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static Bitmap resizeMapIcons(int id, int width, int height, Context ctx) {
        if (ctx.getResources() != null) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(ctx.getResources(), id, options);

            options.inSampleSize = calculateInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;
//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
            Bitmap imageBitmap = BitmapFactory.decodeResource(ctx.getResources(), id, options);
            return imageBitmap;
        }
        return null;
    }

    public static String extractStringFromTextView(TextView tv) {
        CharSequence cs = tv.getText();

        if (cs != null) {
            return cs.toString();
        }

        return null;
    }

    public static boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String BitMapToString(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 500, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public static Bitmap StringToBitMap(String encodedString, int reqWidth, int reqHeight) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);

            InputStream is = new ByteArrayInputStream(encodeByte);

            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, bitmapFactoryOptions);

            bitmapFactoryOptions.inSampleSize = calculateInSampleSize(bitmapFactoryOptions, reqWidth, reqHeight);

            bitmapFactoryOptions.inJustDecodeBounds = false;

            is = new ByteArrayInputStream(encodeByte);

            Bitmap bitmap = BitmapFactory.decodeStream(is, null, bitmapFactoryOptions);

            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Uri uri, int reqWidth, int reqHeight, Context context) {
        try {
            String path = getRealPathFromURI(uri, context);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            return bitmap;
        } catch (Exception ex) {
            return null;
        }
    }

    private static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //Check device have service or not to set function push
//    public static boolean checkPlayServices(Activity context) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
//                        .show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//            }
//            return false;
//        }
//        return true;
//    }

    //Use Phone call
    public static void actionCall(String number, Context context) {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        try {
            context.startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    //Use Phone SMS
    public static void sendSMS(String number, Context context) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", number);
//        smsIntent.putExtra("sms_body", "Test ");

        try {
            context.startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getTime() {
        String datetime;
        TimeZone tz = TimeZone.getDefault();
        Calendar calendar = Calendar.getInstance(tz);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        if (calendar.get(Calendar.MONTH) + 1 < 10) {
            month = "0" + month;
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + day;
        }
        datetime = month + "/" + day + "/" + year;
        return datetime;
    }

    public static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static void saveSessionId(String sessionId, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sessionId", sessionId);
        editor.commit();
    }

    public static String getSessionId(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getString("sessionId", null);
    }

    public static void saveNumsOfNotify(int num, Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("numNotify", num);
        editor.commit();
    }

    public static int getNumsOfNotify(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getInt("numNotify", 0);
    }

    public static void saveRememberMe(boolean isCheck, Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("rememberMe", isCheck);
        editor.commit();
    }

    public static Boolean getRememberMe(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getBoolean("rememberMe", false);
    }
    public static void saveUserInfo(UserInfo userInfo, Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userInfo", json);
        editor.commit();
    }

    public static UserInfo getUserInfo(Context context) {
        Gson gson = new Gson();
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userInfo = pref.getString("userInfo", null);
        if(userInfo == null)
            return null;
        return gson.fromJson(userInfo, UserInfo.class);
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}

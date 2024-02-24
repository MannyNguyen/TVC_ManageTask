package demo.example.com.tvc_erp.utils;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snapbuck2 on 1/23/16.
 */
public class ImagePickerHelper {

    private Context context;

    public ImagePickerHelper(Context context) {
        this.context = context;
    }

    public static Uri outputFileUri;

    public Uri getOutputFileUri() {
        return outputFileUri;
    }

    public Intent getPickIntent() {

        // Determine Uri of camera image to save.
        final String filename = getUniqueImageFilename();
        File realImageFile = FileHelper.getOutputMediaFilePath(filename);
        outputFileUri = Uri.fromFile(realImageFile);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        return chooserIntent;
    }

    public Uri[] getUrisImageAfterPick(Intent data) {
        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }

//        Uri selectedImageUri;
        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
        if (isCamera) {
            Uri selectedImageUri = getOutputFileUri();
            mArrayUri.add(selectedImageUri);
        } else {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    Log.i("aaaaaa222", "" + uri + "");
                }
            } else {
                if (data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    mArrayUri.add(selectedImageUri);
                    Log.i("aaaaaa111", "" + selectedImageUri + "");
                }
            }
        }

        return mArrayUri.toArray(new Uri[mArrayUri.size()]);
    }

    private String getUniqueImageFilename() {
        return "img_" + System.currentTimeMillis() + ".jpg";
    }
}

package demo.example.com.tvc_erp.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import demo.example.com.tvc_erp.ui.activity.TCVApplication;


/**
 * Created by Snapbuck2 on 1/23/16.
 */
public class FileHelper {
    public static byte[] getByteDataFromUri(Context context, Uri uri, int width, int height) {
        String filePath = getPath(context, uri);
        Bitmap bitmapConvert = shrinkImage(filePath, width, height);

        if (filePath != null) {
            ExifInterface exif = null;
            ByteArrayOutputStream bmOutput = new ByteArrayOutputStream();
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmapConvert = Bitmap.createBitmap(bitmapConvert, 0, 0, bitmapConvert.getWidth(), bitmapConvert.getHeight(), matrix, true);

                bitmapConvert.compress(Bitmap.CompressFormat.JPEG, 100,
                        bmOutput);
                return bmOutput.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] getByteDataFromUri(Context context, Uri uri) {
        InputStream iStream = null;
        try {
            iStream = context.getContentResolver().openInputStream(uri);
            byte[] inputData = getBytes(iStream);
            return inputData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getSize(Context context, Uri uri) {
        InputStream iStream = null;
        try {
            iStream = context.getContentResolver().openInputStream(uri);
            return iStream.available();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Bitmap getBitMapFromUri(Context context, Uri uri, int width, int height) {
        byte[] data = FileHelper.getByteDataFromUri(context, uri, width, height);
        if (data != null) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            return bm;
        }
        return null;
    }


    public static boolean testFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static Bitmap getThumnailVideoPath(Context context, Uri uri) {

        String path = getPath(context, uri);
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                MediaStore.Images.Thumbnails.MINI_KIND);
        Log.i("aaaa", thumb + "");
        return thumb;

    }

    public static String getFileNameFromUrl(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        return fileName;
    }

    public static Bitmap shrinkImage(String filePath, int width, int height) {
        FileInputStream fileInputStream = null;
        if (filePath != null) {
            File file = new File(filePath);
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BitmapFactory.Options bitopt = new BitmapFactory.Options();
            bitopt.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fileInputStream, null, bitopt);

            int h = (int) Math.ceil(bitopt.outHeight / (float) height);
            int w = (int) Math.ceil(bitopt.outWidth / (float) width);

            if (h > 1 || w > 1) {
                if (h > w) {
                    bitopt.inSampleSize = h;

                } else {
                    bitopt.inSampleSize = w;
                }
            }
            bitopt.inJustDecodeBounds = false;
            Bitmap bit = BitmapFactory.decodeFile(filePath, bitopt);
            return bit;
        }
        return null;
    }

    public static boolean isNewGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.contentprovider".equals(uri.getAuthority());
    }

    public static String writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return path;
    }

    public static Uri getUriAuthority(Context context, Uri uri){
        if (isNewGooglePhotosUri(uri)) {
            InputStream is = null;
            if (uri.getAuthority() != null) {
                try {
                    is = context.getContentResolver().openInputStream(uri);
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    uri = Uri.parse(writeToTempImageAndGetPathUri(context, bmp));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return uri;
    }

    public static String getPath(final Context context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isNewGooglePhotosUri(uri)) {
            InputStream is = null;
            if (uri.getAuthority() != null) {
                try {
                    is = context.getContentResolver().openInputStream(uri);
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    uri = Uri.parse(writeToTempImageAndGetPathUri(context, bmp));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;


    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static File getOutputMediaFilePath(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + TCVApplication.getInstance().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
    }

    public static boolean checkImageType(Context mContext, Uri uri) {
        String extension = "";

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(mContext.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }

        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg") && extension.equals("png"))
            return false;
        else
            return true;
    }

    public static boolean checkVideoType(Context mContext, Uri uri) {
        String extension = "";

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(mContext.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }

        if (!extension.equals("mp4"))
            return false;
        else
            return true;
    }

}

package demo.example.com.tvc_erp.api;

import android.util.Log;

import demo.example.com.tvc_erp.ui.enums.API_Method;
import demo.example.com.tvc_erp.api.object_request_api.ActionRequest;
import demo.example.com.tvc_erp.utils.Config;

/**
 * Created by prosoft on 1/11/16.
 */
public class APIConnectionRequest {

    public static void API_LOGIN(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_LOGIN);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_LOGIN, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_LIST_TASK(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_LIST_TASK);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_LIST_TASK, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_DELETE_TASK(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_DELETE_TASK);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_DELETE_TASK, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_ADD_COMMENT(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_ADD_COMMENT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_ADD_COMMENT, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }
    public static void API_DEL_COMMENT(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_DEL_COMMENT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_DEL_COMMENT, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }
    public static void API_GET_DETAIL_ATTACHMENT(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_DETAIL_ATTACHMENT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_DETAIL_ATTACHMENT, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_URL_ATTACHMENT(CallBackAPI callBackDone, Object jsonObject, String documentCode, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + String.format(Config.API_GET_URL_ATTACHMENT, documentCode));
        GetJsonAPI.getQueries(Config.BASEURL + String.format(Config.API_GET_URL_ATTACHMENT, documentCode), API_Method.GET, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_LIST_USERS(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_LIST_USERS);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_LIST_USERS, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_JOB_TYPE(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_JOB_TYPE);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_JOB_TYPE, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_JOB_PRIORITY(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_JOB_PRIORITY);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_JOB_PRIORITY, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_POST_CREATE_TASK(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_POST_CREATE_TASK);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_POST_CREATE_TASK, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_ACCESS_GROUP(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_ACCESS_GROUP);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_ACCESS_GROUP, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_COMMENT_TASK(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_COMMENT_TASK);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_COMMENT_TASK, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_UPDATE_COMMENT_TASK(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_UPDATE_COMMENT_TASK);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_UPDATE_COMMENT_TASK, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_LIST_BUILDING(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_LIST_BUILDING);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_LIST_BUILDING, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_GET_LIST_APARTMENT(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_GET_LIST_APARTMENT);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_GET_LIST_APARTMENT, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }

    public static void API_DOWNLOAD_IMAGE(CallBackDownloadAPI callBackDownloadAPI, String URL, String fileName) {
        Log.v("TestLog", URL);
        GetJsonAPI.getQueries(URL, API_Method.DOWNLOAD, callBackDownloadAPI, fileName);
    }

    public static void API_UPDATE_TOKEN(CallBackAPI callBackDone, Object jsonObject, ActionRequest actionRequest) {
        Log.v("TestLog", Config.BASEURL + Config.API_UPDATE_TOKEN);
        GetJsonAPI.getQueries(Config.BASEURL + Config.API_UPDATE_TOKEN, API_Method.POST, jsonObject, callBackDone, actionRequest);
    }
}

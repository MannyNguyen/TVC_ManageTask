package demo.example.com.tvc_erp.ui;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import demo.example.com.tvc_erp.api.APIConnectionRequest;
import demo.example.com.tvc_erp.api.CallBackAPI;
import demo.example.com.tvc_erp.api.CallBackDownloadAPI;
import demo.example.com.tvc_erp.api.object_request_api.ActionTaskRequest;
import demo.example.com.tvc_erp.api.object_request_api.AddCommentRequest;
import demo.example.com.tvc_erp.api.object_request_api.DeleteCommentRequest;
import demo.example.com.tvc_erp.api.object_request_api.GetAccessGroupResquest;
import demo.example.com.tvc_erp.api.object_request_api.GetCommentRequest;
import demo.example.com.tvc_erp.api.object_request_api.GetDetailAttachmentRequest;
import demo.example.com.tvc_erp.api.object_request_api.GetJobTypeRequest;
import demo.example.com.tvc_erp.api.object_request_api.GetListApartmentResquest;
import demo.example.com.tvc_erp.api.object_request_api.GetListBuildingResquest;
import demo.example.com.tvc_erp.api.object_request_api.GetListRequest;
import demo.example.com.tvc_erp.api.object_request_api.GetListUsersRequest;
import demo.example.com.tvc_erp.api.object_request_api.LoginRequest;
import demo.example.com.tvc_erp.api.object_request_api.LoginWithSessionRequest;
import demo.example.com.tvc_erp.api.object_request_api.PostCreateTaskRequest;
import demo.example.com.tvc_erp.api.object_request_api.PostUpdateTaskRequest;
import demo.example.com.tvc_erp.api.object_request_api.TokenRequest;
import demo.example.com.tvc_erp.api.object_request_api.UpdateCommentRequest;
import demo.example.com.tvc_erp.api.object_response_api.GetAccessGroupResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetActionTaskResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetAddMesageResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetCommentTaskResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetDeleteCommentResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetDetailAttachmentResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetJobPriorityResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetJobTYpeResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetListApartmentRespone;
import demo.example.com.tvc_erp.api.object_response_api.GetListBuildingResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetListResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetListUsersResponse;
import demo.example.com.tvc_erp.api.object_response_api.GetPostTaskResponse;
import demo.example.com.tvc_erp.api.object_response_api.LoginResponse;
import demo.example.com.tvc_erp.api.object_response_api.TokenResponse;
import demo.example.com.tvc_erp.ui.enums.ModelEvent;
import demo.example.com.tvc_erp.api.object_request_api.ActionRequest;
import demo.example.com.tvc_erp.ui.objects.Attachment;
import demo.example.com.tvc_erp.ui.objects.CurrentUserInfo;
import demo.example.com.tvc_erp.ui.objects.FormTask;
import demo.example.com.tvc_erp.ui.objects.ModelError;
import demo.example.com.tvc_erp.ui.objects.ModelObject;
import demo.example.com.tvc_erp.ui.objects.Task;
import demo.example.com.tvc_erp.ui.objects.UserInfo;
import demo.example.com.tvc_erp.utils.TaxiLoyDebug;

/**
 * Created by prosoft on 9/12/16.
 */
public class Model extends Observable implements Observer {


    //Create only one object Model for app
    private static Model _instance;
    private ArrayList<Task> taskArrayList;
    private LoginResponse loginResponse;
    private TokenResponse tokenResponse;
    private int totalCount = 0;
    private String documentCode;
    private ArrayList<GetListUsersResponse.Data> users;
    private ArrayList<GetJobTYpeResponse.Data> types;
    private ArrayList<GetJobPriorityResponse.Data> priorities;
    private ArrayList<GetAccessGroupResponse.Data> accessGroup;
    private ArrayList<GetCommentTaskResponse.Data> commentTasks;
    private ArrayList<GetListBuildingResponse.Data> buildings;
    private ArrayList<GetListApartmentRespone.Data> apartments;
    private ArrayList<Attachment> attachments;
    private byte[] image;
    private String messageError;
    private String title;
    private CurrentUserInfo currentUserInfo;


    public static Model getInstance() {
        if (_instance == null) {
            _instance = new Model();
        }
        return _instance;
    }

    @Override
    public void update(Observable observable, Object object) {

    }

    public ArrayList<Task> getTaskArrayList() {
        if (taskArrayList == null) {
            taskArrayList = new ArrayList<>();
        }
        return taskArrayList;
    }

    public ArrayList<GetListUsersResponse.Data> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public ArrayList<GetJobTYpeResponse.Data> getTypes() {
        if (types == null) {
            types = new ArrayList<>();
        }
        return types;
    }

    public ArrayList<GetJobPriorityResponse.Data> getPriorities() {
        if (priorities == null) {
            priorities = new ArrayList<>();
        }
        return priorities;
    }

    public ArrayList<GetCommentTaskResponse.Data> getCommentTasks() {
        if (commentTasks == null) {
            commentTasks = new ArrayList<>();
        }
        return commentTasks;
    }

    public ArrayList<Attachment> getAttachments() {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        return attachments;
    }

    public ArrayList<GetAccessGroupResponse.Data> getAccessGroups() {
        if (accessGroup == null) {
            accessGroup = new ArrayList<>();
        }
        return accessGroup;
    }

    public ArrayList<GetListBuildingResponse.Data> getBuildings() {
        if (buildings == null) {
            buildings = new ArrayList<>();
        }
        return buildings;
    }

    public ArrayList<GetListApartmentRespone.Data> getApartments() {
        if (apartments == null) {
            apartments = new ArrayList<>();
        }
        return apartments;
    }

    public CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(LoginResponse loginResponse) {
        this.tokenResponse = tokenResponse;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }


    public void callAPILoginPasserger(UserInfo userInfo, String securityCode, String oldSessionId) {

        //Response
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    try {
                        JSONObject data = result.getJSONObject("result").getJSONObject("data");
                        boolean isLogin = data.getBoolean("login");
                        if (isLogin) {
                            Gson gson = new Gson();
                            //Parse json from result
                            setLoginResponse(gson.fromJson(result.toString(), LoginResponse.class));

                            CurrentUserInfo currentUserInfo = new CurrentUserInfo();
                            currentUserInfo.setCompany(getLoginResponse().result.data.company);
                            currentUserInfo.setOperatorid(getLoginResponse().result.data.operatorid);
                            currentUserInfo.setTabname(getLoginResponse().result.data.tabname);
                            currentUserInfo.setUserName(getLoginResponse().result.data.operatorname);
                            currentUserInfo.setEmployee(getLoginResponse().result.data.employee);
                            setCurrentUserInfo(currentUserInfo);
                            notifyObserversOfEvent(ModelEvent.LOGIN_SUCCESS);
                        } else {
                            String message = result.getJSONObject("result").getString("message");
                            Log.i("Message", "" + message);
                            if (message.contains("Logon ")) {
                                notifyObserversOfEvent(ModelEvent.LOGIN_ALREADY);
                            } else {
                                notifyObserversOfEvent(ModelEvent.LOGIN_FAIL);
                            }


                        }

                    } catch (Exception ex) {
                        Log.e("Error ", "" + ex);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);

            }

            @Override
            public void onComplete() {

            }
        });

        ActionRequest actionRequest = new ActionRequest("ConnectDB", "getConfig");

        //Request API
        if (oldSessionId == null) {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.id = 1;
            loginRequest.password = userInfo.getPassword();
            loginRequest.username = userInfo.getEmail();
            loginRequest.securitycode = securityCode;
            APIConnectionRequest.API_LOGIN(callBackDone, loginRequest, actionRequest);
        } else {
            LoginWithSessionRequest loginRequest = new LoginWithSessionRequest();
            loginRequest.id = 1;
            loginRequest.password = userInfo.getPassword();
            loginRequest.username = userInfo.getEmail();
            loginRequest.securitycode = securityCode;
            loginRequest.oldSessionId = oldSessionId;
            APIConnectionRequest.API_LOGIN(callBackDone, loginRequest, actionRequest);
        }

    }

    public void callAPIToken(String token, String status) {
        //Response
        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                Log.i("LOG_TOKEN_RESULT", result+"");
                if (result != null) {


                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = token;
        Log.i("LOG_REQUEST_TOKEN", token);
        tokenRequest.limit = 15;

        //Run
        ActionRequest actionRequest = new ActionRequest("FrmStFireBase", "upd");
        APIConnectionRequest.API_UPDATE_TOKEN(callBackDone, tokenRequest, actionRequest);

    }

    public void callAPIGetListTask(String searchString, final int page, int limit, String category, String read, String sort) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetListResponse response = gson.fromJson(result.toString(), GetListResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    setTotalCount(response.result.totalCount);
                    if (getTaskArrayList().size() > 0) {
                        getTaskArrayList().remove(getTaskArrayList().size() - 1);
                    }
                    if (page == 0) {
                        getTaskArrayList().clear();
                    }
                    for (GetListResponse.Data data : response.result.data) {
                        Task task = new Task(false);
                        task.setAuthor(data.author);
                        task.setTitle(data.title);
                        task.setType(data.recordtype);
                        task.setCreateDate(data.createddate);
                        task.setNumCmt(data.cmts.size());
                        task.setTaskMaster(data.taskmaster);
                        task.setRecordID(data.recordid);
                        task.setAttachCount(data.attachCount);
                        task.setPic(data.pic);
                        task.setStartDate(data.startdate);
                        task.setEndDate(data.enddate);
                        task.setDueDate(data.duedate);
                        String[] parts = data.cc.split(";");
                        task.setCommentList(data.cmts);
                        task.setStatus(data.status);
                        task.setJobType(data.jobtype);
                        task.setPriorityl(data.priority);
                        List<String> listTemp = new ArrayList<>();
                        for (String cc : parts) {
                            if (cc != "") {
                                listTemp.add(cc);
                            }
                        }
                        task.setCc(listTemp);
                        task.setDetail(data.description);
                        getTaskArrayList().add(task);
                    }


                    notifyObserversOfEvent(ModelEvent.GET_TASK_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetListRequest loginRequest = new GetListRequest(sort, "DESC");
        loginRequest.category = category;
        loginRequest.searchString = searchString;
        loginRequest.read = read;
        loginRequest.start = page;
        loginRequest.limit = limit;

        ActionRequest actionRequest = new ActionRequest("FrmCcWork", "get");
        APIConnectionRequest.API_GET_LIST_TASK(callBackDone, loginRequest, actionRequest);

        //Run callAPILoginPassenger


    }


    public void callActionTask(String status, String recordid) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetActionTaskResponse response = gson.fromJson(result.toString(), GetActionTaskResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        notifyObserversOfEvent(ModelEvent.ADD_ACTION_TASK_SUCCESS);
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        ActionTaskRequest loginRequest = new ActionTaskRequest();
        loginRequest.recordid = recordid;
        loginRequest.status = status;

        ActionRequest actionRequest = new ActionRequest("FrmCcWork", "upd");
        APIConnectionRequest.API_DELETE_TASK(callBackDone, loginRequest, actionRequest);

        //Run callAPILoginPassenger


    }

    public void callAddComment(String comment, String recordid, byte[] image) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetAddMesageResponse response = gson.fromJson(result.toString(), GetAddMesageResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        notifyObserversOfEvent(ModelEvent.ADD_MESSAGE_SUCCESS);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);


            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        AddCommentRequest commentRequest = new AddCommentRequest();
        commentRequest.recordid = recordid;
        commentRequest.comments = comment;
        if (image.length != 0) {
            commentRequest.attachments.add(commentRequest.new Attachment(getUniqueImageFilename(), image));
        }

        ActionRequest actionRequest = new ActionRequest("FrmCcWork", "addCmt");
        APIConnectionRequest.API_ADD_COMMENT(callBackDone, commentRequest, actionRequest);

        //Run callAPILoginPassenger


    }

    public void getDetailAttachment(String recordid, String linenum) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetDetailAttachmentResponse response = gson.fromJson(result.toString(), GetDetailAttachmentResponse.class);
                    getAttachments().clear();
                    for (GetDetailAttachmentResponse.Data data : response.result.data) {
                        Attachment attachment = new Attachment();
                        attachment.setFilename(data.filename);
                        attachment.setDocumentcode(data.documentcode);
                        getAttachments().add(attachment);
                    }
                    notifyObserversOfEvent(ModelEvent.GET_DETAIL_ATTACHMENT_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetDetailAttachmentRequest commentRequest = new GetDetailAttachmentRequest();
        commentRequest.referencekey1 = recordid;
        commentRequest.referencekey2 = linenum;
        commentRequest.linkto = "WLT";
        commentRequest.limit = 15;

        ActionRequest actionRequest = new ActionRequest("FrmFdDocument", "getLink");
        APIConnectionRequest.API_GET_DETAIL_ATTACHMENT(callBackDone, commentRequest, actionRequest);

    }

    public void callDeleteComment(String linenum, String recordid) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetDeleteCommentResponse response = gson.fromJson(result.toString(), GetDeleteCommentResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        notifyObserversOfEvent(ModelEvent.DELETE_MESSAGE_SUCCESS);
                    }

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        DeleteCommentRequest commentRequest = new DeleteCommentRequest();
        commentRequest.recordid = recordid;
        commentRequest.linenum = Integer.valueOf(linenum);

        ActionRequest actionRequest = new ActionRequest("FrmCcWork", "delCmt");
        APIConnectionRequest.API_DEL_COMMENT(callBackDone, commentRequest, actionRequest);

        //Run callAPILoginPassenger


    }

    public void getDetailAttachment(String documentCode) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        APIConnectionRequest.API_GET_URL_ATTACHMENT(callBackDone, null, documentCode, null);

    }

    public void getListUsers() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetListUsersResponse response = gson.fromJson(result.toString(), GetListUsersResponse.class);
                    getUsers().clear();
                    for (GetListUsersResponse.Data data : response.result.data) {
                        getUsers().add(data);
                    }

                    notifyObserversOfEvent(ModelEvent.GET_LIST_USERS_SUCCESS);

                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetListUsersRequest commentRequest = new GetListUsersRequest();
        commentRequest.start = 0;
        commentRequest.limit = 15;
        commentRequest.isShortData = "Y";

        ActionRequest actionRequest = new ActionRequest("FrmHrEmployee", "get");
        APIConnectionRequest.API_GET_LIST_USERS(callBackDone, commentRequest, actionRequest);

    }

    public void getJobType() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetJobTYpeResponse response = gson.fromJson(result.toString(), GetJobTYpeResponse.class);
                    getTypes().clear();
                    for (GetJobTYpeResponse.Data data : response.result.data) {
                        getTypes().add(data);
                    }

                    notifyObserversOfEvent(ModelEvent.GET_LIST_JOB_TYPE_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetJobTypeRequest commentRequest = new GetJobTypeRequest();
        commentRequest.start = 0;
        commentRequest.limit = 15;
        commentRequest.isShortData = "Y";
        commentRequest.category = "CALLTYPE";

        ActionRequest actionRequest = new ActionRequest("FrmCsCodeDictionary", "get");
        APIConnectionRequest.API_GET_JOB_TYPE(callBackDone, commentRequest, actionRequest);

    }

    public void getJobPriority() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetJobPriorityResponse response = gson.fromJson(result.toString(), GetJobPriorityResponse.class);
                    getPriorities().clear();
                    for (GetJobPriorityResponse.Data data : response.result.data) {
                        getPriorities().add(data);
                    }

                    notifyObserversOfEvent(ModelEvent.GET_LIST_JOB_PRIORITY_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetJobTypeRequest commentRequest = new GetJobTypeRequest();
        commentRequest.start = 0;
        commentRequest.limit = 15;
        commentRequest.isShortData = "Y";
        commentRequest.category = "CALLPRIORITY";

        ActionRequest actionRequest = new ActionRequest("FrmCsCodeDictionary", "get");
        APIConnectionRequest.API_GET_JOB_PRIORITY(callBackDone, commentRequest, actionRequest);

    }

    public void postCreateTask(FormTask formTask, String recordType) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetPostTaskResponse response = gson.fromJson(result.toString(), GetPostTaskResponse.class);
                    if(response.result.success){
                        notifyObserversOfEvent(ModelEvent.POST_TASK_SUCCESS);
                    }


                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        PostCreateTaskRequest commentRequest = new PostCreateTaskRequest();
        commentRequest.startdate = formTask.getStartDate();
        commentRequest.taskmaster = formTask.getTaskMaster();
        commentRequest.recordtype = recordType;
        commentRequest.status = formTask.getStatus();
        commentRequest.title = formTask.getTitle();
        commentRequest.duedate = formTask.getDueDate();
        commentRequest.enddate = formTask.getEndDate();
        commentRequest.jobtype = formTask.getJobType();
        commentRequest.priority = formTask.getJobPrioriy();
        commentRequest.description = formTask.getDescription();
        commentRequest.email = formTask.getEmail();
        commentRequest.cc = formTask.getCc();
        commentRequest.dagid = formTask.getDagid();
        commentRequest.pic = formTask.getPersonCharge();
        commentRequest.parentid = formTask.getParentId();
        commentRequest.anal_wlt0 = formTask.getAnal_wlt0();
        commentRequest.anal_wlt1 = formTask.getAnal_wlt1();

        for (int i = 0; i < formTask.getImageByteArray().size(); i++) {
            commentRequest.attachments.add(commentRequest.new Attachment(getUniqueImageFilename(), (byte[]) formTask.getImageByteArray().get(i)));
        }


        ActionRequest actionRequest = new ActionRequest("FrmCcWork", "add");
        APIConnectionRequest.API_POST_CREATE_TASK(callBackDone, commentRequest, actionRequest);

    }

    public void postUpdateTask(FormTask formTask, String recordType, String recordId) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    GetPostTaskResponse response = gson.fromJson(result.toString(), GetPostTaskResponse.class);
                    if(response.result.success){
                        notifyObserversOfEvent(ModelEvent.POST_TASK_SUCCESS);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        PostUpdateTaskRequest commentRequest = new PostUpdateTaskRequest();
        commentRequest.startdate = formTask.getStartDate();
        commentRequest.taskmaster = formTask.getTaskMaster();
        commentRequest.recordtype = recordType;
        commentRequest.status = formTask.getStatus();
        commentRequest.title = formTask.getTitle();
        commentRequest.duedate = formTask.getDueDate();
        commentRequest.enddate = formTask.getEndDate();
        commentRequest.jobtype = formTask.getJobType();
        commentRequest.priority = formTask.getJobPrioriy();
        commentRequest.description = formTask.getDescription();
        commentRequest.email = formTask.getEmail();
        commentRequest.cc = formTask.getCc();
        commentRequest.dagid = formTask.getDagid();
        commentRequest.pic = formTask.getPersonCharge();
        commentRequest.recordid = recordId;
        commentRequest.anal_wlt0 = formTask.getAnal_wlt0();
        commentRequest.anal_wlt1 = formTask.getAnal_wlt1();

        for (int i = 0; i < formTask.getImageByteArray().size(); i++) {
            commentRequest.attachments.add(commentRequest.new Attachment(getUniqueImageFilename(), (byte[]) formTask.getImageByteArray().get(i)));
        }

        ActionRequest actionRequest = new ActionRequest("FrmCcWork", "upd");
        APIConnectionRequest.API_POST_CREATE_TASK(callBackDone, commentRequest, actionRequest);

    }

    public void downloadImageURL(String url, String fileName) {

        CallBackDownloadAPI callBackDone = new CallBackDownloadAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackDownloadAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(byte[] result) {
                if (result != null) {
                    setImage(result);
                    notifyObserversOfEvent(ModelEvent.DOWNLOAD_IMAGE_SUCCESS);
                    Log.i("adadadadad", "adadadadadad");
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                setMessageError(error.getMessage());
                setTitle(error.getCode());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
            }

            @Override
            public void onComplete() {

            }
        });

        APIConnectionRequest.API_DOWNLOAD_IMAGE(callBackDone, url, fileName);

    }

    public void getAccessGroup() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetAccessGroupResponse response = gson.fromJson(result.toString(), GetAccessGroupResponse.class);
                    getPriorities().clear();
                    for (GetAccessGroupResponse.Data data : response.result.data) {
                        getAccessGroups().add(data);
                    }

                    notifyObserversOfEvent(ModelEvent.GET_ACCESS_GROUP_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetAccessGroupResquest commentRequest = new GetAccessGroupResquest();
        commentRequest.isShortData = "Y";
        commentRequest.start = 0;
        commentRequest.limit = 15;


        ActionRequest actionRequest = new ActionRequest("FrmCsDataAccessGroup", "get");
        APIConnectionRequest.API_GET_ACCESS_GROUP(callBackDone, commentRequest, actionRequest);

    }

    public void getCommentOfTask(String recordId) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetCommentTaskResponse response = gson.fromJson(result.toString(), GetCommentTaskResponse.class);
                    getCommentTasks().clear();
                    for (GetCommentTaskResponse.Data data : response.result.data) {
                        getCommentTasks().add(data);
                    }

                    notifyObserversOfEvent(ModelEvent.GET_COMMENT_TASK_SUCCESS);
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API

        GetCommentRequest commentRequest = new GetCommentRequest();
        commentRequest.recordid = recordId;

        ActionRequest actionRequest = new ActionRequest("FrmCcWork" , "getCmt");
        APIConnectionRequest.API_GET_COMMENT_TASK(callBackDone, commentRequest, actionRequest);

    }

    public void updateCommentOfTask(String recordid, String linenum, String comment) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetAddMesageResponse response = gson.fromJson(result.toString(), GetAddMesageResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        notifyObserversOfEvent(ModelEvent.UPDATE_MESSAGE_SUCCESS);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        UpdateCommentRequest commentRequest = new UpdateCommentRequest();
        commentRequest.recordid = recordid;
        commentRequest.comments = comment;
        commentRequest.linenum = Integer.valueOf(linenum);

        ActionRequest actionRequest = new ActionRequest("FrmCcWork" , "addCmt");
        APIConnectionRequest.API_UPDATE_COMMENT_TASK(callBackDone, commentRequest, actionRequest);

    }

    public void getListBuiding() {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetListBuildingResponse response = gson.fromJson(result.toString(), GetListBuildingResponse.class);
                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getBuildings().clear();
                        for (GetListBuildingResponse.Data data : response.result.data) {
                            getBuildings().add(data);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_BUILDINGS_SUCCESS);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetListBuildingResquest commentRequest = new GetListBuildingResquest();
        commentRequest.isShortData = "Y";

        ActionRequest actionRequest = new ActionRequest("FrmPmBlock" , "get");
        APIConnectionRequest.API_GET_LIST_BUILDING(callBackDone, commentRequest, actionRequest);

    }

    public void getListAparment(String value) {

        CallBackAPI callBackDone = new CallBackAPI();
        callBackDone.setMyTaskCompleteListener(new CallBackAPI.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(JSONObject result) {
                if (result != null) {
                    Gson gson = new Gson();
                    //Parse json from result
                    GetListApartmentRespone response = gson.fromJson(result.toString(), GetListApartmentRespone.class);
                    //get idpassenger from json to setIdPassenger.
                    if (response.result.success) {
                        getApartments().clear();
                        for (GetListApartmentRespone.Data data : response.result.data) {
                            getApartments().add(data);
                        }
                        notifyObserversOfEvent(ModelEvent.GET_APARTMENTS_SUCCESS);
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ModelObject modelObject) {

            }

            @Override
            public void onFail(ModelError error) {
                Log.v("TestLog", "ERROR" + error.getCode() + " : " + error.getMessage());
                notifyObserversOfEvent(ModelEvent.ERRORUNSPECIFIED_ERROR);
                setMessageError(error.getMessage());
                setTitle(error.getCode());
            }

            @Override
            public void onComplete() {

            }
        });

        //Request API
        GetListApartmentResquest commentRequest = new GetListApartmentResquest();
        commentRequest.isShortData = "Y";
        commentRequest.filters.add(commentRequest.new Filter(value));

        ActionRequest actionRequest = new ActionRequest("FrmPmProperty" , "get");
        APIConnectionRequest.API_GET_LIST_APARTMENT(callBackDone, commentRequest, actionRequest);

    }

    public void notifyObserversOfEvent(ModelEvent mEvent) {
        setChanged();
        TaxiLoyDebug.d("Model notifying observers: " + mEvent);
        notifyObservers(mEvent);
    }

    private String getUniqueImageFilename() {
        return "img_" + System.currentTimeMillis() + ".jpg";
    }
}

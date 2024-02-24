package demo.example.com.tvc_erp.ui.enums;

/**
 * Created by prosoft on 1/12/16.
 */
public enum ModelEvent {
    INITIAL(0),

    LOGIN_SUCCESS(0),
    LOGIN_FAIL(1),
    LOGIN_ALREADY(2),
    GET_TASK_SUCCESS(3),

    GET_DETAIL_ATTACHMENT_SUCCESS(4),
    GET_LIST_USERS_SUCCESS(5),
    GET_LIST_JOB_TYPE_SUCCESS(6),
    GET_LIST_JOB_PRIORITY_SUCCESS(7),
    GET_ACCESS_GROUP_SUCCESS(8),
    ADD_ACTION_TASK_SUCCESS(9),
    ADD_MESSAGE_SUCCESS(10),
    DELETE_MESSAGE_SUCCESS(11),
    DOWNLOAD_IMAGE_SUCCESS(12),
    POST_TASK_SUCCESS(13),
    GET_COMMENT_TASK_SUCCESS(14),
    UPDATE_MESSAGE_SUCCESS(15),
    GET_BUILDINGS_SUCCESS(16),
    GET_APARTMENTS_SUCCESS(17),
    ERRORUNSPECIFIED_ERROR(32);

    private final int value;

    ModelEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

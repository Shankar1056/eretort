package com.apextechies.eretort.retrofit;


import com.apextechies.eretort.BuildConfig;

public class ApiUrl {
    public static final String BASE_URL = "http://eretort.com/android_eretort_api/index.php/";
    public static final String API_KEY = "1a!2b@3c#4d$5e%6f^";

    public static final String ABOUT_US_API_URL = "about_us";
    public static final String APPLYLEAVE = "applyLeave";
    public static final String LEAVEREQUESTLIST = "leave_RequestList";
    public static final String UPDATELEAVE = "updateLeave";
    public static final String TEACHERLIST = "get_teacherList";
    public static final String ADDTEACHER = "add_teacher";
    public static final String ADDSTUDENT = "add_student";
    public static final String GALLERYLIST = "get_gallery";
    public static final String TOPPERLIST = "get_topperList";
    public static final String FEEDBACK = "add_Feedback";
    public static final String LOGIN = "user_login";
    public static final String UPDATETEACHER = "update_Teacher";
    public static final String TIME_TABLE = "get_TimeTable";
    public static final String GET_CLASS_WISE_STUDENT_LIST = "studentlist_byclas_sec";
    public static final String GET_CLASS_WISE_FEE_DETAILS = "get_fee_type";
    public static final String INSER_TATTENDANCE = "insert_attendance";
    public static final String GET_STUDENT_ATTENDANCE_LIST = "get_student_attendance_list";
    public static final String DELETE_STUDENT = "delete_student";
    public static final String CHANGE_PASSWORD = "change_user_password";
    public static final String ADD_EVENT = "upcoming_events";
    public static final String GET_EVENT_LIST = "events_list";
    public static final String PUBLISH_NOTICE = "notice_board";
    public static final String GET_NOTICE_LIST = "get_notice_board";
    public static final String EDIT_DELETE_NOTICE = "update_notice";
    public static final String EDIT_DELETE_EVENT = "update_events";
    public static final String GENERIC_API = "app_version";
    public static final String SEND_MESSAGE = BASE_URL + "send_sms";
    public static String PLAYSTORE_LINK = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
    public static String ADMISSION_API = "takeAdmission.php";

    public static final String QUIZ_CATEGORY = "get_quiz_cat";
    public static final String QUIZ_SUBCATEGORY = "get_quiz_sub_cat";
    public static final String QUIZ_QUESTION = "get_quiz_question";
}

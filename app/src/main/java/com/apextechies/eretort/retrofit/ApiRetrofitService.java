package com.apextechies.eretort.retrofit;


import android.support.annotation.Nullable;

import com.apextechies.eretort.model.AboutUsModel;
import com.apextechies.eretort.model.CategoryModel;
import com.apextechies.eretort.model.CommonResponse;
import com.apextechies.eretort.model.EventsAndNoticeLisrModel;
import com.apextechies.eretort.model.FeeDetailsModel;
import com.apextechies.eretort.model.FeedBackModel;
import com.apextechies.eretort.model.GalleryModel;
import com.apextechies.eretort.model.InsertAttendanceModel;
import com.apextechies.eretort.model.QuesAnsModel;
import com.apextechies.eretort.model.StuTeaModel;
import com.apextechies.eretort.model.StudentListByClassModel;
import com.apextechies.eretort.model.SubCategoryModel;
import com.apextechies.eretort.model.TeacherListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Shankar on 1/27/2018.
 */

public interface ApiRetrofitService {

    @POST(ApiUrl.ABOUT_US_API_URL)
    @FormUrlEncoded
    Call<AboutUsModel> getAboutUsDetails(@Header("x-api-key") String apiKey,
                                         @Header("api_token") String apiToken,
                                         @Field("type") String userTpe,
                                         @Field("school_id") String schoolId,
                                         @Field("id") String getDetailsFor);




    @POST(ApiUrl.ADDTEACHER)
    @FormUrlEncoded
    Call<StuTeaModel> addTeacher(@Header("x-api-key") String apiKey,
                                 @Header("api_token") String apiToken,
                                 @Field("type") String userTpe,
                                 @Field("school_id") String schoolId,
                                 @Field("teacher_id") String teacher_id, @Field("name") String name, @Field("qualification") String qualification
            , @Field("mobile_number") String mobile_number, @Field("alternate_number") String alternate_number, @Field("email_id") String email_id
            , @Field("classteacher_for") String classteacher_for, @Field("joining_date") String joining_date, @Field("address") String address
            , @Field("operation") String operation, @Field("image") String image, @Field("facebook_id") String facebook_id, @Field("what_teach") String what_teach);

    @POST(ApiUrl.ADDSTUDENT)
    @FormUrlEncoded
    Call<StuTeaModel> addStudent(@Header("x-api-key") String apiKey,
                                 @Header("api_token") String apiToken,
                                 @Field("admin_id") String userTpe,
                                 @Field("school_id") String schoolId,
                                 @Field("user_id") String user_id, @Field("roll_no") String roll_no, @Field("name") String name,
                                 @Field("email") String email, @Field("mobile") String mobile_number, @Field("class_id") String clas,
                                 @Field("sec_id") String sec, @Field("admission_date") String admission_date, @Field("residential_address") String residential_address,
                                 @Field("father_name") String fatherName, @Field("fateher_mobile") String fateherMobile,
                                 @Field("mother_name") String motherName, @Field("mother_mobile") String motherMobile);

    @FormUrlEncoded
    @POST(ApiUrl.GALLERYLIST)
    Call<GalleryModel> listGallery(@Header("x-api-key") String apiKey,
                                   @Header("api_token") String apiToken,
                                   @Field("type") String userTpe,
                                   @Field("school_id") String schoolId);

    @POST(ApiUrl.FEEDBACK)
    @FormUrlEncoded
    Call<FeedBackModel> sendFeedback(@Header("x-api-key") String apiKey,
                                     @Header("api_token") String apiToken,
                                     @Field("type") String userTpe,
                                     @Field("school_id") String schoolId,
                                     @Field("name") String name, @Field("mobile") String mobile, @Field("message") String message, @Field("rating") String rating, @Field("date") String date);

    @POST(ApiUrl.UPDATETEACHER)
    @FormUrlEncoded
    Call<CommonResponse> updateTeacher(@Header("x-api-key") String apiKey,
                                       @Header("api_token") String apiToken,
                                       @Field("type") String userTpe,
                                       @Field("school_id") String schoolId,
                                       @Field("teacher_id") String teacher_id, @Field("name") String name, @Field("mobile_number") String mobile_number, @Field("alternate_number") String alternate_number
            , @Field("email_id") String email_id, @Field("address") String address, @Field("qualification") String qualification);



    @POST(ApiUrl.GET_CLASS_WISE_STUDENT_LIST)
    @FormUrlEncoded
    Call<StudentListByClassModel> getStudentsListByClassWise(@Field("class_id") String className,
                                                             @Field("sec_id") String sec);

    @POST(ApiUrl.GET_CLASS_WISE_FEE_DETAILS)
    @FormUrlEncoded
    Call<FeeDetailsModel> getClassWiseFeeDetailsList(@Header("x-api-key") String apiKey,
                                                     @Header("api_token") String apiToken,
                                                     @Field("type") String userTpe,
                                                     @Field("school_id") String schoolId,
                                                     @Field("class") String className,
                                                     @Field("session") String sec,
                                                     @Field("operation") String operation);

    @POST(ApiUrl.GET_STUDENT_ATTENDANCE_LIST)
    @FormUrlEncoded
    Call<StudentListByClassModel> getStudentsAttendance(@Header("x-api-key") String apiKey,
                                                        @Header("api_token") String apiToken,
                                                        @Field("type") String userTpe,
                                                        @Field("school_id") String schoolId,
                                                        @Field("class") String className, @Field("sec") String sec, @Field("studentId") String studentId, @Field("date") String date);

    @POST(ApiUrl.INSER_TATTENDANCE)
    Call<CommonResponse> insertAttendance(@Header("x-api-key") String apiKey,
                                          @Header("api_token") String apiToken,
                                          @Body InsertAttendanceModel insertAttendanceModel);

    @FormUrlEncoded
    @POST(ApiUrl.DELETE_STUDENT)
    Call<CommonResponse> deleteStudent(@Header("x-api-key") String apiKey,
                                       @Header("api_token") String apiToken,
                                       @Field("type") String userTpe,
                                       @Field("school_id") String schoolId,
                                       @Field("user_id") String deleteStudent);

    @FormUrlEncoded
    @POST(ApiUrl.CHANGE_PASSWORD)
    Call<CommonResponse> changePassword(@Header("x-api-key") String apiKey,
                                        @Header("api_token") String apiToken,
                                        @Field("type") String userTpe,
                                        @Field("school_id") String schoolId,
                                        @Field("user_id") String userId,
                                        @Field("old_password") String oldPassword,
                                        @Field("new_password") String newPassword);

    @FormUrlEncoded
    @POST(ApiUrl.PUBLISH_NOTICE)
    Call<CommonResponse> publishNotice(@Field("title") String title, @Field("message") String message, @Field("date") String date);

    @FormUrlEncoded
    @POST(ApiUrl.ADD_EVENT)
    Call<CommonResponse> addEvent(@Field("title") String title, @Field("message") String message, @Field("date") String date, @Field("posted_by") String posted_by);

    @FormUrlEncoded
    @POST(ApiUrl.GET_EVENT_LIST)
    Call<EventsAndNoticeLisrModel> getEventsList(@Header("x-api-key") String apiKey,
                                                 @Header("api_token") String apiToken,
                                                 @Field("type") String userTpe,
                                                 @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST(ApiUrl.GET_NOTICE_LIST)
    Call<EventsAndNoticeLisrModel> getNoticeBoardList(@Header("x-api-key") String apiKey,
                                                      @Header("api_token") String apiToken,
                                                      @Field("type") String userTpe,
                                                      @Field("school_id") String schoolId);

    @FormUrlEncoded
    @POST(ApiUrl.EDIT_DELETE_EVENT)
    Call<CommonResponse> callUpdateDeleteEvents(@Header("x-api-key") String apiKey,
                                                @Header("api_token") String apiToken,
                                                @Field("type") String userTpe,
                                                @Field("school_id") String schoolId,
                                                @Field("title") String title, @Field("message") String message, @Field("date") String date, @Field("posted_by") String posted_by,
                                                @Field("status") String status, @Field("events_id") String id, @Field("operation") String isToUpdate);

    @FormUrlEncoded
    @POST(ApiUrl.EDIT_DELETE_NOTICE)
    Call<CommonResponse> callUpdateDeleteNoticeBoard(@Header("x-api-key") String apiKey,
                                                     @Header("api_token") String apiToken,
                                                     @Field("type") String userTpe,
                                                     @Field("school_id") String schoolId,
                                                     @Field("title") String title, @Field("message") String message, @Field("date") String date, @Field("posted_by") String posted_by,
                                                     @Field("status") String status, @Field("notice_id") String id, @Field("operation") String isToUpdate);


    @FormUrlEncoded
    @POST(ApiUrl.TEACHERLIST)
    Call<TeacherListModel> teacherList(@Field("school_id") String schoolId);


}

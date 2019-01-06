package com.apextechies.eretort.retrofit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.DialogClickListener;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.model.AboutUsModel;
import com.apextechies.eretort.model.CategoryModel;
import com.apextechies.eretort.model.CommonResponse;
import com.apextechies.eretort.model.EventsAndNoticeLisrModel;
import com.apextechies.eretort.model.FeeDetailsModel;
import com.apextechies.eretort.model.FeedBackModel;
import com.apextechies.eretort.model.GeneralParamsModel;
import com.apextechies.eretort.model.InsertAttendanceModel;
import com.apextechies.eretort.model.QuesAnsModel;
import com.apextechies.eretort.model.StuTeaModel;
import com.apextechies.eretort.model.StudentListByClassModel;
import com.apextechies.eretort.model.SubCategoryModel;
import com.apextechies.eretort.model.TeacherListModel;
import com.apextechies.eretort.utilz.Utilz;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitDataProvider extends AppCompatActivity implements ServiceMethods {
    Context context;

    public RetrofitDataProvider(Context context) {
        this.context = context;
    }

    private ApiRetrofitService createRetrofitService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiRetrofitService.class);
    }

    @Override
    public void addteacher(final Context mActivity, String teacher_id, String name, String qualification, String mobile_number, String alternate_number,
                           String email_id, String classteacher_for, String joining_date, String address, String mStrOperation, String mStrImageUrl, String facebookId, String whatTeach, final DownlodableCallback<StuTeaModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().addTeacher(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                teacher_id, name, qualification, mobile_number, alternate_number, email_id, classteacher_for, joining_date, address, mStrOperation, mStrImageUrl, facebookId, whatTeach).enqueue(
                new Callback<StuTeaModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StuTeaModel> call, @NonNull final Response<StuTeaModel> response) {
                        if (response.isSuccessful()) {
                            StuTeaModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StuTeaModel> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void addstudent(final Context mActivity, String user_id, String rollNumber, String name, String email, String mobile, String clas, String sec,
                           String admission_date, String residential_address, String fName, String fMobile, String mName, String mMobile, final DownlodableCallback<StuTeaModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().addStudent(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                user_id, rollNumber, name, email, mobile, clas, sec, admission_date, residential_address, fName, fMobile, mName, mMobile).enqueue(
                new Callback<StuTeaModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StuTeaModel> call, @NonNull final Response<StuTeaModel> response) {
                        if (response.isSuccessful()) {
                            StuTeaModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StuTeaModel> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }


    private GeneralParamsModel getGeneralParameters() {
        String apiKeyStr = ApiUrl.API_KEY;
        String tokenStr = ClsGeneral.getStrPreferences(AppConstants.TOKEN);
        String loginAsStr = ClsGeneral.getStrPreferences(AppConstants.LOGIN_AS);
        String schoolIdStr = ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID);

        GeneralParamsModel generalParamsModel = new GeneralParamsModel(apiKeyStr, tokenStr, loginAsStr, schoolIdStr);

        return generalParamsModel;
    }



    @Override
    public void feedback(final Context mActivity, String name, String mobile, String message, String rating, String date, final DownlodableCallback<FeedBackModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().sendFeedback(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                name, mobile, message, rating, date).enqueue(
                new Callback<FeedBackModel>() {
                    @Override
                    public void onResponse(@NonNull Call<FeedBackModel> call, @NonNull final Response<FeedBackModel> response) {
                        if (response.isSuccessful()) {
                            FeedBackModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FeedBackModel> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }




    private void handleResponseCode(final Context mActivity, int code) {
        Utilz.closeDialog();
        /*if (code == AppConstants.RESPONSE_CODE_201) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.send_message), getChoiceDialogClickListener(mActivity, code));
        } else*/
        if (code == AppConstants.RESPONSE_CODE_204) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.no_data_found), mActivity.getResources().getString(R.string.no_data_found_message), getChoiceDialogClickListener(mActivity, code));
        } else if (code == AppConstants.RESPONSE_CODE_400) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.invalid_request), mActivity.getResources().getString(R.string.invalid_request_message), getChoiceDialogClickListener(mActivity, code));
        } else if (code == AppConstants.RESPONSE_CODE_405) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.invalid_request), mActivity.getResources().getString(R.string.invalid_request_message), getChoiceDialogClickListener(mActivity, code));
        } else if (code == AppConstants.RESPONSE_CODE_401) {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.session_expired), mActivity.getResources().getString(R.string.invalid_session_login_again), getChoiceDialogClickListener(mActivity, code));
        } else {
            Utilz.showMessageDialogForResponseCodes(mActivity, mActivity.getResources().getString(R.string.something_went_wrong), mActivity.getResources().getString(R.string.something_went_wrong_error_message), getChoiceDialogClickListener(mActivity, code));
        }
    }

    private DialogClickListener getChoiceDialogClickListener(final Context context, final int code) {
        return new DialogClickListener() {
            @Override
            public void onClickOfPositive() {
                if (code == AppConstants.RESPONSE_CODE_201) {
                } else if (code == AppConstants.RESPONSE_CODE_204) {
                } else if (code == AppConstants.RESPONSE_CODE_400) {
                } else if (code == AppConstants.RESPONSE_CODE_401) {
                    //logout and go to login screen
                  //  Utilz.logout(context);
                } else if (code == AppConstants.RESPONSE_CODE_405) {
                } else {
                }
            }
        };
    }



    @Override
    public void getStudentsListByClassWise(final Context mActivity, String className, String sec, final DownlodableCallback<StudentListByClassModel> callback) {
        createRetrofitService().getStudentsListByClassWise("10", "a").enqueue(
                new Callback<StudentListByClassModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StudentListByClassModel> call, @NonNull final Response<StudentListByClassModel> response) {
                        if (response.isSuccessful()) {
                            StudentListByClassModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StudentListByClassModel> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void getClassWiseFeeDetailsList(final Context mActivity, String className, String session, String operation, final DownlodableCallback<FeeDetailsModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getClassWiseFeeDetailsList(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                className, session, operation).enqueue(
                new Callback<FeeDetailsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<FeeDetailsModel> call, @NonNull final Response<FeeDetailsModel> response) {
                        if (response.isSuccessful()) {
                            FeeDetailsModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<FeeDetailsModel> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void getStudentsAttendance(final Context mActivity, String className, String sec, String studentId, String date, final DownlodableCallback<StudentListByClassModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().getStudentsAttendance(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                className, sec, studentId, date).enqueue(
                new Callback<StudentListByClassModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StudentListByClassModel> call, @NonNull final Response<StudentListByClassModel> response) {
                        if (response.isSuccessful()) {
                            StudentListByClassModel teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StudentListByClassModel> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void attendance(final Context mActivity, InsertAttendanceModel insertAttendanceModel, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().insertAttendance(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                insertAttendanceModel).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void deleteStudent(final Context mActivity, String studentUserIdStr, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().deleteStudent(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                studentUserIdStr).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void changePassword(final Context mActivity, String userId, String loginType, String oldPassword, String newPassword, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().changePassword(generalParamsModel.getApiKey(),
                generalParamsModel.getToken(),
                generalParamsModel.getLoginAs(),
                generalParamsModel.getSchoolId(),
                userId, oldPassword, newPassword).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void addEvent(final Context mActivity, String title, String message, String date, String posted_by, String status, final DownlodableCallback<CommonResponse> callback) {
        createRetrofitService().addEvent(title, message, date, posted_by).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    public void publishNotice(final Context mActivity, String title, String message, String date, String posted_by, String status, final DownlodableCallback<CommonResponse> callback) {
        createRetrofitService().publishNotice(
                title, message, date).enqueue(
                new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                        if (response.isSuccessful()) {
                            CommonResponse teacherListDataModelPojo = response.body();
                            callback.onSuccess(teacherListDataModelPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }
    @Override
    public void teacherList(final Context mActivity, final DownlodableCallback<TeacherListModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        createRetrofitService().teacherList(generalParamsModel.getSchoolId()).enqueue(
                new Callback<TeacherListModel>() {
                    @Override
                    public void onResponse(@NonNull Call<TeacherListModel> call, @NonNull final Response<TeacherListModel> response) {
                        if (response.isSuccessful()) {
                            TeacherListModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);
                        } else {
                            handleResponseCode(mActivity, response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TeacherListModel> call, @NonNull Throwable t) {
                        Utilz.showLog("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    public void callUpdateDeleteNoticeAndEvents(final Context mActivity, String title, String message, String date, String posted_by, String status, String mApiUrl, String mNoticeEventId, boolean isToUpdate, final DownlodableCallback<CommonResponse> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        String isToUpdateStr;
        if (isToUpdate)
            isToUpdateStr = AppConstants.UPDATE;
        else
            isToUpdateStr = AppConstants.DELETE;
        Callback callback1 = new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull final Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    CommonResponse teacherListDataModelPojo = response.body();
                    callback.onSuccess(teacherListDataModelPojo);
                } else {
                    handleResponseCode(mActivity, response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        };
        if (!TextUtils.isEmpty(mApiUrl) && mApiUrl.equalsIgnoreCase(ApiUrl.EDIT_DELETE_NOTICE))
            createRetrofitService().callUpdateDeleteNoticeBoard(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId(),
                    title, message, date, posted_by, status, mNoticeEventId, isToUpdateStr).enqueue(callback1);
        else
            createRetrofitService().callUpdateDeleteEvents(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId(),
                    title, message, date, posted_by, status, mNoticeEventId, isToUpdateStr).enqueue(callback1);
    }

    @Override
    public void getEventsOrNoticeList(final Context mActivity, final boolean isToGetEventsList, final DownlodableCallback<EventsAndNoticeLisrModel> callback) {
        GeneralParamsModel generalParamsModel = getGeneralParameters();
        Callback callback1 = new Callback<EventsAndNoticeLisrModel>() {
            @Override
            public void onResponse(@NonNull Call<EventsAndNoticeLisrModel> call, @NonNull final Response<EventsAndNoticeLisrModel> response) {
                if (response.isSuccessful()) {
                    EventsAndNoticeLisrModel mobileRegisterPojo = response.body();
                    callback.onSuccess(mobileRegisterPojo);
                } else {
                    handleResponseCode(mActivity, response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventsAndNoticeLisrModel> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        };

        if (isToGetEventsList) {
            createRetrofitService().getEventsList(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId()).enqueue(callback1);
        } else {
            createRetrofitService().getNoticeBoardList(generalParamsModel.getApiKey(),
                    generalParamsModel.getToken(),
                    generalParamsModel.getLoginAs(),
                    generalParamsModel.getSchoolId()).enqueue(callback1);
        }
    }



}
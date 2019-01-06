package com.apextechies.eretort.retrofit;


import android.app.Activity;
import android.content.Context;

import com.apextechies.eretort.model.CommonResponse;
import com.apextechies.eretort.model.EventsAndNoticeLisrModel;
import com.apextechies.eretort.model.FeeDetailsModel;
import com.apextechies.eretort.model.FeedBackModel;
import com.apextechies.eretort.model.InsertAttendanceModel;
import com.apextechies.eretort.model.StuTeaModel;
import com.apextechies.eretort.model.StudentListByClassModel;
import com.apextechies.eretort.model.TeacherListModel;



/**
 * Created by Shankar on 1/27/2018.
 */

public interface ServiceMethods {


    void addteacher(Context mActivity, String teacher_id, String name, String qualification, String mobile_number, String alternate_number, String email_id,
                    String classteacher_for, String joining_date, String address, String operation, String mStrImageUrl, String facebookId, String whatTeach, DownlodableCallback<StuTeaModel> callback);

    void addstudent(Context mActivity, String user_id, String roll_no, String name, String email, String mobile, String clas, String sec,
                    String admission_date, String residential_address, String fName, String fMobile, String mName, String mMobile, DownlodableCallback<StuTeaModel> callback);



    void feedback(Context mActivity, String name, String mobile, String message, String rating, String date, DownlodableCallback<FeedBackModel> callback);

    void getStudentsListByClassWise(Context mActivity, String clas, String sec, DownlodableCallback<StudentListByClassModel> callback);

    void getClassWiseFeeDetailsList(Context mActivity, String clas, String session, String operation, DownlodableCallback<FeeDetailsModel> callback);

    void getStudentsAttendance(Context mActivity, String className, String sec, String studentId, String date, DownlodableCallback<StudentListByClassModel> callback);

    void attendance(Context mActivity, InsertAttendanceModel insertAttendanceModel, DownlodableCallback<CommonResponse> callback);

    void deleteStudent(Context mActivity, String studentRegId, DownlodableCallback<CommonResponse> callback);

    void getEventsOrNoticeList(Context mActivity, boolean isToGetEventsList, DownlodableCallback<EventsAndNoticeLisrModel> callback);
    void teacherList(Context mActivity, DownlodableCallback<TeacherListModel> callback);

}

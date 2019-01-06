package com.apextechies.eretort.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.adapter.AttendanceAdapter;
import com.apextechies.eretort.common.CsvFileWriter;
import com.apextechies.eretort.common.Prefs;
import com.apextechies.eretort.model.Student;
import com.apextechies.eretort.utilz.RealMController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Shankar on 1/14/2018.
 */

public class Attendance extends AppCompatActivity {
    private static final String TAG = Attendance.class.getSimpleName();
    public static final int REQ_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 301;
    public static final String PERMISSION_READ_EXT_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String PERMISSION_WRITE_EXT_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static String[] mStrArrExternalStorageReadWritePermissions = {PERMISSION_READ_EXT_STORAGE, PERMISSION_WRITE_EXT_STORAGE};
    private String fileName = "";
    private TextView filePath;
    private Activity mActivity;
    private Realm realm;
    private RecyclerView mRecyclerView;
    private AttendanceAdapter mAdapter;
    private List<Student>
            studentList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);

        initView();

        initReam();

        getStudentListFromServer();

        manageStudentList();

        manageCreateAndUploadAttendence();

        checkPermissionForReadStorage();
    }

    private void initReam() {
        //get realm instance
        this.realm = RealMController.with(this).getRealm();

        // refresh the realm instance
        RealMController.with(this).refresh();
    }

    private void initView() {
        mActivity = this;
        studentList = new ArrayList();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        filePath = (TextView) findViewById(R.id.filePath);
    }

    private void manageCreateAndUploadAttendence() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Attendence");

        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();

        Log.i(TAG, "var : " + var);

        fileName = folder.toString() + "/" + "Class8.csv";
        filePath.setText(fileName);
    }

    private void getStudentListFromServer() {
        //Use Here your common internet checker method
        boolean internetConnection = true;
        if (internetConnection) {
            //TODO Need to get this list from API call
            for (int i = 1; i <= 15; i++) {
                Student st = new Student("SchoolName" + i, "Student Name " + i, "Father Name " + i, "F", 21);
                studentList.add(st);
            }
            //saving loaded data from server to realm
            if (studentList != null && studentList.size() > 0) {
                for (Student student : studentList) {
                    // Persist your data easily
                    realm.beginTransaction();
                    try {
                        realm.copyToRealm(student);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    realm.commitTransaction();
                }

                Prefs.setPreLoad(true);
            }
        } else if (Prefs.isPreLoaded()) {
            RealMController realMController = RealMController.with(Attendance.this);
            if (realMController != null)
                studentList.addAll(realMController.getStudentsList());
        }
    }

    private void manageStudentList() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new AttendanceAdapter(studentList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

    }

    public void uploadAttendence(View view) {
        Toast.makeText(mActivity, "Uploading File", Toast.LENGTH_SHORT).show();

    }

    public void seeAttendence(View view) throws IOException {
        String data = "";
        List<Student> studentsList = ((AttendanceAdapter) mAdapter).getStudentist();
        for (int i = 0; i < studentsList.size(); i++) {
            Student singleStudent = studentsList.get(i);
            if (singleStudent.isSelected() == true) {
                Student student1 = new Student(singleStudent.getStudentId(), singleStudent.getStudentName(), singleStudent.getFatherName(), singleStudent.getGender(), singleStudent.getAge());
                data = data + "\n" + singleStudent.getStudentName().toString();
            }
        }
        Toast.makeText(mActivity, " " + data, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(fileName) && studentsList != null && studentsList.size() > 0) {
            CsvFileWriter.writeCsvFile(fileName, studentsList);
        } else {
            Toast.makeText(mActivity, "", Toast.LENGTH_SHORT).show();
        }
        showAttendence();
    }

    public void showAttendence() {
        // Create URI
        File file = new File(fileName);
        if (file.exists()) {
            Uri path = Uri.fromFile(file);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/vnd.ms-excel");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(pdfIntent);
            } catch (Exception e) {
                Toast.makeText(Attendance.this, "Please install MS-Excel app to view the file.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*******************************************************************************
     * Method Name : checkPermissionForReadStorage
     * Description : This method will request  Permission  for read and Write Storage
     */
    public void checkPermissionForReadStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, mStrArrExternalStorageReadWritePermissions,
                    REQ_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_READ_EXTERNAL_STORAGE_PERMISSION: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //For simple deny permission
                            showPopupForCameraPermission();
                        } else if (!showRationale) {
                            // for NEVER ASK AGAIN deny permission
                            showPopupForCameraPermission();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method Name : showPopupForCameraPermission
     * Description : Method used to show popup for camera permission
     */
    private void showPopupForCameraPermission() {
        Toast.makeText(mActivity, "Need storage permission, please enable it from settings.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}

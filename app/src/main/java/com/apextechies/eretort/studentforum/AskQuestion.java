package com.apextechies.eretort.studentforum;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.ImagePath;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.common.ImageCompression;
import com.apextechies.eretort.common.PreferenceName;
import com.apextechies.eretort.model.AskQuestionModel;
import com.apextechies.eretort.model.ReplyModel;
import com.apextechies.eretort.model.RetrivedFromAWS;
import com.apextechies.eretort.simpleaws.UploadService;
import com.apextechies.eretort.utilz.Download_web;
import com.apextechies.eretort.utilz.OnTaskCompleted;
import com.apextechies.eretort.utilz.Utilz;
import com.apextechies.eretort.webservices.WebServices;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shankar on 11/6/2017.
 */

public class AskQuestion extends AppCompatActivity implements View.OnClickListener{
    private EditText edit_ques;
    private ArrayList<RetrivedFromAWS> uploadedpath = new ArrayList<>();
    private Dialog dialog;
    private TextView dialogfoldername;
    private TextView dialoguploadcount;
    private TextView progresspercent;
    private ProgressBar dialogprogress;
    private String path="", type;
    private static final String TAG = "AskQuestion";
    private String folderName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_question);
         type = getIntent().getStringExtra("type");

        initWidgit();
    }

    private void initWidgit() {
        findViewById(R.id.upload).setOnClickListener(this);
        edit_ques = (EditText)findViewById(R.id.edit_ques);
    }

    public void onSelectImageClick(View view) {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                path = getPathFromContentUri(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.upload:
                if ( path.length()>0) {
                    uploadedpath.clear();
                  //  opencustomdialog();
                    uloadfiletoAws();
                }
                else if (edit_ques.getText().toString().trim().length()>0)
                {
                    try {
                        if (type.equals("ask")) {
                            uploadDataToServer("");
                        }
                        else if (type.equals("ans")) {
                            uploadReplyDataToServer("");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else
                {
                    Toast.makeText(this, "Ask question please ", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }
    private void opencustomdialog() {

        dialog = new Dialog(AskQuestion.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.customdialog_uploadimage);


        dialogfoldername = (TextView) dialog.findViewById(R.id.foldername);
        dialoguploadcount = (TextView) dialog.findViewById(R.id.uploadcount);
        TextView dialogstatus = (TextView) dialog.findViewById(R.id.status);
        dialogprogress = (ProgressBar) dialog.findViewById(R.id.progresss);
        progresspercent = (TextView) dialog.findViewById(R.id.progresspercent);


        dialog.show();


    }

    private void uloadfiletoAws() {

        if (type.equals("ask")) {
             folderName = ClsGeneral.getPreferences(AskQuestion.this, PreferenceName.SCHOOLNAME);
        }
        else if (type.equals("ans")) {
             folderName = ClsGeneral.getPreferences(AskQuestion.this, PreferenceName.SCHOOLNAME+"_Reply");
        }
        if (path.length() > 0) {
                ImageCompression imageCompression = new ImageCompression(AskQuestion.this, path, new ImagePath() {
                    @Override
                    public void srart(boolean isstart) {

                        Utilz.showProgress(AskQuestion.this, getResources().getString(R.string.pleasewait));
                    }

                    @Override
                    public void path(String image) {
                        if (image!=null && image.length()>0)
                        {
                            opencustomdialog();

                            Intent intent = new Intent(AskQuestion.this, UploadService.class);
                            intent.putExtra(UploadService.ARG_FILE_PATH, image);
                            intent.putExtra(UploadService.ARG_FOLDER_NAME, folderName);
                            startService(intent);
                        }
                    }

                    @Override
                    public void stop(boolean isstop) {

                        Utilz.dismissProgressDialog();
                    }
                });
                imageCompression.execute();

            /*Intent intent = new Intent(AskQuestion.this, UploadService.class);
            intent.putExtra(UploadService.ARG_FILE_PATH, path);
            intent.putExtra(UploadService.ARG_FOLDER_NAME, folderName);
            startService(intent);*/
        }
    }
    private String getPathFromContentUri(Uri uri) {
        String path = uri.getPath();
        if (uri.toString().startsWith("content://")) {
            String[] projection = {MediaStore.MediaColumns.DATA};
            ContentResolver cr = getApplicationContext().getContentResolver();
            Cursor cursor = cr.query(uri, projection, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(0);
                    }
                } finally {
                    cursor.close();
                }
            }

        }
        return path;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter f = new IntentFilter();
        f.addAction(UploadService.UPLOAD_STATE_CHANGED_ACTION);
        registerReceiver(uploadStateReceiver, f);
    }

    @Override
    public void onStop() {
        unregisterReceiver(uploadStateReceiver);
        super.onStop();
    }

    private BroadcastReceiver uploadStateReceiver = new BroadcastReceiver() {
        String imagePath;
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            //dialogstatus.setText(b.getString(UploadService.MSG_EXTRA));
            if (b.getString(UploadService.MSG_EXTRA).contains("successfully")) {
                uploadedpath.add(new RetrivedFromAWS(b.getString(UploadService.FOLDERNAME), b.getString
                        (UploadService.PATH_LOCATION)));
                //  if (b.getInt(UploadService.COUNT) == textname.size()) {
                dialog.dismiss();
                for (int i = 0; i < uploadedpath.size(); i++) {
                    Log.e("Folder name ", "" + uploadedpath.get(i).getFoldername());
                    Log.e("Folder Path ", "" + uploadedpath.get(i).getPath());
                    imagePath = uploadedpath.get(i).getPath();

                }

                try {
                    if (type.equals("ask")) {
                        uploadDataToServer(imagePath);
                    }
                    else if (type.equals("ans")) {
                        uploadReplyDataToServer(imagePath);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                // }
            }
            int percent = b.getInt(UploadService.PERCENT_EXTRA);
            try {
                dialogprogress.setIndeterminate(percent < 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (percent == -1) {
                dialog.dismiss();
                //Toast.makeText(context, "Please try Again", Toast.LENGTH_SHORT).show();
                return;
            }
            dialogprogress.setProgress(percent);
            progresspercent.setText("" + percent + " %");
            dialoguploadcount.setText("" + b.getInt(UploadService.COUNT) + "/1");
            dialogfoldername.setText(b.getString(UploadService.FOLDERNAME));
        }
    };

    private void uploadDataToServer(String imagepath) throws JSONException {
        String currentdate = Utilz.getCurrentDate();
        String currenttime = Utilz.getCurrentTime(AskQuestion.this);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
         final ArrayList<AskQuestionModel> askquestionList = new ArrayList<>();
        Utilz.showDailog(AskQuestion.this, getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(AskQuestion.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (response.length() > 0) {
                    Utilz.closeDialog();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("true")) {
                           /* JSONArray array = obj.getJSONArray("data");
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject jo = array.getJSONObject(i);
                                String forum_id = jo.optString("forum_id");
                                String registratinId = jo.optString("registratinId");
                                String questionText = jo.optString("questionText");
                                String questionImage = jo.optString("questionImage");
                                String forumStatus = jo.optString("forumStatus");
                                String schoolName = jo.optString("schoolName");
                                askquestionList.add(new AskQuestionModel(forum_id,registratinId,questionText,questionImage,forumStatus,
                                        schoolName));
                            }
*/
                                startActivity(new Intent(AskQuestion.this,QuestionList.class));
                                finish();


                        }
                        else
                        {
                            Toast.makeText(AskQuestion.this, ""+obj.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("reg_id", "1056"));
        nameValuePairs.add(new BasicNameValuePair("ques_text", edit_ques.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("ques_image", imagepath));
        nameValuePairs.add(new BasicNameValuePair("date", currentdate));
        nameValuePairs.add(new BasicNameValuePair("time", currenttime));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.STUDENTFORUM);
    }

    private void  uploadReplyDataToServer(String imagepath) throws JSONException {
        String currentdate = Utilz.getCurrentDate();
        String currenttime = Utilz.getCurrentTime(AskQuestion.this);
        final ArrayList<ReplyModel> replyModels = new ArrayList<>();
        String forumId = getIntent().getStringExtra("forumId");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        final ArrayList<AskQuestionModel> askquestionList = new ArrayList<>();
        Utilz.showDailog(AskQuestion.this, getResources().getString(R.string.please_wait));
        Download_web web = new Download_web(AskQuestion.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                if (response.length() > 0) {
                    Utilz.closeDialog();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("true")) {
                            JSONArray array = obj.getJSONArray("data");
                            for (int i = 0 ; i<array.length(); i++)
                            {
                                JSONObject jo = array.getJSONObject(i);
                                String reply_id = jo.optString("id");
                                String registrationNumber = jo.optString("registrationNumber");
                                String replyText = jo.optString("replyText");
                                String replyImage = jo.optString("replyImage");
                                String schoolName = jo.optString("schoolName");
                                String replyStatus = jo.optString("replyStatus");
                                String forum_id = jo.optString("forum_id");
                                String date = jo.optString("date");
                                String time = jo.optString("time");

                                replyModels.add(new ReplyModel(reply_id,registrationNumber,replyText,replyImage,schoolName,replyStatus,forum_id, date, time));
                            }
                            Intent intent=new Intent();
                            intent.putParcelableArrayListExtra("MESSAGE",replyModels);
                            setResult(2,intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(AskQuestion.this, ""+obj.optString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("registrationNumber", "1056"));
        nameValuePairs.add(new BasicNameValuePair("replyText", edit_ques.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("replyImage", imagepath));
        nameValuePairs.add(new BasicNameValuePair("forum_id", forumId));
        nameValuePairs.add(new BasicNameValuePair("date", currentdate));
        nameValuePairs.add(new BasicNameValuePair("time", currenttime));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.FORUMSINGLEREPLY);
    }


}




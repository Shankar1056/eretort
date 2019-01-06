package com.apextechies.eretort.utilz;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apextechies.eretort.BuildConfig;
import com.apextechies.eretort.R;
import com.apextechies.eretort.allinterface.DialogClickListener;
import com.apextechies.eretort.common.AppConstants;
import com.apextechies.eretort.common.ClsGeneral;
import com.apextechies.eretort.retrofit.ApiUrl;
import com.google.android.gms.fido.u2f.api.common.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.joda.time.format.DateTimeFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Utilz {
    static ProgressDialog dialog;
    public static final int HTTP_TIMEOUT = 60 * 1000; // milliseconds
    private static HttpClient mHttpClient;
    private static boolean IS_DEBUG_ENABLE = BuildConfig.DEBUG;
    private static String TAG = Utilz.class.getSimpleName();


    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isInternetConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void showLog(String TAG, String message) {
        if (IS_DEBUG_ENABLE)
            Log.i(TAG, message);
    }

    public static boolean isValidEmail1(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            // check if no view has focus:
            View view = activity.getCurrentFocus();
            if (view != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }


    public static int getDateFromString(String dateStr) {
        int date = 0;
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = DATE_FORMAT.parse(dateStr);
            date = parsedDate.getDate();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String calculatedistanceinkm(double latti, double longi, String latitude, String longitude) {


        double distance = 0, dist = 0;
        try {


            double theta = longi - Double.parseDouble(longitude);
            dist = Math.sin(deg2rad(latti)) * Math.sin(deg2rad(Double.parseDouble(latitude))) + Math.cos(deg2rad(latti)) * Math.cos(deg2rad(Double.parseDouble(latitude))) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%.2f", dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static void showProgress(Context context, String message) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }

        dialog = ProgressDialog.show(context, null, message, true, true);

    }

    public static void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void displayMessageAlert(String Message, Context context) {
        try {
            new AlertDialog.Builder(context).setMessage(Message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            }).create().show();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public static void logDisplay(String Message, Context context) {
        if (Message != null) {
            if (Message.trim().length() > 0 && context != null) {
                Log.d("PJ", Message);
            }
        }
    }



    public static void toastDisplay(String Message, Context con) {
        Toast toast = Toast.makeText(con, Message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void HideSoftkeyPad(EditText textfiled, Context c) {
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textfiled.getWindowToken(), 0);
    }






    public static void showDailog(Context c, String msg) {
        dialog = new ProgressDialog(c);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(msg);
        dialog.show();
    }

    public static void closeDialog() {
        if (dialog != null)
            dialog.cancel();
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentTime(Context askQuestion) {
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = sdf.format(d);
        return currentDateTimeString;
    }

    public static String getCurrentDateInDigit(Context timeTableActivity) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String todaysday(Context timeTableActivity) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }
    private static Calendar c;
    private static List<String> output;

    public static List<String> getCalendar()
    {
        c = Calendar.getInstance();
        output =  new ArrayList<String>();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        //Get current Day of week and Apply suitable offset to bring the new calendar
        //back to the appropriate Monday, i.e. this week or next
        switch (c.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY:
                c.add(Calendar.DATE,-1);
                break;

            case Calendar.TUESDAY:
                c.add(Calendar.DATE,-2);
                break;

            case Calendar.WEDNESDAY:
                c.add(Calendar.DATE, -3);
                break;

            case Calendar.THURSDAY:
                c.add(Calendar.DATE,-4);
                break;

            case Calendar.FRIDAY:
                c.add(Calendar.DATE,-5);
                break;

            case Calendar.SATURDAY:
                c.add(Calendar.DATE,-6);
                break;
        }

        //Add the Monday to the output
       // output.add(c.getTime().toString());
        for (int x = 1; x <=6; x++)
        {
            //Add the remaining days to the output
            c.add(Calendar.DATE,1);
            output.add(df.format(c.getTime()));
        }
        return output;
    }

    public static void showMessageDialogForResponseCodes(final Context mActivity, final String title, final String message, final DialogClickListener clickListener) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.show_title_message_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            TextView titleTextView = dialog.findViewById(R.id.titleTextView);
            TextView messageTextView = dialog.findViewById(R.id.messageTextView);
            if (!TextUtils.isEmpty(title))
                titleTextView.setText(title);
            else
                titleTextView.setText(AppConstants.WARNING);
            messageTextView.setText(message);
            TextView textViewCancel = dialog.findViewById(R.id.textViewCancel);
            TextView crossTv = dialog.findViewById(R.id.crossTv);
            textViewCancel.setVisibility(View.INVISIBLE);
            TextView textViewOk = dialog.findViewById(R.id.textViewOk);

            if (textViewOk != null) {
                textViewOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                        clickListener.onClickOfPositive();
                    }
                });
            }
            if (crossTv != null) {
                crossTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                    }
                });
            }
            if (dialog != null && !dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
        }
    }
    public static void showNoInternetConnectionDialog(final Context mActivity) {
        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mActivity);
            builder.setTitle(mActivity.getResources().getString(R.string.no_internet_connection_msg_title));
            builder.setMessage(mActivity.getResources().getString(R.string.no_internet_connection_msg));
            builder.setNegativeButton(AppConstants.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AppCompatDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }

    public static void showMessageOnDialog(final Activity mActivity, final String title, final String message, final String negBtnTitle, final String posBtnTitle) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.show_title_message_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            TextView titleTextView = dialog.findViewById(R.id.titleTextView);
            TextView messageTextView = dialog.findViewById(R.id.messageTextView);
            TextView crossTv = dialog.findViewById(R.id.crossTv);
            crossTv.setVisibility(View.GONE);
            titleTextView.setText(title);
            messageTextView.setText(message);
            TextView textViewCancel = dialog.findViewById(R.id.textViewCancel);
            TextView textViewOk = dialog.findViewById(R.id.textViewOk);
            if (!TextUtils.isEmpty(negBtnTitle))
                textViewCancel.setText(negBtnTitle);
            if (!TextUtils.isEmpty(posBtnTitle))
                textViewOk.setText(posBtnTitle);

            if (!TextUtils.isEmpty(posBtnTitle)) {
                textViewOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                        if (mActivity != null && !mActivity.isFinishing())
                            mActivity.finish();
                    }
                });
            }
            if (!TextUtils.isEmpty(negBtnTitle)) {
                textViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                    }
                });
            }
            if (dialog != null && !dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }
    public static void openDialer(final Context mActivity, final String mobileNo) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mobileNo));
        mActivity.startActivity(intent);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            mActivity.startActivity(intent);
            return;
        }
    }

    public static String getRandomUserName(String userNameStr) {
        Random rand = new Random();
        int randomNo = rand.nextInt(90000) + 10000;
        if (!TextUtils.isEmpty(userNameStr) && userNameStr.length() > 3 && userNameStr.contains(" ")) {
            userNameStr = userNameStr.substring(0, userNameStr.indexOf(" ")).trim();
        }
        userNameStr = userNameStr.trim() + "@" + randomNo;
        return userNameStr;
    }
    public static List<String> getClassList() {
        List<String> classList;
        classList = new ArrayList<>();
        classList.add("Select Class");
        classList.add("10");
        classList.add("9");
        classList.add("8");
        classList.add("7");
        classList.add("6");
        classList.add("5");
        classList.add("4");
        classList.add("3");
        classList.add("2");
        classList.add("1");
        classList.add("LKG");
        classList.add("UKG");
        classList.add("NURSERY");
        return classList;
    }

    public static List<String> getClassNameList() {
        List<String> classNameList;
        classNameList = new ArrayList<>();
        classNameList.add("Class - 10");
        classNameList.add("Class - 9");
        classNameList.add("Class - 8");
        classNameList.add("Class - 7");
        classNameList.add("Class - 6");
        classNameList.add("Class - 5");
        classNameList.add("Class - 4");
        classNameList.add("Class - 3");
        classNameList.add("Class - 2");
        classNameList.add("Class - 1");
        classNameList.add("Class - LKG");
        classNameList.add("Class - UKG");
        classNameList.add("Class - NURSERY");
        return classNameList;
    }
    public static List<String> getSectionList() {
        List<String> sectionList = new ArrayList<>();
        sectionList.add("Select Section");
        sectionList.add("A");
        sectionList.add("B");
        sectionList.add("C");
        return sectionList;
    }

    public static String getRandomUserIdFromName(Context mContext) {
        String userNameStr = mContext.getResources().getString(R.string.app_name);
        Random rand = new Random();
        int randomNo = rand.nextInt(90000) + 10000;
        if (!TextUtils.isEmpty(userNameStr) && userNameStr.length() > 3) {
            userNameStr = userNameStr.substring(0, 3).toUpperCase().trim();
        }
        userNameStr = userNameStr + "-" + randomNo;
        return userNameStr;
    }

    public static void setMessageDialog(final Activity mContext, final String mStrPhoneNo, final boolean isStudent) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.send_message_dialog);
            dialog.setTitle(null);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            final EditText messageEditText = dialog.findViewById(R.id.messageEditText);
            TextView textViewCancel = dialog.findViewById(R.id.textViewCancel);
            TextView textViewSend = dialog.findViewById(R.id.textViewSend);
            textViewSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mStrMessage = messageEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(mStrMessage)) {
                        String messageBody = messageEditText.getText().toString().trim();
                        String sendTo = AppConstants.INDIVIDUAL, sectionName = "", className = "";
                        String schoolId = ClsGeneral.getStrPreferences(AppConstants.SCHOOL_ID);
                        String smsAllowedStatus = ClsGeneral.getStrPreferences(AppConstants.SMS_ALLOWED_STATUS);
                       /* if (isStudent) {
                            callSendMessageApi(mContext, messageBody, mStrPhoneNo, AppConstants.STUDENT, sendTo, schoolId, smsAllowedStatus, true);
                        } else {
                            callSendMessageApi(mContext, messageBody, mStrPhoneNo, AppConstants.TEACHER, sendTo, schoolId, smsAllowedStatus, true);
                        }*/
                    } else {
                        Toast.makeText(mContext.getApplicationContext(), mContext.getResources().getString(R.string.enter_your_message_here), Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
            textViewCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDayNameAndDate() {
        String mStrCurrentDate = getCurrentDate();
        String mStrDayName = getDayNameFromDate(getCurrentDate());
        if (!TextUtils.isEmpty(mStrDayName))
            mStrDayName = mStrDayName + ", " + mStrCurrentDate;
        return mStrDayName;
    }

    public static String getDayNameFromDate(String mStrDate) {
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy").withLocale(Locale.ENGLISH);
        org.joda.time.LocalDate localDate = formatter.parseLocalDate(mStrDate);
        int dayOfWeek = localDate.getDayOfWeek(); // Follows ISO 8601 standard, where Monday = 1, Sunday = 7.
        if (dayOfWeek == 1)
            return AppConstants.MONDAY;
        else if (dayOfWeek == 2)
            return AppConstants.TUESDAY;
        else if (dayOfWeek == 3)
            return AppConstants.WEDNESDAY;
        else if (dayOfWeek == 4)
            return AppConstants.THURSDAY;
        else if (dayOfWeek == 5)
            return AppConstants.FRIDAY;
        else if (dayOfWeek == 6)
            return AppConstants.SATURDAY;
        else
            return "Sunday";
    }

    public static void showMessageDialog(final Context mActivity, final String msg) {
        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mActivity);
            builder.setMessage(msg);
            builder.setNegativeButton(AppConstants.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AppCompatDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Utilz.showLog(TAG, "Show Dialog: " + e.getMessage());
        }
    }

}


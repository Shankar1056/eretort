package com.apextechies.eretort.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.apextechies.eretort.AppController;
import com.apextechies.eretort.model.ClassAndSectionModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClsGeneral {
	public static Context mContext;

	public static void setPreferences(Context context, String key, String value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		String text = prefs.getString(key, "");
		return text;
	}


	public static void setPreferences(String key, String value) {
		SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void setPreferences(String key, boolean value) {
		SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setPreferences(String key, int value) {
		SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static boolean getBoolPreferences(String key) {
		SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		return prefs.getBoolean(key, false);
	}

	public static String getStrPreferences(String key) {
		SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		return prefs.getString(key, "");
	}

	public static int getIntPreferences(String key) {
		SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		return prefs.getInt(key, 0);
	}

	public static List<ClassAndSectionModel> getClassAndSection() {
		Gson gson = new Gson();
		List<ClassAndSectionModel> productFromShared;
		SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("CLASS_AND_SECTION", Context.MODE_PRIVATE);
		String jsonPreferences = sharedPref.getString("CLASS_AND_SECTION", "");

		Type type = new TypeToken<List<ClassAndSectionModel>>() {
		}.getType();
		productFromShared = gson.fromJson(jsonPreferences, type);
		if (productFromShared != null && productFromShared.size() > 0)
			return productFromShared;
		else
			return null;
	}

	private void setClassAndSection(ClassAndSectionModel classAndSection) {
		Gson gson = new Gson();
		String jsonCurProduct = gson.toJson(classAndSection);

		SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences("CLASS_AND_SECTION", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		editor.putString("CLASS_AND_SECTION", jsonCurProduct);
		editor.commit();
	}




	public static boolean emailValidator(String email) {
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isNetworkAvailable(Context context)
	{

		ConnectivityManager connectivity  = null;
		boolean isNetworkAvail = false;

		try
		{
			connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivity != null)
			{
				NetworkInfo[] info = connectivity.getAllNetworkInfo();

				if (info != null)
				{
					for (int i = 0; i < info.length; i++)
					{
						if (info[i].getState() == NetworkInfo.State.CONNECTED)
						{

							return true;
						}
					}
				}
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connectivity !=null)
			{
				connectivity = null;
			}
		}
		return isNetworkAvail;
	}


}

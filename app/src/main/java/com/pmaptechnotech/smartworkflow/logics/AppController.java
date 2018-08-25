package com.pmaptechnotech.smartworkflow.logics;


import android.app.Application;

public class AppController extends Application {
	public static final String TAG = AppController.class.getSimpleName();
	private static AppController mInstance;
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
	public static synchronized AppController getInstance() {
		return mInstance;
	}
}
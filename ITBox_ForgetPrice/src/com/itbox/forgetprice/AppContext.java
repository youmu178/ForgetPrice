package com.itbox.forgetprice;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class AppContext extends Application {
	private static AppContext mInstance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		ActiveAndroid.initialize(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	public static AppContext getInstance() {
		return mInstance;
	}
}

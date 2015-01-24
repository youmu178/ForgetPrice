package com.itbox.forgetprice.util;

import com.itbox.forgetprice.AppContext;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Utils {

	public static String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) AppContext.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		return imei;
	}
}

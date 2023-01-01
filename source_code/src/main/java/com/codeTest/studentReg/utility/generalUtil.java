package com.codeTest.studentReg.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class generalUtil {
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
}
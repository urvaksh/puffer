package com.codeaspect.puffer.testutils;

import java.util.Calendar;
import java.util.Date;

public class TestUtils {
	
	public static Date createDate(int date, int month, int year){
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}

}

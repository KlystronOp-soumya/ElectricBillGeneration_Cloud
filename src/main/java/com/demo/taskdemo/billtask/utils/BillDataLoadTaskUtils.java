package com.demo.taskdemo.billtask.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.demo.taskdemo.billtask.entites.BillDataLoadInfo;

public class BillDataLoadTaskUtils {

	public static BillDataLoadInfo getBillDataLoadInfo() {
		SimpleDateFormat formatterDate = new SimpleDateFormat("Y-M-D");
		SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm:s");

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		// Date date = new Date(2023, 2, 31);
		BillDataLoadInfo billDataLoadInfo = new BillDataLoadInfo();
		billDataLoadInfo.setCurrDay(date.getDate());
		billDataLoadInfo.setCurrMonth(date.getMonth() + 1);
		billDataLoadInfo.setCurrYear(date.getYear() + 1900);
		billDataLoadInfo.setJournalDate(formatterDate.format(date));
		billDataLoadInfo.setJournalTime(formatterTime.format(date));
		billDataLoadInfo.setBillQuarter(getBillQuarter(date.getMonth() + 1));

		return billDataLoadInfo;
	}

	public static boolean isBillingQuarter(final int currMonth) {
		// 3 6 9 12 are the billing quarters
		return (currMonth == 3 || currMonth == 6 || currMonth == 9 || currMonth == 12) ? true : false;

	}

	private static int getBillQuarter(int currMonth) {
		if (currMonth == 3)
			return 1;
		else if (currMonth == 6)
			return 2;
		else if (currMonth == 9)
			return 3;
		else if (currMonth == 12)
			return 4;
		return 0;
	}

	public static boolean isCsvExists() {
		File file = new File(Constants.INPUT_CSV_PATH.getValue());

		return file.exists();
	}
}

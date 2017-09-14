package com.qheai.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 操作日期类
 * 
 * @author ssm
 *
 */
public class CalendarUtil
{

	/**
	 * 获取第一次启动时执行任务的时间
	 * 
	 * @return
	 */
	public static Date getFirstTime()
	{
		Calendar calendar = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		// calendar.add(Calendar.MINUTE, 1);//测试用的
		Date firstTime = calendar.getTime(); // 得出第一次执行任务的时间,此处为第二天00：00：00
		return firstTime;
	}

	/**
	 * 获取执行任务时间
	 * 
	 * @return
	 */
	public static Date getNewTime()
	{
		Calendar calendar = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date newTime = calendar.getTime(); // 得出执行任务的时间,此处为当天的00：00：00
		return newTime;
	}

}

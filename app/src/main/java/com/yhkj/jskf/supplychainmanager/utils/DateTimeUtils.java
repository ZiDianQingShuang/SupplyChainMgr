package com.yhkj.jskf.supplychainmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
	/**
	 * Date -> String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatTime(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * long -> String
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatTime(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(time));
	}

	/**
	 * 将旧的时间字符串 -> 指定的时间字符串
	 * 
	 * @param time
	 *            时间字符串
	 * @param oldPattern
	 *            旧时间格式
	 * @param newPattern
	 *            新时间格式
	 * @return
	 */
	public static String formatTime(String time, String oldPattern, String newPattern) {
		// yyyy-MM-dd hh:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat(oldPattern);
		try {
			return formatTime(sdf.parse(time), newPattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * String -> long
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static Date DateStringToDate(String time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * String -> long
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static long DateStringToLong(String time, String pattern) {
		if (null == DateStringToDate(time, pattern)) {
			return 0;
		}
		return DateStringToDate(time, pattern).getTime();
	}

	/**
	 * 比较时间显示 如 几个小时前等等
	 * 
	 * @param dataTime
	 * @param sysTime
	 * @return
	 */
	public static String diffTime(long dataTime, long sysTime) {
		dataTime = dataTime / 1000;// 秒
		sysTime = sysTime / 1000;// 秒
		long sec = sysTime - dataTime;
		if (sec < 60) {
			return "刚刚";
		}
		dataTime = dataTime / 60;// 分钟
		sysTime = sysTime / 60;// 分钟
		long difMinu = sysTime - dataTime;
		if (difMinu < 60) {
			return difMinu + "分钟前";
		}
		dataTime = dataTime / 60;
		sysTime = sysTime / 60;
		long difHour = sysTime - dataTime;
		if (difHour < 24) {
			return difHour + "小时前";
		}

		dataTime = dataTime / 24;
		sysTime = sysTime / 24;
		long diffDay = sysTime - dataTime;
		if (diffDay < 30) {
			return diffDay + "天前";
		}

		dataTime = dataTime / 30;
		sysTime = sysTime / 30;

		long diffMonth = sysTime - dataTime;
		if (diffMonth < 12) {
			return diffMonth + "个月前";
		}

		dataTime = dataTime / 12;
		sysTime = sysTime / 12;
		long diffYear = sysTime - dataTime;
		if (diffYear < 5) {
			return diffYear + "年前";
		}
		return formatTime(dataTime, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 比较时间显示 默认是和本地时间比较
	 * 
	 * @param clientTime
	 * @return
	 */
	public static String diffTime(long clientTime) {
		Calendar c = Calendar.getInstance();
		return diffTime(clientTime, c.getTimeInMillis());
	}

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	public static String diffTimeNew(long clientTime) {
		Date date = new Date(clientTime);
		return diffTime(date);
	}

	public static String diffTime(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年

	public static String getTimeFormatText(Long clientTime) {
		return getTimeFormatText(new Date(clientTime));
	}
	/**
	 * 返回文字描述的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "个月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "个小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}
}

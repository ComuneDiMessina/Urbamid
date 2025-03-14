package it.eng.tz.urbamid.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * Class with utility methods for manage date
 * @author Antonio La Gatta
 * @date 09 mag 2017
 * @version 1.0
 */
public class DateUtil {

	private static final SimpleDateFormat IT_DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final SimpleDateFormat EN_DATETIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private static final SimpleDateFormat IT_DATE_FORMAT     = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat EN_DATE_FORMAT     = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat IT_TIME_FORMAT     = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat EN_TIME_FORMAT     = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * Restituisca la data corrente
	 * 
	 * @return Date
	 */
	public static java.sql.Timestamp getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return new java.sql.Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return {@link java.sql.Date} instance from {@link java.util.Date} instance
	 */
	public static java.sql.Date toSqlDate(java.util.Date date){
		if(date == null)
			return null;
		return new java.sql.Date(date.getTime());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return {@link java.sql.Timestamp} instance from {@link java.util.Date} instance
	 */
	public static java.sql.Timestamp toTimestamp(java.util.Date date){
		if(date == null)
			return null;
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return {@link java.sql.Time} instance from {@link java.util.Date} instance
	 */
	public static java.sql.Time toTime(java.util.Date date){
		if(date == null)
			return null;
		return new java.sql.Time(date.getTime());
	}


	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param dateTime
	 * @param locale
	 * @return string format of a datetime by locale
	 */
	public static String dateTimeToString(java.util.Date dateTime, Locale locale){
		return LocaleUtil.isItalian(locale) ? dateTimeToItalianString(dateTime) : dateTimeToNotItalianString(dateTime);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param dateTime
	 * @return italian string format of a datetime by locale
	 */
	public static String dateTimeToItalianString(java.util.Date dateTime){
		if(dateTime == null)
			return "";
		return IT_DATETIME_FORMAT.format(dateTime);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param dateTime
	 * @return not italian string format of a datetime by locale
	 */
	public static String dateTimeToNotItalianString(java.util.Date dateTime){
		if(dateTime == null)
			return "";
		return EN_DATETIME_FORMAT.format(dateTime);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @param locale
	 * @return string format of a date by locale
	 */
	public static String dateToString(java.util.Date date, Locale locale){
		return LocaleUtil.isItalian(locale) ? dateToItalianString(date) : dateToNotItalianString(date);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return italian string format of a date by locale
	 */
	public static String dateToItalianString(java.util.Date date){
		if(date == null)
			return "";
		return IT_DATE_FORMAT.format(date);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return not italian string format of a date by locale
	 */
	public static String dateToNotItalianString(java.util.Date date){
		if(date == null)
			return "";
		return EN_DATE_FORMAT.format(date);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @param locale
	 * @return string format of a time by locale
	 */
	public static String timeToString(java.util.Date time, Locale locale){
		return LocaleUtil.isItalian(locale) ? timeToItalianString(time) : timeToNotItalianString(time);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param time
	 * @return italian string format of a time by locale
	 */
	public static String timeToItalianString(java.util.Date time){
		if(time == null)
			return "";
		return IT_TIME_FORMAT.format(time);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param time
	 * @return not italian string format of a time by locale
	 */
	public static String timeToNotItalianString(java.util.Date time){
		if(time == null)
			return "";
		return EN_TIME_FORMAT.format(time);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param dateTime
	 * @param locale
	 * @return string format of a datetime by locale
	 * @throws ParseException
	 */
	public static java.sql.Timestamp stringToDateTime(String dateTime, Locale locale) throws ParseException{
		return LocaleUtil.isItalian(locale) ? stringItalianToDateTime(dateTime) : stringNotItalianToDateTime(dateTime);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return italian string format of a datetime by locale
	 * @throws ParseException
	 */
	public static java.sql.Timestamp stringItalianToDateTime(String dateTime) throws ParseException{
		if(StringUtil.isBlank(dateTime))
			return null;
		return toTimestamp(IT_DATETIME_FORMAT.parse(dateTime));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param dateTime
	 * @return not italian string format of a datetime by locale
	 * @throws ParseException
	 */
	public static java.sql.Timestamp stringNotItalianToDateTime(String dateTime) throws ParseException{
		if(StringUtil.isBlank(dateTime))
			return null;
		return toTimestamp(EN_DATETIME_FORMAT.parse(dateTime));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @param locale
	 * @return string format of a date by locale
	 * @throws ParseException
	 */
	public static java.sql.Date stringToDate(String date, Locale locale) throws ParseException{
		return LocaleUtil.isItalian(locale) ? stringItalianToDate(date) : stringNotItalianToDate(date);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return italian string format of a date by locale
	 * @throws ParseException
	 */
	public static java.sql.Date stringItalianToDate(String date) throws ParseException{
		if(StringUtil.isBlank(date))
			return null;
		return toSqlDate(IT_DATE_FORMAT.parse(date));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param date
	 * @return not italian string format of a date by locale
	 * @throws ParseException
	 */
	public static java.sql.Date stringNotItalianToDate(String date) throws ParseException{
		if(StringUtil.isBlank(date))
			return null;
		return toSqlDate(EN_DATE_FORMAT.parse(date));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param time
	 * @param locale
	 * @return string format of a date by locale
	 * @throws ParseException
	 */
	public static java.sql.Time stringToTime(String time, Locale locale) throws ParseException{
		return LocaleUtil.isItalian(locale) ? stringItalianToTime(time) : stringNotItalianToTime(time);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 09 mag 2017
	 * @version 1.0
	 * @param time
	 * @return italian string format of a time by locale
	 * @throws ParseException
	 */
	public static java.sql.Time stringItalianToTime(String time) throws ParseException{
		if(StringUtil.isBlank(time))
			return null;
		return toTime(IT_TIME_FORMAT.parse(time));
	}
	/**
	 * @author Antonio La Gatta
	 * @time 09 mag 2017
	 * @version 1.0
	 * @param time
	 * @return not italian string format of a time by locale
	 * @throws ParseException
	 */
	public static java.sql.Time stringNotItalianToTime(String time) throws ParseException{
		if(StringUtil.isBlank(time))
			return null;
		return toTime(EN_TIME_FORMAT.parse(time));
	}
	
	public static Date transformStringToDate(String date, String formatDate) throws ParseException {
		if (!StringUtils.hasText(date) || !StringUtils.hasText(formatDate))
			return null;

		DateFormat sdf = new SimpleDateFormat(formatDate);

		return sdf.parse(date);

	}

}

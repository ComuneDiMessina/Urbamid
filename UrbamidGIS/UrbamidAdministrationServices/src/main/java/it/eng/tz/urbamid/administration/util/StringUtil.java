package it.eng.tz.urbamid.administration.util;
 
import java.io.File;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.util.StringUtils;

public abstract class StringUtil {
	
	public static final String CHARSET_STRING = "UTF-8"; 
	public static final String CHARSET_STRING_LOWER = CHARSET_STRING.toLowerCase(); 
	public static final Charset CHARSET = Charset.forName(CHARSET_STRING); 
	private static final String OS_SEPARTOR = String.valueOf(File.separatorChar);
	private static final String SEPARTOR = "/";


	public static String toString(Object obj) {
		return String.valueOf(obj);
	}

	public static String concateneString(Object... tokens) {
		if(tokens == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for(Object token : tokens) {
			if(token instanceof char[])
				sb.append(new String((char[])token));
			else
				sb.append(token);
		}
		return sb.toString();
	
	}

	public static String concateneStringPath(String... strings) {
		return concateneStringPath((Object[])strings);
	}

	public static String concateneStringPath(Object...objects) {
		return concateneStringPath(OS_SEPARTOR, objects);
	}
	
	public static String concateneStringPathUnix(String... strings) {
		return concateneStringPathUnix((Object[])strings);
	}
	
	public static String concateneStringPathUnix(Object...objects) {		
		return concateneStringPath(SEPARTOR, objects);
	}
	
	private static String concateneStringPath(String sep, Object...objects) {
		StringBuilder sb = new StringBuilder();
		if(objects != null) {
			for(Object object : objects) {
				if((object == null || object.toString().startsWith(sep) == false)
				 && sb.toString().endsWith(sep) == false
				 && (sep.equals(SEPARTOR) || sb.length() > 0) 
				)
					sb.append(sep);
				sb.append(object);
			}
		}
		return sb.toString();
	}

	public static boolean isEmpty(String string) {
		return StringUtils.isEmpty(string);
	}

	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	public static String convertLike(Object s) {
		return concateneString("%", s, "%");
	}

	public static String convertLeftLike(Object s) {
		return concateneString("%", s);
	}

	public static String convertRightLike(String s) {
		return concateneString(s, "%");
	}

	public static String maskPassword(String s) {
		return StringUtil.isEmpty(s) ? "" : s.replaceAll(".", "*");
	}

	public static String cleanString(String s) {
		return s == null ? "" : s;
	}

	public static String formatDoubleToString(Double d) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALIAN);
		DecimalFormat df = (DecimalFormat)nf;
		String pattern="###,##0.00";
		df.applyPattern(pattern);
		String output = df.format(d);
		return output;
	}
}
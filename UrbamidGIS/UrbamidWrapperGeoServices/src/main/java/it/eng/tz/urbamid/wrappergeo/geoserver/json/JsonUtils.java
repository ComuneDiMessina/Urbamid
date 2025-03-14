package it.eng.tz.urbamid.wrappergeo.geoserver.json;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import it.eng.tz.urbamid.wrappergeo.exception.WrapperGeoServiceException;


/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class JsonUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	public static List<String> parseJsonAsStringList(String json) {
		return parseJsonAsStringList(new Gson(), json);
	}
	
	public static List<String> parseJsonAsStringList(Gson gson, String json) {
		Type collectionType = new TypeToken<List<String>>() {}.getType();
		List<String> resultList = gson.fromJson(json, collectionType);
		
		return resultList;
	}
	
	public static List<Integer> parseJsonAsIntegerList(String json) {
		Gson gson = GsonFactory.getGson();
		return parseJsonAsIntegerList(gson, json);
	}
	
	public static List<Long> parseJsonAsLongList(String json) {
		Gson gson = GsonFactory.getGson();
		return parseJsonAsLongList(gson, json);
	}
	public static List<Integer> parseJsonAsIntegerList(Gson gson, String json) {
		Type collectionType = new TypeToken<List<Integer>>() {}.getType();
		List<Integer> resultList = gson.fromJson(json, collectionType);
		
		return resultList;
	}
	
	public static List<Long> parseJsonAsLongList(Gson gson, String json) {
		Type collectionType = new TypeToken<List<Long>>() {}.getType();
		List<Long> resultList = gson.fromJson(json, collectionType);
		
		return resultList;
	}
	
	public static List<Double> parseJsonAsDoubleList(String json) {
		Gson gson = GsonFactory.getGson();
		return parseJsonAsDoubleList(gson, json);
	}
	
	public static List<BigDecimal> parseJsonAsBigDecimalList(String json) {
		Gson gson = GsonFactory.getGson();
		return parseJsonAsBigDecimalList(gson, json);
	}
	
	
	public static List<Double> parseJsonAsDoubleList(Gson gson, String json) {
		Type collectionType = new TypeToken<List<Double>>() {}.getType();
		List<Double> resultList = gson.fromJson(json, collectionType);
		
		return resultList;
	}
	
	public static List<BigDecimal> parseJsonAsBigDecimalList(Gson gson, String json) {
		Type collectionType = new TypeToken<List<BigDecimal>>() {}.getType();
		List<BigDecimal> resultList = gson.fromJson(json, collectionType);
		
		return resultList;
	}
	
	public static <T> List<T> parseJsonAsObjectList(String json, Class<T> typeClass)
	{
		Gson gson = GsonFactory.getGson();
	    List<T> resultList = parseJsonAsObjectList(gson, json, typeClass);
	    return resultList;
	}
	
	public static <T, S> Map<T,S> parseJsonAsMap(String json) {
		Gson gson = GsonFactory.getGson();
		Type collectionType = new TypeToken<Map<T, S>>() {}.getType();
		Map<T, S> resultMap = gson.fromJson(json, collectionType);
		return resultMap;
	}
	
	
	public static <T> List<T> parseJsonAsObjectList(Gson gson, String json, Class<T> typeClass)
	{
	    return gson.fromJson(json, new JsonListOf<T>(typeClass));
	}
	
	public static <T> T parseFromObject(String dataString, Class<T> classOfT) throws WrapperGeoServiceException {
		try {
			Gson gson = GsonFactory.getGson();
			T parsedObject = gson.fromJson(dataString, classOfT);
			
			return parsedObject;
			
		} catch (Exception x) {
			logger.debug("Error parsing " + dataString + " parameter", x);
			throw new WrapperGeoServiceException(x);
		}
	}
	
	/**
	 * Torna il contenuto del campo specificato in formato json (stringa)
	 * Se il campo specificato non esiste, torna null.
	 * @param json
	 * @param field
	 * @return
	 */
	public static String getFieldAsJson(String json, String field) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		
		JsonObject root = je.getAsJsonObject();
		JsonElement fieldElement = root.get(field);
		if (fieldElement == null) {
			return null;
		}
		String resultJson = fieldElement.toString();
		
		return resultJson;	
	}

	/**
	 * Torna il contenuto del campo specificato come stringa
	 * @param json
	 * @param field
	 * @return
	 */
	public static String getFieldAsJsonString(String json, String field) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		
		JsonObject root = je.getAsJsonObject();
		JsonElement fieldElement = root.get(field);
		String resultContent = fieldElement.getAsString();
		
		return resultContent;
	}
	
	/**
	 * Torna il contenuto del campo specificato come intero
	 * @param json
	 * @param field
	 * @return
	 */
	public static Integer getFieldAsJsonInteger(String json, String field) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		
		JsonObject root = je.getAsJsonObject();
		JsonElement fieldElement = root.get(field);
		Integer resultContent = fieldElement.getAsInt();
		
		return resultContent;
	}
	
	/**
	 * Torna il contenuto del campo specificato come lista di stringhe
	 * @param json
	 * @param arrayField
	 * @return
	 */
	public static List<String> getArrayFieldAsJson(String json, String arrayField) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		JsonObject root = je.getAsJsonObject();
		JsonElement fieldArrayElement = root.get(arrayField);
		JsonArray array = fieldArrayElement.getAsJsonArray();
		
		List<String> res = new ArrayList<String>();
		for (int j=0; j<array.size(); j++) {
			JsonElement element = array.get(j);
			res.add(element.toString());
		}
		
		return res;
	}
	
	/**
	 * To Json
	 * @param obj
	 * @return Json string
	 */
	public static String toJson(Object obj) {
		Gson gson = GsonFactory.getGson();
		String json = gson.toJson(obj);
		return json;
		
	}
	
	/**
	 * To Json with serialization of nulls
	 * @param obj
	 * @return
	 */
	public static String toJsonWithSerializationOfNulls(Object obj) {
		Gson gson = GsonFactory.getGson(true, true, false);
		String json = gson.toJson(obj);
		return json;
	}
	
	/**
	 * To Json
	 * @param filterExposedFields
	 * @param serializeNulls
	 * @return
	 */
	public static String toJson(Object obj, boolean filterExposedFields, boolean serializeNulls) {
		Gson gson = GsonFactory.getGson(filterExposedFields, serializeNulls, false);
		String json = gson.toJson(obj);
		return json;
	}
}

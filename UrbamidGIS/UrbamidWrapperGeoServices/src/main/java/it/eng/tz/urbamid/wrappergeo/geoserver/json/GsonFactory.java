package it.eng.tz.urbamid.wrappergeo.geoserver.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GsonFactory {

	public static Gson getGson() {
		return getGson(true, false, false);
	}

	public static Gson getGsonWithNoFilters() {
		return getGson(false, false, false);
	}

	public static Gson getGson(boolean filterExposedFields,
			boolean serializeNulls, boolean prettyPrinting) {
		Gson gson = null;
		GsonBuilder gsonBuilder = new GsonBuilder();

		// Enable pretty printing.
		if (prettyPrinting) {
			gsonBuilder.setPrettyPrinting();
		}

		if (filterExposedFields) {
			gsonBuilder.setExclusionStrategies(new GsonExclusionStrategy());
		}
		if (serializeNulls) {
			gsonBuilder.serializeNulls();
		}
		/*gsonBuilder.registerTypeAdapterFactory(new ImmutableMapTypeAdapterFactory());
		gsonBuilder.registerTypeAdapter(ImmutableMap.class, ImmutableMapTypeAdapterFactory.newCreator());*/
//		gsonBuilder.registerTypeAdapterFactory(new JtsAdapterFactory()).registerTypeAdapterFactory(new GeometryAdapterFactory());
		
		//gsonBuilder.setDateFormat("dd-MM-yyyy HH:mm:ss.SSS").create();
//		gsonBuilder.setDateFormat(DateFormatUtils.GENERAL_DATE_FORMAT).create();
		gson = gsonBuilder.create();

		return gson;
	}
}

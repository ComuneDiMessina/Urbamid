package it.eng.tz.urbamid.wrappergeo.geoserver.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
public class GsonExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
      return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
      return f.getAnnotation(GsonExclude.class) != null;
    }

}
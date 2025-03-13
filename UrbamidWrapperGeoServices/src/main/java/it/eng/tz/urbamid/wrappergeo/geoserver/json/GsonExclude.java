package it.eng.tz.urbamid.wrappergeo.geoserver.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GsonExclude {
  // Field tag only annotation
}

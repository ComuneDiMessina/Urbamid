package it.eng.tz.urbamid.wrappergeo.geoserver.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * @author Andrea Pogliaghi at pogliaghi@gesp.it
 *
 * @param <T>
 */
public class JsonListOf<T> implements ParameterizedType
{
  private Class<?> wrapped;

  public JsonListOf(Class<T> wrapper)
  {
    this.wrapped = wrapper;
  }

  @Override
  public Type[] getActualTypeArguments()
  {
      return new Type[] { wrapped };
  }

  @Override
  public Type getRawType()
  {
    return List.class;
  }

  @Override
  public Type getOwnerType()
  {
    return null;
  }
}
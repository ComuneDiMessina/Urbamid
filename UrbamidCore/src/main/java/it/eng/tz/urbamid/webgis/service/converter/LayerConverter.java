package it.eng.tz.urbamid.webgis.service.converter;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.core.service.util.IBeanPopulate;
import it.eng.tz.urbamid.webgis.dao.mapper.LayerBean;
import it.eng.tz.urbamid.webgis.dao.model.Layer;

/**
 * Converter tra {@link Layer} e {@link LayerBean}
 * 
 * @author Alessandro Paolillo
 * @date 21 ott 2016
 * @version 1.0
 */
@Component
public class LayerConverter implements IBeanPopulate<Layer, LayerBean>
{

	public LayerConverter()
	{
	}

	/**
	 * @author Alessandro Paolillo
	 * @date 21 ott 2016
	 * @version 1.0
	 * @see it.eng.tz.core.util.IBeanPopulate#convertdbToPage(java.lang.Object)
	 */
	@Override
	public LayerBean convertdbToPage(Layer src) throws Exception
	{
		LayerBean result = new LayerBean();
		result.setProperties(src.getProperties());
		result.setWktGeometry(src.getWktGeometry());
		return result;
	}

	/**
	 * @author Alessandro Paolillo
	 * @date 21 ott 2016
	 * @version 1.0
	 * @see it.eng.tz.core.util.IBeanPopulate#convertpageToDb(java.lang.Object)
	 */
	@Override
	public Layer convertpageToDb(LayerBean src) throws Exception
	{
		throw new IllegalArgumentException("Metodo non implementato");
	}

}

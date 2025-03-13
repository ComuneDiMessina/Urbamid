package it.eng.tz.urbamid.amministrazione.service.converter;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.amministrazione.dao.mapper.RuoliFunzionalitaBean;
import it.eng.tz.urbamid.amministrazione.dao.model.RuoliFunzionalita;
import it.eng.tz.urbamid.core.service.util.IBeanPopulate;

/**
 * Converter tra {@link RuoliFunzionalita} e {@link RuoliFunzionalitaBean}
 * 
 * @author Alessandro Paolillo
 * @date 21 ott 2016
 * @version 1.0
 */
@Component
public class RuoliFunzionalitaConverter implements IBeanPopulate<RuoliFunzionalita, RuoliFunzionalitaBean>
{

	public RuoliFunzionalitaConverter()
	{
	}

	/**
	 * @author Alessandro Paolillo
	 * @date 21 ott 2016
	 * @version 1.0
	 * @see it.eng.tz.core.util.IBeanPopulate#convertdbToPage(java.lang.Object)
	 */
	@Override
	public RuoliFunzionalitaBean convertdbToPage(RuoliFunzionalita src) throws Exception
	{
		RuoliFunzionalitaBean result = new RuoliFunzionalitaBean();
		result.setId(src.getId());
		result.setRuolo(src.getRuolo());
		result.setFunzionalita(src.getFunzionalita());
		return result;
	}

	/**
	 * @author Alessandro Paolillo
	 * @date 21 ott 2016
	 * @version 1.0
	 * @see it.eng.tz.core.util.IBeanPopulate#convertpageToDb(java.lang.Object)
	 */
	@Override
	public RuoliFunzionalita convertpageToDb(RuoliFunzionalitaBean src) throws Exception
	{
		throw new IllegalArgumentException("Metodo non implementato");
	}

}

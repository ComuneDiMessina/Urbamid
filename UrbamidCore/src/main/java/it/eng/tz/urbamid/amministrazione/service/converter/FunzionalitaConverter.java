package it.eng.tz.urbamid.amministrazione.service.converter;

import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.amministrazione.dao.mapper.FunzionalitaBean;
import it.eng.tz.urbamid.amministrazione.dao.mapper.RuoliFunzionalitaBean;
import it.eng.tz.urbamid.amministrazione.dao.model.Funzionalita;
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
public class FunzionalitaConverter implements IBeanPopulate<Funzionalita, FunzionalitaBean>
{

	public FunzionalitaConverter()
	{
	}

	/**
	 * @author Alessandro Paolillo
	 * @date 21 ott 2016
	 * @version 1.0
	 * @see it.eng.tz.core.util.IBeanPopulate#convertdbToPage(java.lang.Object)
	 */
	@Override
	public FunzionalitaBean convertdbToPage(Funzionalita src) throws Exception
	{
		FunzionalitaBean result = new FunzionalitaBean();
		result.setId(src.getId());
		result.setCodice(src.getCodice());
		result.setDenominazione(src.getDenominazione());
		result.setImgUrl(src.getImgUrl());
		result.setLinkUrl(src.getLinkUrl());
		result.setPermesso(src.getPermesso());

		return result;
	}

	/**
	 * @author Alessandro Paolillo
	 * @date 21 ott 2016
	 * @version 1.0
	 * @see it.eng.tz.core.util.IBeanPopulate#convertpageToDb(java.lang.Object)
	 */
	@Override
	public Funzionalita convertpageToDb(FunzionalitaBean src) throws Exception
	{
		throw new IllegalArgumentException("Metodo non implementato");
	}

}
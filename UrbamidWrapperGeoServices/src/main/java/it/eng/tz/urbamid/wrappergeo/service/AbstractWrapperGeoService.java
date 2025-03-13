package it.eng.tz.urbamid.wrappergeo.service;

import org.springframework.data.domain.Sort;

public class AbstractWrapperGeoService {

	
	/**
	 * Metodo per costruire l'ordinamento dei risultati restituiti dalla query
	 * @param campoOrd
	 * @param tipoOrd
	 * @return
	 */
	protected Sort getSort(String campoOrd, String tipoOrd)
	{
		Sort sort = Sort.by(campoOrd);
		if (tipoOrd.equals("asc")) 
		{
			sort.ascending();
		}
		else if (tipoOrd.equals("asc")) 
		{
			sort.descending();
		}
		return sort;
	}
	
	
	protected String getFormattedNumber(Integer num, String numDigit){
		
		// inserire controllo che sia un numero
		
		StringBuffer sb = new StringBuffer("%");
		sb.append(numDigit);
		sb.append("d");
		
		return String.format(sb.toString(), num); 
	}

	protected String normalizzaNumeroMappale(String mappale) {
		if(mappale.chars().allMatch(Character::isDigit)) {
			if (mappale.length() == 1) {
				return "0000" + mappale;
			} else if (mappale.length() == 2) {
				return "000" + mappale;
			} else if (mappale.length() == 3) {
				return "00" + mappale;
			} else if (mappale.length() == 4) {
				return "0" + mappale;
			} else {
				return null;
			}
		} else {
			return mappale;
		}
	}
		
}

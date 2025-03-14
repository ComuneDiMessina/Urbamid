package it.eng.tz.urbamid.catasto.service;

import org.springframework.data.domain.Sort;

import it.eng.tz.urbamid.catasto.util.FieldFilter;

public class AbstractCatastoService extends FieldFilter {

	
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
	
	protected String normalizzaNumero(String numero) {
		if(numero != null) {
			if(numero.chars().allMatch(Character::isDigit)) {
				if(numero.length() == 1) {
					return "0000" + numero;
				} else if(numero.length() == 2) {
					return "000" + numero;
				} else if(numero.length() == 3) {
					return "00" + numero;
				} else if(numero.length() == 4) {
					return "0" + numero;
				} else {
					return numero;
				}
			} else {
				return numero;
			}
		} else {
			return numero;
		}
	}
	
	protected String normalizzaSubalterno(String subalterno) {
		if(subalterno != null) {
			if(subalterno.chars().allMatch(Character::isDigit)) {
				if(subalterno.length() == 1) {
					return "000" + subalterno;
				} else if(subalterno.length() == 2) {
					return "00" + subalterno;
				} else if(subalterno.length() == 3) {
					return "0" + subalterno;
				} else {
					return subalterno;
				}
			} else {
				return subalterno;
			}
		} else {
			return subalterno;
		}
	}
	
	protected String normalizzaZona(String zona) {
		if(zona != null) {
			if(zona.chars().allMatch(Character::isDigit)) {
				if(zona.length() == 1) {
					return "00" + zona;
				} else if(zona.length() == 2) {
					return "0" + zona;
				} else {
					return zona;
				}
			} else {
				return zona;
			}
		} else {
			return zona;
		}
	}

	protected String normalizzaNumeroMappale(String mappale) {
		if (mappale != null) {
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
					return mappale;
				}
			} else {
				return mappale;
			}			
		} else {
			return mappale;
		}
	}

	
	protected String normalizzaNumeroFoglio(String foglio) {
		if(foglio != null) {
			if(foglio.chars().allMatch(Character::isDigit)) {
				if (foglio.length() == 1) {
					return "000" + foglio;
				} else if (foglio.length() == 2) {
					return "00" + foglio;
				} else if (foglio.length() == 3) {
					return "0" + foglio;
				} else if (foglio.length() == 4) {
					return foglio;
				} else {
					return null;
				}
			} else {
				return foglio;
			}			
		} else {
			return foglio;
		}
	}
	
	protected String normalizzaNumeroFoglioPerRP(String foglio) {
		if(foglio != null && !foglio.isEmpty()) {
			if (foglio.startsWith("000"))
				return foglio.substring(foglio.indexOf("000")+3,foglio.length());
			else if (foglio.startsWith("00"))
				return foglio.substring(foglio.lastIndexOf("00")+2,foglio.length()); // FASTEST
			else if (foglio.startsWith("0"))
				return foglio.substring(foglio.lastIndexOf("0")+1,foglio.length()); // FASTEST
			else 
				return foglio;
		} else {
			return foglio;
		}
	}
	
	protected String normalizzaNumeroMappalePerRP(String mappale) {
		if(mappale != null && !mappale.isEmpty()) {
			if (mappale.startsWith("0000"))
				return mappale.substring(mappale.indexOf("0000")+4,mappale.length());
			else if (mappale.startsWith("000"))
				return mappale.substring(mappale.indexOf("000")+3,mappale.length());
			else if (mappale.startsWith("00"))
				return mappale.substring(mappale.indexOf("00")+2,mappale.length());
			else if (mappale.startsWith("0"))
				return mappale.substring(mappale.indexOf("0")+1,mappale.length());
			else 
				return mappale;
		} else {
			return mappale;
		}
	}
		
}

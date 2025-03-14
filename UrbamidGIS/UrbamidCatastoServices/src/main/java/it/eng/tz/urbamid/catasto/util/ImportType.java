package it.eng.tz.urbamid.catasto.util;

import org.springframework.util.Assert;

/**
 * Enumerazione per i tipi di batch che è possibile eseguire.
 * I tipi sono due: <br/>
 * <ol>
 * 	<li><b>ATTUALITA</b>: batch per il caricamento dell'attualita' dei dati catastali ad una certa data, ovvero
 * i dati catastali completi ad una certa data.</li>
 *  <li><b>AGGIORNAMENTO</b>: batch per il caricamento dell'aggiornamento dei dati catastali avvenuto in un certo range
 *  di date.</li>
 * </ol>
 */
public enum ImportType {
	
	AGGIORNAMENTO,
	ATTUALITA;

	public static ImportType fromString(String batchType) {
		Assert.hasLength(batchType, "String fot batch type MUST non be empty!");
		ImportType type = null;
		for(ImportType bt : ImportType.values()) {
			if(bt.name().equalsIgnoreCase(batchType)) {
				type = bt;
				break;
			}
		}
		return type;
	}
	
}

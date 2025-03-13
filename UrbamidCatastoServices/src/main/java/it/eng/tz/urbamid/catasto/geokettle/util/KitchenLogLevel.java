package it.eng.tz.urbamid.catasto.geokettle.util;

import org.springframework.util.StringUtils;

/**
 * Enumerazione che astrae i codici dei livelli di log per l'esecuzione dello script kitchen.sh, in
 * accordo con la documentazione Pentaho a <a href="https://help.pentaho.com/Documentation/7.1/0L0/0Y0/070">questo link</a>.
 */
public enum KitchenLogLevel {
	
	BASIC("Basic"), 
	DETAILED("Detailed"), 
	DEBUG("Debug"),
	ROW_LEVEL("Rowlevel"),
	ERROR("Error"),
	NOTHING("Nothing");
	
	private String level;
	
	private KitchenLogLevel(String level) {
		this.level = level;
	}
	
	public String level() {
		return this.level;
	}
	
	public static KitchenLogLevel fromString(String logLevel) {
		KitchenLogLevel kitchenLogLevel = KitchenLogLevel.BASIC;
		if(StringUtils.hasText(logLevel)) {
			for(KitchenLogLevel ll : KitchenLogLevel.values()) {
				if(ll.level().equalsIgnoreCase(logLevel)) {
					kitchenLogLevel = ll;
					break;
				}
			}
		}
		return kitchenLogLevel;
	}
	
}

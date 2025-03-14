package it.eng.tz.urbamid.catasto.geokettle.util;

import java.util.Optional;

/**
 * Quando eseguiamo lo script kitchen.sh da linea di comando, viene (in generale) restituito uno status code 
 * numerico che indica lo stato di esecuzione dello script.
 * In accordo con la documentazione Pentaho a <a href="https://help.pentaho.com/Documentation/7.1/0L0/0Y0/070">questo link</a>,
 * è stata costruita l'enumeration seguente che astrae i possibili codici di stato.
 */
public enum KitchenScriptStatusCode {
	
	STATUS_CODE_0(0, "The job ran without a problem.", true),
	STATUS_CODE_1(1, "Errors occurred during processing.", false),
	STATUS_CODE_2(2, "An unexpected error occurred during loading or running of the job.", false),
	STATUS_CODE_7(7, "The job couldn't be loaded from XML or the Repository.", false),
	STATUS_CODE_8(8, "Error loading steps or plugins (error in loading one of the plugins mostly).", false),
	STATUS_CODE_9(9, "Command line usage printing.", true);
	
	private int statusCode;
	private String descrizione;
	private boolean isEsecuzioneOK;
	
	private KitchenScriptStatusCode(int statusCode, String descrizione, boolean isEsecuzioneOK) {
		this.statusCode = statusCode;
		this.descrizione = descrizione;
		this.isEsecuzioneOK = isEsecuzioneOK;
	}

	public int statusCode() {
		return this.statusCode;
	}
	
	public String descrizione() {
		return this.descrizione;
	}
	
	public boolean isEsecuzioneOK() {
		return this.isEsecuzioneOK;
	}
	
	public static Optional<KitchenScriptStatusCode> fromStatusCode(int statusCode) {
		KitchenScriptStatusCode kitchenScriptStatusCode = null;
		for(KitchenScriptStatusCode code : KitchenScriptStatusCode.values()) {
			if(code.statusCode == statusCode) {
				kitchenScriptStatusCode = code;
				break;
			}
		}
		return Optional.ofNullable(kitchenScriptStatusCode);
	}
	
	public static int[] statiOK() {
		int size = 0;
		for(KitchenScriptStatusCode code : KitchenScriptStatusCode.values()) {
			if(code.isEsecuzioneOK) 
				size++;
		}
		int index = 0;
		int[] statiOK = new int[size];
		for(KitchenScriptStatusCode code : KitchenScriptStatusCode.values()) {
			if(code.isEsecuzioneOK) {
				statiOK[index] = code.statusCode;
				index++;
			}
		}
		return statiOK;
	}
	
	public static int[] statiKO() {
		int size = 0;
		for(KitchenScriptStatusCode code : KitchenScriptStatusCode.values()) {
			if(!code.isEsecuzioneOK) 
				size++;
		}
		int index = 0;
		int[] statiKO = new int[size];
		for(KitchenScriptStatusCode code : KitchenScriptStatusCode.values()) {
			if(!code.isEsecuzioneOK) {
				statiKO[index] = code.statusCode;
				index++;
			}
		}
		return statiKO;
	}

}

package it.eng.tz.urbamid.catasto.web.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eng.tz.urbamid.catasto.util.ImportType;

@ApiModel(description="Modello per i parametri di avvio di un job di import", value="ParametriAvvioJob")
public class ParametriAvvioJobDTO implements Serializable {

	private static final long serialVersionUID = -7858438699560928502L;

	/**
	 * Livello di log di GeoKettle
	 */
	private String livelloLog;
	
	/**
	 * Tipo di import
	 */
	@NotNull
	private ImportType importType;
	
	/**
	 * Parametri di connessione al database
	 */
	@Valid
	private ConnessioneDatabaseDTO connessione;
	
	/**
	 * Costruttore di default.
	 */
	public ParametriAvvioJobDTO() {
		super();
	}

	@ApiModelProperty(name="livelloLog", position=1, required=true, value="Livello di log", example="Basic")
	public String getLivelloLog() {
		return livelloLog;
	}

	public void setLivelloLog(String livelloLog) {
		this.livelloLog = livelloLog;
	}

	@ApiModelProperty(name="importType", position=2, required=true, value="Tipo di import", example = "ATTUALITA")
	public ImportType getImportType() {
		return importType;
	}

	public void setImportType(ImportType importType) {
		this.importType = importType;
	}

	@ApiModelProperty(name="connessione", position=3, required=true, value="Parametri di connessione al db")
	public ConnessioneDatabaseDTO getConnessione() {
		return connessione;
	}

	public void setConnessione(ConnessioneDatabaseDTO connessione) {
		this.connessione = connessione;
	}

	@Override
	public String toString() {
		return "ParametriAvvioJobDTO [livelloLog=" + livelloLog + ", importType=" + importType + ", connessione="
				+ connessione + "]";
	}

}

package it.eng.tz.urbamid.wrappergeo.geoserver.entities;

import java.io.Serializable;

public class GSMetadata implements Serializable {

	private static final long serialVersionUID = 4985269082711234384L;
	
	private GSEntryDB entry;

	public GSEntryDB getEntry() {
		return entry;
	}

	public void setEntry(GSEntryDB entry) {
		this.entry = entry;
	}
	
}

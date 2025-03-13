package it.eng.tz.urbamid.catasto.geokettle.util;


import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe che serve per stampare nei log applicativi i valori di log provenienti dallo script.
 */
public class PrintingLogOutputStream extends LogOutputStream {

	private static final Logger LOG = LoggerFactory.getLogger(PrintingLogOutputStream.class.getName());
	
	private StringBuilder output = new StringBuilder();

	@Override
	protected void processLine(String line, int level) {
		LOG.debug("APACHE COMMON EXEC OUTPUT: {}", line);
		output.append(line).append("\n");
	}

	public String getOutput() {
		return output.toString();
	}
	
}
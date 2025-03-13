package it.eng.tz.urbamid.catasto.geokettle.util;


import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintResultHandler extends DefaultExecuteResultHandler {

	private static final Logger LOG = LoggerFactory.getLogger(PrintResultHandler.class.getClass());
	
	private ExecuteWatchdog watchdog;

	public PrintResultHandler(final ExecuteWatchdog watchdog) {
		this.watchdog = watchdog;
	}

	public PrintResultHandler(final int exitValue) {
		super.onProcessComplete(exitValue);
	}

	@Override
	public void onProcessComplete(final int exitValue) {
		super.onProcessComplete(exitValue);
		LOG.info("[RESULT HANDLER] Lo script è stato correttamente eseguito.");
	}

	@Override
	public void onProcessFailed(final ExecuteException e) {
		super.onProcessFailed(e);
		if (watchdog != null && watchdog.killedProcess()) {
			LOG.error("[RESULT HANDLER] Il processo dello script è andato in time-out");
		} else {
			LOG.error("[RESULT HANDLER] Il processo dello script è fallito: {}.", e.getMessage());
		}
	}
}
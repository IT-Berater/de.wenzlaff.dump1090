package de.wenzlaff.dump1090.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.be.Flugzeuge;

/**
 * Klasse logt die Flugzeuge in das Logfile.
 * 
 * @author Thomas Wenzlaff
 * 
 */
public class LogAktion implements Aktion {

	private static final Logger LOG = LoggerFactory.getLogger(LogAktion.class);

	private Flugzeuge flugzeuge;

	/**
	 * Der Konstruktor mit Flugzeug.
	 * 
	 * @param flugzeuge
	 */
	public LogAktion(Flugzeuge flugzeuge) {
		this.flugzeuge = flugzeuge;
	}

	@Override
	public void run() {
		if (this.flugzeuge != null && this.flugzeuge.getNotfall() != null) {
			LOG.info("Anzahl Flugzeuge im Notfall: {}", this.flugzeuge.getNotfall().size());
			LOG.info("Flugzeuge im Notfall: {}\n", this.flugzeuge.getNotfall());
		} else {
			LOG.info("Kein Flugzeug vorhanden");
		}
	}

	public void error(Exception e) {
		LOG.error("Fehler: {}", e);
	}

}

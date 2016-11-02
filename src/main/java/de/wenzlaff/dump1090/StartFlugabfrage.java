package de.wenzlaff.dump1090;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.action.TimerAktion;

/**
 * 
 * @author Thomas Wenzlaff
 *
 */
public class StartFlugabfrage {

	private static final Logger LOG = LoggerFactory.getLogger(StartFlugabfrage.class);

	/** Plant die Ausführungen. */
	private ScheduledExecutorService scheduler;

	/**
	 * Start der Abfrage.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.info("Starte abfrage der Flugzeuge die einen Notfall melden.");
		StartFlugabfrage start = new StartFlugabfrage();
		start.startAnzahlProTagTimer();
	}

	public StartFlugabfrage() {
		scheduler = Executors.newScheduledThreadPool(1);
	}

	private void startAnzahlProTagTimer() {
		// Todo: zum testen alle 10 Sekunden
		LOG.info("Frage alle 10 Sekunden ab...");
		scheduler.scheduleAtFixedRate(new TimerAktion(), 1, 10, TimeUnit.SECONDS);
	}

}

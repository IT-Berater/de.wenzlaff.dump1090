package de.wenzlaff.dump1090;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.action.TimerAktion;

/**
 * Startet eine Abfrage.
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
		if (args.length != 2) {
			LOG.error("Aufruf des StartFlugabfrage Programms mit zwei Parametern: de.wenzlaff.dump1090.StartFlugabfrage [Server IP] [Intervall in Minuten]");
			LOG.error("z.B.: de.wenzlaff.dump1090.StartFlugabfrage 10.0.9.32 5");
			return;
		}
		String ip = args[0];
		String interval = args[1];
		LOG.info("Starte abfrage der Flugzeuge die einen Notfall melden. Server IP Adresse: {} Intervall alle: {} Minuten", ip, interval);

		StartFlugabfrage start = new StartFlugabfrage();
		start.startAnzahlProTagTimer(ip, interval);
	}

	public StartFlugabfrage() {
		scheduler = Executors.newScheduledThreadPool(1);
	}

	private void startAnzahlProTagTimer(String ip, String interval) {
		LOG.info("Frage alle " + interval + " Minuten ab...");
		scheduler.scheduleAtFixedRate(new TimerAktion(ip), 0, Long.parseLong(interval), TimeUnit.MINUTES);
	}

}

package de.wenzlaff.dump1090;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.action.PushoverAktion;
import de.wenzlaff.dump1090.action.TimerAktion;

/**
 * Startet eine Abfrage.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class StartFlugabfrage {

	private static final Logger LOG = LoggerFactory.getLogger(StartFlugabfrage.class);

	private static final String VERSION = "0.0.1";

	/** Plant die Ausführungen. */
	private ScheduledExecutorService scheduler;

	private String ip;
	private String interval;

	/**
	 * Start der Abfrage. Endet nie. Bricht ab, wenn nicht zwei Parameter übergeben wurden.
	 * 
	 * Aufruf z.B. java -jar de.wenzlaff.dump1090-0.0.2-SNAPSHOT.jar 10.0.9.32 5
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			LOG.error("Programm Abbruch, da nicht die richtige Anzahl von Parametern übergeben.");
			LOG.error("Aufruf des StartFlugabfrage Programms mit zwei Parametern: [Server IP] [Intervall in Minuten]");
			LOG.error("z.B.: java -jar de.wenzlaff.dump1090-0.0.2-SNAPSHOT.jar 10.0.9.32 5");
			LOG.error("Details zum Programm de.wenzlaff.dump1090 mit Versions Nr. {} siehe http://www.wenzlaff.info", VERSION);
			System.exit(8);
		}
		String ip = args[0];
		String interval = args[1];
		LOG.info("Starte abfrage der Flugzeuge die einen Notfall melden. Server IP Adresse: {} Intervall alle: {} Minuten", ip, interval);

		StartFlugabfrage task = new StartFlugabfrage(ip, interval);
		task.startEndlosNotfallabfrage();
	}

	/**
	 * Startet die Abfrage
	 * 
	 * @param ip
	 * @param interval
	 */
	public StartFlugabfrage(String ip, String interval) {
		this.ip = ip;
		this.interval = interval;

		// Pushover senden, das es nun läuft ...
		PushoverAktion startNachrich = new PushoverAktion();
		startNachrich.sendPushoverNachricht("Starte das de.wenzlaff.dump1090 Programm alle " + interval + " Minuten gegen IP Adresse: " + ip);

		// erzeugen eines Thread Pools
		scheduler = Executors.newScheduledThreadPool(1);

	}

	public void startEndlosNotfallabfrage() {
		// Abfrage im Interval starten
		startAnzahlProTagTimer(this.ip, this.interval);
	}

	private void startAnzahlProTagTimer(String ip, String interval) {
		LOG.info("Frage nun alle " + interval + " Minuten ohne weitere Ausgaben ab ...");
		scheduler.scheduleAtFixedRate(new TimerAktion(ip), 0, Long.parseLong(interval), TimeUnit.MINUTES);
	}

}

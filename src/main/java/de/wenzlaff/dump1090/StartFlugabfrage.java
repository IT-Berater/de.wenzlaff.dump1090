package de.wenzlaff.dump1090;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.action.PushoverAktion;
import de.wenzlaff.dump1090.action.TimerAktion;
import de.wenzlaff.dump1090.action.Version;

/**
 * Startet eine Abfrage.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class StartFlugabfrage {

	private static final Logger LOG = LoggerFactory.getLogger(StartFlugabfrage.class);

	private static final String VERSION = new Version().getVersion();

	/** Plant die Ausführungen. */
	private ScheduledExecutorService scheduler;

	/** Die IP Adresse des Servers wo Dump1090 läuft. z.B. 10.0.9.32 */
	private String ipAdresse;

	/** Das Abfrage Interval in Minuten. */
	private String intervalInMinuten;

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
	 * @param ipAdresse
	 * @param intervalInMinuten
	 */
	public StartFlugabfrage(String ipAdresse, String intervalInMinuten) {
		this.ipAdresse = ipAdresse;
		this.intervalInMinuten = intervalInMinuten;

		// Pushover senden, das es nun läuft ...
		PushoverAktion startNachrich = new PushoverAktion();
		startNachrich.sendPushoverNachricht("Starte das de.wenzlaff.dump1090 " + VERSION + " Programm alle " + this.intervalInMinuten + " Minuten gegen IP Adresse: " + ipAdresse);

		// erzeugen eines Thread Pools
		scheduler = Executors.newScheduledThreadPool(1);
	}

	public void startEndlosNotfallabfrage() {
		// Abfrage im Interval starten
		startAnzahlProTagTimer(this.ipAdresse, this.intervalInMinuten);
	}

	private void startAnzahlProTagTimer(String ipAdresse, String intervalInMinuten) {
		LOG.info("Frage nun alle " + intervalInMinuten + " Minuten ohne weitere Ausgaben ab ...");
		scheduler.scheduleAtFixedRate(new TimerAktion(ipAdresse), 0, Long.parseLong(intervalInMinuten), TimeUnit.MINUTES);
	}

	private synchronized String getVersion() {
		String version = null;

		// try to load from maven properties first
		try {
			Properties p = new Properties();
			InputStream is = getClass().getResourceAsStream("/META-INF/maven/de.wenzlaff.dump1090/de/wenzlaff/dump1090/pom.properties");
			if (is != null) {
				p.load(is);
				version = p.getProperty("version", "");
			}
		} catch (Exception e) {
			// ignore
		}

		// fallback to using Java API
		if (version == null) {
			Package aPackage = getClass().getPackage();
			if (aPackage != null) {
				version = aPackage.getImplementationVersion();
				if (version == null) {
					version = aPackage.getSpecificationVersion();
				}
			}
		}

		if (version == null) {
			// we could not compute the version so use a blank
			version = "";
		}

		return version;
	}

}

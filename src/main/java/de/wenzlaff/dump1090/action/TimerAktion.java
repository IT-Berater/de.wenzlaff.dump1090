package de.wenzlaff.dump1090.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.wenzlaff.dump1090.be.Flugzeug;
import de.wenzlaff.dump1090.be.Flugzeuge;

/**
 * Einlesen der aircraft.json Datei von einem Server.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class TimerAktion extends TimerTask implements Aktion {

	private static final int MAX_GESPEICHERTE_FLUGZEUGE = 50;

	private static final Logger LOG = LoggerFactory.getLogger(TimerAktion.class);

	private String serverUrl;

	/**
	 * Die Flugzeuge die schon benachrichtig wurden. Key ist die hex.
	 */
	private Map<String, Flugzeug> benachrichtigteFlugzeuge;

	public TimerAktion(String ip) {
		setUrl(ip);
		benachrichtigteFlugzeuge = new HashMap<String, Flugzeug>();
	}

	@Override
	public void run() {
		LOG.debug("Timer Aktion {}", new Date());
		InputStream is;
		try {
			is = new URL(serverUrl).openStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String aircraftDatei = JsonReader.readAll(rd);

			Gson gson = new GsonBuilder().create();
			Flugzeuge flugzeuge = gson.fromJson(aircraftDatei, Flugzeuge.class);

			if (flugzeuge.getNotfall().size() > 0) { // nur wenn im Notfall aktion ausgeben
				LogAktion log = new LogAktion(flugzeuge);
				log.run();
				PushoverAktion pushover = new PushoverAktion(flugzeuge);

				pushover.run();
			}

			// Nachricht wenn Flugzeug in Hannover landet
			List<Flugzeug> alleImLandeAnflug = flugzeuge.getFlugzeugeImLandeanflug();
			if (alleImLandeAnflug.size() > 0) {

				LOG.debug("Anzahl Flugzeuge in Landeanflug: " + alleImLandeAnflug);

				// Flugzeuge merken
				for (int i = 0; i < alleImLandeAnflug.size(); i++) {
					Flugzeug f = alleImLandeAnflug.get(i);
					// und wenn noch nicht erfolgt, einmal Nachricht senden
					if (!this.benachrichtigteFlugzeuge.containsKey(f.getHex())) {
						LOG.info("---------> Nachricht senden: {}", f);
						this.benachrichtigteFlugzeuge.put(f.getHex(), f);
						PushoverAktion pushover = new PushoverAktion(f);
						pushover.sendPushoverNachricht();
					}
				}
			}

			// löschen der gemerkten Flugzeuge
			if (this.benachrichtigteFlugzeuge.values().size() > MAX_GESPEICHERTE_FLUGZEUGE) {
				LOG.info("---------> LÖSCHE alle {} Flugzeuge im Speicher.", MAX_GESPEICHERTE_FLUGZEUGE);
				this.benachrichtigteFlugzeuge.clear();
			}

		} catch (ConnectException e) {
			LOG.error("Verbindungs Error zu Adresse {} Error: {}", serverUrl, e);
		} catch (IOException e1) {
			LOG.error("Error {}", e1);
		} catch (Exception e2) {
			LOG.error("Error {}", e2);
		}
	}

	private void setUrl(String ip) {
		serverUrl = "http://" + ip + "/dump1090/data/aircraft.json";
	}

}

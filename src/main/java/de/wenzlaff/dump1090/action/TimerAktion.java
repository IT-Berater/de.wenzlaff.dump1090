package de.wenzlaff.dump1090.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.wenzlaff.dump1090.be.Flugzeuge;
import de.wenzlaff.dump1090.util.JsonUtil;
import net.pushover.client.PushoverException;

public class TimerAktion extends TimerTask {

	private static final Logger LOG = LoggerFactory.getLogger(TimerAktion.class);

	private String serverUrl;

	public TimerAktion(String ip) {
		setUrl(ip);
	}

	private void setUrl(String ip) {
		serverUrl = "http://" + ip + "/dump1090/data/aircraft.json";
	}

	@Override
	public void run() {

		InputStream is;
		try {
			is = new URL(serverUrl).openStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String aircraftDatei = JsonUtil.readAll(rd);

			Gson gson = new GsonBuilder().create();
			Flugzeuge flugzeuge = gson.fromJson(aircraftDatei, Flugzeuge.class);

			if (flugzeuge.getNotfall().size() > 0) { // nur wenn im Notfall aktion ausgeben
				LogAktion log = new LogAktion(flugzeuge);
				log.run();
				PushoverAktion pushover = new PushoverAktion(flugzeuge);
				try {
					pushover.run();
				} catch (PushoverException e) {
					log.error(e);
				}
			}
		} catch (ConnectException e) {
			LOG.error("Verbindungs Error zu Adresse {} Error: {}", serverUrl, e);
		} catch (IOException e1) {
			LOG.error("Error {}", e1);
		}
	}

}

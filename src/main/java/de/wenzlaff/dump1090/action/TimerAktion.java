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

public class TimerAktion extends TimerTask {

	private static final Logger LOG = LoggerFactory.getLogger(TimerAktion.class);

	private static final String IP = "10.0.9.32";

	private static final String DUMP_SERVER_URL = "http://" + IP + "/dump1090/data/aircraft.json";

	@Override
	public void run() {

		InputStream is;
		try {
			is = new URL(DUMP_SERVER_URL).openStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String aircraftDatei = JsonUtil.readAll(rd);

			Gson gson = new GsonBuilder().create();
			Flugzeuge flugzeuge = gson.fromJson(aircraftDatei, Flugzeuge.class);

			if (flugzeuge.getNotfall().size() > 0) { // nur wenn im Notfall
														// aktion ausgeben
				LOG.info("Anzahl Flugzeuge im Notfall: {}", flugzeuge.getNotfall().size());
				LOG.info("Flugzeuge im Notfall: {}", flugzeuge.getNotfall());
			}
		} catch (ConnectException e) {
			LOG.error("Verbindungs Error zu Adresse {} Error: {}", DUMP_SERVER_URL, e);
		} catch (IOException e1) {
			LOG.error("Error {}", e1);
		}
	}

}

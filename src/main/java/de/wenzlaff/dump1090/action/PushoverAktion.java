package de.wenzlaff.dump1090.action;

import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.be.Converter;
import de.wenzlaff.dump1090.be.Flugzeug;
import de.wenzlaff.dump1090.be.Flugzeuge;
import de.wenzlaff.dump1090.be.PushoverSound;
import net.pushover.client.MessagePriority;
import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import net.pushover.client.Status;

/**
 * Diese Klasse versendet die Flugzeuginformatioen an Pushover.
 * 
 * Bei jedem Flugnotfall eine Nachricht.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class PushoverAktion implements Aktion {

	private static final String NACHRICHTEN_TITEL = "de.wenzlaff.dump1090";

	private static final Logger LOG = LoggerFactory.getLogger(PushoverAktion.class);

	private String pushoverUserToken;

	private String pushoverMyApiToken;

	private String pushoverNachrichtUrl;

	private String pushoverDevices[];

	/** Hier werden alle Flugzeuge (HEX) aufgelistet die auf der Blacklist stehen. */
	private String blackListHexFlugzeuge[];

	private Flugzeuge flugzeuge;

	private Flugzeug flugzeug;

	/**
	 * Konstruktor.
	 * 
	 * @param flugzeuge
	 */
	public PushoverAktion(Flugzeuge flugzeuge) {
		this.flugzeuge = flugzeuge;
		setProperties();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param flugzeug
	 */
	public PushoverAktion(Flugzeug flugzeug) {
		this.flugzeug = flugzeug;
		setProperties();
	}

	public PushoverAktion() {
		setProperties();
	}

	private void setProperties() {
		Properties properties = SetupReader.getProperties();
		pushoverUserToken = properties.getProperty("pushover_user_id_token");
		pushoverMyApiToken = properties.getProperty("pushover_my_app_api_token");
		pushoverNachrichtUrl = properties.getProperty("pushover_nachricht_url");

		String pushoverDevice = properties.getProperty("pushover_device", "device");
		if (pushoverDevice != null) {
			pushoverDevices = pushoverDevice.split(";");
			LOG.debug("Sende Pushover an folgende Device {}", pushoverDevice);
		}

		String blackList = properties.getProperty("black_list_flugzeuge");
		if (blackList != null) {
			blackListHexFlugzeuge = blackList.split(";");
			if (!blackList.isEmpty()) {
				LOG.debug("Blocke die folgenden Flugzeuge mit HEX: {}", blackList);
			}
		}
	}

	@Override
	public void run() {
		LOG.info("Pushover Aktion mit {} ... ", this.flugzeuge);

		PushoverClient client = new PushoverRestClient();

		// für jedes Flugzeug eine Nachricht senden
		for (int i = 0; i < this.flugzeuge.getAnzahlFlugzeuge(); i++) {
			// aber nur wenn es nicht auf der Blacklist steht
			Flugzeug flugzeug = this.flugzeuge.getFlugzeuge().get(i);
			if (!isFlugzeugAufBlacklist(flugzeug)) {
				String nachricht = this.flugzeuge.toString();
				LOG.info(nachricht);
				String nachrichtenUrl = pushoverNachrichtUrl + this.flugzeuge.getFlugzeuge().get(i).getHex();
				LOG.info("Nachrichten URL: {}", nachrichtenUrl);

				for (int j = 0; j < pushoverDevices.length; j++) {
					Status result = null;
					try {
						// Senden der Nachricht per Pushover
						result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht)
								.setDevice(pushoverDevices[j]).setPriority(MessagePriority.HIGH).setTitle("Flugzeug - Alarm!").setUrl(nachrichtenUrl)
								.setTitleForURL("Flugzeug Notfall").setSound(PushoverSound.magic.name()).build());
					} catch (PushoverException e) {
						LOG.error("Fehler: Pushover Status: {}", result);
						LOG.error("Fehler:{}", e);
						return;
					}
					// Log status
					if (result.getStatus() != 1) { // Status eins ist OK, alles anderer Fehler siehe https://pushover.net/api
						LOG.error(String.format("Fehler: Pushover Status: %d, Request ID: %s", result.getStatus(), result.getRequestId()));
					} else {
						LOG.info("OK: Pushover Nachricht (Request Id: {} ) erfolgreich versendet um {}.", result.getRequestId(), new Date());
					}
				}
			}
		}
	}

	/**
	 * Methode überprüft ob das Flugzeug auf der Blackliste (properties) ist, dann true sonst false.
	 * 
	 * @param flugzeug
	 *            das zu überprüfende Flugzeug
	 * @return true, wenn es auf der Blacklliste ist, sonst false
	 */
	private boolean isFlugzeugAufBlacklist(Flugzeug flugzeug) {
		// ist das Flugzeug in der Blackliste
		for (int i = 0; i < blackListHexFlugzeuge.length; i++) {
			if (flugzeug.getHex().trim().toLowerCase().equals(blackListHexFlugzeuge[i].trim().toLowerCase())) {
				// ja, es ist in der Liste
				LOG.info("Flugzeug mit HEX {} ist auf der Blacklist und wird ignoriert.", flugzeug.getHex());
				return true;
			}
		}
		return false; // nein, nicht auf der Blackliste
	}

	public void sendPushoverNachricht(String nachricht) {
		LOG.info("OK: Versende Pushover Aktion mit folgender Nachricht: {}", nachricht);

		PushoverClient client = new PushoverRestClient();

		for (int i = 0; i < pushoverDevices.length; i++) {
			Status result = null;
			try {
				result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht).setDevice(pushoverDevices[i])
						.setPriority(MessagePriority.HIGH).setTitle(NACHRICHTEN_TITEL).setUrl("http://www.wenzlaff.info").setTitleForURL("www.wenzlaff.info")
						.setSound(PushoverSound.magic.name()).build()); // sounds siehe https://pushover.net/api#sounds
			} catch (PushoverException e) {
				LOG.error("Fehler beim versenden der Pushover Nachricht: {} wegen: {} mit Result: {}", nachricht, e, result);
			}
		}
	}

	public void sendPushoverNachricht() {
		if (this.flugzeug != null) {
			if (!isFlugzeugAufBlacklist(this.flugzeug)) {
				String nachricht = getNachrichtFormat(this.flugzeug);
				LOG.info("Versende Pushover Aktion mit folgender Nachricht: {}", nachricht);

				String nachrichtenUrl = pushoverNachrichtUrl + this.flugzeug.getHex();
				LOG.info("Nachrichten URL: {}", nachrichtenUrl);

				PushoverClient client = new PushoverRestClient();

				for (int i = 0; i < pushoverDevices.length; i++) {
					Status result = null;
					try {
						result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht)
								.setDevice(pushoverDevices[i]).setPriority(MessagePriority.HIGH).setTitle(NACHRICHTEN_TITEL).setUrl(nachrichtenUrl)
								.setTitleForURL(pushoverNachrichtUrl).setSound(PushoverSound.magic.name()).build()); // sounds siehe https://pushover.net/api#sounds
					} catch (PushoverException e) {
						LOG.error("Fehler beim versenden der Pushover Nachricht: {} wegen: {} mit Result: {}", nachricht, e, result);
					}
				}
			}
		}
	}

	/**
	 * Liefert das Format für die Nachrichten.
	 * 
	 * @return String mit dem Flugzeugformat.
	 */
	private String getNachrichtFormat(Flugzeug flugzeug) {
		StringBuilder builder = new StringBuilder();
		builder.append("Flugzeug ");

		if (flugzeug != null) {
			if (flugzeug.getFlight() != null) {
				builder.append("Flug: ");
				builder.append(flugzeug.getFlight().trim());
				builder.append(", ");
			}
			if (flugzeug.getHex() != null) {
				builder.append("HEX: ");
				builder.append(flugzeug.getHex().toUpperCase());
				builder.append(", ");
			}
			if (flugzeug.getAltitudeAsString() != null) {
				builder.append("Höhe: ");
				builder.append(Converter.getMeterVonFuss(flugzeug.getAltitudeAsString()));
				builder.append(" m, ");
			}
			if (flugzeug.getSpeed() != null) {
				builder.append("Geschwindigkeit: ");
				builder.append(Converter.getKmVonKnoten(flugzeug.getSpeed()));
				builder.append(" km/h, ");
			}
			if (flugzeug.getSquawk() != null) {
				builder.append("Squawk: ");
				builder.append(flugzeug.getSquawk());
				builder.append(", ");
			}
			if (flugzeug.getLat() != null) {
				builder.append("Lat: ");
				builder.append(flugzeug.getLat());
				builder.append(", ");
			}
			if (flugzeug.getLon() != null) {
				builder.append("Lon: ");
				builder.append(flugzeug.getLon());
			}
		}
		return builder.toString();
	}

}

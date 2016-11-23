package de.wenzlaff.dump1090.action;

import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOG = LoggerFactory.getLogger(PushoverAktion.class);

	private String pushoverUserToken;

	private String pushoverMyApiToken;

	private String pushoverNachrichtUrl;

	private String pushoverDevices[];

	private Flugzeuge flugzeuge;

	private Flugzeug flugzeug;

	public PushoverAktion(Flugzeuge flugzeuge) {
		this.flugzeuge = flugzeuge;
		setProperties();
	}

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
		pushoverDevices = pushoverDevice.split(";");
	}

	@Override
	public void run() {
		LOG.info("Pushover Aktion mit {} ... ", this.flugzeuge);

		PushoverClient client = new PushoverRestClient();

		// für jedes Flugzeug eine Nachricht senden
		for (int i = 0; i < this.flugzeuge.getAnzahlFlugzeuge(); i++) {

			String nachricht = this.flugzeuge.toString();
			LOG.info(nachricht);
			String nachrichtenUrl = pushoverNachrichtUrl + this.flugzeuge.getFlugzeuge().get(i).getHex();
			LOG.info("Nachrichten URL: {}", nachrichtenUrl);

			for (int j = 0; j < pushoverDevices.length; j++) {
				Status result = null;
				try {
					// Senden der Nachricht
					result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht)
							.setDevice(pushoverDevices[j]).setPriority(MessagePriority.HIGH).setTitle("Flugzeug - Alarm!").setUrl(nachrichtenUrl).setTitleForURL("Flugzeug Notfall")
							.setSound(PushoverSound.magic.name()).build());
				} catch (PushoverException e) {
					LOG.error("Fehler: Pushover Status: {}", result);
					LOG.error("Fehler:{}", e);
					return;
				}
				// Log status
				if (result.getStatus() != 1) { // Status eins ist OK, alles anderer Fehler siehe https://pushover.net/api
					LOG.error(String.format("Fehler: Pushover Status: %d, Request ID: %s", result.getStatus(), result.getRequestId()));
				} else {
					LOG.info("Pushover Nachricht (Request Id: {} ) erfolgreich versendet um {}.", result.getRequestId(), new Date());
				}
			}
		}
	}

	public void sendPushoverNachricht(String nachricht) {
		LOG.info("Versende Pushover Aktion mit folgender Nachricht: {}", nachricht);

		PushoverClient client = new PushoverRestClient();

		for (int i = 0; i < pushoverDevices.length; i++) {
			Status result = null;
			try {
				result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht).setDevice(pushoverDevices[i])
						.setPriority(MessagePriority.HIGH).setTitle("Nachricht von de.wenzlaff.dump1090").setUrl("http://www.wenzlaff.info").setTitleForURL("www.wenzlaff.info")
						.setSound(PushoverSound.magic.name()).build()); // sounds siehe https://pushover.net/api#sounds
			} catch (PushoverException e) {
				LOG.error("Fehler beim versenden der Pushover Nachricht: {} wegen: {} mit Result: {}", nachricht, e, result);
			}
		}
	}

	public void sendPushoverNachricht() {
		String nachricht = this.flugzeug.getFormat();
		LOG.info("Versende Pushover Aktion mit folgender Nachricht: {}", nachricht);

		String nachrichtenUrl = pushoverNachrichtUrl + this.flugzeug.getHex();
		LOG.info("Nachrichten URL: {}", nachrichtenUrl);

		PushoverClient client = new PushoverRestClient();

		for (int i = 0; i < pushoverDevices.length; i++) {
			Status result = null;
			try {
				result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht).setDevice(pushoverDevices[i])
						.setPriority(MessagePriority.HIGH).setTitle("Nachricht von de.wenzlaff.dump1090").setUrl(nachrichtenUrl).setTitleForURL(pushoverNachrichtUrl)
						.setSound(PushoverSound.magic.name()).build()); // sounds siehe https://pushover.net/api#sounds
			} catch (PushoverException e) {
				LOG.error("Fehler beim versenden der Pushover Nachricht: {} wegen: {} mit Result: {}", nachricht, e, result);
			}
		}
	}

}
package de.wenzlaff.dump1090.action;

import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.be.Flugzeuge;
import de.wenzlaff.dump1090.util.Setup;
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
public class PushoverAktion {

	private static final Logger LOG = LoggerFactory.getLogger(PushoverAktion.class);

	private String pushoverUserToken;

	private String pushoverMyApiToken;

	private String pushoverNachrichtUrl;

	private Flugzeuge flugzeuge;

	public PushoverAktion(Flugzeuge flugzeuge) {
		this.flugzeuge = flugzeuge;

		Properties properties = Setup.getProperties();
		pushoverUserToken = properties.getProperty("pushover_user_id_token");
		pushoverMyApiToken = properties.getProperty("pushover_my_app_api_token");
		pushoverNachrichtUrl = properties.getProperty("pushover_nachricht_url");
	}

	public void run() throws PushoverException {
		LOG.info("Pushover Aktion mit {} ... ", this.flugzeuge);

		PushoverClient client = new PushoverRestClient();

		// für jedes Flugzeug eine Nachricht senden
		for (int i = 0; i < this.flugzeuge.getAnzahlFlugzeuge(); i++) {

			String nachricht = this.flugzeuge.toString();
			LOG.info(nachricht);
			String nachrichtenUrl = pushoverNachrichtUrl + this.flugzeuge.getFlugzeuge().get(0).getHex();
			LOG.info("Nachrichten URL: {}", nachrichtenUrl);

			// Senden der Nachricht
			Status result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht).setDevice("device")
					.setPriority(MessagePriority.HIGH).setTitle("Alarm!").setUrl(nachrichtenUrl).setTitleForURL("Flugzeug Notfall").setSound("magic").build());

			if (result.getStatus() != 1) { // Status eins ist oK, alles anderer Fehler https://pushover.net/api
				LOG.error(String.format("Fehler: Pushover Status: %d, request id: %s", result.getStatus(), result.getRequestId()));
			} else {
				LOG.info("Pushover Nachricht (Request Id: {} ) erfolgreich versendet um {}.", result.getRequestId(), new Date());
			}

		}
	}

	public void sendPushoverNachricht(String nachricht) {
		PushoverClient client = new PushoverRestClient();
		Status result = null;
		try {
			result = client.pushMessage(
					PushoverMessage.builderWithApiToken(pushoverMyApiToken).setUserId(pushoverUserToken).setMessage(nachricht).setDevice("device").setPriority(MessagePriority.HIGH)
							.setTitle("Nachricht von de.wenzlaff.dump1090").setUrl("http://www.wenzlaff.info").setTitleForURL("www.wenzlaff.info").setSound("magic").build());
		} catch (PushoverException e) {
			LOG.error("Fehler beim versenden der Pushover Nachricht: {} wegen: {} mit Result: {}", nachricht, e, result);
		}
	}

}

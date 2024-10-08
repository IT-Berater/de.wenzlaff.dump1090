package de.wenzlaff.dump1090.be;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.action.SetupReader;

/**
 * Flugzeuge.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class Flugzeuge {

	private static final Logger LOG = LoggerFactory.getLogger(Flugzeuge.class);

	/**
	 * Längengrad von Langenhagen in der Tempelhofer Str. 9.742556, Breitengrad
	 * 52.438453 mit Höhe 51 m.
	 */
	private String laengengradMin;

	/**
	 * Die maximale Höhe in Fuss (bei Meter mal * 0.3048 z.B. 2000 Fuss * 0.3 = 609
	 * Meter).
	 */
	private int maxHoehe;

	/** Der Name der Variable muss so sein wie in der JSON Datei. */
	private String now;
	private String messages;
	private List<Flugzeug> aircraft;

	public Flugzeuge() {
		aircraft = new ArrayList<Flugzeug>();

		Properties properties = SetupReader.getProperties();
		laengengradMin = properties.getProperty("pushover_laengengrad_min", "9.742556");
		maxHoehe = Integer.valueOf(properties.getProperty("pushover_max_hoehe", "3000"));
	}

	/**
	 * Liefert alle Flugzeuge mit Luftnotfall.
	 * 
	 * Mit den Codes 7500, 7600 und 7700 werden Informationen über die Art einer
	 * Luftnotlage übermittelt.
	 * 
	 * Gebräuchliche Merksprüche sind angefügt:
	 * 
	 * <pre>
	    7500 – Flugzeugentführung (hijacking; seven-five - man with a knife)
	    7600 – Funkausfall (radio failure; seven-six - hear nix / radio nix / need a radio fix)
	    7700 – Luftnotfall (emergency; seven-seven - going to heaven / falling from heaven / pray to heaven)
	 * </pre>
	 * 
	 * @return die Liste aller Flugzeuge mit einem Notfall.
	 */
	public List<Flugzeug> getNotfall() {
		List<Flugzeug> notfallFlugzeuge = new ArrayList<Flugzeug>();

		for (Flugzeug flugzeug : aircraft) {
			if (flugzeug.getSquawk() != null) {
				if (flugzeug.getSquawk().equals(Luftnotfall.ENTFUEHRUNG.getCode()) || flugzeug.getSquawk().equals(Luftnotfall.FUNKAUSFALL.getCode())
						|| flugzeug.getSquawk().equals(Luftnotfall.LUFTNOTFALL.getCode())) {
					notfallFlugzeuge.add(flugzeug);
				}
			}
		}
		return notfallFlugzeuge;
	}

	/**
	 * Liefert alle Flugzeuge im Landeanflug und Start.
	 * 
	 * @return die Flugzeuge die im An bzw. Abflug sind.
	 */
	public List<Flugzeug> getFlugzeugeImAnAbflug() {

		List<Flugzeug> flugzeuge = new ArrayList<Flugzeug>();

		for (Flugzeug flugzeug : aircraft) {
			if (isImStartUndLandeanflug(flugzeug)) {
				LOG.debug("erfasst {}", flugzeug);
				flugzeuge.add(flugzeug);
			}
		}
		return flugzeuge;
	}

	/**
	 * Liefert true, wenn ein Flugzeug im Landeanflug oder Start ist, sonst false.
	 * 
	 * Es wird überprüft ob das Flugzeug unter der maximalen Höhe ist. Ob das
	 * Flugzeug in der Luft ist. Am Boden wird nicht berücksichtigt.
	 * 
	 * Und ob das Flugzeug aus dem Osten kommt oder startet in richtung Osten.
	 * 
	 * @param flugzeug das überprüft wird.
	 * @return true wenn Start oder Landung aus bzw. nach Osten.
	 */
	private boolean isImStartUndLandeanflug(Flugzeug flugzeug) {
		boolean status = false;

		if (flugzeug.getAltitude() != null) {
			// nur Flugzeuge die kleiner als die maximale Höhe sind
			if (flugzeug.getAltitude().intValue() < maxHoehe) {
				// nur Flugzeuge in der Luft, über den Boden
				if (flugzeug.getAltitude().intValue() > 0) {
					// nur Flugzeuge die von Osten kommen
					if (flugzeug.getLongitude().compareTo(new BigDecimal(laengengradMin)) > 0) {
						status = true;
					}
				}
			}
		}
		return status;
	}

	public void addFlugzeug(Flugzeug f) {
		aircraft.add(f);
	}

	public void clear() {
		aircraft.clear();
	}

	/**
	 * @return das flugzeuge
	 */
	public List<Flugzeug> getFlugzeuge() {
		return aircraft;
	}

	public int getAnzahlFlugzeuge() {
		return aircraft.size();
	}

	/**
	 * @return das now
	 */
	public String getNow() {
		return now;
	}

	/**
	 * @param now das now wird gesetzt
	 */
	public void setNow(String now) {
		this.now = now;
	}

	/**
	 * @return das messages
	 */
	public String getMessages() {
		return messages;
	}

	/**
	 * @param messages das messages wird gesetzt
	 */
	public void setMessages(String messages) {
		this.messages = messages;
	}

	/**
	 * @param flugzeuge das flugzeuge wird gesetzt
	 */
	public void setFlugzeuge(List<Flugzeug> flugzeuge) {
		this.aircraft = flugzeuge;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Flugzeuge [");
		if (now != null) {
			builder.append("now=");
			builder.append(now);
			builder.append(", ");
		}
		if (messages != null) {
			builder.append("messages=");
			builder.append(messages);
			builder.append(", ");
			builder.append("\n");
		}
		if (aircraft != null) {
			builder.append("aircraft=");
			builder.append(aircraft);
		}
		builder.append("]");
		return builder.toString();
	}

}

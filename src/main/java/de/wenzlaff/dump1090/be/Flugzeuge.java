package de.wenzlaff.dump1090.be;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.dump1090.action.TimerAktion;

/**
 * Flugzeuge.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class Flugzeuge {

	/** Testmodus wenn auf true, bei false ist Produktion. */
	private boolean TEST = true;

	/** Der Name der Variable muss so sein wie in der JSON Datei. */
	private String now;
	private String messages;
	private List<Flugzeug> aircraft;

	private static final Logger LOG = LoggerFactory.getLogger(TimerAktion.class);

	public Flugzeuge() {
		super();
		if (TEST) {
			LOG.info("Testmodus ist angeschaltet.");
		}
		aircraft = new ArrayList<Flugzeug>();
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
				if (flugzeug.getSquawk().equals("7500") || flugzeug.getSquawk().equals("7600")
						|| flugzeug.getSquawk().equals("7700")) {
					notfallFlugzeuge.add(flugzeug);
				}
			}
		}

		if (TEST) {
			// testweise ein Flugzeug ergänzen
			notfallFlugzeuge.add(aircraft.get(0));

		}
		return notfallFlugzeuge;
	}

	public void addFlugzeug(Flugzeug f) {
		aircraft.add(f);
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
	 * @param now
	 *            das now wird gesetzt
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
	 * @param messages
	 *            das messages wird gesetzt
	 */
	public void setMessages(String messages) {
		this.messages = messages;
	}

	/**
	 * @param flugzeuge
	 *            das flugzeuge wird gesetzt
	 */
	public void setFlugzeuge(List<Flugzeug> flugzeuge) {
		aircraft = flugzeuge;
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
		builder.append("\n");
		return builder.toString();
	}

}

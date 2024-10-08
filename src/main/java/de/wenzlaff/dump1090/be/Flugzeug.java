package de.wenzlaff.dump1090.be;

import java.math.BigDecimal;
import java.util.List;

/**
 * Flugzeug Klasse. Pojo gem.
 * 
 * https://github.com/mutability/dump1090/blob/master/README-json.md
 * 
 * <pre>
	aircraft.json
	
	This file contains dump1090's list of recently seen aircraft. The keys are:
	
	    now: the time this file was generated, in seconds since Jan 1 1970 00:00:00 GMT (the Unix epoch).
	    messages: the total number of Mode S messages processed since dump1090 started.
	    
	    aircraft: an array of JSON objects, one per known aircraft. Each aircraft has the following keys. Keys will be omitted if data is not available.
	        hex: the 24-bit ICAO identifier of the aircraft, as 6 hex digits. The identifier may start with '~', this means that the address is a non-ICAO address (e.g. from TIS-B).
	        squawk: the 4-digit squawk (octal representation)
	        flight: the flight name / callsign
	        lat, lon: the aircraft position in decimal degrees
	        nucp: the NUCp (navigational uncertainty category) reported for the position
	        seen_pos: how long ago (in seconds before "now") the position was last updated
	        altitude: the aircraft altitude in feet, or "ground" if it is reporting it is on the ground
	        vert_rate: vertical rate in feet/minute
	        track: true track over ground in degrees (0-359)
	        speed: reported speed in kt. This is usually speed over ground, but might be IAS - you can't tell the difference here, sorry!
	        messages: total number of Mode S messages received from this aircraft
	        seen: how long ago (in seconds before "now") a message was last received from this aircraft
	        rssi: recent average RSSI (signal power), in dbFS; this will always be negative.
	        
	        Beispiel eines Eintrags:
	        
	"hex":"4596b2","squawk":"1742","lat":51.993682,"lon":11.000218,
    "nucp":0,"seen_pos":6.1,"altitude":30000,"vert_rate":0,"track":346,
	"speed":246,"mlat":["lat","lon","track","speed","vert_rate"],"tisb":[],"messages":244,"seen":0.7,"rssi":-32.7},
 * 
 * </pre>
 * 
 * 
 * @author Thomas Wenzlaff
 *
 */
public class Flugzeug {

	/** Der Name der Variable muss so sein wie in der JSON Datei. */

	/**
	 * hex: the 24-bit ICAO identifier of the aircraft, as 6 hex digits. The
	 * identifier may start with '~', this means that the address is a non-ICAO
	 * address (e.g. from TIS-B).
	 */
	private String hex;
	/** squawk: the 4-digit squawk (octal representation) */
	private String squawk;
	/** lat: the aircraft position in decimal degrees */
	private String lat;
	/** lon: the aircraft position in decimal degrees */
	private String lon;
	/**
	 * altitude: the aircraft altitude in feet (Fuss), or "ground" if it is
	 * reporting it is on the ground
	 */
	private String altitude;
	/**
	 * speed: reported speed in kt (Knoten). This is usually speed over ground, but
	 * might be IAS - you can't tell the difference here, sorry!
	 */
	private String speed;
	/** flight: the flight name / callsign */
	private String flight;

	/**
	 * nucp: the NUCp (navigational uncertainty category) reported for the position
	 */
	private String nucp;
	/**
	 * seen_pos: how long ago (in seconds before "now") the position was last
	 * updated
	 */
	private String seen_pos;
	/** vert_rate: vertical rate in feet/minute */
	private String vert_rate;
	/** track: true track over ground in degrees (0-359) */
	private String track;
	/** messages: total number of Mode S messages received from this aircraft */
	private String messages;
	/**
	 * seen: how long ago (in seconds before "now") a message was last received from
	 * this aircraft
	 */
	private String seen;
	/**
	 * rssi: recent average RSSI (signal power), in dbFS; this will always be
	 * negative.
	 */
	private String rssi;

	private List<String> mlat;

	private List<String> tisb;

	public Flugzeug() {
		super();
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public String getSquawk() {
		return squawk;
	}

	public void setSquawk(String squawk) {
		this.squawk = squawk;
	}

	public void setSquawk(Luftnotfall luftnotfall) {
		this.squawk = luftnotfall.getCode();
	}

	/**
	 * Get Breitengrade. Lat. N-S
	 * 
	 * @return String mit dem Breitengrad.
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * Set Breitengrad. Lat. N-S. z.B. 52,439444
	 * 
	 * @param lat Latitude
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * Längengrade. Lon. z.B. 9,74281454
	 * 
	 * @return String mit dem Längengrad.
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * Längengrade. Lon. z.B. 9,74281454
	 * 
	 * @return BigDecimal mit dem Längengrad oder 0
	 */
	public BigDecimal getLongitude() {
		if (this.lon != null) {
			return new BigDecimal(this.lon);
		} else {
			return new BigDecimal("0");
		}
	}

	/**
	 * Set Längengrade.
	 * 
	 * @param lon Longitude
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * Höhe. Kann null sein wenn keine Höhe angegeben.
	 * 
	 * @return String mit der Höhe
	 */
	public String getAltitudeAsString() {
		return altitude;
	}

	/**
	 * Höhe in Fuss. Kann null sein wenn keine Höhe angegeben.
	 * 
	 * @return Integer mit der Höhe
	 */
	public Integer getAltitude() {
		Integer hoehe = null;
		if (this.altitude != null) {

			try {
				hoehe = Integer.valueOf(altitude);
			} catch (NumberFormatException e) {
				// unbekannte Höhe, dann bleibt es bei null
			}
		}
		return hoehe;
	}

	/**
	 * Set Höhe in Fuss.
	 * 
	 * @param altitude Höhe
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
		if (this.altitude != null) {
			this.altitude = this.altitude.trim();
		}
	}

	/**
	 * Liefert die Geschwindigkeit
	 * 
	 * @return String mit der Geschwindigkeit.
	 */
	public String getSpeed() {
		return speed;
	}

	/**
	 * Setzt die Geschwindigkeit in Meilen.
	 * 
	 * @param speed Geschwindigkeit
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * Liefert den Flug.
	 * 
	 * @return String mit der Flugbezeichnung.
	 */
	public String getFlight() {
		return flight;
	}

	/**
	 * @param flight das flight wird gesetzt
	 */
	public void setFlight(String flight) {
		this.flight = flight;
	}

	/**
	 * @return String mit dem Nucp
	 */
	public String getNucp() {
		return nucp;
	}

	/**
	 * @param nucp das nucp wird gesetzt
	 */
	public void setNucp(String nucp) {
		this.nucp = nucp;
	}

	/**
	 * @return das seen_pos
	 */
	public String getSeen_pos() {
		return seen_pos;
	}

	/**
	 * @param seen_pos das seen_pos wird gesetzt
	 */
	public void setSeen_pos(String seen_pos) {
		this.seen_pos = seen_pos;
	}

	/**
	 * @return das vert_rate
	 */
	public String getVert_rate() {
		return vert_rate;
	}

	/**
	 * @param vert_rate das vert_rate wird gesetzt
	 */
	public void setVert_rate(String vert_rate) {
		this.vert_rate = vert_rate;
	}

	/**
	 * @return String mit dem Track
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * Setzt den Track.
	 * 
	 * @param track das track wird gesetzt
	 */
	public void setTrack(String track) {
		this.track = track;
	}

	/**
	 * Get Mlat.
	 * 
	 * @return mit Mlat
	 */
	public List<String> getMlat() {
		return mlat;
	}

	/**
	 * Setzt Mlat
	 * 
	 * @param mlat das mlat wird gesetzt
	 */
	public void setMlat(List<String> mlat) {
		this.mlat = mlat;
	}

	/**
	 * Get Tisb.
	 * 
	 * @return das tisb
	 */
	public List<String> getTisb() {
		return tisb;
	}

	/**
	 * Set Tisb
	 * 
	 * @param tisb das tisb wird gesetzt
	 */
	public void setTisb(List<String> tisb) {
		this.tisb = tisb;
	}

	/**
	 * Get Message.
	 * 
	 * @return die messages
	 */
	public String getMessages() {
		return messages;
	}

	/**
	 * Setzt die Message.
	 * 
	 * @param messages die messages wird gesetzt
	 */
	public void setMessages(String messages) {
		this.messages = messages;
	}

	/**
	 * Liefert gesehen.
	 * 
	 * @return String mit Seen
	 */
	public String getSeen() {
		return seen;
	}

	/**
	 * @param seen das seen wird gesetzt
	 */
	public void setSeen(String seen) {
		this.seen = seen;
	}

	/**
	 * Get Rssi.
	 * 
	 * @return String mit Rssi
	 */
	public String getRssi() {
		return rssi;
	}

	/**
	 * Set Rssi.
	 * 
	 * @param rssi das rssi wird gesetzt
	 */
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Flugzeug [");

		if (flight != null) {
			builder.append("flight=");
			builder.append(flight);
			builder.append(", ");
		}
		if (hex != null) {
			builder.append("hex=");
			builder.append(hex);
			builder.append(", ");
		}
		if (speed != null) {
			builder.append("speed=");
			builder.append(speed);
			builder.append(" Knoten/h, ");
		}
		if (altitude != null) {
			builder.append("altitude=");
			builder.append(altitude);
			builder.append(" Fuss, ");
		}
		if (squawk != null) {
			builder.append("squawk=");
			builder.append(squawk);
			builder.append(", ");
		}
		if (lat != null) {
			builder.append("lat=");
			builder.append(lat);
			builder.append(", ");
		}
		if (lon != null) {
			builder.append("lon=");
			builder.append(lon);
			builder.append(", ");
		}
		if (nucp != null) {
			builder.append("nucp=");
			builder.append(nucp);
			builder.append(", ");
		}
		if (seen_pos != null) {
			builder.append("seen_pos=");
			builder.append(seen_pos);
			builder.append(", ");
		}
		if (vert_rate != null) {
			builder.append("vert_rate=");
			builder.append(vert_rate);
			builder.append(", ");
		}
		if (track != null) {
			builder.append("track=");
			builder.append(track);
			builder.append(", ");
		}
		if (messages != null) {
			builder.append("messages=");
			builder.append(messages);
			builder.append(", ");
		}
		if (seen != null) {
			builder.append("seen=");
			builder.append(seen);
			builder.append(", ");
		}
		if (rssi != null) {
			builder.append("rssi=");
			builder.append(rssi);
			builder.append(", ");
		}
		if (mlat != null && !mlat.isEmpty()) {
			builder.append("mlat=");
			builder.append(mlat);
			builder.append(", ");
		}
		if (tisb != null && !tisb.isEmpty()) {
			builder.append("tisb=");
			builder.append(tisb);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flight == null) ? 0 : flight.hashCode());
		result = prime * result + ((hex == null) ? 0 : hex.hashCode());
		result = prime * result + ((squawk == null) ? 0 : squawk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flugzeug other = (Flugzeug) obj;
		if (flight == null) {
			if (other.flight != null)
				return false;
		} else if (!flight.equals(other.flight))
			return false;
		if (hex == null) {
			if (other.hex != null)
				return false;
		} else if (!hex.equals(other.hex))
			return false;
		if (squawk == null) {
			if (other.squawk != null)
				return false;
		} else if (!squawk.equals(other.squawk))
			return false;
		return true;
	}

}

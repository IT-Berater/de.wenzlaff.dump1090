package de.wenzlaff.dump1090.be;

import java.math.BigDecimal;

/**
 * Converter für Einheiten.
 * 
 * @author Thomas Wenzlaff
 *
 */
public final class Converter {

	/**
	 * Konstruktor.
	 */
	private Converter() {
		// keine Instanzen
	}

	/**
	 * Umrechnung von Knoten nach Km/h.
	 * 
	 * @param meilen
	 *            mit der Geschwindigkeit in Meilen
	 * @return BigDecimal mit Km/h
	 */
	public static BigDecimal getKmVonKnoten(String meilen) {
		return new BigDecimal(meilen.trim()).multiply(new BigDecimal(1.852)).setScale(0, BigDecimal.ROUND_UP);
	}

	/**
	 * Umgrechnung von Fuss nach Meter.
	 * 
	 * @param fuss
	 *            mit der Länge in Fuss
	 * @return BigDecimal mit Meter
	 */
	public static BigDecimal getMeterVonFuss(String fuss) {
		return new BigDecimal(fuss.trim()).multiply(new BigDecimal(0.3048)).setScale(0, BigDecimal.ROUND_UP);
	}

}

package de.wenzlaff.dump1090.action;

import org.junit.jupiter.api.Test;

import de.wenzlaff.dump1090.be.Flugzeug;
import de.wenzlaff.dump1090.be.Flugzeuge;
import de.wenzlaff.dump1090.be.Luftnotfall;

/**
 * Test für das Log.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class LogAktionTest {

	@Test
	public void testLogAktion() {
		LogAktion a = new LogAktion(null);
		a.run();
	}

	@Test
	public void testRunNull() {
		LogAktion a = new LogAktion(null);
		a.run();
	}

	@Test
	public void testErrorNull() {
		LogAktion a = new LogAktion(null);
		a.error(null);

	}

	@Test
	public void testError() {
		LogAktion a = new LogAktion(getFlugzeugImNotfall());
		a.error(new IllegalArgumentException("Fehler Test"));
	}

	@Test
	public void testRunMitFlugzeug() {
		LogAktion a = new LogAktion(getFlugzeugImNotfall());
		a.run();
	}

	/**
	 * Ein Testflugzeut das im Notfall ist.
	 * 
	 * @return Flugzeug mit Notfall.
	 */
	private Flugzeuge getFlugzeugImNotfall() {
		Flugzeug f = new Flugzeug();
		f.setAltitude("1000");
		f.setHex("EZY18KA");
		f.setSpeed("1000");
		f.setSquawk(Luftnotfall.LUFTNOTFALL);

		Flugzeuge flugzeuge = new Flugzeuge();
		flugzeuge.addFlugzeug(f);
		return flugzeuge;
	}

}

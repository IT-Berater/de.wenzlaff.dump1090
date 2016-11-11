package de.wenzlaff.dump1090.be;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse für die Flugzeuge.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class FlugzeugeTest {

	/** Test Flugzeug. */
	private Flugzeuge flugzeuge;

	@Before
	public void iniTestfall() {
		flugzeuge = new Flugzeuge();
	}

	@Test
	public void testFlugzeuge() {
		assertNotNull(this.flugzeuge);
	}

	@Test
	public void testGetNotfallKeinNotfall() {
		assertEquals(0, this.flugzeuge.getNotfall().size());
	}

	@Test
	public void testGetNotfallMitNotfall() {
		Flugzeug f = getFlugzeugImNotfall();
		this.flugzeuge.addFlugzeug(f);
		assertEquals(1, this.flugzeuge.getNotfall().size());
		System.out.println(f);
	}

	/**
	 * Ein Testflugzeut das im Notfall ist.
	 * 
	 * @return Flugzeug mit Notfall.
	 */
	private Flugzeug getFlugzeugImNotfall() {
		Flugzeug f = new Flugzeug();
		f.setAltitude("1000");
		f.setHex("FFFF");
		f.setSpeed("1000");
		f.setSquawk(Luftnotfall.LUFTNOTFALL);
		return f;
	}

	@Test
	public void testGetAnzahlFlugzeuge() {
		assertEquals(0, this.flugzeuge.getAnzahlFlugzeuge());
	}

	@Test
	public void testGetNow() {
		assertEquals(null, this.flugzeuge.getNow());
	}

	@Test
	public void testSetNow() {
		this.flugzeuge.setNow("");
		assertEquals("", this.flugzeuge.getNow());
	}

	@Test
	public void testToString() {
		assertEquals(24, this.flugzeuge.toString().length());
	}

}

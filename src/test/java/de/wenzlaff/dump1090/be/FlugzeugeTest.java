package de.wenzlaff.dump1090.be;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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
	public void testGetFlugzeugel() {
		assertEquals(0, this.flugzeuge.getFlugzeuge().size());
	}

	@Test
	public void testGetNotfallMitNotfall() {
		Flugzeug f = getFlugzeugImNotfall();
		this.flugzeuge.addFlugzeug(f);
		assertEquals(1, this.flugzeuge.getNotfall().size());
		System.out.println(f);
	}

	@Test
	public void testGetNotfallMitAllenNotfall() {

		for (Luftnotfall notfall : Luftnotfall.values()) {
			Flugzeug f = getFlugzeugImNotfall(notfall);
			this.flugzeuge.addFlugzeug(f);
			assertEquals(1, this.flugzeuge.getNotfall().size());
			System.out.println("Notfall: " + notfall + " Flugzeug: " + f + " Notfall Art: " + notfall.getText() + " - " + notfall);

			assertEquals(notfall.toString(), notfall.getText());

			this.flugzeuge.clear();
		}
	}

	@Test
	public void testGetNotfallNulll() {
		Flugzeug f = getFlugzeugImNotfall();
		String nullString = null;
		f.setSquawk(nullString);
		this.flugzeuge.addFlugzeug(f);
		assertEquals(0, this.flugzeuge.getNotfall().size());
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

	/**
	 * Ein Testflugzeut das im Notfall ist.
	 * 
	 * @return Flugzeug mit Notfall.
	 */
	private Flugzeug getFlugzeugImNotfall(Luftnotfall notfall) {
		Flugzeug f = new Flugzeug();
		f.setAltitude("1000");
		f.setHex("FFFF");
		f.setSpeed("1000");
		f.setSquawk(notfall);
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

	@Test
	public void testMessageg() {
		this.flugzeuge.setMessages("123");
		assertEquals("123", this.flugzeuge.getMessages());
	}

	@Test
	public void flugzeugeSetzen() {
		this.flugzeuge.setFlugzeuge(new ArrayList<Flugzeug>());
		assertEquals(0, this.flugzeuge.getAnzahlFlugzeuge());
	}

}

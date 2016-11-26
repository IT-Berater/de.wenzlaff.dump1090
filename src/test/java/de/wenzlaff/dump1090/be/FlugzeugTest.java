package de.wenzlaff.dump1090.be;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse für die Flugzeuge.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class FlugzeugTest {

	private Flugzeug flugzeug;

	@Before
	public void setUp() throws Exception {
		flugzeug = new Flugzeug();
	}

	@Test
	public void testFlugzeug() {
		assertNotNull(flugzeug);
	}

	@Test
	public void testGetHex() {
		assertNull(flugzeug.getHex());
	}

	@Test
	public void testSetHex() {
		flugzeug.setHex("junit");
		assertEquals("junit", flugzeug.getHex());
	}

	@Test
	public void testGetSquawk() {
		assertNull(flugzeug.getSquawk());
	}

	@Test
	public void testSetSquawkString() {
		flugzeug.setSquawk("junit");
		assertEquals("junit", flugzeug.getSquawk());
	}

	@Test
	public void testSetSquawkLuftnotfall() {
		flugzeug.setSquawk(Luftnotfall.FUNKAUSFALL);
		assertEquals(Luftnotfall.FUNKAUSFALL.getCode(), flugzeug.getSquawk());
	}

	@Test
	public void testIsLandeanflug() {
		assertNull(flugzeug.getAltitude());
		assertNull(flugzeug.getAltitudeAsString());

		flugzeug.setAltitude("2000");
		assertEquals(Integer.valueOf(2000), flugzeug.getAltitude());
	}

	@Test
	public void testGleich() {
		Flugzeug flugzeugKopie = new Flugzeug();
		assertEquals(true, flugzeug.equals(flugzeugKopie));
		flugzeugKopie.setFlight("NEU");
		assertEquals(false, flugzeug.equals(flugzeugKopie));
	}

}

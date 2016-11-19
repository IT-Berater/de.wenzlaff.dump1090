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
	public void testFormat() {
		Flugzeug f = new Flugzeug();
		f.setAltitude("1000");
		f.setHex("FFFF");
		f.setSpeed("300");
		f.setLat("52.454084");
		f.setLon("9.709167");
		f.setFlight("TUI53Y");
		f.setSquawk("1000");

		assertNotNull(f.getFormat());
		System.out.println(f.getFormat());
	}

}

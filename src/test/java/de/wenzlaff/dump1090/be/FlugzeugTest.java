package de.wenzlaff.dump1090.be;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testklasse für die Flugzeuge.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class FlugzeugTest {

	private Flugzeug flugzeug;

	@BeforeEach
	void setUp() throws Exception {
		flugzeug = new Flugzeug();
	}

	@Test
	void testFlugzeug() {
		assertNotNull(flugzeug);
	}

	@Test
	void testGetHex() {
		assertNull(flugzeug.getHex());
	}

	@Test
	void testSetHex() {
		flugzeug.setHex("junit");
		assertEquals("junit", flugzeug.getHex());
	}

	@Test
	void testGetSquawk() {
		assertNull(flugzeug.getSquawk());
	}

	@Test
	void testSetSquawkString() {
		flugzeug.setSquawk("junit");
		assertEquals("junit", flugzeug.getSquawk());
	}

	@Test
	void testSetSquawkLuftnotfall() {
		flugzeug.setSquawk(Luftnotfall.FUNKAUSFALL);
		assertEquals(Luftnotfall.FUNKAUSFALL.getCode(), flugzeug.getSquawk());
	}

	@Test
	void testIsLandeanflug() {
		assertNull(flugzeug.getAltitude());
		assertNull(flugzeug.getAltitudeAsString());

		flugzeug.setAltitude("2000");
		assertEquals(Integer.valueOf(2000), flugzeug.getAltitude());
	}

	@Test
	void testGleich() {
		Flugzeug flugzeugKopie = new Flugzeug();
		assertEquals(true, flugzeug.equals(flugzeugKopie));
		flugzeugKopie.setFlight("NEU");
		assertEquals(false, flugzeug.equals(flugzeugKopie));
	}
}

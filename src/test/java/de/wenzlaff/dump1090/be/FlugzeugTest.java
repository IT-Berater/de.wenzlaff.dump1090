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

	private Flugzeug f;

	@Before
	public void setUp() throws Exception {
		f = new Flugzeug();
	}

	@Test
	public void testFlugzeug() {
		assertNotNull(f);
	}

	@Test
	public void testGetHex() {
		assertNull(f.getHex());
	}

	@Test
	public void testSetHex() {
		f.setHex("junit");
		assertEquals("junit", f.getHex());
	}

	@Test
	public void testGetSquawk() {
		assertNull(f.getSquawk());
	}

	@Test
	public void testSetSquawkString() {
		f.setSquawk("junit");
		assertEquals("junit", f.getSquawk());
	}

	@Test
	public void testSetSquawkLuftnotfall() {
		f.setSquawk(Luftnotfall.FUNKAUSFALL);
		assertEquals(Luftnotfall.FUNKAUSFALL.getCode(), f.getSquawk());
	}

}

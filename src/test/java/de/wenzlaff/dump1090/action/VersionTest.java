package de.wenzlaff.dump1090.action;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test der Version.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class VersionTest {

	@Test
	public void testGetVersion() {
		Version v = new Version();
		System.out.println(v.getVersion());
		assertTrue(v.getVersion().length() >= 0);
	}

}

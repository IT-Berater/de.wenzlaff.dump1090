package de.wenzlaff.dump1090.action;

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
	}

}

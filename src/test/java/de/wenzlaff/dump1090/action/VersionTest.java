package de.wenzlaff.dump1090.action;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test der Version.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class VersionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetVersion() {
		Version v = new Version();
		assertTrue("Version:" + v.getVersion(), v.getVersion().length() > 4);
		System.out.println(v.getVersion());
	}

}

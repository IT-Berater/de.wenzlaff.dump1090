package de.wenzlaff.dump1090.action;

import org.junit.Test;

/**
 * Test für die Timer Aktion.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class TimerAktionTest {

	@Test
	public void testRunNull() {
		new TimerAktion(null);
	}

	@Test
	public void testTimerAktion() {
		new TimerAktion("10.0.9.23");

	}

}

package de.wenzlaff.dump1090.action;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.wenzlaff.dump1090.be.Flugzeug;
import de.wenzlaff.dump1090.be.Flugzeuge;
import de.wenzlaff.dump1090.be.Luftnotfall;

/**
 * 
 * @author Thomas Wenzlaff
 *
 */
public class PushoverAktionTest {

	private PushoverAktion pushoverAktion;

	@Before
	public void setUp() throws Exception {
		this.pushoverAktion = new PushoverAktion(getFlugzeugImNotfall());
	}

	@Test
	public void testPushoverAktion() {
		assertNotNull(this.pushoverAktion);
	}

	@Test
	public void testRun() throws Exception {
		if (this.pushoverAktion != null) {
			this.pushoverAktion.run();
		}
	}

	@Test
	public void testSendNachricht() throws Exception {
		this.pushoverAktion.sendPushoverNachricht();
	}

	@Test
	public void testSendNachrichtText() throws Exception {
		this.pushoverAktion.sendPushoverNachricht("JUnit Testnachricht");
	}

	/**
	 * Ein Testflugzeut das im Notfall ist.
	 * 
	 * @return Flugzeug mit Notfall.
	 */
	private Flugzeuge getFlugzeugImNotfall() {
		Flugzeug f = new Flugzeug();
		f.setAltitude("1000");
		f.setHex("EZY18KA");
		f.setSpeed("1000");
		f.setSquawk(Luftnotfall.LUFTNOTFALL);

		Flugzeuge flugzeuge = new Flugzeuge();
		flugzeuge.addFlugzeug(f);
		return flugzeuge;
	}

}

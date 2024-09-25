package de.wenzlaff.dump1090.action;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.wenzlaff.dump1090.be.Flugzeug;
import de.wenzlaff.dump1090.be.Flugzeuge;
import de.wenzlaff.dump1090.be.Luftnotfall;

/**
 * 
 * @author Thomas Wenzlaff
 *
 */
public class PushoverAktionTest {

	/**
	 * Damit nur lokal getestet wird wenn true. Bei false werden nicht alle Tests
	 * ausgeführt.
	 */
	private boolean lokalerTestmodus;

	private PushoverAktion pushoverAktion;

	@BeforeEach
	public void ini() {
		Properties p = SetupReader.getProperties();
		lokalerTestmodus = Boolean.valueOf(p.getProperty("lokaler_testmodus", "false"));
		System.out.println("Testmodus: " + lokalerTestmodus);
		this.pushoverAktion = new PushoverAktion(getFlugzeugImNotfall());
	}

	@Test
	public void testPushoverAktion() {
		assertNotNull(this.pushoverAktion);
	}

	@Test
	public void testRun() throws Exception {
		if (lokalerTestmodus) {
			this.pushoverAktion.run();
		}
	}

	@Test
	public void testSendNachricht() throws Exception {
		if (lokalerTestmodus) {
			this.pushoverAktion.sendPushoverNachricht();
		}
	}

	@Test
	public void testSendNachrichtText() throws Exception {
		if (lokalerTestmodus) {
			this.pushoverAktion.sendPushoverNachricht("JUnit Testnachricht");
		}
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

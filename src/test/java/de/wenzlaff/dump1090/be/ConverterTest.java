package de.wenzlaff.dump1090.be;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import de.wenzlaff.dump1090.action.SetupReader;

/**
 * Test des Konverters.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class ConverterTest {

	/** Damit nur lokal getestet wird wenn true. Bei false werden nicht alle Tests ausgeführt. */
	private boolean lokalerTestmodus;

	@Before
	public void ini() {
		Properties p = SetupReader.getProperties();
		lokalerTestmodus = Boolean.valueOf(p.getProperty("lokaler_testmodus", "false"));
		System.out.println("Testmodus: " + lokalerTestmodus);
	}

	@Test
	public void testGetKmVonKnoten() {
		if (lokalerTestmodus) {
			assertEquals("2", Converter.getKmVonKnoten("1").toPlainString());
		}
	}

	@Test
	public void testGetMeterVonFuss() {
		if (lokalerTestmodus) {
			assertEquals("1", Converter.getMeterVonFuss("0.3048").toPlainString());
		}
	}

}

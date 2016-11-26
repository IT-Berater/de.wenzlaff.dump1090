package de.wenzlaff.dump1090.be;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test des Konverters.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class ConverterTest {

	@Test
	public void testGetKmVonKnoten() {
		assertEquals("2", Converter.getKmVonKnoten("1").toPlainString());
	}

	@Test
	public void testGetMeterVonFuss() {
		assertEquals("1", Converter.getMeterVonFuss("0.3048").toPlainString());
	}

}

package de.wenzlaff.dump1090.be;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Testsuite.
 * 
 * @author Thomas Wenzlaff
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ FlugzeugeEinlesenTest.class, FlugzeugeTest.class, FlugzeugTest.class, ConverterTest.class })
public class AllTests {

}

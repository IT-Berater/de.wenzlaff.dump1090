package de.wenzlaff.dump1090.action;

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
@SuiteClasses({ PushoverAktionTest.class, VersionTest.class, TimerAktionTest.class, LogAktionTest.class })
public class AllTests {

}

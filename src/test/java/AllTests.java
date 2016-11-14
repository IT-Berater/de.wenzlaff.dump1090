import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Testsuite für alles.
 * 
 * @author Thomas Wenzlaff
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ de.wenzlaff.dump1090.action.AllTests.class, de.wenzlaff.dump1090.be.AllTests.class })
public class AllTests {

}

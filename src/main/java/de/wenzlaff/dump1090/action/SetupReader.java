package de.wenzlaff.dump1090.action;

import java.io.FileReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reader für das initiale Setup.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class SetupReader {

	private static final String PROPERTIES_DATEINAME = "de.wenzlaff.dump1090.properties";

	private static final Logger LOG = LoggerFactory.getLogger(SetupReader.class);

	private static Properties properties;

	/**
	 * Liefert die Setup Einstellungen.
	 * 
	 * @return Properties aus der Datei oder default.
	 */
	public static Properties getProperties() {

		if (properties == null) {
			try {
				FileReader file = new FileReader(PROPERTIES_DATEINAME);
				properties = new Properties();
				properties.load(file);
				LOG.info("Lese setup aus Datei {}) ein.", PROPERTIES_DATEINAME);

			} catch (Exception e) {
				LOG.error("Konnte keine Properties-Datei {} finden. Error: {}", PROPERTIES_DATEINAME, e);
				properties = new Properties();
				LOG.info("Verwende default Setup.");
			}
		}
		return properties;
	}

}

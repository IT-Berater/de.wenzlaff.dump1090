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

	/**
	 * Liefert die Setup Einstellungen.
	 * 
	 * @return Properties aus der Datei oder default.
	 */
	public static Properties getProperties() {

		Properties properties;
		try {
			FileReader file = new FileReader(PROPERTIES_DATEINAME);
			properties = new Properties();
			properties.load(file);

		} catch (Exception e) {
			LOG.error("Konnte keine Properties-Datei finden. Error: {}", e);
			properties = new Properties();
		}

		return properties;
	}

}

package de.wenzlaff.dump1090.action;

import java.io.FileReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reader für das initiale Setup aus einer Propertie Datei.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class SetupReader {

	/** Der Name der Propertie Datei für das einlesen der Einstellungen. */
	private static final String PROPERTIES_DATEINAME = "de.wenzlaff.dump1090.properties";

	private static final Logger LOG = LoggerFactory.getLogger(SetupReader.class);

	/** Die Properties. */
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
				LOG.info("Lese setup aus Datei ({}) ein.", PROPERTIES_DATEINAME);

			} catch (Exception e) {
				LOG.error("Konnte keine Properties-Datei mit Namen {} finden. Error: {}", PROPERTIES_DATEINAME, e);
				properties = new Properties();
				LOG.info("Verwende das default Setup.");
			}
		}
		return properties;
	}

}

package de.wenzlaff.dump1090.be;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import de.wenzlaff.dump1090.util.JsonUtil;

/**
 * Test Klasse mit JUnit 5.
 * 
 * Mit den Codes 7500, 7600 und 7700 werden Informationen über die Art einer Luftnotlage übermittelt. Gebräuchliche Merksprüche sind angefügt:
 * 
 * <pre>
    7500 – Flugzeugentführung (hijacking; seven-five - man with a knife)
    7600 – Funkausfall (radio failure; seven-six - hear nix / radio nix / need a radio fix)
    7700 – Luftnotfall (emergency; seven-seven - going to heaven / falling from heaven / pray to heaven)
 * </pre>
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FlugzeugeEinlesenTest {

	private static final String DUMP_SERVER_URL = "http://10.0.7.32/dump1090/data/aircraft.json";

	@Test
	@DisplayName("Einlesen von Flugzeugdaten aus der 1. Json Datei")
	public void a_einlesenVonFlugzeugen1() throws Exception {

		InputStream inputStream = new FileInputStream("src/test/java/de/wenzlaff/dump1090/be/aircraft-1.json");
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		Gson gson = new GsonBuilder().create();
		Flugzeuge flugzeuge = gson.fromJson(reader, Flugzeuge.class);
		System.out.println(flugzeuge);
		System.out.println("Anzahl der Flugzeuge in der Datei: " + flugzeuge.getAnzahlFlugzeuge());
		assertEquals(30, flugzeuge.getAnzahlFlugzeuge());
	}

	@Test
	@DisplayName("Einlesen von Flugzeugdaten aus der 2. Json Datei")
	public void b_einlesenVonFlugzeugen2() throws Exception {

		InputStream inputStream = new FileInputStream("src/test/java/de/wenzlaff/dump1090/be/aircraft-2.json");
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		Gson gson = new GsonBuilder().create();
		Flugzeuge flugzeuge = gson.fromJson(reader, Flugzeuge.class);
		System.out.println(flugzeuge);
		System.out.println("Anzahl der Flugzeuge in der Datei: " + flugzeuge.getAnzahlFlugzeuge());
		assertEquals(30, flugzeuge.getAnzahlFlugzeuge());
	}

	@Test
	public void c_lesenViaUrl2() throws Exception {

		InputStream is = new URL(DUMP_SERVER_URL).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText = JsonUtil.readAll(rd);

		JsonElement je = new JsonParser().parse(jsonText);
		System.out.println(je);

		System.out.println("Zeit" + JsonUtil.getAtPath(je, "now").getAsString() + " Nachricht Nr. " + JsonUtil.getAtPath(je, "messages").getAsString());
		System.out.println("Flugzeug:  + " + JsonUtil.getAtPath(je, "aircraft"));
	}

	@Test
	public void d_lesenViaServerUrl3() throws Exception {

		InputStream is = new URL(DUMP_SERVER_URL).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText = JsonUtil.readAll(rd);

		Gson gson = new GsonBuilder().create();
		Flugzeuge flugzeuge = gson.fromJson(jsonText, Flugzeuge.class);

		System.out.println(flugzeuge);
		System.out.println("Anzahl der Flugzeuge in der Datei: " + flugzeuge.getAnzahlFlugzeuge());

		assertTrue(flugzeuge.getAnzahlFlugzeuge() > 0);
	}

	@Test
	public void e_lesenNotfallViaServerUrl4() throws Exception {

		InputStream is = new URL(DUMP_SERVER_URL).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText = JsonUtil.readAll(rd);

		Gson gson = new GsonBuilder().create();
		Flugzeuge flugzeuge = gson.fromJson(jsonText, Flugzeuge.class);

		System.out.println(flugzeuge.getNotfall());
		System.out.println("Anzahl Flugzeuge im Notfall: " + flugzeuge.getNotfall().size());
	}

}

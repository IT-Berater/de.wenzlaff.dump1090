package de.wenzlaff.dump1090.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.JsonElement;

/**
 * Json Reader.
 * 
 * @author Thomas Wenzlaff
 *
 */
public class JsonReader {

	public static String readAll(Reader rd) throws IOException {

		BufferedReader reader = new BufferedReader(rd);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	public static JsonElement getAtPath(JsonElement e, String path) {

		JsonElement current = e;
		String ss[] = path.split("/");
		for (int i = 0; i < ss.length; i++) {
			current = current.getAsJsonObject().get(ss[i]);
		}
		return current;
	}

}

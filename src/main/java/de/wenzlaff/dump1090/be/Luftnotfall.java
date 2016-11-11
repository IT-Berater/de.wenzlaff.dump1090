package de.wenzlaff.dump1090.be;

/**
 * Luftnotfall.
 * 
 * Mit den Codes 7500, 7600 und 7700 werden Informationen über die Art einer Luftnotlage übermittelt.
 * 
 * Gebräuchliche Merksprüche sind angefügt:
 * 
 * <pre>
    7500 – Flugzeugentführung (hijacking; seven-five - man with a knife)
    7600 – Funkausfall (radio failure; seven-six - hear nix / radio nix / need a radio fix)
    7700 – Luftnotfall (emergency; seven-seven - going to heaven / falling from heaven / pray to heaven)
 * </pre>
 * 
 */
public enum Luftnotfall {

	/**
	 * 7500 – Flugzeugentführung (hijacking; seven-five - man with a knife)
	 */
	ENTFUEHRUNG("7500", "Flugzeugentführung"),
	/**
	 * Funkausfall (radio failure; seven-six - hear nix / radio nix / need a radio fix)
	 */
	FUNKAUSFALL("7600", "Funkausfall"),
	/**
	 * Luftnotfall (emergency; seven-seven - going to heaven / falling from heaven / pray to heaven)
	 */
	LUFTNOTFALL("7700", "Luftnotfall");

	private String code;
	private String text;

	private Luftnotfall(String code, String text) {
		this.code = code;
		this.text = text;
	}

	/**
	 * Liefert die Code Nummer des Notfalls. Z.b. 7500 oder 7700.
	 * 
	 * @return das code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Liefert den Text des Notfalls. Z.b. Funkausfall oder Flugzeugentführung.
	 * 
	 * @return das text
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return getText();
	}

}
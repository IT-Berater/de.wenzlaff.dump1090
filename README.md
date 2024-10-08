[![Build Status](https://travis-ci.org/IT-Berater/de.wenzlaff.dump1090.svg?branch=master)](https://travis-ci.org/IT-Berater/de.wenzlaff.dump1090) 
[![codecov](https://codecov.io/gh/IT-Berater/de.wenzlaff.dump1090/branch/master/graph/badge.svg)](https://codecov.io/gh/IT-Berater/de.wenzlaff.dump1090) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.wenzlaff.dump1090/de.wenzlaff.dump1090/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.wenzlaff.dump1090/de.wenzlaff.dump1090)
[![Github Releases](https://img.shields.io/github/downloads/atom/atom/latest/total.svg)](https://github.com/IT-Berater/de.wenzlaff.dump1090)


# de.wenzlaff.dump1090

Java Wrapper für die Dump1090 Schnittstelle.

Dieses Projekt ist eine Javaschnittstelle für den Zugriff auf die Daten von Dump1090 per Java Objekte.


Mit dieser Javaschnittstelle können einfach Pushover Nachrichten an jedes Handy bzw. Tablet versendet werden wenn ein Flugzeug startet oder landet. Da wir in der Nähe vom Flughafen Hannover (EDDV, HAJ) leben, sehen wir gerne Flugzeuge starten und landen. Die beiden Start.- und Landebahnen liegen in Ost-West Richtung. Wenn wir aus dem Küchenfenster schauen oder wenn wir auf der Terrasse sitzen, sehen wir alle Flugzeuge die von Osten aus ankommen bzw. in die Richtung wegfliegen. Nun möchte ich gerne eine Nachricht mit Höhe, Geschwindigkeit, Flugnummer usw. auf mein Handy erhalten, wenn ein Flugzeug aus dem Osten zum landen ankommt bzw. in Richtung Osten starte. Alle anderen Flugzeuge die in die andere Richtung starten bzw. landen sollen nicht berücksichtigt werden. Auch nicht Flugzeuge die nur Hannover überfliegen.

Auch soll eine Pushover versendet werden wenn ein Luftnotfall (med. Notfall, Entführung usw. ) per Transponder gemeldet wird.

Hier mal eine beispiel Pushover Nachricht:

![](/bilder/pushover-nachricht.png)

Es gibt einige Internet Seiten die auch solche Nachrichten versenden. Aber nicht so speziell.

Es soll aber die eigenen Daten die mit einem lokal laufenden Dump1090 Server empfangen werden ausgewertet werden.

Dazu wird die Funktionalität des ![Dump1090 mutability](https://github.com/mutability/dump1090) verwendet. Dieser erzeugt im regelmäßigen Abständen eine JSon Datei (http://IP-ADRESSE/dump1090/data/aircraft.json) die durch diese Java Schnittstelle ausgewertet wird.

Diese Java-Programm kann dann auf einem Raspberry Pi laufen. Auf einen Mac unter macOS läuft es aber auch. Windows ist noch nicht getestet, sollt aber auch so laufen.


Folgende fachlichen Klassen gibt es, die auch für eigene Zwecke verwendet werden können:

* Flugzeug
 * Stellte ein Flugzeug dar.

* Flugzeuge
 * Container für mehrere Flugzeuge.

* Luftnotfall
 * Alle möglichen Luftnotfälle.

* PushoverSound
 * Die möglichen Töne für die Pushover Nachrichten..

* Converter
 * Die Konverter von Meilen nach Km usw.
 
# Vorbedingung
 Es muss ein ![Dump1090 mutability](https://github.com/mutability/dump1090) Server laufen und die aircraf.json Datei (http://IP-ADRESSE/dump1090/data/aircraft.json) muss bei eingabe im Browser angezeigt werden. Es muss auch Java mind. 1.8 installiert sein.
 
# Installation
 Ein Maven Java-Projekt anlegen und in der pom.xml die Schnittstelle einbinden:

	<dependencies>
	   <dependency>
	 		<groupId>de.wenzlaff.dump1090</groupId>
	 		<artifactId>de.wenzlaff.dump1090</artifactId>
	 		<version>0.1.0</version> 
	 	</dependency>
	  </dependencies>

 Dann in einer Java Klasse den Aufruf:
 		
	// IP-Adresse anpassen
	StartFlugabfrage task = new StartFlugabfrage("10.0.9.32", "1");
	task.startEndlosNotfallabfrage();
		
Dann fehlt nur noch eine de.wenzlaff.dump1090.properties Datei im Classpath mit dem Inhalt:


	# Einstellungs Datei für de.wenzlaff.dump1090
	# Thomas Wenzlaff www.wenzlaff.de

	# die Pushover User ID Token
	pushover_user_id_token = Pushover Token
	# die Pushover App Api Token
	pushover_my_app_api_token = Pushover Api Token

	# die Nachricht URL für den Link in der Message, wird um die HEX erweitert
	pushover_nachricht_url = http://fr24.com/

	# an welche Geräte soll die Pushover Nachrichten gesendet werden, alle ist "device", oder aber getrennt durch ; (Semikolon) z.B iPhone1;iPhone2
	pushover_device = iPhone

	# der Längengrad von dem die Aktivierung los geht, z.B. Langenhagen in der Tempelhofer Str.: 9.742556, Breitengrad 52.438453 mit Höhe 51 m
	# siehe dazu auch das Diagramm weiter unten
	pushover_laengengrad_min = 9.742556

	# die maximale Höhe in Fuss (bei Meter mal * 0.3048 z.B. 2000 Fuss * 0.3 = 609 Meter, 3000 Fuss = 914 m
	pushover_max_hoehe = 3000

	# die Server Adresse 
	dump1090_server_ip = 10.0.9.32

	# der Testmodus, bei true werden alles lokalen Tests ausgeführt, bei false nicht
	lokaler_testmodus = true
	
	# HEX alle Flugzeuge die ignoriert werden - Blacklist z.B. Sportflugzeuge getrennt durch Semikolon
    #                      GFD1  ;GAF621;GAF639
    black_list_flugzeuge = 3cd061;3e8204;3ea653
	
# Start

Start über die Javaklasse oder auch über die Kommandozeile:

	java -jar de.wenzlaff.dump1090-0.1.0.jar 10.0.9.32 5
	

# Klassendiagramm
 
 ![Klassendiagramm](/bilder/Klassendiagramm.png)

# Aufbau

Es muss der Längengrad ermittelt werden, ab dem die Warnungen erscheinen sollen. Dazu mal dieses Foto von Langenhagen. Der Längengrad muss dann in der de.wenzlaff.dump1090.properties Datei unter pushover_laengengrad_min eingetragen werden. Es werden also alle Flugzeuge östliche (rechts) von dem Längengrad ermittelt.


![Langenhagen](/bilder/flughafen-eddv.png)

# Funktion

Es wird im Interval die vom Dump1090-Server geschriebenen JSon Datei (aircraft.json) eingelesen und in der Java Flugzeug Klasse bereitgestellt. Es wird also zur Entkopplung nicht der Stream des Dump1090 Servers gelesen. Die empfagenen Flugzeugdaten werden ausgewertet. Wenn das Flugzeug den Längengrad überschreitet und die eingestellte Höhe hat, wird eine Pushover Nachricht versendet. Ebenso wenn ein Notfall Transpondersignal empfangen wird.

Manchmal nerven Flugzeuge, wie z.B. diese Ausbildungsflüge, die nur durch die Weltgeschichte fliegen (klar Ausbildung muss sein, aber warum in Langenhagen):

![Langenhagen](/bilder/blacklist.png)

Dafür gibt es jetzt eine Blackliste. Einfach in der Propertie Datei de.wenzlaff.dump1090.properties die Kennung eingeben und schon wird das Flugzeug ignoriert.


Details siehe [http://blog.wenzlaff.de/]

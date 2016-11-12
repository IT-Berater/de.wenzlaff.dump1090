package de.wenzlaff.dump1090.be;

/**
 * Alle möglichen Pushover Sounds.
 * 
 * Siehe https://pushover.net/api#sounds
 * 
 * @author Thomas Wenzlaff
 *
 */
public enum PushoverSound {

	pushover, // Pushover (default)
	bike, // Bike
	bugle, // Bugle
	cashregister, // Cash Register
	classical, // Classical
	cosmic, // Cosmic
	falling, // Falling
	gamelan, // Gamelan
	incoming, // Incoming
	intermission, // Intermission
	magic, // Magic
	mechanical, // Mechanical
	pianobar, // Piano Bar
	siren, // Siren
	spacealarm, // Space Alarm
	tugboat, // Tug Boat
	alien, // Alien Alarm (long)
	climb, // Climb (long)
	persistent, // Persistent (long)
	echo, // Pushover Echo (long)
	updown, // Up Down (long)
	none, // None (silent)
}

package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.IMMPlayback;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

/**
 * A MessageGenerator generates midi messages, when are
 * delegated to a recipient Bead.
 */
public interface MessageGenerator extends IMMPlayback {
	/**
	 * Set the AudioContext.
	 * 
	 * @param ac the AudioContext
	 */
	public void setAudioContext(AudioContext ac);
	
	/**
	 * Set the recipient Bead.
	 * 
	 * @param recipient the recipient Bead
	 */
	public void setRecipient(Bead recipient);
	
	/**
	 * Start generating midi messages.
	 */
	public void start();
	
	/**
	 * Stop generating midi messages.
	 */
	public void stop();
	
	public MessageGenerator clone();
}

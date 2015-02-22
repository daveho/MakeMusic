package io.github.daveho.makemusic;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

/**
 * An {@link IMessageGenerator} generates midi messages, which are
 * delegated to a recipient Bead.
 */
public interface IMessageGenerator extends IMMPlayback {
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
}

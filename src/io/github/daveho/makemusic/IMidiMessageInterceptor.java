package io.github.daveho.makemusic;

import javax.sound.midi.MidiMessage;

import net.beadsproject.beads.core.Bead;

public interface IMidiMessageInterceptor extends IMMPlayback {

	/**
	 * Set the downstream Bead, to which MidiMessages should
	 * be delivered after being intercepted.
	 * 
	 * @param downstream the downstream Bead
	 */
	public void setDownstream(Bead downstream);

	public MidiMessage getMessage();

	public long getTimeStamp();

	/**
	 * Return this object as a {@link Bead}.
	 * 
	 * @return this object as a {@link Bead}
	 */
	public Bead asBead();

}
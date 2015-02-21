package io.github.daveho.makemusic;

import javax.sound.midi.MidiMessage;

import net.beadsproject.beads.core.Bead;
import io.github.daveho.gervill4beads.MidiMessageSource;

/**
 * Implementation of {@link MidiMessageSource} that intercepts
 * MidiMessages, calls the abstract downcall method
 * {@link #onMessageReceived(MidiMessage, long)}, and then
 * delegates to a downstream Bead.  This is useful as a base
 * class for recorders and transformers of midi messages.
 * 
 * @author David Hovemeyer
 */
public abstract class MidiMessageInterceptor extends Bead implements MidiMessageSource {
	private MidiMessage msg;
	private long timeStamp;
	private Bead downstream;
	
	/**
	 * Constructor.
	 */
	public MidiMessageInterceptor() {
	}

	/**
	 * Set the downstream Bead, to which MidiMessages should
	 * be delivered after being intercepted.
	 * 
	 * @param downstream the downstream Bead
	 */
	public void setDownstream(Bead downstream) {
		this.downstream = downstream;
	}
	
	@Override
	protected void messageReceived(Bead message) {
		if (downstream == null) {
			return;
		}
		if (message instanceof MidiMessageSource) {
			MidiMessageSource upstream = (MidiMessageSource) message;
			msg = upstream.getMessage();
			timeStamp = upstream.getTimeStamp();
			onMessageReceived(msg, timeStamp);
			downstream.message(this);
		}
	}

	/**
	 * Called when a MidiMessage is received.
	 * 
	 * @param m   the MidiMessage
	 * @param ts  the timestamp
	 */
	protected abstract void onMessageReceived(MidiMessage m, long ts);

	@Override
	public MidiMessage getMessage() {
		return this.msg;
	}

	@Override
	public long getTimeStamp() {
		return this.timeStamp;
	}
}

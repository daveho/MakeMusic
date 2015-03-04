package io.github.daveho.makemusic.playback;

import javax.sound.midi.MidiMessage;

import net.beadsproject.beads.core.Bead;
import io.github.daveho.gervill4beads.MidiMessageSource;
import io.github.daveho.makemusic.IMidiMessageInterceptor;

/**
 * Implementation of {@link MidiMessageSource} that intercepts
 * MidiMessages, calls the abstract downcall method
 * {@link #onMessageReceived(MidiMessage, long)}, and then
 * delegates to a downstream Bead.  This is useful as a base
 * class for recorders and transformers of midi messages.
 * 
 * @author David Hovemeyer
 */
public abstract class AbstractMidiMessageInterceptor extends Bead implements MidiMessageSource, IMidiMessageInterceptor {
	private MidiMessage msg;
	private long timeStamp;
	private Bead downstream;
	
	/**
	 * Constructor.
	 */
	public AbstractMidiMessageInterceptor() {
	}

	/* (non-Javadoc)
	 * @see io.github.daveho.makemusic.IMidiMessageInterceptor#setDownstream(net.beadsproject.beads.core.Bead)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see io.github.daveho.makemusic.IMidiMessageInterceptor#getMessage()
	 */
	@Override
	public MidiMessage getMessage() {
		return this.msg;
	}

	/* (non-Javadoc)
	 * @see io.github.daveho.makemusic.IMidiMessageInterceptor#getTimeStamp()
	 */
	@Override
	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	@Override
	public Bead asBead() {
		return this;
	}
}

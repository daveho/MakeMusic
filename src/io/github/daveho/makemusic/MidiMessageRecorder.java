package io.github.daveho.makemusic;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Record MidiEvents, while also delegating them to another
 * Receiver.
 * 
 * @author David Hovemeyer
 */
public class MidiMessageRecorder implements Receiver {
	private final List<MidiMessageAndTimeStamp> messages;
	private final Receiver delegate;
	
	/**
	 * Constructor.
	 * 
	 * @param delegate another Receiver to which received
	 *                 MidiEvents should be delegated
	 */
	public MidiMessageRecorder(Receiver delegate) {
		this.messages = new ArrayList<MidiMessageAndTimeStamp>();
		this.delegate = delegate;
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		MidiMessage copy = (MidiMessage) message.clone();
		messages.add(new MidiMessageAndTimeStamp(copy, timeStamp));
		delegate.send(message, timeStamp);
	}
	
	/**
	 * Get recorded MidiMessages (and their timestamps).
	 * 
	 * @return recorded MidiMessages and timestamps
	 */
	public List<MidiMessageAndTimeStamp> getMessages() {
		return messages;
	}

	@Override
	public void close() {
		delegate.close();
	}

}

package io.github.daveho.makemusic;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiMessage;

/**
 * Record MidiEvents, while also delegating them to another
 * Receiver.
 * 
 * @author David Hovemeyer
 */
public class MidiMessageRecorder extends MidiMessageInterceptor {
	private final List<MidiMessageAndTimeStamp> messages;
	
	/**
	 * Constructor.
	 */
	public MidiMessageRecorder() {
		this.messages = new ArrayList<MidiMessageAndTimeStamp>();
	}
	
	@Override
	protected void onMessageReceived(MidiMessage m, long ts) {
		messages.add(new MidiMessageAndTimeStamp(m, ts));
	}
	
	/**
	 * Get recorded MidiMessages (and their timestamps).
	 * 
	 * @return recorded MidiMessages and timestamps
	 */
	public List<MidiMessageAndTimeStamp> getMessages() {
		return messages;
	}
}

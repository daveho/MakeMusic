package io.github.daveho.makemusic;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;
import io.github.daveho.makemusic.data.MidiData;

import javax.sound.midi.MidiMessage;

/**
 * Record MidiEvents, capturing them in a {@link MidiData} object,
 * while also delegating them to another Receiver.
 * 
 * @author David Hovemeyer
 */
public class MidiMessageRecorder extends MidiMessageInterceptor {
	private final MidiData midiMessageAndTimestampList;
	
	/**
	 * Constructor.
	 */
	public MidiMessageRecorder() {
		this.midiMessageAndTimestampList = new MidiData();
	}
	
	@Override
	protected void onMessageReceived(MidiMessage m, long ts) {
		midiMessageAndTimestampList.add(new MidiMessageAndTimeStamp(m, ts));
	}
	
	/**
	 * Get the {@link MidiData}.
	 * 
	 * @return the {@link MidiData}
	 */
	public MidiData getMidiData() {
		return midiMessageAndTimestampList;
	}
}

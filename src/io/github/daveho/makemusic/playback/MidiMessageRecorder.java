package io.github.daveho.makemusic.playback;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;
import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.MMPlayback;
import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.MidiData;
import io.github.daveho.makemusic.data.MidiMessageRecorderData;

import javax.sound.midi.MidiMessage;

/**
 * Record MidiEvents, capturing them in a {@link MidiData} object,
 * while also delegating them to another Receiver.
 * 
 * @author David Hovemeyer
 */
@MMPlayback(dataClass=MidiMessageRecorderData.class)
public class MidiMessageRecorder extends AbstractMidiMessageInterceptor {
	private MidiMessageRecorderData data;
	private final MidiData midiMessageAndTimestampList;
	
	/**
	 * Constructor.
	 */
	public MidiMessageRecorder() {
		this.midiMessageAndTimestampList = new MidiData();
	}
	
	@Override
	public Class<? extends IMMData> getDataType() {
		return MidiMessageRecorderData.class;
	}
	
	@Override
	public void setData(IMMData data) {
		if (!(data instanceof MidiMessageRecorderData)) {
			throw new IllegalArgumentException("Can't initialize " +
					MidiMessageRecorder.class.getSimpleName() +
					" from " + data.getClass().getSimpleName());
		}
		this.data = (MidiMessageRecorderData) data;
	}
	
	@Override
	public void onStartPlayback(CompositionData compositionData) {
		// TODO: Assign a path (filename) to the resulting data
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

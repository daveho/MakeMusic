package io.github.daveho.makemusic.playback;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;
import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.IMidiMessageInterceptor;
import io.github.daveho.makemusic.MMPlayback;
import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.MidiData;
import io.github.daveho.makemusic.data.MidiMessageRecorderData;

import javax.sound.midi.MidiMessage;

/**
 * {@link IMidiMessageInterceptor} implementation which captures
 * midi messages and their timestamps, saving them to a
 * {@link MidiData} object to be stored in the {@link CompositionData}.
 * 
 * @author David Hovemeyer
 */
@MMPlayback(dataClass=MidiMessageRecorderData.class)
public class MidiMessageRecorder extends AbstractMidiMessageInterceptor {
	private MidiMessageRecorderData data;
	private final MidiData midiData;
	
	/**
	 * Constructor.
	 */
	public MidiMessageRecorder() {
		this.midiData = new MidiData();
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
		// Assign a path (filename) to the resulting data
		if (data.hasStringParam("path")) {
			// A path has already been defined by the MidiMessageRecorderData
			midiData.setPath(data.getParamAsString("path"));
		} else {
			// Assign a path
			midiData.setPath(findUnusedMidiDataPath(compositionData));
		}
		
		// Add the recording MidiData to the CompositionData
		compositionData.addMidiData(midiData);
	}
	
	private String findUnusedMidiDataPath(CompositionData compositionData) {
		int count = 0;
		while (true) {
			String candidate = String.format("/midi/recording%04d.txt", count);
			if (compositionData.findMidiDataWithPath(candidate) == null) {
				return candidate;
			}
			count++;
		}
	}

	@Override
	protected void onMessageReceived(MidiMessage m, long ts) {
		midiData.add(new MidiMessageAndTimeStamp(m, ts));
	}
	
	/**
	 * Get the {@link MidiData}.
	 * 
	 * @return the {@link MidiData}
	 */
	public MidiData getMidiData() {
		return midiData;
	}
}

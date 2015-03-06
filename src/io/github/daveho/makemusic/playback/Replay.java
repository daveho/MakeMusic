package io.github.daveho.makemusic.playback;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;
import io.github.daveho.gervill4beads.ReceivedMidiMessageSource;
import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.IMessageGenerator;
import io.github.daveho.makemusic.MMPlayback;
import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.MidiData;
import io.github.daveho.makemusic.data.ReplayData;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

/**
 * Implementation if {@link IMessageGenerator} that replays
 * the midi messages from a {@link MidiData} object.
 * 
 * @author David Hovemeyer
 */
@MMPlayback(dataClass=ReplayData.class)
public class Replay implements IMessageGenerator {
	private class ReplayBead extends ReceivedMidiMessageSource {
		public ReplayBead(AudioContext ac) {
			super(ac);
		}
	}
	
	private ReplayData data;
	private MidiData midiData;
	private ReplayBead bead;

	@Override
	public Class<? extends IMMData> getDataType() {
		return ReplayData.class;
	}

	@Override
	public void setData(IMMData data) {
		if (!(data instanceof ReplayData)) {
			throw new IllegalArgumentException("Can't initialize " +
					Replay.class.getSimpleName() + " from " +
					data.getClass().getSimpleName());
		}
		this.data = (ReplayData) data;
	}

	@Override
	public void onStartPlayback(CompositionData compositionData) {
		String path = data.getParamAsString("path");
		midiData = compositionData.findMidiDataWithPath(path);
		if (midiData == null) {
			throw new RuntimeException("Couldn't find MidiData with path " + path + " to replay");
		}
	}

	@Override
	public void setAudioContext(AudioContext ac) {
		// Create MidiBead
		bead = new ReplayBead(ac);
		
		// Queue midi messages!
		for (MidiMessageAndTimeStamp mmts : midiData.getMidiMessageAndTimestampList()) {
			bead.send(mmts.msg, mmts.timeStamp);
		}
		
		// Hook into AudioContext
		ac.invokeBeforeEveryFrame(bead);
	}

	@Override
	public void setRecipient(Bead recipient) {
		bead.addMessageListener(recipient);
	}

	@Override
	public void start() {
		// Nothing to do (?)
	}

	@Override
	public void stop() {
		// Nothing to do (?)
	}
}

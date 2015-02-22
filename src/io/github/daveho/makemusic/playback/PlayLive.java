package io.github.daveho.makemusic.playback;

import io.github.daveho.gervill4beads.CaptureMidiMessages;
import io.github.daveho.gervill4beads.Midi;
import io.github.daveho.gervill4beads.ReceivedMidiMessageSource;
import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.MMPlayback;
import io.github.daveho.makemusic.data.PlayLiveData;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

@MMPlayback(dataClass=PlayLiveData.class)
public class PlayLive implements IMessageGenerator {
	private PlayLiveData data;
	private ReceivedMidiMessageSource midiSource;
	private MidiDevice device;

	@Override
	public Class<? extends IMMData> getDataType() {
		return PlayLiveData.class;
	}

	@Override
	public void setData(IMMData data) {
		if (!(data instanceof PlayLiveData)) {
			throw new IllegalArgumentException("Can't create PlayLiveMessageGenerator from " + data.getClass().getSimpleName());
		}
		this.data = (PlayLiveData) data;
	}

	@Override
	public void setAudioContext(AudioContext ac) {
		midiSource = new ReceivedMidiMessageSource(ac);
		try {
			this.device = CaptureMidiMessages.getMidiInput(midiSource);
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException("Could not capture midi messages", e);
		}
	}

	@Override
	public void setRecipient(Bead recipient) {
		midiSource.addMessageListener(recipient);
	}

	@Override
	public void start() {
		if (data.hasParam("patch")) {
			int patch = data.getParamAsInt("patch");
			midiSource.send(Midi.createShortMessage(ShortMessage.PROGRAM_CHANGE, patch), 0L);
		}
	}

	@Override
	public void stop() {
		device.close();
	}
}

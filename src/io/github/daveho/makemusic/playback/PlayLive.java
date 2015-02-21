package io.github.daveho.makemusic.playback;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

import io.github.daveho.gervill4beads.CaptureMidiMessages;
import io.github.daveho.gervill4beads.Midi;
import io.github.daveho.gervill4beads.ReceivedMidiMessageSource;
import io.github.daveho.makemusic.data.MMData;
import io.github.daveho.makemusic.data.PlayLiveData;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

public class PlayLive implements MessageGenerator {
	private PlayLiveData data;
	private ReceivedMidiMessageSource midiSource;
	private MidiDevice device;

	@Override
	public Class<? extends MMData> getDataType() {
		return PlayLiveData.class;
	}

	@Override
	public void setData(MMData data) {
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
		if (data.hasProperty("patch")) {
			int patch = data.getPropertyAsInt("patch");
			midiSource.send(Midi.createShortMessage(ShortMessage.PROGRAM_CHANGE, patch), 0L);
		}
	}

	@Override
	public void stop() {
		device.close();
	}

	@Override
	public PlayLive clone() {
		try {
			return (PlayLive) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException("Should not happen", e);
		}
	}

}

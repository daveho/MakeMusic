package io.github.daveho.makemusic.playback;

import io.github.daveho.gervill4beads.GervillUGen;
import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.ISynth;
import io.github.daveho.makemusic.MMPlayback;
import io.github.daveho.makemusic.data.GervillData;

import java.util.Collections;

import javax.sound.midi.MidiUnavailableException;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.UGen;

@MMPlayback(dataClass=GervillData.class)
public class GervillSynth implements ISynth {
	@SuppressWarnings("unused")
	private GervillData data;
	private GervillUGen ugen;

	@Override
	public Class<? extends IMMData> getDataType() {
		return GervillData.class;
	}

	@Override
	public void setData(IMMData data) {
		if (!(data instanceof GervillData)) {
			throw new IllegalStateException("Can't initialize GervillSynth from " + data.getClass().getSimpleName());
		}
		this.data = (GervillData) data;
	}

	@Override
	public void init(AudioContext ac) {
		try {
			this.ugen = new GervillUGen(ac, Collections.<String, Object>emptyMap());
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException("Couldn't create GervillUGen", e);
		}
	}
	
	@Override
	public void stop() {
		// FIXME: do we need to do anything here?
	}
	
	public UGen getUGen() {
		if (ugen == null) {
			throw new IllegalStateException("Not initialized");
		}
		return ugen;
	}
}

package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.IMMPlayback;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.UGen;

public interface ISynth extends IMMPlayback {
	public void init(AudioContext ac);
	
	public UGen getUGen();

	public void stop();
}

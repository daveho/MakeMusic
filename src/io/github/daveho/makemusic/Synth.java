package io.github.daveho.makemusic;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.UGen;

public interface Synth extends MMPlayback {
	public void init(AudioContext ac);
	
	public UGen getUGen();
	
	public Synth clone();
}

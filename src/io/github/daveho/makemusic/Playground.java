package io.github.daveho.makemusic;

import javax.sound.midi.MidiUnavailableException;

import net.beadsproject.beads.core.AudioContext;

public class Playground {
	public Playground() {
	}
	
	public void start() {
		AudioContext ac = new AudioContext();
		
		// FIXME: hard-coded stuff
		CompositionData compositionData = new CompositionData();
		
		TrackData td = new TrackData();
		
		MetronomeData md = new MetronomeData();
		td.setMessageGeneratorData(md);
		
		GervillData gd = new GervillData();
		td.setSynthData(gd);
		
		EffectsChainData ecd = new EffectsChainData();
		td.setEffectsChainData(ecd);
		
		compositionData.addTrackData(td);
		
		CompositionPlayer player = new CompositionPlayer(compositionData);
		player.play(ac);
	}
	
	public static void main(String[] args) throws MidiUnavailableException {
		Playground playground = new Playground();
		playground.start();
	}
}

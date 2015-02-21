package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.EffectsChainData;
import io.github.daveho.makemusic.data.GervillData;
import io.github.daveho.makemusic.data.MetronomeData;
import io.github.daveho.makemusic.data.PlayLiveData;
import io.github.daveho.makemusic.data.TrackData;
import io.github.daveho.makemusic.playback.CompositionPlayer;

import javax.sound.midi.MidiUnavailableException;

import net.beadsproject.beads.core.AudioContext;

public class Playground {
	public Playground() {
	}
	
	public void start() {
		AudioContext ac = new AudioContext();
		
		// FIXME: hard-coded stuff
		CompositionData compositionData = new CompositionData();
		
		addMetronomeTrack(compositionData);
		addPlayLiveTrack(compositionData);
		
		CompositionPlayer player = new CompositionPlayer(compositionData);
		player.play(ac);
	}

	private void addMetronomeTrack(CompositionData compositionData) {
		TrackData td = new TrackData();
		
		MetronomeData md = new MetronomeData();
		td.setMessageGeneratorData(md);
		
		GervillData gd = new GervillData();
		td.setSynthData(gd);
		
		EffectsChainData ecd = new EffectsChainData();
		td.setEffectsChainData(ecd);
		
		compositionData.addTrackData(td);
	}
	
	public void addPlayLiveTrack(CompositionData compositionData) {
		TrackData td = new TrackData();
		
		PlayLiveData plmgd = new PlayLiveData();
		plmgd.setProperty("patch", 54);
		td.setMessageGeneratorData(plmgd);
		
		GervillData gd = new GervillData();
		td.setSynthData(gd);
		
		EffectsChainData ecd = new EffectsChainData();
		td.setEffectsChainData(ecd);
		
		compositionData.addTrackData(td);
	}
	
	public static void main(String[] args) throws MidiUnavailableException {
		Playground playground = new Playground();
		playground.start();
	}
}

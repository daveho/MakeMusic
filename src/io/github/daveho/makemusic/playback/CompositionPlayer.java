package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.IMessageGenerator;
import io.github.daveho.makemusic.IMidiMessageInterceptor;
import io.github.daveho.makemusic.IMidiMessageInterceptorData;
import io.github.daveho.makemusic.ISynth;
import io.github.daveho.makemusic.Registry;
import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.TrackData;

import java.util.ArrayList;
import java.util.List;

import net.beadsproject.beads.core.AudioContext;

public class CompositionPlayer {
	private AudioContext ac;
	private CompositionData data;
	private List<Track> tracks;

	public CompositionPlayer(CompositionData data) {
		this.data = data;
		this.tracks = new ArrayList<>();
	}
	
	public void start(AudioContext ac) {
//		System.out.println("Start CompositionPlayer");
		
		this.ac = ac;
		
		for (TrackData td : data.getTrackDataList()) {
			Track t = new Track();

			// Create MessageGenerator
			IMessageGenerator mg = Registry.getInstance().createMessageGenerator(td.getMessageGeneratorData());
			mg.setData(td.getMessageGeneratorData());
			t.setMessageGenerator(mg);
			
			// Create midi message interceptors
			for (IMidiMessageInterceptorData mmid : td.getMidiMessageInterceptorDataList()) {
				IMidiMessageInterceptor interceptor = Registry.getInstance().createMidiInterceptor(mmid);
				t.addMidiMessageInterceptor(interceptor);
			}
			
			// Create Synth
			ISynth synth = Registry.getInstance().createSynth(td.getSynthData());
			synth.setData(td.getSynthData());
			t.setSynth(synth);

			// Create EffectsChain
			EffectsChain effects = new EffectsChain(ac);
			effects.setData(td.getEffectsChainData());
			t.setEffectsChain(effects);
			
			tracks.add(t);
		}
		
		ac.start();
		
		for (Track t : tracks) {
			t.start(ac, data);
		}
	}

	public void stop() {
		for (Track t : tracks) {
			t.stop();
		}
		ac.stop();
	}
}

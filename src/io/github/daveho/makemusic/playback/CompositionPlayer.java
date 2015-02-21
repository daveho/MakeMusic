package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.Registry;
import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.TrackData;

import java.util.ArrayList;
import java.util.List;

import net.beadsproject.beads.core.AudioContext;

public class CompositionPlayer {
	private CompositionData data;

	public CompositionPlayer(CompositionData data) {
		this.data = data;
	}
	
	public void play(AudioContext ac) {
		List<MessageGenerator> messageGenerators = new ArrayList<MessageGenerator>();
		
		for (TrackData td : data.getTrackDataList()) {
			// Create MessageGenerator
			MessageGenerator mg = Registry.getInstance().createMessageGenerator(td.getMessageGeneratorData());
			mg.setData(td.getMessageGeneratorData());
			
			// Create Synth
			Synth synth = Registry.getInstance().createSynth(td.getSynthData());
			synth.setData(td.getSynthData());
			EffectsChain effects = new EffectsChain(ac);
			effects.setData(td.getEffectsChainData());
			
			// Initialize the Synth
			synth.init(ac);
			
			// Feed MidiEvents from the MessageGenerator to the Synth
			mg.setAudioContext(ac);
			mg.setRecipient(synth.getUGen());
			messageGenerators.add(mg);
			
			// Connect the Synth's output to the EffectsChain's input
			effects.getIn().addInput(synth.getUGen());
			
			// Connect the EffectsChain's output to the AudioContext's output
			ac.out.addInput(effects.getOut());
		}
		
		ac.start();
		
		for (MessageGenerator mg : messageGenerators) {
			mg.start();
		}
	}
}

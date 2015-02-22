package io.github.daveho.makemusic.playback;

import net.beadsproject.beads.core.AudioContext;

public class Track {
	private IMessageGenerator messageGenerator;
	private ISynth synth;
	private EffectsChain effectsChain;
	
	public Track() {
		
	}
	
	public void setMessageGenerator(IMessageGenerator messageGenerator) {
		this.messageGenerator = messageGenerator;
	}
	
	public void setSynth(ISynth synth) {
		this.synth = synth;
	}
	
	public void setEffectsChain(EffectsChain effectsChain) {
		this.effectsChain = effectsChain;
	}

	public void start(AudioContext ac) {
//		System.out.println("Start track");
		
		// Initialize the Synth
		synth.init(ac);

		// Feed MidiEvents from the MessageGenerator to the Synth
		messageGenerator.setAudioContext(ac);
		messageGenerator.setRecipient(synth.getUGen());
		
		// Connect the Synth's output to the EffectsChain's input
		effectsChain.getIn().addInput(synth.getUGen());
		
		// Connect the EffectsChain's output to the AudioContext's output
		ac.out.addInput(effectsChain.getOut());
		
		// Tell the MessageGenerator to start
		messageGenerator.start();
	}
	
	public void stop() {
		messageGenerator.stop();
		synth.stop();
	}
}

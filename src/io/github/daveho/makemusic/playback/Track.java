package io.github.daveho.makemusic.playback;

public class Track {
	private MessageGenerator messageGenerator;
	private Synth synth;
	private EffectsChain effectsChain;
	
	public Track() {
		
	}
	
	public void setMessageGenerator(MessageGenerator messageGenerator) {
		this.messageGenerator = messageGenerator;
	}
	
	public void setSynth(Synth synth) {
		this.synth = synth;
	}
	
	public void setEffectsChain(EffectsChain effectsChain) {
		this.effectsChain = effectsChain;
	}
}

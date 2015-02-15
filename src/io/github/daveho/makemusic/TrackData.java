package io.github.daveho.makemusic;

public class TrackData {
	private MMData messageGeneratorData;
	private MMData synthData;
	private EffectsChainData effectsChainData;
	
	public TrackData() {
		
	}
	
	public void setMessageGeneratorData(MMData messageGeneratorData) {
		this.messageGeneratorData = messageGeneratorData;
	}
	
	public MMData getMessageGeneratorData() {
		return messageGeneratorData;
	}
	
	public void setSynthData(MMData synthData) {
		this.synthData = synthData;
	}
	
	public MMData getSynthData() {
		return synthData;
	}
	
	public void setEffectsChainData(EffectsChainData effectsChainData) {
		this.effectsChainData = effectsChainData;
	}
	
	public EffectsChainData getEffectsChainData() {
		return effectsChainData;
	}
}

package io.github.daveho.makemusic.data;

import io.github.daveho.makemusic.IMMData;

// FIXME: mark with @MMData annotation?
public class TrackData {
	private IMMData messageGeneratorData;
	private IMMData synthData;
	private EffectsChainData effectsChainData;
	
	public TrackData() {
		
	}
	
	public void setMessageGeneratorData(IMMData messageGeneratorData) {
		this.messageGeneratorData = messageGeneratorData;
	}
	
	public IMMData getMessageGeneratorData() {
		return messageGeneratorData;
	}
	
	public void setSynthData(IMMData synthData) {
		this.synthData = synthData;
	}
	
	public IMMData getSynthData() {
		return synthData;
	}
	
	public void setEffectsChainData(EffectsChainData effectsChainData) {
		this.effectsChainData = effectsChainData;
	}
	
	public EffectsChainData getEffectsChainData() {
		return effectsChainData;
	}
}

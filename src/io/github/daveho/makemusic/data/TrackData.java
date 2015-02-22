package io.github.daveho.makemusic.data;

import java.util.ArrayList;
import java.util.List;

import io.github.daveho.makemusic.IMMData;

// FIXME: mark with @MMData annotation?
public class TrackData {
	private IMMData messageGeneratorData;
	private List<IMMData> midiMessageInterceptorDataList;
	private IMMData synthData;
	private EffectsChainData effectsChainData;
	
	public TrackData() {
		this.midiMessageInterceptorDataList = new ArrayList<>();
	}
	
	public void setMessageGeneratorData(IMMData messageGeneratorData) {
		this.messageGeneratorData = messageGeneratorData;
	}
	
	public IMMData getMessageGeneratorData() {
		return messageGeneratorData;
	}
	
	public void addMessageInterceptorData(IMMData data) {
		midiMessageInterceptorDataList.add(data);
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

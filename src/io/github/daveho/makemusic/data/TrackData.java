package io.github.daveho.makemusic.data;

import java.util.ArrayList;
import java.util.List;

import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.IMessageGeneratorData;
import io.github.daveho.makemusic.ISynthData;

// FIXME: mark with @MMData annotation?
public class TrackData {
	private IMessageGeneratorData messageGeneratorData;
	private List<IMMData> midiMessageInterceptorDataList;
	private ISynthData synthData;
	private EffectsChainData effectsChainData;
	
	public TrackData() {
		this.midiMessageInterceptorDataList = new ArrayList<>();
	}
	
	public void setMessageGeneratorData(IMessageGeneratorData messageGeneratorData) {
		this.messageGeneratorData = messageGeneratorData;
	}
	
	public IMessageGeneratorData getMessageGeneratorData() {
		return messageGeneratorData;
	}
	
	public void addMessageInterceptorData(IMMData data) {
		midiMessageInterceptorDataList.add(data);
	}
	
	public void setSynthData(ISynthData synthData) {
		this.synthData = synthData;
	}
	
	public ISynthData getSynthData() {
		return synthData;
	}
	
	public void setEffectsChainData(EffectsChainData effectsChainData) {
		this.effectsChainData = effectsChainData;
	}
	
	public EffectsChainData getEffectsChainData() {
		return effectsChainData;
	}
}

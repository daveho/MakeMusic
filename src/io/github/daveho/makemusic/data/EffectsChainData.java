package io.github.daveho.makemusic.data;

public class EffectsChainData extends AbstractMMData {
	@Override
	public String getCode() {
		return "effects";
	}
	
	@Override
	public EffectsChainData clone() {
		return (EffectsChainData) super.clone();
	}
}

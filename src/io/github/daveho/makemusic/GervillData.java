package io.github.daveho.makemusic;

public class GervillData extends AbstractMMData {
	// Right now, we don't have anything configurable for GervillSynth
	@Override
	public String getCode() {
		return "gervill";
	}
	
	@Override
	public GervillData clone() {
		return (GervillData) super.clone();
	}
}

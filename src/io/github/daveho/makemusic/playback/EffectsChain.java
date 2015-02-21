package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.data.EffectsChainData;
import io.github.daveho.makemusic.data.MMData;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.UGen;
import net.beadsproject.beads.ugens.Gain;

public class EffectsChain implements MMPlayback {
	private EffectsChainData data;
	private UGen in;
	private Gain out;
	
	public EffectsChain(AudioContext ac) {
		this.out = new Gain(ac, 2, 1.0f);
		this.in = out;
	}
	
	public UGen getIn() {
		return in;
	}
	
	public Gain getOut() {
		return out;
	}

	@Override
	public Class<? extends MMData> getDataType() {
		return EffectsChainData.class;
	}

	@Override
	public void setData(MMData data) {
		if (!(data instanceof EffectsChainData)) {
			throw new IllegalArgumentException("Can't initialize EffectsChain with " + data.getClass().getSimpleName());
		}
		this.data = (EffectsChainData) data;
	}

	@Override
	public EffectsChain clone() {
		try {
			return (EffectsChain) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException("Should not happen", e);
		}
	}
}

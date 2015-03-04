package io.github.daveho.makemusic.playback;

import io.github.daveho.makemusic.IMMData;
import io.github.daveho.makemusic.IMMPlayback;
import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.EffectsChainData;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.UGen;
import net.beadsproject.beads.ugens.Gain;

public class EffectsChain implements IMMPlayback {
	@SuppressWarnings("unused")
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
	public Class<? extends IMMData> getDataType() {
		return EffectsChainData.class;
	}

	@Override
	public void setData(IMMData data) {
		if (!(data instanceof EffectsChainData)) {
			throw new IllegalArgumentException("Can't initialize EffectsChain with " + data.getClass().getSimpleName());
		}
		this.data = (EffectsChainData) data;
	}
	
	@Override
	public void onStartPlayback(CompositionData compositionData) {
		// Nothing to do currently
	}
}

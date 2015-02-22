package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.IMMData;
import io.github.daveho.makemusic.playback.GervillSynth;
import io.github.daveho.makemusic.playback.MessageGenerator;
import io.github.daveho.makemusic.playback.Metronome;
import io.github.daveho.makemusic.playback.PlayLive;
import io.github.daveho.makemusic.playback.Synth;

import java.util.ArrayList;
import java.util.List;

public class Registry {
	private static final Registry instance = new Registry();
	
	public static Registry getInstance() {
		return instance;
	}
	
	public List<MessageGenerator> messageGenerators;
	public List<Synth> synths;
	
	private Registry() {
		messageGenerators = new ArrayList<MessageGenerator>();
		synths = new ArrayList<Synth>();
		
		// TODO: use reflection/annotations to find these automatically
		messageGenerators.add(new Metronome());
		messageGenerators.add(new PlayLive());
		synths.add(new GervillSynth());
	}
	
	/**
	 * Create a {@link MessageGenerator} using given {@link IMMData}.
	 * 
	 * @param data the {@link IMMData}
	 * @return a {@link MessageGenerator}
	 */
	public MessageGenerator createMessageGenerator(IMMData data) {
		for (MessageGenerator prototype : messageGenerators) {
			if (prototype.getDataType() == data.getClass()) {
				// Found a matching prototype - clone it
				return prototype.clone();
			}
		}
		throw new IllegalArgumentException("No MessageGenerator found for " + data.getClass().getSimpleName());
	}

	/**
	 * Create a {@link Synth} using given {@link IMMData}.
	 * 
	 * @param data (the {@link IMMData})
	 * @return a {@link Synth}
	 */
	public Synth createSynth(IMMData data) {
		for (Synth prototype : synths) {
			if (prototype.getDataType() == data.getClass()) {
				// Found a matching prototype: clone it
				return prototype.clone();
			}
		}
		throw new IllegalArgumentException("No Synth found for " + data.getClass().getSimpleName());
	}
}

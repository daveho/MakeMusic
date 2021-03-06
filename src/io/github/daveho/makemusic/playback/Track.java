package io.github.daveho.makemusic.playback;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import io.github.daveho.makemusic.IMessageGenerator;
import io.github.daveho.makemusic.IMidiMessageInterceptor;
import io.github.daveho.makemusic.ISynth;
import io.github.daveho.makemusic.data.CompositionData;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

/**
 * A {@Track} is an {@link IMessageGenerator} feeding into an
 * {@link ISynth} (optionally preceeded by one or more
 * {@link AbstractMidiMessageInterceptor}s), the synth's output being
 * processed by an {@link EffectsChain} and then fed into
 * the AudioContext output.
 * 
 * @author David Hovemeyer
 */
public class Track {
	private IMessageGenerator messageGenerator;
	private ISynth synth;
	private EffectsChain effectsChain;
	private List<IMidiMessageInterceptor> interceptorList;
	
	private Bead midiIn;
	
	public Track() {
		this.interceptorList = new ArrayList<>();
	}
	
	public void setMessageGenerator(IMessageGenerator messageGenerator) {
		this.messageGenerator = messageGenerator;
	}
	
	public void setSynth(ISynth synth) {
		this.synth = synth;
	}
	
	public void setEffectsChain(EffectsChain effectsChain) {
		this.effectsChain = effectsChain;
	}
	
	public void addMidiMessageInterceptor(IMidiMessageInterceptor interceptor) {
		this.interceptorList.add(interceptor);
	}

	public void start(AudioContext ac, CompositionData compositionData) {
//		System.out.println("Start track");
		
		// Invoke onStartPlayback methods for all playback objects
		messageGenerator.onStartPlayback(compositionData);
		//for ()
		
		// Initialize the Synth
		synth.init(ac);
		
		// The default midi input is the synth's UGen
		midiIn = synth.getUGen();
		
		// Add any MidiMessageInterceptors
		for (ListIterator<IMidiMessageInterceptor> i = interceptorList.listIterator(interceptorList.size()); i.hasPrevious(); ) {
			IMidiMessageInterceptor interceptor = i.previous();
			interceptor.setDownstream(midiIn);
			midiIn = interceptor.asBead();
		}

		// Feed MidiEvents from the MessageGenerator to the midi input Bead
		// (which defaults to the Synth's UGen)
		messageGenerator.setAudioContext(ac);
		messageGenerator.setRecipient(midiIn);
		
		// Connect the Synth's output to the EffectsChain's input
		effectsChain.getIn().addInput(synth.getUGen());
		
		// Connect the EffectsChain's output to the AudioContext's output
		ac.out.addInput(effectsChain.getOut());
		
		// Tell the MessageGenerator to start
		messageGenerator.start();
	}
	
	public void stop() {
		messageGenerator.stop();
		synth.stop();
	}
}

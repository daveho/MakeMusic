package io.github.daveho.makemusic;

import java.util.Collections;

import javax.sound.midi.MidiUnavailableException;

import io.github.daveho.gervill4beads.CaptureMidiMessages;
import io.github.daveho.gervill4beads.GervillUGen;
import io.github.daveho.gervill4beads.MidiMessageSource;
import net.beadsproject.beads.core.AudioContext;

public class Playground {
	protected AudioContext ac;
	protected MidiMessageSource midiSource;
	protected GervillUGen gervill;
	
	public Playground() {
		ac = new AudioContext();
	}
	
	public void start() throws MidiUnavailableException {
		midiSource = new MidiMessageSource(ac);
		CaptureMidiMessages.getMidiInput(midiSource);
		
		gervill = new GervillUGen(ac, Collections.emptyMap());
		midiSource.addMessageListener(gervill);
		
		ac.out.addInput(gervill);
		
		ac.start();
	}
	
	public static void main(String[] args) throws MidiUnavailableException {
		Playground playground = new Playground();
		playground.start();
	}
}

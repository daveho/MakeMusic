package io.github.daveho.makemusic.playback;

import io.github.daveho.gervill4beads.MidiMessageSource;
import io.github.daveho.makemusic.data.IMMData;
import io.github.daveho.makemusic.data.MetronomeData;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;

public class Metronome implements MessageGenerator {
	private class MetronomeBead extends Bead implements MidiMessageSource {
		private int count;
		private MidiMessage msg;
		private long timeStamp;
		
		@Override
		protected void messageReceived(Bead message) {
			// See if a metronome events (note on and note off) should be fired in the
			// next audio frame.  Note that we don't support generating more than
			// one metronome event per audio frame.
			double time = ac.getTime();
			long nextTimeMs = count * data.getIntervalMs();
			if (nextTimeMs < time + frameTimeMs) {
				if (active && recipient != null) {
					try {
						//System.out.println("Tick!");
						msg = new ShortMessage(ShortMessage.NOTE_ON + 9, data.getNote(), data.getVelocity());
						timeStamp = nextTimeMs * 1000L;
						recipient.message(this);
						msg = new ShortMessage(ShortMessage.NOTE_OFF + 9, data.getNote(), 0);
						timeStamp += 100000L; // 100,000 us == 100 ms
						recipient.message(this);
					} catch (InvalidMidiDataException e) {
						throw new IllegalStateException("This should not happen", e);
					}
				}
				count++;
			}
		}
		
		@Override
		public MidiMessage getMessage() {
			return msg;
		}
		
		@Override
		public long getTimeStamp() {
			return timeStamp;
		}
	}
	
	private AudioContext ac;
	private double frameTimeMs;
	private Bead recipient;
	private volatile boolean active;
	private MetronomeData data;
	
	public Metronome() {
	}
	
	@Override
	public Class<? extends IMMData> getDataType() {
		return MetronomeData.class;
	}
	
	@Override
	public void setData(IMMData data) {
		if (!(data instanceof MetronomeData)) {
			throw new IllegalStateException("Can't initialize Metronome with " + data.getClass().getSimpleName());
		}
		this.data = (MetronomeData) data;
	}
	
	@Override
	public Metronome clone() {
		try {
			return (Metronome) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException("Should not happen", e);
		}
	}

	@Override
	public void setAudioContext(AudioContext ac) {
		this.ac = ac;
		this.frameTimeMs = ac.samplesToMs(ac.getBufferSize()); 
		MetronomeBead metronomeBead = new MetronomeBead();
		ac.invokeBeforeEveryFrame(metronomeBead);
	}
	
	@Override
	public void setRecipient(Bead recipient) {
		this.recipient = recipient;
	}

	@Override
	public void start() {
		active = true;
	}

	@Override
	public void stop() {
		active = false;
	}
}

package io.github.daveho.makemusic.data;

public class MetronomeData extends AbstractMMData {
	/**
	 * Default interval is 120 beats per minute.
	 */
	public static long DEFAULT_INTERVAL_MS = (60*1000) / 120;
	
	/**
	 * Default note to send.  This should correspond to the midi
	 * channel 10 percussion sounds in the GM1 sound set.
	 * The default is 40 (electric snare).
	 */
	public static int DEFAULT_NOTE = 37;
	
	/**
	 * Default velocity.
	 */
	public static int DEFAULT_VELOCITY = 96;

	public MetronomeData() {
		setParam("intervalMs", DEFAULT_INTERVAL_MS);
		setParam("note", DEFAULT_NOTE);
		setParam("velocity", DEFAULT_VELOCITY);
	}
	
	@Override
	public String getCode() {
		return "metronome";
	}
	
	public long getIntervalMs() {
		return getParamAsLong("intervalMs");
	}

	public int getNote() {
		return getParamAsInt("note");
	}

	public int getVelocity() {
		return getParamAsInt("velocity");
	}
	
	@Override
	public MetronomeData clone() {
		return (MetronomeData) super.clone();
	}
}

package io.github.daveho.makemusic.data;

import io.github.daveho.makemusic.MMData;

import java.util.ArrayList;
import java.util.List;

@MMData(code="cd")
public class CompositionData {
	private List<TrackData> trackDataList;
	private List<MidiData> midiDataList;
	
	public CompositionData() {
		trackDataList = new ArrayList<>();
		midiDataList = new ArrayList<>();
	}
	
	public void addTrackData(TrackData trackData) {
		trackDataList.add(trackData);
	}
	
	public List<TrackData> getTrackDataList() {
		return trackDataList;
	}

	public int getNumTracks() {
		return trackDataList.size();
	}
	
	public void addMidiData(MidiData midiData) {
		midiDataList.add(midiData);
	}
	
	public List<MidiData> getMidiDataList() {
		return midiDataList;
	}
	
	public int getNumMidiData() {
		return midiDataList.size();
	}
}

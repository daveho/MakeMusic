package io.github.daveho.makemusic.data;

import java.util.ArrayList;
import java.util.List;

public class CompositionData {
	private List<TrackData> trackDataList;
	
	public CompositionData() {
		trackDataList = new ArrayList<TrackData>();
	}
	
	public void addTrackData(TrackData trackData) {
		trackDataList.add(trackData);
	}
	
	public List<TrackData> getTrackDataList() {
		return trackDataList;
	}
}

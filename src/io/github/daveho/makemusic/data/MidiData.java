package io.github.daveho.makemusic.data;

import io.github.daveho.gervill4beads.MidiMessageAndTimeStamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to encapsulate saved midi data (list of {@link MidiMessageAndTimeStamp}).
 * Each has a path name indicating where the data should be stored
 * in the composition directory structure.
 * 
 * @author David Hovemeyer
 */
public class MidiData {
	private String path;
	private List<MidiMessageAndTimeStamp> msgAndTsList;
	
	public MidiData() {
		path = null;
		msgAndTsList = new ArrayList<MidiMessageAndTimeStamp>();
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		if (!hasPath()) {
			throw new IllegalStateException("No path set!");
		}
		return path;
	}
	
	public boolean hasPath() {
		return path != null;
	}
	
	public void add(MidiMessageAndTimeStamp msgAndTs) {
		msgAndTsList.add(msgAndTs);
	}
	
	public List<MidiMessageAndTimeStamp> getMidiMessageAndTimestampList() {
		return Collections.unmodifiableList(msgAndTsList);
	}
}

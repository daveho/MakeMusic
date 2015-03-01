package io.github.daveho.makemusic;

import java.io.IOException;
import java.io.Writer;

import io.github.daveho.makemusic.data.CompositionData;
import io.github.daveho.makemusic.data.TrackData;

public class CompositionDataWriter {
	public void write(CompositionData compositionData, ICompositionDataSink dataSink) throws IOException {
		try (Writer w = dataSink.write("/composition.txt")) {
			CompositionFileWriter cfw = new CompositionFileWriter();
			cfw.write(compositionData, w);
		}
		
		int trackNum = 0;
		for (TrackData td : compositionData.getTrackDataList()) {
			try (Writer w = dataSink.write(String.format("/track%02d.txt", trackNum))) {
				TrackFileWriter tfw = new TrackFileWriter();
				tfw.write(w, td);
			}
			trackNum++;
		}
	}
}

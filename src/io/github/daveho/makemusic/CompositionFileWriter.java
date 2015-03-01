package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.CompositionData;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class CompositionFileWriter {
	public void write(CompositionData compositionData, Writer w) throws IOException {
		PrintWriter pw = new PrintWriter(w);
		
		// TODO: write composition parameters
		
		// Tracks.
		// Note that we just write the paths of the (yet to be written)
		// track files.  We don't write any actual track data.
		for (int trackNum = 0; trackNum < compositionData.getNumTracks(); trackNum++) {
			pw.printf("track /track%02d.txt\n", trackNum);
		}
		
		pw.flush();
	}
}

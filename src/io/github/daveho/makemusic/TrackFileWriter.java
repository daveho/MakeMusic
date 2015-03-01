package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.TrackData;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class TrackFileWriter {
	public void write(Writer w, TrackData trackData) throws IOException {
		PrintWriter pw = new PrintWriter(w);
		
		IMessageGeneratorData msgGenData = trackData.getMessageGeneratorData();
		if (msgGenData != null) {
			pw.printf("msggen %s%s\n", msgGenData.getCode(), ParamUtils.asString(msgGenData));
		}
		
		ISynthData synthData = trackData.getSynthData();
		if (synthData != null) {
			pw.printf("synth %s%s\n", synthData.getCode(), ParamUtils.asString(synthData));
		}
		
		pw.flush();
	}
}

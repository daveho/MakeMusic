package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.TrackData;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class TrackFileWriter {
	public void write(Writer w, TrackData trackData) throws IOException {
		PrintWriter pw = new PrintWriter(w);
		
		// Write message generator data
		IMessageGeneratorData msgGenData = trackData.getMessageGeneratorData();
		if (msgGenData != null) {
			pw.printf("msggen %s%s\n", msgGenData.getCode(), ParamUtils.asString(msgGenData));
		}
		
		// Write midi message interceptor data
		for (IMidiMessageInterceptorData midiMessageInteceptorData : trackData.getMidiMessageInterceptorDataList()) {
			pw.printf("interceptor %s%s\n", midiMessageInteceptorData.getCode(), ParamUtils.asString(midiMessageInteceptorData));
		}
		
		// Write synth data
		ISynthData synthData = trackData.getSynthData();
		if (synthData != null) {
			pw.printf("synth %s%s\n", synthData.getCode(), ParamUtils.asString(synthData));
		}
		
		// TODO: write effects chain data
		
		pw.flush();
	}
}

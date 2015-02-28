package io.github.daveho.makemusic;

import io.github.daveho.makemusic.data.TrackData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class TrackDataReader {
	private TrackData trackData;
	
	public TrackDataReader() {
		trackData = new TrackData();
	}
	
	public void read(Reader r_) throws IOException {
		BufferedReader r = new BufferedReader(r_);
		
		int lineNum = 0;
		
		try {
			boolean done = false;
			while (!done) {
				String line = r.readLine();
				if (line == null) {
					done = true;
				}
				lineNum++;
				line = line.trim();
				if (!line.equals("")) {
					List<String> tokens = Arrays.asList(line.split("\\s+"));
					if (tokens.size() < 2) {
						throw new IOException("Invalid syntax");
					}
					// Get command and code (code is for the data object class)
					String cmd = tokens.get(0);
					String code = tokens.get(1);
					// Remaining tokens are parameter name/value pairs
					tokens = tokens.subList(2, tokens.size());
					if (cmd.equals("msggen")) {
						// Message generator data
						IMessageGeneratorData data = Registry.getInstance().createMessageGeneratorData(code);
						setParams(data, tokens);
						trackData.setMessageGeneratorData(data);
					} else if (cmd.equals("synth")) {
						// Synth data
						ISynthData data = Registry.getInstance().createSynthData(code);
						setParams(data, tokens);
						trackData.setSynthData(data);
					} else {
						throw new IOException("Unknown command \"" + cmd + "\"");
					}
				}
			}
		} catch (IOException e) {
			throw new IOException("Error at line " + lineNum + ": " + e.getMessage(), e);
		} catch (Throwable e) {
			throw new IOException("Error at line " + lineNum + ": " + e.getMessage(), e);
		}
	}

	private void setParams(IMMData data, List<String> tokens) {
		for (String pair : tokens) {
			int eq = pair.indexOf('=');
			if (eq < 0) {
				throw new RuntimeException("Invalid param/value pair: " + pair);
			}
			String paramName = pair.substring(0, eq);
			Double value = Double.valueOf(pair.substring(eq+1));
			data.setParam(paramName, value);
		}
	}
}

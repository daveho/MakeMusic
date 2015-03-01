package io.github.daveho.makemusic;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CompositionFileReader {
	public interface Events {
		public void onTrack(String trackPath) throws Throwable;
	}
	
	public void read(Reader r, Events callback) throws IOException {
		TokenizingReader tr = new TokenizingReader();
		tr.read(r, new TokenizingReader.Events() {
			@Override
			public void onNonEmptyLine(List<String> tokens) throws Throwable {
				if (tokens.size() < 2) {
					throw new IOException("Invalid syntax");
				}
				String cmd = tokens.get(0);
				if (cmd.equals("track")) {
					String path = tokens.get(1);
					callback.onTrack(path);
				} else {
					throw new IOException("Unknown command: " + cmd);
				}
			}
		});
	}
}

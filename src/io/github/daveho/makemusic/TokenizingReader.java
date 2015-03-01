package io.github.daveho.makemusic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class TokenizingReader {
	public interface Events {
		public void onNonEmptyLine(List<String> tokens) throws Throwable;
	}

	public void read(Reader r, Events callback) throws IOException {
		BufferedReader br = new BufferedReader(r);
		
		int lineNum = 0;
		
		try {
			boolean done = false;
			while (!done) {
				String line = br.readLine();
				if (line == null) {
					done = true;
				} else {
					lineNum++;
					line = line.trim();
					if (!line.equals("")) {
						List<String> tokens = Arrays.asList(line.split("\\s+"));
						callback.onNonEmptyLine(tokens);
					}
				}
			}
		} catch (IOException e) {
			throw new IOException("Error at line " + lineNum + ": " + e.getMessage(), e);
		} catch (Throwable e) {
			throw new IOException("Error at line " + lineNum + ": " + e.getMessage(), e);
		}
	}
}

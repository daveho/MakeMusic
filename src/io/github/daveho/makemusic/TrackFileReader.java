package io.github.daveho.makemusic;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Reader class for reading track data from a file.
 */
public class TrackFileReader {
	/**
	 * Callback interface for events.
	 */
	public interface Events {
		/**
		 * Called when {@link IMessageGeneratorData} is read.
		 * @param data the {@link IMessageGeneratorData}
		 */
		public void onMessageGeneratorData(IMessageGeneratorData data) throws Throwable;
		/**
		 * Called when {@link ISynthData} is read.
		 * @param data the {@link ISynthData}
		 */
		public void onSynthData(ISynthData data) throws Throwable;
	}
	
	/**
	 * Read a track data file.
	 * The caller is responsible for closing the Reader.
	 * 
	 * @param r a Reader reading from the file
	 * @param callback events callback
	 * @throws IOException
	 */
	public void read(Reader r, Events callback) throws IOException {
		TokenizingReader tr = new TokenizingReader();
		tr.read(r, new TokenizingReader.Events() {
			@Override
			public void onNonEmptyLine(List<String> tokens) throws Throwable {
				// Get command and code (code is for the data object class)
				String cmd = tokens.get(0);
				String code = tokens.get(1);
				// Remaining tokens are parameter name/value pairs
				tokens = tokens.subList(2, tokens.size());
				if (cmd.equals("msggen")) {
					// Message generator data
					IMessageGeneratorData data = Registry.getInstance().createMessageGeneratorData(code);
					ParamUtils.fromTokens(data, tokens);
					callback.onMessageGeneratorData(data);
				} else if (cmd.equals("synth")) {
					// Synth data
					ISynthData data = Registry.getInstance().createSynthData(code);
					ParamUtils.fromTokens(data, tokens);
					callback.onSynthData(data);
				} else {
					throw new IOException("Unknown command \"" + cmd + "\"");
				}
			}
		});
	}
}
